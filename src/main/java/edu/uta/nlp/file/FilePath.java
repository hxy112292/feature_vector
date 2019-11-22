package edu.uta.nlp.file;

import java.io.File;

/**
 * @author hxy
 */
public class FilePath {

    public static String getRequirementPath() {

        return System.getProperty("user.dir") + File.separator + "requirement";

    }

    public static String getLabeledResultPath() {

        return System.getProperty("user.dir") + File.separator + "feature_vector_result" + File.separator + "label";
    }

    public static String getUseCasePath() {

        return System.getProperty("user.dir") + File.separator + "feature_vector_result" + File.separator + "use case";
    }

    public static String getUnLabeledResultPath() {

        return System.getProperty("user.dir") + File.separator + "feature_vector_result" + File.separator + "no label";
    }

    public static String getModelPath() {

        return System.getProperty("user.dir") + File.separator + "weka_model" + File.separator + "Team_6_Weka.model";
    }

}
