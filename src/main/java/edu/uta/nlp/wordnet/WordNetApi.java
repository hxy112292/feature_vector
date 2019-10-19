package edu.uta.nlp.wordnet;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.uta.nlp.constant.SynsetType;
import edu.uta.nlp.util.PropertiesUtil;

import java.io.IOException;
import java.net.URL;

/**
 * @author hxy
 */
public class WordNetApi {


    public static String getWord(String word, POS pos) throws IOException {

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

        return result;
    }

    public static String getDefinition(String word, POS pos) throws IOException {

        String result = "";

        IDictionary dict = findDict();

        dict.open();//Open dict

        IIndexWord idxWord =dict.getIndexWord(word, pos);
        if(idxWord == null) {
            word = word.substring(word.lastIndexOf(" ") == -1 ? 0 : word.lastIndexOf(" "));
            idxWord =dict.getIndexWord(word, pos);
            if(idxWord == null) {
                return result;
            }
        }

        for(IWordID wordID : idxWord.getWordIDs()) {
            result = result + dict.getWord(wordID).getSynset().getGloss();
        }

        dict.close();

        return result.toLowerCase();
    }

    public static Integer getType(String word, POS pos) throws IOException {

        Integer result;

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
                        return SynsetType.OTHER.SynsetType();
                    }
                }
            }
        }

        result = dict.getWord(idxWord.getWordIDs().get(0)).getSynset().getType();

        dict.close();

        return result;
    }

    private static IDictionary findDict() throws IOException {

        URL url=new URL("file", null, PropertiesUtil.getPropery("wordnet.dir"));
        IDictionary dict=new Dictionary(url);

        return dict;
    }
}
