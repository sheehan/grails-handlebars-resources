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
        (1..2).each {
            File input = loadFile("input.${it}.handlebars")
            File expected = loadFile("output.${it}_handlebars.js")
            File target = File.createTempFile('target', '.js')

            precompiler.precompile(input, target)

            compareIgnoreWhiteSpace expected.text, target.text
        }
    }

    private File loadFile(String name) {
        new File(this.getClass().classLoader.getResource(name).file)
    }

    private compareIgnoreWhiteSpace(s1, s2) {
        s1 = s1.replaceAll(/\s+/, ' ')
        s2 = s2.replaceAll(/\s+/, ' ')
        assert s1 == s2
    }

}
