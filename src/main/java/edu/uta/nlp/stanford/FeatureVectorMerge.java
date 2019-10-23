package edu.uta.nlp.stanford;

import edu.mit.jwi.item.LexFile;
import edu.mit.jwi.item.POS;
import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;
import edu.uta.nlp.entity.ClassificationCoreLabel;
import edu.uta.nlp.entity.FeatureVector;
import edu.uta.nlp.file.CSVFile;
import edu.uta.nlp.file.FilePath;
import edu.uta.nlp.wordnet.WordNetApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class FeatureVectorMerge {

    public static void generate() throws Exception{

        // Create the Stanford CoreNLP pipeline
        Properties props = PropertiesUtils.asProperties("annotators",
                "tokenize,ssplit,pos,lemma,depparse,natlog,openie,ner");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        Vcat vcat = new Vcat();

        File folder = new File(FilePath.getRequirementPath());
        for (final File file : folder.listFiles()) {
            BufferedReader bufferedReader = (new BufferedReader(new FileReader(file)));
            // Loop over sentences in the document
            int sentNo = 0;
            String line;
            StringBuilder sb = new StringBuilder("requirement, subject, s-tag, s-ner, s-type, verb, v-tag, v-cat, v-process, object, o-tag, o-ner, o-type, label \n");
            while ((line = bufferedReader.readLine()) != null) {

                line = line.replaceAll("\\([^\\)]*\\)","");

                Annotation doc = new Annotation(line);
                pipeline.annotate(doc);

                for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {
                    System.out.println("Sentence #" + ++sentNo + ": " + sentence.get(CoreAnnotations.TextAnnotation.class));

                    ArrayList<ClassificationCoreLabel> listOfClassificationPerWord = new ArrayList<ClassificationCoreLabel>();

                    for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                        String word = token.get(CoreAnnotations.TextAnnotation.class);
                        String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                        String ner = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);

                        ClassificationCoreLabel classificationCoreLabel = new ClassificationCoreLabel(word, pos, ner);
                        listOfClassificationPerWord.add(classificationCoreLabel);

                    }

                    // Get the FeatureVectorMerge triples for the sentence
                    Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);

                    // Print the triples
                    for (RelationTriple triple : triples) {

                        FeatureVector featureVector = new FeatureVector();
                        featureVector.setSubject(triple.subjectLemmaGloss());
                        featureVector.setVerb(triple.relationLemmaGloss());
                        featureVector.setObject(triple.objectLemmaGloss());

                        System.out.println(triple.confidence + ":\t" + triple.subjectLemmaGloss() + ":\t"
                                + triple.relationLemmaGloss() + ":\t" + triple.objectLemmaGloss());


                        String[] oieSubjectArray = triple.subjectGloss().split(" ");
                        String[] oieObjectArray = triple.objectGloss().split(" ");
                        String[] oieRelationArray = triple.relationGloss().split(" ");

                        for (ClassificationCoreLabel ccl : listOfClassificationPerWord) {
                            if (ccl.getWord().equals(oieSubjectArray[oieSubjectArray.length - 1])) {
                                featureVector.setSubjectTag(ccl.getPos());
                                featureVector.setSubjectNER((WordType.getType(featureVector.getSubject(), POS.NOUN).equals(LexFile.NOUN_PERSON.getName().toUpperCase())) ? "PERSON" : ccl.getNer());
                            }
                            if (ccl.getWord().equals(oieObjectArray[oieObjectArray.length - 1])) {
                                featureVector.setObjectTag(ccl.getPos());
                                featureVector.setObjectNer(ccl.getNer());
                            }
                            if (ccl.getWord().equals(oieRelationArray[oieRelationArray.length - 1])) {
                                featureVector.setVerbTag(ccl.getPos());
                            }
                        }

                        featureVector.setSubjectType(WordType.getType(featureVector.getSubject(), POS.NOUN));
                        featureVector.setObjectType(WordType.getType(featureVector.getObject(), POS.NOUN));
                        featureVector.setVerbProcess(WordNetApi.getRelationWord(featureVector.getVerb(), POS.VERB).contains("ion") ? "TRUE" : "FALSE");
                        featureVector.setVerbCat(vcat.getVcat(featureVector.getVerb()));

                        sb.append(sentNo + "," + featureVector.toString() + " \n");

                    }
                }
            }
            String fileName = file.getName();
            CSVFile.writeToFeatureVector(sb.toString(), fileName + "-FeatureVector");
        }
    }
}
