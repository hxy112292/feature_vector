package edu.uta.nlp.controller;

import edu.mit.jwi.item.LexFile;
import edu.mit.jwi.item.POS;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.PropertiesUtils;
import edu.stanford.nlp.util.StringUtils;
import edu.uta.nlp.constant.SynsetType;
import edu.uta.nlp.entity.ClassificationCoreLabel;
import edu.uta.nlp.entity.FeatureVector;
import edu.uta.nlp.entity.OpenIESimpleLemma;
import edu.uta.nlp.file.CSVFile;
import edu.uta.nlp.file.FilePath;
import edu.uta.nlp.iterator.Aggregate;
import edu.uta.nlp.iterator.Iterator;
import edu.uta.nlp.stanford.CoreAnnotate;
import edu.uta.nlp.stanford.OpenIE;
import edu.uta.nlp.stanford.Pipeline;
import edu.uta.nlp.util.FileUtil;
import edu.uta.nlp.wordnet.Vcat;
import edu.uta.nlp.wordnet.WordNetApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * @author hxy
 */
public class FeatureVectorGenerate {

    private static final Logger logger = LoggerFactory.getLogger(FeatureVectorGenerate.class);

    public static void generate() throws Exception {
        File folder = new File(FilePath.getRequirementPath());
        //scan requirements
        scanFile(folder, "");
    }
    private static void scanFile(File folder, String fileDirectory) throws Exception {

        for (final File file : folder.listFiles()) {
            if(file.isDirectory()) {
                scanFile(file, fileDirectory+File.separator+file.getName());
            } else {
                String fileName = fileDirectory+File.separator+file.getName();
                fileName = FileUtil.getFileNameNoEx(fileName);
                //generate feature vector
                String fileContent = featureVectorProcess(file);
                //write to csv file
                CSVFile csvFile = CSVFile.getInstance();
                csvFile.writeToFeatureVector(fileContent, fileName + "-FeatureVector");
            }
        }
    }
    private static String featureVectorProcess(File file) throws Exception{
        BufferedReader bufferedReader = (new BufferedReader(new FileReader(file)));
        Vcat vcat = Vcat.getInstance();
        // Loop over sentences in the document
        int sentNo = 0;
        // line in file
        String line;
        //csv file header
        StringBuilder sb = new StringBuilder("requirement, subject, s-tag, s-ner, s-type, verb, v-tag, v-cat, v-controller, object, o-tag, o-ner, o-type, label \n");
        while ((line = bufferedReader.readLine()) != null) {

            line = line.replaceAll("\\([^\\)]*\\)","");

            logger.info("#R" + (++sentNo) + ": " + line);

            //iterator mode
            Aggregate<FeatureVector> featureVectorAggregate =  new Aggregate();
            featureVectorAggregate.addAll(OpenIE.generate(line));
            Iterator<FeatureVector> featureVectorIterator = featureVectorAggregate.getIterator();

            while(featureVectorIterator.hasNext()) {
                FeatureVector featureVector = (FeatureVector) featureVectorIterator.next();

                //use openIE to get subject, verb, object
                OpenIESimpleLemma openIESimpleLemma = OpenIE.selectLemma(featureVector);
                String sentence = openIESimpleLemma.toString();
                Aggregate<ClassificationCoreLabel> coreLabelAggregate = new Aggregate();
                coreLabelAggregate.addAll(CoreAnnotate.generate(sentence));
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

                sb.append(sentNo + "," + featureVector.toString() + " \n");
            }
        }
        return sb.toString();
    }
}
