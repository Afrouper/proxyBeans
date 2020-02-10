package de.afrouper.beans.json;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;

public class TestHelper {

    static String readJson(String name) throws Exception {
        final String path = "/" + TestHelper.class.getPackage().getName().replace('.', '/');
        return FileUtils.readFileToString(new File(TestHelper.class.getResource(path + "/" + name).toURI()), StandardCharsets.UTF_8);
    }
}
