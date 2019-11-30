package edu.uta.nlp.tool.stanford.strategy;

import edu.mit.jwi.item.POS;
import edu.uta.nlp.tool.NlpTool;
import edu.uta.nlp.tool.stanford.StanfordTool;
import edu.uta.nlp.util.StrUtil;
import edu.uta.nlp.wordnet.WordNetApi;

/**
 * @author hxy
 */
public class ThreeWord implements VerbSelectStrategy{

    @Override
    public String process(String verb) throws Exception {
        String lastWord = StrUtil.getLastWord(verb);
        String firstWord = StrUtil.getFirstWord(verb);
        String secondWord = StrUtil.getIndexOfWord(verb, 2);
        NlpTool nlptool = new StanfordTool();
        //do something
        if(WordNetApi.isPos(firstWord, POS.VERB)) {
            if(nlptool.isPosByString(lastWord, "RB") || nlptool.isPosByString(lastWord, "IN") ||
                    nlptool.isPosByString(lastWord, "TO")|| nlptool.isPosByString(lastWord, "JJ")) {
                return firstWord;
            }
        } else if(nlptool.isPosByString(firstWord, "RB") && WordNetApi.isPos(secondWord, POS.VERB)) {
            if (nlptool.isPosByString(lastWord, "IN") || nlptool.isPosByString(lastWord, "TO") ||
                    nlptool.isPosByString(lastWord, "JJ")) {
                return secondWord;
            }
        }
        return null;
    }
}
