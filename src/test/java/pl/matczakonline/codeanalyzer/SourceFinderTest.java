package pl.matczakonline.codeanalyzer;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by michn on 10.04.2016.
 */
public class SourceFinderTest {


    @Test
    public void givenEmptyFile_whenGetClasses_returnsEmptyMap() throws Exception {
        String source = "  \t\n";

        SourceFinder sourceFinder = new SourceFinder(source);
        Map<String, String> classes = sourceFinder.getClasses();
        assertTrue(classes.isEmpty());
    }

    @Test
    public void givenOneEmptyClassInSourceFile_whenGetClasses_returnsSourceOfOneClass() throws Exception {
        String source = "/**\n * Created by michn on 10.04.2016.\n */\npublic class pl.matczakonline.codeanalyzer.SourceFinder {\n\n}\n";

        SourceFinder sourceFinder = new SourceFinder(source);
        Map<String, String> classes = sourceFinder.getClasses();
        assertEquals(1, classes.size());
        assertTrue(classes.containsKey("pl.matczakonline.codeanalyzer.SourceFinder"));
        assertEquals("\n\n", classes.get("pl.matczakonline.codeanalyzer.SourceFinder"));
    }


    @Test
    public void givenTwoEmptyClassesInSourceFile_whenGetClasses_returnsSourceOfTwoClasses() throws Exception {
        String source = "/**\n * Created by michn on 10.04.2016.\n */\npublic class pl.matczakonline.codeanalyzer.SourceFinder {\n\n}\n"
                + "/**\n" +
                " * Created by michn on 10.04.2016.\n" +
                " */\n" +
                "class pl.matczakonline.codeanalyzer.SourceFinder2 { private int i; }\n";

        SourceFinder sourceFinder = new SourceFinder(source);
        Map<String, String> classes = sourceFinder.getClasses();
        assertEquals(2, classes.size());

        assertTrue(classes.containsKey("pl.matczakonline.codeanalyzer.SourceFinder"));
        assertEquals("\n\n", classes.get("pl.matczakonline.codeanalyzer.SourceFinder"));

        assertTrue(classes.containsKey("pl.matczakonline.codeanalyzer.SourceFinder2"));
        assertEquals(" private int i; ", classes.get("pl.matczakonline.codeanalyzer.SourceFinder2"));
    }
}