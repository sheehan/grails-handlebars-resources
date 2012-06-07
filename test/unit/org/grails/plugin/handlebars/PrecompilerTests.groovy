package org.grails.plugin.handlebars

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import org.junit.Test
import org.junit.Before

@TestMixin(GrailsUnitTestMixin)
class PrecompilerTests {

    Precompiler precompiler

    @Before
    void setUp() {
        precompiler = new Precompiler()
    }

    @Test
    void precompileTemplate() {
        String expected = """
function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var foundHelper, self=this;


  return "<div>Simple</div>";}
        """

        String compiledTemplate = precompiler.precompileTemplate('<div>Simple</div>')

        assert compiledTemplate.trim() == expected.trim()
    }

    @Test
    void precompile() {
        File input = new File(this.getClass().classLoader.getResource('input.1.handlebars').file)
        File expected = new File(this.getClass().classLoader.getResource('ouput.1_handlebars.js').file)
        File target = File.createTempFile('target', '.js')

        precompiler.precompile(input, target)

        assert expected.text == target.text
    }

}
