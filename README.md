# Handlebars.js Resources plug-in for Grails

This plug-in supports using [Handlebars.js](http://handlebarsjs.com/) templates with the Grails [Resources Plugin](http://www.grails.org/plugin/resources).
This plugin includes a resource mapper that will [precompile](http://handlebarsjs.com/precompilation.html) the template files into JavaScript and
make it available for other resource processing, including minification and bundling. The Handlebars.js resource files are also included.

## Installation

    grails install-plugin handlebars-resources

## Usage

### Declaring Resources

    application {
        dependsOn 'handlebars_runtime'
        resource url: 'templates/person.handlebars', attrs: [type: 'js'], bundle:'bundle_application'
        resource url: 'templates/error.handlebars', attrs: [type: 'js'], bundle:'bundle_application'
        resource url:'js/application.js'
    }

#### Settings

*   **dependsOn**: `handlebars` or `handlebars_runtime`. If only using precompiled templates the smaller handlebars_runtime should be used.
*   **url**: location of the handlebars template file.
*   **attrs[type]**: must be `js`.
*   **bundle**: must be set as will not default correctly. To add to default bundle use `bundle_<module name>`.

### Using in the Browser

Template functions are stored in the `Handlebars.templates` object using the template name. If the template name is
`person.show`, then the template function can be accessed from `Handlebars.templates['person.show']`. See the Template Names section for how template names are calculated.

See the [Handlebars.js website](http://handlebarsjs.com/) for more information on using Handlebars template functions.

## Template Names

Template names are based on the resource URL. If the URL is `templates/foo.handlebars`, then the template name will be `templates.foo`.
Note that the `.handlebars` extension is removed.

The default path separator is `.`. If you want to change it, you can specify a value for `templatesPathSeparator` in the configuration. For example,
adding

    grails.resources.mappers.handlebars.templatesPathSeparator = '-'

will change the template name to `templates-foo`.

If you specify a value for `templatesRoot` in the configuration, then that value will be stripped from the template name. For example, adding

    grails.resources.mappers.handlebars.templatesRoot = 'templates'

will change the template name to just `foo`.

## Configuration

All configuration variables should be relative to:

    grails.resources.mappers.handlebars

*   **templatesRoot**: The root folder of the templates relative to `web-app`. This value will be stripped from template paths when calculating the template name. Default is none.
*   **templatesPathSeparator**: The delimiter to use for template names. Default is `.`

## Changelog

#### v0.2

*   **BREAKING** Updated template naming scheme to handle nested templates in a manner similar to the [ember-rails plugin](https://github.com/emberjs/ember-rails)