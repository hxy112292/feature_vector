package edu.uta.nlp.weka;

import edu.uta.nlp.file.FilePath;
import weka.classifiers.Classifier;

/**
 * @author hxy
 */
public class Model {

    private static volatile Model instance;

    private Classifier cls;

    private Model() throws Exception{
        cls = (Classifier) weka.core.SerializationHelper.read(FilePath.getModelPath());
    }

    public static synchronized Model getInstance() throws Exception{
        if (instance != null) {
            return instance;
        }
        instance = new Model();
        return instance;
    }

    public Classifier getCls() {
        return cls;
    }

    public void setCls(Classifier cls) {
        this.cls = cls;
    }
}
