package org.grails.plugin.handlebars

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.plugins.support.aware.GrailsApplicationAware
import org.grails.plugin.resource.mapper.MapperPhase
import org.grails.plugin.resource.ResourceMeta

/**
 * @author Matt Sheehan
 *
 * Precompile .handlebars template files into .js files.
 */
class HandlebarsResourceMapper implements GrailsApplicationAware {

    GrailsApplication grailsApplication

    def phase = MapperPhase.GENERATION

    static defaultIncludes = ['**/*.handlebars', "**/*.hbs"]

    def map(ResourceMeta resource, config) {

        Precompiler precompiler = new Precompiler()
        File originalFile = resource.processedFile
        File input = getOriginalFileSystemFile(resource.sourceUrl)

        String templateName = calculateTemplateName(resource, config)

        if (resource.sourceUrl) {
            File target = new File(generateCompiledFileFromOriginal(originalFile.absolutePath))

            log.debug "Compiling handlebars file [${originalFile}] into [${target}]"

            try {
                precompiler.precompile input, target, templateName

                resource.processedFile = target
                resource.sourceUrlExtension = 'js'
                resource.contentType = 'text/javascript'
                resource.updateActualUrlFromProcessedFile()
            } catch (e) {
                log.error "error precompiling handlebars file: ${originalFile}", e
            }
        }
    }

    String calculateTemplateName(ResourceMeta resource, config) {
        String pathSeparator = getString(config, 'templatesPathSeparator', '/')
        String root = getString(config, 'templatesRoot')

        String templateName = resource.sourceUrl
        if (root) {
            if (!root.startsWith('/')) {
                root = '/' + root
            }
            if (!root.endsWith('/')) {
                root += '/'
            }
            if (templateName.startsWith(root)) {
                templateName -= root
            }
        }

        int extensionIndex = templateName.lastIndexOf('.')
        templateName = templateName[0..<extensionIndex]

        templateName.split('/').findAll().join(pathSeparator)


    }

    private String getString(Map config, String key, String defaultVal = null) {
        config[key] instanceof String ? config[key] : defaultVal
    }

    private String generateCompiledFileFromOriginal(String original) {
        int index = original.lastIndexOf('.')
        String baseName = original[0..<index]
        String extension = original[index+1..-1]
        "${baseName}_${extension}.js"
    }

    private File getOriginalFileSystemFile(String sourcePath) {
        grailsApplication.parentContext.getResource(sourcePath).file
    }
}
