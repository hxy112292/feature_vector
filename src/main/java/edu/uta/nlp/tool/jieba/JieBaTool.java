package edu.uta.nlp.tool.jieba;

import edu.uta.nlp.entity.ClassificationCoreLabel;
import edu.uta.nlp.entity.FeatureVector;
import edu.uta.nlp.entity.OpenIESimpleLemma;
import edu.uta.nlp.tool.NlpTool;

import java.util.List;

/**
 * @author jmp
 */
public class JieBaTool implements NlpTool {
    @Override
    public List<ClassificationCoreLabel> generatePosTag(String line) {
        return null;
    }

    @Override
    public boolean isPosByString(String word, String pos) {
        return false;
    }

    @Override
    public boolean isPosByChar(String word, char pos) {
        return false;
    }

    @Override
    public List<FeatureVector> selectOpenIEGross(String line) throws Exception {
        return null;
    }

    @Override
    public OpenIESimpleLemma selectOpenIELemma(FeatureVector featureVector) throws Exception {
        return null;
    }
}
