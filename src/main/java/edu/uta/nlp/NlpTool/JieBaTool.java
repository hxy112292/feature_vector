package edu.uta.nlp.NlpTool;

import edu.uta.nlp.entity.ClassificationCoreLabel;
import edu.uta.nlp.entity.FeatureVector;
import edu.uta.nlp.entity.OpenIESimpleLemma;

import java.util.List;

public class JieBaTool implements NlpTool {
    @Override
    public List<ClassificationCoreLabel> generate(String line) {
        return null;
    }

    @Override
    public boolean isPos(String word, String pos) {
        return false;
    }

    @Override
    public List<FeatureVector> selectGross(String line) throws Exception {
        return null;
    }

    @Override
    public OpenIESimpleLemma selectLemma(FeatureVector featureVector) throws Exception {
        return null;
    }
}
