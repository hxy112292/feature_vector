package edu.uta.nlp.tool.stanford;

import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.StringUtils;
import edu.uta.nlp.constant.SynsetType;
import edu.uta.nlp.entity.ClassificationCoreLabel;
import edu.uta.nlp.entity.FeatureVector;
import edu.uta.nlp.entity.OpenIESimpleLemma;
import edu.uta.nlp.tool.NlpTool;
import edu.uta.nlp.tool.stanford.strategy.VerbLemmaStrategy;
import edu.uta.nlp.util.StrUtil;
import edu.uta.nlp.wordnet.Vcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author jmp
 */
public class StanfordTool implements NlpTool {

    private static final Logger logger = LoggerFactory.getLogger(StanfordTool.class);

    @Override
    public List<ClassificationCoreLabel> generatePosTag(String line) {
        List<ClassificationCoreLabel> listOfClassificationPerWord = new ArrayList<ClassificationCoreLabel>();
        Annotation doc = new Annotation(line);
        Pipeline pipeline = Pipeline.getInstance();
        pipeline.getPipe().annotate(doc);
        CoreMap sentence = doc.get(CoreAnnotations.SentencesAnnotation.class).get(0);
        for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
            String word = token.get(CoreAnnotations.TextAnnotation.class);
            if(StringUtils.isNullOrEmpty(word)) {
                word="";
            }
            String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
            if(StringUtils.isNullOrEmpty(pos)) {
                pos="";
            }
            String ner = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
            if(StringUtils.isNullOrEmpty(ner)) {
                ner="";
            }

            ClassificationCoreLabel classificationCoreLabel = new ClassificationCoreLabel(word, pos, ner);
            listOfClassificationPerWord.add(classificationCoreLabel);
        }
        return listOfClassificationPerWord;
    }

    @Override
    public boolean isPosByString(String word, String pos) {
        String tag = generatePosTag(word).get(0).getPos().toUpperCase();
        if(!StringUtils.isNullOrEmpty(tag)) {
            if(!StringUtils.isNullOrEmpty(pos) && tag.equals(pos)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    @Override
    public List<FeatureVector> selectOpenIEGross(String line) throws Exception {
        List<FeatureVector> featureVectorList = new ArrayList<>();
        FeatureVector protoype = new FeatureVector();
        Annotation doc = new Annotation(line);
        Pipeline pipeline = Pipeline.getInstance();
        pipeline.getPipe().annotate(doc);
        for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {
            // Get the FeatureVector triples for the sentence
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

    @Override
    public OpenIESimpleLemma selectOpenIELemma(FeatureVector featureVector) throws Exception {
        OpenIESimpleLemma openIESimpleLemma = new OpenIESimpleLemma();

        openIESimpleLemma.setSubject(StrUtil.getLastWord(featureVector.getSubject()));

        String verb = VerbLemmaStrategy.getVerb(featureVector.getVerb());
        openIESimpleLemma.setVerb(verb);
        if(verb == null) {
            openIESimpleLemma.setVerb(StrUtil.getFirstWord(featureVector.getVerb()));
            openIESimpleLemma.setVcat(SynsetType.OTHER.toString());
        }

        openIESimpleLemma.setObject(StrUtil.getLastWord(featureVector.getObject()));

        return openIESimpleLemma;
    }

    @Override
    public boolean isPosByChar(String word, char pos) {
        String tag = generatePosTag(word).get(0).getPos().toUpperCase();
        if(!StringUtils.isNullOrEmpty(tag)) {
            if(tag.charAt(0) == pos) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
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
