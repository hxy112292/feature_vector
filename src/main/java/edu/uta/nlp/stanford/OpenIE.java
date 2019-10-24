package edu.uta.nlp.stanford;

import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Synset;
import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.StringUtils;
import edu.uta.nlp.constant.SynsetType;
import edu.uta.nlp.entity.FeatureVector;
import edu.uta.nlp.entity.OpenIESimpleLemma;
import edu.uta.nlp.entity.WordInfo;
import edu.uta.nlp.util.StrUtil;
import edu.uta.nlp.wordnet.WordNetApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author hxy
 */
public class OpenIE {

    private static final Logger logger = LoggerFactory.getLogger(OpenIE.class);

    public static List<FeatureVector> generate(StanfordCoreNLP pipeline, String line) throws Exception{

        List<FeatureVector> featureVectorList = new ArrayList<>();
        Annotation doc = new Annotation(line);
        pipeline.annotate(doc);
        for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {
            // Get the FeatureVectorMerge triples for the sentence
            Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
            for (RelationTriple triple : triples) {
                String subjectLemmaGloss = triple.subjectLemmaGloss();
                String objectGloss = triple.objectGloss();
                String relationLemmaGloss = triple.relationLemmaGloss();
                relationLemmaGloss = removeFirstMD(relationLemmaGloss);

                logger.info(triple.confidence + ":\t" + subjectLemmaGloss + ":\t" + relationLemmaGloss + ":\t" + objectGloss);

                FeatureVector featureVector = new FeatureVector();
                featureVector.setSubject(subjectLemmaGloss);
                featureVector.setObject(objectGloss);
                featureVector.setVerb(relationLemmaGloss);
                featureVectorList.add(featureVector);
            }
        }
        return featureVectorList;
    }

    public static OpenIESimpleLemma selectLemma(StanfordCoreNLP pipeline, FeatureVector featureVector) throws Exception{

        OpenIESimpleLemma openIESimpleLemma = new OpenIESimpleLemma();

        openIESimpleLemma.setSubject(StrUtil.getLastWord(featureVector.getSubject()));

        OpenIESimpleLemma verb = selectVerbLemma(pipeline, featureVector.getVerb());
        openIESimpleLemma.setVerb(verb.getVerb());
        if(!StringUtils.isNullOrEmpty(verb.getVerb())) {
            openIESimpleLemma.setVcat(verb.getVcat());
        }

        openIESimpleLemma.setObject(StrUtil.getLastWord(featureVector.getObject()));

        return openIESimpleLemma;
    }

    public static OpenIESimpleLemma selectVerbLemma(StanfordCoreNLP pipeline, String verb) throws Exception{

        Integer wordTotal = verb.split(" ").length;
        String lastWord = StrUtil.getLastWord(verb);
        String firstWord = StrUtil.getFirstWord(verb);

        OpenIESimpleLemma result = new OpenIESimpleLemma();

        if(wordTotal == 1) {
            result.setVerb(verb);
            return result;
        }
        if(wordTotal == 2) {
            if(CoreAnnotate.isPos(pipeline, firstWord, "RB")) {
                if(WordNetApi.isPos(lastWord, POS.VERB)) {
                    result.setVerb(lastWord);
                } else {
                    result.setVerb(lastWord);
                    result.setVcat(SynsetType.OTHER.toString());
                }
                return result;
            }
            if(WordNetApi.isPos(firstWord, POS.VERB)) {
                if(CoreAnnotate.isPos(pipeline, lastWord, "RB")) {
                    result.setVerb(firstWord);
                    return result;
                }
                if(CoreAnnotate.isPos(pipeline, lastWord, "IN")) {
                    result.setVerb(firstWord);
                    return result;
                }
                if(CoreAnnotate.isPos(pipeline, lastWord, "TO")) {
                    result.setVerb(firstWord);
                    return result;
                }
                if(CoreAnnotate.isPos(pipeline, lastWord, "JJ")) {
                    result.setVerb(firstWord);
                    return result;
                }
                if(CoreAnnotate.isPosByChar(pipeline, lastWord, 'N') || WordNetApi.isPos(lastWord, POS.NOUN)) {
                    result.setVerb(lastWord);
                    result.setVcat(SynsetType.OTHER.toString());
                    return result;
                }
            }
        }
        if(wordTotal == 3) {
            String secondWord = StrUtil.getIndexOfWord(verb, 2);
            if(CoreAnnotate.isPos(pipeline, firstWord, "RB")) {
                if(WordNetApi.isPos(secondWord, POS.VERB)) {
                    if(CoreAnnotate.isPos(pipeline, lastWord, "IN")) {
                        result.setVerb(secondWord);
                        return result;
                    }
                    if(CoreAnnotate.isPos(pipeline, lastWord, "TO")) {
                        result.setVerb(secondWord);
                        return result;
                    }
                    if(CoreAnnotate.isPos(pipeline, lastWord, "JJ")) {
                        result.setVerb(secondWord);
                        return result;
                    }
                } else {
                    result.setVerb(lastWord);
                    result.setVcat(SynsetType.OTHER.toString());
                    return result;
                }
            }
            if(WordNetApi.isPos(firstWord, POS.VERB) && CoreAnnotate.isPos(pipeline, secondWord, "RB")) {
                if(CoreAnnotate.isPos(pipeline, lastWord, "IN")) {
                    result.setVerb(firstWord);
                    return result;
                }
                if(CoreAnnotate.isPos(pipeline, lastWord, "TO")) {
                    result.setVerb(firstWord);
                    return result;
                }
                if(CoreAnnotate.isPosByChar(pipeline, lastWord, 'N') || WordNetApi.isPos(lastWord, POS.NOUN)) {
                    result.setVerb(lastWord);
                    result.setVcat(SynsetType.OTHER.toString());
                    return result;
                }
            }
        }
        result.setVerb(lastWord);
        result.setVcat(SynsetType.OTHER.toString());
        return result;
    }

    private static String removeFirstMD(String word) throws Exception{

        Vcat vcat = new Vcat();
        String result = "";
        String[] tmp = word.split(" ");
        if(tmp.length < 2) {
            return word;
        }
        String cat = vcat.getVcat(tmp[0]).toUpperCase();
        if(cat.equals("AUXILIARY")) {
            for(int i=1;i<tmp.length;i++) {
                if(i != tmp.length-1) {
                    result = result + tmp[i] + " ";
                } else {
                    result = result + tmp[i];
                }
            }
            return result;
        }
        return word;
    }
}
