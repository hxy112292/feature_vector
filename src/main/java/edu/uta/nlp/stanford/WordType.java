package edu.uta.nlp.stanford;

import edu.mit.jwi.item.LexFile;
import edu.mit.jwi.item.POS;
import edu.uta.nlp.entity.WordInfo;
import edu.uta.nlp.wordnet.WordNetApi;

import java.util.List;

/**
 * @author hxy
 */
public class WordType {

    public static String getType(String word, POS pos) throws Exception{
        List<WordInfo> wordInfoList = WordNetApi.getWordInfo(word, pos);
        for(WordInfo wordInfo : wordInfoList) {
            if(wordInfo.getLexFileName().equals(LexFile.NOUN_PERSON.getName())) {
                return wordInfo.getLexFileName().toUpperCase();
            }
        }

        return wordInfoList.get(0).getLexFileName().toUpperCase();
    }
}
