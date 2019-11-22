package edu.uta.nlp;

import edu.uta.nlp.controller.FeatureVectorController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hxy
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        try {
            FeatureVectorController.extractUseCase();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
