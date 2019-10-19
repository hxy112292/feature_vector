package edu.uta.nlp.file;

import java.io.File;

public class FilePath {

    public static String getRequirementPath() {

        return System.getProperty("user.dir") + File.separator + "requirement";

    }

    public static String getResultPath() {

        return System.getProperty("user.dir") + File.separator + "feature_vector_result";

    }

}
