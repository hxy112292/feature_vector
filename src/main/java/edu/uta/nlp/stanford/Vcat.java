package edu.uta.nlp.stanford;

import edu.mit.jwi.item.POS;
import edu.uta.nlp.constant.SynsetType;
import edu.uta.nlp.entity.WordInfo;
import edu.uta.nlp.wordnet.WordNetApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hxy
 */
public class Vcat {

    static Map<String, String> vcat = new HashMap<String, String>();

    public Vcat() {
        addVerbCategories();
    }

    public String getVcat(String word) throws Exception{
        String cat;
        if((cat = vcat.get(word)) != null) {
            return cat.toUpperCase();
        }

        List<WordInfo> wordInfoList = WordNetApi.getWordInfo(word, POS.VERB);
        for(WordInfo wordInfo : wordInfoList) {
            if(wordInfo.getType().equals(SynsetType.getTag(POS.NUM_VERB))) {
                return SynsetType.getTag(POS.NUM_VERB);
            }
        }

        return SynsetType.OTHER.toString();
    }

    private static void addVerbCategories() {
        vcat.put("has", "possession");
        vcat.put("have", "possession");
        vcat.put("had", "possession");
        vcat.put("possess", "possession");
        vcat.put("consist of", "comprised of");
        vcat.put("comprised of", "comprised of");
        vcat.put("constituent of", "comprised of");
        vcat.put("compose", "consist");
        vcat.put("form", "consist");
        vcat.put("composed", "consist");
        vcat.put("formed", "consist");
        vcat.put("consist", "consist");
        vcat.put("encompass", "consist");
        vcat.put("embrace", "consist");
        vcat.put("constituted", "consist");
        vcat.put("comprised", "consist");
        vcat.put("constitute", "consist");
        vcat.put("comprise", "consist");
        vcat.put("make-up", "consist");
        vcat.put("made-up-4", "consist");
        vcat.put("has", "containment");
        vcat.put("is", "IS-A");
        vcat.put("was", "IS-A");
        vcat.put("are", "IS-A");
        vcat.put("were", "IS-A");
        vcat.put("am", "IS-A");
        vcat.put("regarded as", "IS-A");
        vcat.put("be", "IS-A");
        vcat.put("been", "IS-A");
        vcat.put("can", "auxiliary");
        vcat.put("could", "auxiliary");
        vcat.put("may", "auxiliary");
        vcat.put("might", "auxiliary");
        vcat.put("will", "auxiliary");
        vcat.put("would", "auxiliary");
        vcat.put("shall", "auxiliary");
        vcat.put("should", "auxiliary");
        vcat.put("must", "auxiliary");
        vcat.put("ought to", "auxiliary");
        vcat.put("allow", "permission");
        vcat.put("let", "permission");
    }
}
