package edu.uta.nlp.NlpTool;

import edu.uta.nlp.entity.ClassificationCoreLabel;
import edu.uta.nlp.entity.FeatureVector;
import edu.uta.nlp.entity.OpenIESimpleLemma;

import java.util.List;

public interface NlpTool {
    public  List<ClassificationCoreLabel> generate(String line);
    public  boolean isPos(String word, String pos);
    public  List<FeatureVector> selectGross(String line) throws Exception;
    public  OpenIESimpleLemma selectLemma(FeatureVector featureVector) throws Exception;
}
