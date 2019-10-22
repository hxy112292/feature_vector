package edu.uta.nlp.wordnet;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.*;
import edu.uta.nlp.constant.SynsetType;
import edu.uta.nlp.entity.WordInfo;
import edu.uta.nlp.util.PropertiesUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hxy
 */
public class WordNetApi {


    public static String getRelationWord(String word, POS pos) throws IOException {

        String result = "";

        if(word.contains(" ")) {
            return result;
        }

        IDictionary dict = findDict();

        dict.open();//Open dict

        IIndexWord idxWord =dict.getIndexWord(word, pos);

        if(idxWord == null) {
            return result;
        }

        for(IWordID wordID : idxWord.getWordIDs()) {
            for(IWordID iWord : dict.getWord(wordID).getRelatedWords()) {
                result = result + dict.getWord(iWord).getLemma() + ";";
            }
        }

        return result.toLowerCase();
    }

    public static List<WordInfo> getWordInfo(String word, POS pos) throws IOException {

        List<WordInfo> result = new ArrayList<>();

        IDictionary dict = findDict();

        dict.open();//Open dict

        IIndexWord idxWord =dict.getIndexWord(word, pos);
        IIndexWord tmp;
        if(idxWord == null) {
            String wordFirst = word.substring(0, word.indexOf(" ") == -1 ? word.length() : word.indexOf(" "));
            String wordLast = word.substring(word.lastIndexOf(" ") == -1 ? 0 : word.lastIndexOf(" "));
            idxWord =dict.getIndexWord(wordLast, pos);
            if(idxWord == null) {
                idxWord =dict.getIndexWord(wordFirst, pos);
                if(idxWord == null) {
                    if((tmp=dict.getIndexWord(word, POS.NOUN)) != null) {
                        idxWord = tmp;
                    } else if((tmp=dict.getIndexWord(word, POS.VERB)) != null) {
                        idxWord = tmp;
                    } else if((tmp=dict.getIndexWord(word, POS.ADJECTIVE)) != null) {
                        idxWord = tmp;
                    } else if((tmp=dict.getIndexWord(word, POS.ADVERB)) != null) {
                        idxWord = tmp;
                    } else {
                        WordInfo wordInfo = new WordInfo();
                        wordInfo.setGloss("");
                        wordInfo.setLexFileName(SynsetType.OTHER.toString());
                        wordInfo.setType(SynsetType.OTHER.toString());
                        result.add(wordInfo);
                        return result;
                    }
                }
            }
        }

        for(IWordID wordID : idxWord.getWordIDs()) {

            ISynset synset = dict.getWord(wordID).getSynset();

            WordInfo wordInfo = new WordInfo();
            wordInfo.setGloss(synset.getGloss());
            wordInfo.setLexFileName(synset.getLexicalFile().getName());
            wordInfo.setType(SynsetType.getTag(synset.getType()));

            result.add(wordInfo);
        }

        dict.close();

        return result;
    }

    private static IDictionary findDict() throws IOException {

        URL url=new URL("file", null, PropertiesUtil.getPropery("wordnet.dir"));
        IDictionary dict=new Dictionary(url);

        return dict;
    }
}
