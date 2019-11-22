package edu.uta.nlp.wordnet;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.*;
import edu.stanford.nlp.util.StringUtils;
import edu.uta.nlp.constant.SynsetType;
import edu.uta.nlp.entity.WordInfo;
import edu.uta.nlp.util.PropertiesUtil;
import edu.uta.nlp.util.StrUtil;

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

        if(StringUtils.isNullOrEmpty(word) || word.contains(" ")) {
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

        if(StringUtils.isNullOrEmpty(word)) {
            return result;
        }

        IDictionary dict = findDict();

        dict.open();//Open dict

        IIndexWord idxWord =dict.getIndexWord(word, pos);
        if(idxWord == null) {
            String wordFirst = StrUtil.getFirstWord(word);
            String wordLast = StrUtil.getLastWord(word);
            idxWord =dict.getIndexWord(wordLast, pos);
            if(idxWord == null) {
                idxWord = dict.getIndexWord(wordFirst, pos);
                if (idxWord == null) {
                    WordInfo wordInfo = new WordInfo();
                    wordInfo.setGloss("");
                    wordInfo.setLexFileName(SynsetType.OTHER.toString());
                    wordInfo.setType(SynsetType.OTHER.toString());
                    result.add(wordInfo);
                    return result;
                }
            }
        }

        result.addAll(collectAllWordInfo(dict, idxWord));

        dict.close();

        return result;
    }

    public static List<WordInfo> getAllWordInfo(String word) throws IOException {

        IIndexWord idxWordNoun;
        IIndexWord idxWordVerb;
        IIndexWord idxWordAdj;
        IIndexWord idxWordAdverb;

        List<WordInfo> result = new ArrayList<>();

        if(StringUtils.isNullOrEmpty(word)) {
            return result;
        }

        IDictionary dict = findDict();

        dict.open();//Open dict

        if ((idxWordNoun = dict.getIndexWord(word, POS.NOUN)) != null) {
            result.addAll(collectAllWordInfo(dict, idxWordNoun));
        }
        if ((idxWordVerb = dict.getIndexWord(word, POS.VERB)) != null) {
            result.addAll(collectAllWordInfo(dict, idxWordVerb));
        }
        if ((idxWordAdj = dict.getIndexWord(word, POS.ADJECTIVE)) != null) {
            result.addAll(collectAllWordInfo(dict, idxWordAdj));
        }
        if ((idxWordAdverb = dict.getIndexWord(word, POS.ADVERB)) != null) {
            result.addAll(collectAllWordInfo(dict, idxWordAdverb));
        }
        dict.close();

        return result;
    }

    public static Boolean isPos (String word, POS pos) throws Exception{

        List<WordInfo> wordInfoList = getWordInfo(word, pos);

        for(WordInfo wordInfo : wordInfoList) {
            if(wordInfo.getType().equals(SynsetType.getTag(POS.NUM_VERB))) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public static String getWordType(String word, POS pos) throws Exception{
        List<WordInfo> wordInfoList = getWordInfo(word, pos);
        for(WordInfo wordInfo : wordInfoList) {
            if(wordInfo.getLexFileName().equals(LexFile.NOUN_PERSON.getName())) {
                return wordInfo.getLexFileName().replace('.', '_').toUpperCase();
            }
        }

        return wordInfoList.get(0).getLexFileName().replace('.', '_').toUpperCase();
    }

    private static IDictionary findDict() throws IOException {

        URL url=new URL("file", null, PropertiesUtil.getPropery("wordnet.dir"));
        IDictionary dict=new Dictionary(url);

        return dict;
    }

    private static List<WordInfo> collectAllWordInfo(IDictionary dict, IIndexWord idxWord) {

        List<WordInfo> result = new ArrayList<>();

        for(IWordID wordID : idxWord.getWordIDs()) {
            ISynset synset = dict.getWord(wordID).getSynset();
            WordInfo wordInfo = new WordInfo();
            wordInfo.setGloss(synset.getGloss());
            wordInfo.setLexFileName(synset.getLexicalFile().getName());
            wordInfo.setType(SynsetType.getTag(synset.getType()));
            result.add(wordInfo);
        }

        return result;
    }
}
