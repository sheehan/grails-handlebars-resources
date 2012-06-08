# Handlebars.js Resources plug-in for Grails

This plug-in supports using [Handlebars.js](http://handlebarsjs.com/) templates with the Grails [Resources Plugin](http://www.grails.org/plugin/resources).
This plugin includes a resource mapper that will [precompile](http://handlebarsjs.com/precompilation.html) the template files into JavaScript and
make it available for other resource processing, including minification and bundling. The Handlebars.js resource files are also included.

## Installation

    grails install-plugin handlebars-resources

## Usage

    application {
        dependsOn 'handlebars_runtime'
        resource url: 'templates/person.handlebars', attrs: [type: 'js'], bundle:'bundle_application'
        resource url: 'templates/error.handlebars', attrs: [type: 'js'], bundle:'bundle_application'
        resource url:'js/application.js'
    }

### Settings

*   **dependsOn**: "handlebars" or "handlebars_runtime". If using only precompiled templates, only handlebars_runtime is required.
*   **url**: location of the handlebars template file.
*   **attrs[type]**: must be js
*   **bundle**: must be set as will not default correctly. To add to default bundle use 'bundle_".

