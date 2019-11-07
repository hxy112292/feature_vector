package edu.uta.nlp.tool.stanford.strategy;

import edu.mit.jwi.item.POS;
import edu.uta.nlp.tool.NlpTool;
import edu.uta.nlp.tool.stanford.StanfordTool;
import edu.uta.nlp.util.StrUtil;
import edu.uta.nlp.wordnet.WordNetApi;

/**
 * @author housirvip
 */

public enum VerbLemmaStrategy {

    /**
     * process one word verb phrase
     *
     * @return verb String
     */
    ONE() {
        @Override
        public String process(String verb) {
            //do something
            return verb;
        }
    },
    /**
     * process two word verb phrase
     *
     * @return verb String
     */
    TWO() {
        @Override
        public String process(String verb) throws Exception {
            String lastWord = StrUtil.getLastWord(verb);
            String firstWord = StrUtil.getFirstWord(verb);
            NlpTool nlptool = new StanfordTool();

            if (WordNetApi.isPos(firstWord, POS.VERB) &&
                    (nlptool.isPosByString(lastWord, "RB") || nlptool.isPosByString(lastWord, "IN") ||
                            nlptool.isPosByString(lastWord, "TO") || nlptool.isPosByString(lastWord, "JJ"))) {
                return firstWord;
            } else if (nlptool.isPosByString(firstWord, "RB") && WordNetApi.isPos(lastWord, POS.VERB)) {
                return firstWord;
            }
            return null;
        }
    },
    /**
     * process three word verb phrase
     *
     * @return verb String
     */
    THREE() {
        @Override
        public String process(String verb) throws Exception{
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
    },

    OTHER() {
        @Override
        public String process(String verb) throws Exception{
            //do something
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
    };

    public abstract String process(String verb) throws Exception;

    public static VerbLemmaStrategy getStrategy(int len) {

        switch (len) {
            case 1:
                return ONE;
            case 2:
                return TWO;
            case 3:
                return THREE;
            default:
                return OTHER;
        }
    }
}
