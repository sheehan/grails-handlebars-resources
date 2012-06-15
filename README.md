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
`person.show`, then the template function can be accessed from `Handlebars.templates.['person.show']`. See the Template Names section for how template names are calculated.

See the [Handlebars.js website](http://handlebarsjs.com/) for more information on using Handlebars template functions.

## Template Names

Template names are based on the file name and path. The root directory of the templates and the delimiter used can be specified in the configuration.

### Examples

<table>
  <tr>
    <th>templatesRoot</th> <th>templatesPathSeparator</th> <th>File</th> <th>Template Name</th>
  </tr>
  <tr>
    <td>(default)</td> <td>(default)</td> <td>/web-app/templates/foo.handlebars</td> <td>templates.foo</td>
  </tr>
  <tr>
    <td>templates</td> <td>(default)</td> <td>/web-app/templates/foo.handlebars</td> <td>foo</td>
  </tr>
  <tr>
    <td>templates</td> <td>(default)</td> <td>/web-app/templates/person/show.handlebars</td> <td>person.show</td>
  </tr>
  <tr>
    <td>templates</td> <td>-</td> <td>/web-app/templates/person/show.handlebars</td> <td>person-show</td>
  </tr>
</table>

## Configuration

All configuration variables should be relative to:

    grails.resources.mappers.handlebars

*   **templatesRoot**: The root folder of the templates relative to `web-app`. This value will be stripped from template paths before calculating the template name. Default is `/`
*   **templatesPathSeparator**: The delimiter to use for template names. Default is `.`

## Changelog

#### v0.2

*   **BREAKING** Updated template naming scheme to handle nested templates in a manner similar to the [ember-rails plugin](https://github.com/emberjs/ember-rails)