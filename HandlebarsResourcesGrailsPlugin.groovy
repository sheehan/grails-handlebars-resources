class HandlebarsResourcesGrailsPlugin {
    def version = "0.1"
    def grailsVersion = "1.3.5 > *"
    def dependsOn = [resources:'1.0 > *']
    def loadAfter = ['resources']
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]
    def title = "Handlebars Resources Plugin"
    def author = "Matt Sheehan"
    def authorEmail = "mr.sheehan@gmail.com"
    def description = '''\
Brief summary/description of the plugin.
'''
    def documentation = "http://grails.org/plugin/handlebars-resources"
    def license = "APACHE"
    def issueManagement = [ system: "github", url: "https://github.com/sheehan/handlebars-resources/issues" ]
    def scm = [ url: "https://github.com/sheehan/handlebars-resources" ]
}