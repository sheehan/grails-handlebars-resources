import org.grails.plugin.resource.ResourceProcessor
import org.grails.plugin.resource.ResourceTagLib

class HandlebarsResourcesGrailsPlugin {
    def version = "1.0.1"
    def grailsVersion = "1.3.7 > *"
    def dependsOn = [resources: "* > 1.2-RC1"]
    def loadAfter = ['resources']
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]
    def title = "Handlebars Resources Plugin"
    def author = "Matt Sheehan"
    def authorEmail = "mr.sheehan@gmail.com"
    def description = '''\
This plug-in supports using Handlebars.js templates with the Grails Resources Plugin. This plugin includes a resource mapper that will precompile
the template files into JavaScript and make it available for other resource processing, including minification and bundling.
The Handlebars.js resource files are also included.
'''
    def documentation = "https://github.com/sheehan/grails-handlebars-resources/blob/master/README.md"
    def license = "APACHE"
    def issueManagement = [ system: "github", url: "https://github.com/sheehan/grails-handlebars-resources/issues" ]
    def scm = [ url: "https://github.com/sheehan/grails-handlebars-resources" ]

    def doWithSpring = { ->
        ['handlebars', 'hbs'].each { String type ->
            ResourceTagLib.SUPPORTED_TYPES[type] = [type:'text/javascript', writer:'js']
            ResourceProcessor.DEFAULT_MODULE_SETTINGS[type] = [
                disposition: 'defer'
            ]
        }

        def handlebarsConfig = application.config.grails?.resources?.mappers?.handlebars

        handlebarsPrecompiler(org.grails.plugin.handlebars.Precompiler){
            if(handlebarsConfig?.extraKnownHelpers){
                extraKnownHelpers = handlebarsConfig?.extraKnownHelpers
            }
        }

        if (handlebarsConfig?.wrapTemplate instanceof Closure) {
            handlebarsPrecompiler.wrapTemplate = handlebarsConfig?.wrapTemplate
        }

    }
}