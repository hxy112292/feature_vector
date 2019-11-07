package edu.uta.nlp;

import edu.uta.nlp.controller.FeatureVectorGenerate;
import edu.uta.nlp.database.service.FeatureVectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hxy
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        try {
            FeatureVectorGenerate.process();
//            FeatureVectorService featureVectorService = new FeatureVectorService();
//            featureVectorService.test();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
