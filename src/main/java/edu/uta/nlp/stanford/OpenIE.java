package edu.uta.nlp.stanford;

import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;
import edu.uta.nlp.constant.SynsetType;
import edu.uta.nlp.entity.FeatureVector;
import edu.uta.nlp.entity.OpenIESimpleLemma;
import edu.uta.nlp.stanford.strategy.VerbLemmaStrategy;
import edu.uta.nlp.util.StrUtil;
import edu.uta.nlp.wordnet.Vcat;
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

    public static List<FeatureVector> selectGross(String line) throws Exception{

        List<FeatureVector> featureVectorList = new ArrayList<>();
        FeatureVector protoype = new FeatureVector();
        Annotation doc = new Annotation(line);
        Pipeline pipeline = Pipeline.getInstance();
        pipeline.getPipe().annotate(doc);
        for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {
            // Get the FeatureVectorGenerate triples for the sentence
            Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
            for (RelationTriple triple : triples) {
                String subjectLemmaGloss = triple.subjectLemmaGloss();
                String objectGloss = triple.objectGloss();
                String relationLemmaGloss = triple.relationLemmaGloss();
                relationLemmaGloss = removeFirstMD(relationLemmaGloss);

                logger.info(triple.confidence + ":\t" + subjectLemmaGloss + ":\t" + relationLemmaGloss + ":\t" + objectGloss);

                //prototype mode
                FeatureVector featureVector = (FeatureVector) protoype.clone();
                featureVector.setSubject(subjectLemmaGloss);
                featureVector.setObject(objectGloss);
                featureVector.setVerb(relationLemmaGloss);
                featureVectorList.add(featureVector);
            }
        }
        return featureVectorList;
    }

    public static OpenIESimpleLemma selectLemma(FeatureVector featureVector) throws Exception{

        OpenIESimpleLemma openIESimpleLemma = new OpenIESimpleLemma();

        openIESimpleLemma.setSubject(StrUtil.getLastWord(featureVector.getSubject()));

        Integer wordTotal = featureVector.getVerb().split(" ").length;
        VerbLemmaStrategy verbLemmaStrategy=VerbLemmaStrategy.getStrategy(wordTotal);
        String verb = verbLemmaStrategy.process(featureVector.getVerb());
        openIESimpleLemma.setVerb(verb);
        if(verb == null) {
            openIESimpleLemma.setVerb(StrUtil.getFirstWord(featureVector.getVerb()));
            openIESimpleLemma.setVcat(SynsetType.OTHER.toString());
        }

        openIESimpleLemma.setObject(StrUtil.getLastWord(featureVector.getObject()));

        return openIESimpleLemma;
    }

    private static String removeFirstMD(String word) throws Exception{

        Vcat vcat = Vcat.getInstance();
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
