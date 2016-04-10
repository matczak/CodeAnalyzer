package pl.matczakonline.codeanalyzer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by michn on 10.04.2016.
 */
public class SourceFinder {

    private final String source;

    public SourceFinder(String source) {
        this.source = source;
    }

    private List<Integer> getAllIndexOfText(String guess) {
        List<Integer> indexes = new LinkedList<Integer>();
        int index = this.source.indexOf(guess);
        while (index >= 0) {

            indexes.add(index);
            index = this.source.indexOf(guess, index + 1);
        }

        return indexes;
    }

    public Map<String, String> getClasses() {
        HashMap<String, String> classes = new HashMap<String, String>();
        List<Integer> indexes = getAllIndexOfText("class");
        for (int index : indexes) {
            String className = getFirstClassNameAfterIndex(index);
            classes.put(className, getContentInBracketsAfterIndex(index));
        }

        return classes;
    }

    public String getContentInBracketsAfterIndex(int index) {
        int beginIndex = source.indexOf("{", index);
        if (beginIndex < 0)
            return "";

        String sourcePart = source.substring(beginIndex);
        int numberOfBrackets = 0;
        int indexOfEndOfClass = -1;

        for (int i = 0; i < sourcePart.length(); ++i) {
            if (sourcePart.charAt(i) == '{')
                ++numberOfBrackets;

            if (sourcePart.charAt(i) == '}')
                --numberOfBrackets;

            if (numberOfBrackets == 0) {
                indexOfEndOfClass = i;
                break;
            }
        }

        if (indexOfEndOfClass != -1)
            return sourcePart.substring(1, indexOfEndOfClass);

        return sourcePart.substring(1);
    }

    private String getFirstClassNameAfterIndex(int index) {
        return source.substring(index + "class".length(), source.indexOf("{", index)).trim();
    }


}
