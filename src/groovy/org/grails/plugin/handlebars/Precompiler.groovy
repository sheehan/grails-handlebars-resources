package org.grails.plugin.handlebars

import org.mozilla.javascript.Scriptable
import org.mozilla.javascript.Function
import org.mozilla.javascript.Context
import org.mozilla.javascript.tools.shell.Global

import javax.annotation.PostConstruct

class Precompiler {

    private Scriptable scope
    private Function precompile

    @PostConstruct
    void init() {
        ClassLoader classLoader = getClass().classLoader
        URL handlebars = classLoader.getResource('handlebars-1.0.rc.1.js')

        Context cx = Context.enter()
        cx.optimizationLevel = 9
        Global global = new Global()
        global.init cx
        scope = cx.initStandardObjects(global)
        cx.evaluateString scope, handlebars.text, handlebars.file, 1, null

        precompile = scope.get("Handlebars", scope).get("precompile", scope)
        Context.exit();
    }

    void precompile(File input, File target, String templateName) {
        String compiledTemplate = callPrecompile(input.text)
        target.write wrapTemplate(templateName, compiledTemplate)
    }

    private synchronized String callPrecompile(Object[] args) {
        Context.call null, precompile, scope, scope, args
    }

    def wrapTemplate = { String templateName, String compiledTemplate ->
        """
(function(){
    var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
    templates['$templateName'] = template($compiledTemplate);
}());
"""
    }
}
