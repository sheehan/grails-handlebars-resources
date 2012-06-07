/**
 * @author Matt Sheehan
 *
 * Mapping file to precompile .handlebars files into .js files.
 */

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.plugins.support.aware.GrailsApplicationAware
import org.grails.plugin.resource.mapper.MapperPhase

class HandlebarsResourceMapper implements GrailsApplicationAware {

    GrailsApplication grailsApplication

    def phase = MapperPhase.GENERATION

    static defaultIncludes = ['**/*.handlebars']

    def map(resource, config) {
        Precompiler precompiler = new Precompiler()
        File originalFile = resource.processedFile
        File input = getOriginalFileSystemFile(resource.sourceUrl);
        File target = new File(generateCompiledFileFromOriginal(originalFile.absolutePath))

        if (resource.sourceUrl) {
            target = new File(generateCompiledFileFromOriginal(originalFile.absolutePath))

            log.debug "Compiling handlebars file [${originalFile}] into [${target}]"

            try {
                precompiler.precompile input, target

                resource.processedFile = target
                resource.sourceUrlExtension = 'js'
                resource.contentType = 'text/javascript'
                resource.updateActualUrlFromProcessedFile()
            } catch (e) {
                log.error "error precompiling handlebars file: ${originalFile}", e
            }
        }
    }

    private String generateCompiledFileFromOriginal(String original) {
        original.replaceAll(/(?i)\.handlebars/, '_handlebars.js')
    }

    private File getOriginalFileSystemFile(String sourcePath) {
        grailsApplication.parentContext.getResource(sourcePath).file
    }
}
