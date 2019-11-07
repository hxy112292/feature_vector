package edu.uta.nlp.tool;

import edu.uta.nlp.entity.ClassificationCoreLabel;
import edu.uta.nlp.entity.FeatureVector;
import edu.uta.nlp.entity.OpenIESimpleLemma;

import java.util.List;

/**
 * @author jmp
 */
public interface NlpTool {
    public List<ClassificationCoreLabel> generatePosTag(String line);

    public boolean isPosByString(String word, String pos);

    public boolean isPosByChar(String word, char pos);

    public List<FeatureVector> selectOpenIEGross(String line) throws Exception;

    public OpenIESimpleLemma selectOpenIELemma(FeatureVector featureVector) throws Exception;
}
