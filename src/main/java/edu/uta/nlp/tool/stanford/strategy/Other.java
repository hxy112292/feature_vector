package edu.uta.nlp.tool.stanford.strategy;

import edu.mit.jwi.item.POS;
import edu.uta.nlp.tool.NlpTool;
import edu.uta.nlp.tool.stanford.StanfordTool;
import edu.uta.nlp.util.StrUtil;
import edu.uta.nlp.wordnet.WordNetApi;

/**
 * @author hxy
 */
public class Other implements VerbSelectStrategy{

    @Override
    public String process(String verb) throws Exception {
        String lastWord = StrUtil.getLastWord(verb);
        String firstWord = StrUtil.getFirstWord(verb);
        NlpTool nlptool = new StanfordTool();
        if(WordNetApi.isPos(firstWord, POS.VERB)) {
            if(nlptool.isPosByString(lastWord, "RB") || nlptool.isPosByString(lastWord, "IN") ||
                    nlptool.isPosByString(lastWord, "TO")|| nlptool.isPosByString(lastWord, "JJ")) {
                return firstWord;
            }
        }
        return null;
    }
}
