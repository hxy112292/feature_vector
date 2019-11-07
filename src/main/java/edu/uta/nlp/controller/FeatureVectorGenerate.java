package edu.uta.nlp.controller;

import edu.mit.jwi.item.LexFile;
import edu.mit.jwi.item.POS;
import edu.stanford.nlp.util.StringUtils;
import edu.uta.nlp.NlpTool.JieBaTool;
import edu.uta.nlp.NlpTool.NlpTool;
import edu.uta.nlp.NlpTool.StanfordTool;
import edu.uta.nlp.constant.SynsetType;
import edu.uta.nlp.entity.ClassificationCoreLabel;
import edu.uta.nlp.entity.FeatureVector;
import edu.uta.nlp.entity.OpenIESimpleLemma;
import edu.uta.nlp.file.CSVFile;
import edu.uta.nlp.file.FilePath;
import edu.uta.nlp.file.ScanFile;
import edu.uta.nlp.iterator.Aggregate;
import edu.uta.nlp.iterator.Iterator;
import edu.uta.nlp.stanford.CoreAnnotate;
import edu.uta.nlp.stanford.OpenIE;
import edu.uta.nlp.wordnet.Vcat;
import edu.uta.nlp.wordnet.WordNetApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

/**
 * @author hxy
 */
public class FeatureVectorGenerate {

    private static final Logger logger = LoggerFactory.getLogger(FeatureVectorGenerate.class);

    public static void process() throws Exception{

        Aggregate<File> fileAggregate =  new Aggregate();
        fileAggregate.addAll(ScanFile.getRequirements());
        Iterator<File> fileIterator = fileAggregate.getIterator();
        while (fileIterator.hasNext()) {
            File file = (File)fileIterator.next();
            if(file.isDirectory()) {
                continue;
            }
            BufferedReader bufferedReader = (new BufferedReader(new FileReader(file)));
            Vcat vcat = Vcat.getInstance();
            // Loop over sentences in the document
            int sentNo = 0;
            // line in file
            String line;
            //csv file header
            StringBuilder sb = new StringBuilder("requirement, subject, s-tag, s-ner, s-type, verb, v-tag, v-cat, v-process, object, o-tag, o-ner, o-type, label \n");
            while ((line = bufferedReader.readLine()) != null) {

                line = line.replaceAll("\\([^\\)]*\\)","");

                logger.info("#R" + (++sentNo) + ": " + line);

                Aggregate<FeatureVector> featureVectorAggregate =  new Aggregate();
                //use openIE to get subject, verb, object gross.
                NlpTool nlptool = new StanfordTool();
                //NlpTool nlptool = new JieBaTool();

                featureVectorAggregate.addAll(nlptool.selectGross(line));
                Iterator<FeatureVector> featureVectorIterator = featureVectorAggregate.getIterator();


                while(featureVectorIterator.hasNext()) {
                    FeatureVector featureVector = (FeatureVector) featureVectorIterator.next();

                    //use openIE to get subject, verb, object lemma.
                    OpenIESimpleLemma openIESimpleLemma = nlptool.selectLemma(featureVector);
                    String sentence = openIESimpleLemma.toString();
                    Aggregate<ClassificationCoreLabel> coreLabelAggregate = new Aggregate();
                    coreLabelAggregate.addAll(nlptool.generate(sentence));
                    Iterator<ClassificationCoreLabel> coreLabelIterator = coreLabelAggregate.getIterator();

                    //get pos, ner
                    while (coreLabelIterator.hasNext()) {
                        ClassificationCoreLabel ccl = (ClassificationCoreLabel) coreLabelIterator.next();
                        if (ccl.getWord().equals(openIESimpleLemma.getSubject())) {
                            featureVector.setSubjectTag(ccl.getPos());
                            featureVector.setSubjectNER((WordNetApi.getWordType(featureVector.getSubject(), POS.NOUN).equals(LexFile.NOUN_PERSON.getName().toUpperCase())) ? "PERSON" : ccl.getNer());
                        }
                        if (ccl.getWord().equals(openIESimpleLemma.getObject())) {
                            featureVector.setObjectTag(ccl.getPos());
                            featureVector.setObjectNer(ccl.getNer());
                        }
                        if (ccl.getWord().equals(openIESimpleLemma.getVerb())) {
                            featureVector.setVerbTag(ccl.getPos());
                        }
                    }
                    featureVector.setSubjectType(WordNetApi.getWordType(featureVector.getSubject(), POS.NOUN));
                    featureVector.setObjectType(WordNetApi.getWordType(featureVector.getObject(), POS.NOUN));
                    featureVector.setVerbProcess(WordNetApi.getRelationWord(openIESimpleLemma.getVerb(), POS.VERB).contains("ion") ? "TRUE" : "FALSE");
                    if(!StringUtils.isNullOrEmpty(openIESimpleLemma.getVcat())) {
                        featureVector.setVerbCat(openIESimpleLemma.getVcat());
                    } else {
                        featureVector.setVerbCat(vcat.getVcat(openIESimpleLemma.getVerb()));
                    }
                    if(featureVector.getVerbCat() == SynsetType.TV.toString() && featureVector.getVerbTag().charAt(0) != 'V') {
                        featureVector.setVerbTag("VB");
                    }

                    featureVector.setRequirement(String.valueOf(sentNo));
                    sb.append(featureVector.toString() + " \n");
                }
            }
            CSVFile csvFile = CSVFile.getInstance();
            String fileName = file.getAbsolutePath().substring(FilePath.getRequirementPath().length());
            csvFile.writeToFeatureVector(sb.toString(), fileName + "-FeatureVector");
        }
    }
}
