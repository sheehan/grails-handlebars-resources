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
    void precompile() {
        (1..2).each {
            File input = loadFile("input.${it}.handlebars")
            File expected = loadFile("output.${it}_handlebars.js")
            File target = File.createTempFile('target', '.js')

            precompiler.precompile(input, target, 'input')

            assert expected.text.trim() == target.text.trim()
        }
    }


    @Test
    void precompile_customWrap() {
        precompiler.wrapTemplate = { String templateName, String compiledTemplate ->
            println compiledTemplate
            "xxx$templateName"
        }
        File input = File.createTempFile('input', '.hbs')
        input << 'abc'
        File target = File.createTempFile('target', '.js')
        precompiler.precompile(input, target, 'name')

        assert "xxxname" == target.text
    }

    private File loadFile(String name) {
        new File(this.getClass().classLoader.getResource(name).file)
    }

}
