package org.grails.plugin.handlebars

import org.mozilla.javascript.Scriptable
import org.mozilla.javascript.Function
import org.mozilla.javascript.Context
import org.mozilla.javascript.tools.shell.Global

class Precompiler {

    private Scriptable scope
    private Function precompile

    Precompiler() {
        ClassLoader classLoader = getClass().getClassLoader()
        URL handlebars = classLoader.getResource('handlebars-1.0.0.beta.6.js')

        Context cx = Context.enter()
        cx.optimizationLevel = 9
        Global global = new Global()
        global.init cx
        scope = cx.initStandardObjects(global)
        cx.evaluateString scope, handlebars.text, handlebars.file, 1, null

        precompile = scope.get("Handlebars", scope).get("precompile", scope)
        Context.exit();
    }

    void precompile(File input, File target) {
        String templateName = input.name
        templateName = templateName.replaceAll(/\.handlebars$/, '')

        String compiledTemplate = precompileTemplate(input.text)

        String output = """
(function(){
    var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
    templates['$templateName'] = template($compiledTemplate);
}());
"""
        target.write output
    }

    String precompileTemplate(String contents) {
        call(precompile, contents)
    }

    private synchronized String call(Function fn, Object[] args) {
        (String) Context.call(null, fn, scope, scope, args);
    }
}
