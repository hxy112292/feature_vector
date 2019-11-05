package edu.uta.nlp.stanford;

import com.sun.istack.Nullable;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.StringUtils;
import edu.uta.nlp.entity.ClassificationCoreLabel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hxy
 */
public class CoreAnnotate {

    public static List<ClassificationCoreLabel> generate(String line) {

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

    public static boolean isPos(String word, String pos) {
        String tag = generate(word).get(0).getPos().toUpperCase();
        if(!StringUtils.isNullOrEmpty(tag)) {
            if(!StringUtils.isNullOrEmpty(pos) && tag.equals(pos)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public static boolean isPosByChar(String word, char pos) {
        String tag = generate(word).get(0).getPos().toUpperCase();
        if(!StringUtils.isNullOrEmpty(tag)) {
            if(tag.charAt(0) == pos) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
