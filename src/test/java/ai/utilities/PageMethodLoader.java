package ai.utilities;

import java.util.*;
import java.util.regex.*;

public class PageMethodLoader {

    public static List<String> getVerifyMethods(String pageFilePath) {
        List<String> methods = new ArrayList<>();

        try {
            String content = new String(java.nio.file.Files.readAllBytes(
                    java.nio.file.Paths.get(pageFilePath)));

            Pattern pattern = Pattern.compile("public boolean (verify\\w+)\\(");
            Matcher matcher = pattern.matcher(content);

            while (matcher.find()) {
                methods.add(matcher.group(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return methods;
    }
}