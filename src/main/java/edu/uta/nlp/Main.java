package edu.uta.nlp;

import edu.uta.nlp.stanford.FeatureVectorMerge;

/**
 * @author hxy
 */
public class Main {

    public static void main(String[] args) {

        try {
            FeatureVectorMerge.generate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
