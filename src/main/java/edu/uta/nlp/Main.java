package edu.uta.nlp;

import edu.mit.jwi.item.POS;
import edu.uta.nlp.stanford.FeatureVectorMerge;
import edu.uta.nlp.wordnet.WordNetApi;

/**
 * @author hxy
 */
public class Main {

    public static void main(String[] args) {

        try {
            FeatureVectorMerge.generate();
//            System.out.println(WordNetApi.getWordInfo("operator", POS.NOUN));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
