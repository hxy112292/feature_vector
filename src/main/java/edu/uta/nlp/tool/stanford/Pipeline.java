package edu.uta.nlp.tool.stanford;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.PropertiesUtils;

import java.util.Properties;

/**
 * Singleton Pattern
 * @author hxy
 */
public class Pipeline {


    private StanfordCoreNLP pipe;

    private static volatile Pipeline instance;

    private Pipeline() {
        // Create the Stanford CoreNLP pipeline
        Properties props = PropertiesUtils.asProperties("annotators",
                "tokenize,ssplit,pos,lemma,depparse,natlog,openie,ner");
        pipe = new StanfordCoreNLP(props);
    }

    public static synchronized Pipeline getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new Pipeline();
        return instance;
    }

    public StanfordCoreNLP getPipe() {
        return pipe;
    }

    public void setPipe(StanfordCoreNLP pipe) {
        this.pipe = pipe;
    }
}
