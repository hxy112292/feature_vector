package edu.uta.nlp.stanford;

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
import edu.uta.nlp.util.FileUtil;
import edu.uta.nlp.wordnet.WordNetApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;

/**
 * @author hxy
 */
public class FeatureVectorMerge {

    private static final Logger logger = LoggerFactory.getLogger(FeatureVectorMerge.class);

    public static void generate() throws Exception {

        // Create the Stanford CoreNLP pipeline
        Properties props = PropertiesUtils.asProperties("annotators",
                "tokenize,ssplit,pos,lemma,depparse,natlog,openie,ner");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        File folder = new File(FilePath.getRequirementPath());
        //scan requirements
        scanFile(pipeline, folder, "");
    }
    private static void scanFile(StanfordCoreNLP pipeline, File folder, String fileDirectory) throws Exception {

        for (final File file : folder.listFiles()) {
            if(file.isDirectory()) {
                scanFile(pipeline, file, fileDirectory+File.separator+file.getName());
            } else {
                String fileName = fileDirectory+File.separator+file.getName();
                fileName = FileUtil.getFileNameNoEx(fileName);
                //generate feature vector
                String fileContent = featureVectorProcess(pipeline, file);
                //write to csv file
                CSVFile.writeToFeatureVector(fileContent, fileName + "-FeatureVector");
            }
        }
    }
    private static String featureVectorProcess(StanfordCoreNLP pipeline, File file) throws Exception{
        BufferedReader bufferedReader = (new BufferedReader(new FileReader(file)));
        Vcat vcat = new Vcat();
        // Loop over sentences in the document
        int sentNo = 0;
        String line;
        //csv file header
        StringBuilder sb = new StringBuilder("requirement, subject, s-tag, s-ner, s-type, verb, v-tag, v-cat, v-process, object, o-tag, o-ner, o-type, label \n");
        while ((line = bufferedReader.readLine()) != null) {

            line = line.replaceAll("\\([^\\)]*\\)","");

            logger.info("#R" + (++sentNo) + ": " + line);
            List<FeatureVector> openIEList = OpenIE.generate(pipeline, line);

            for(FeatureVector featureVector : openIEList) {

                //use openIE to get subject, verb, object
                OpenIESimpleLemma openIESimpleLemma = OpenIE.selectLemma(pipeline, featureVector);
                String sentence = openIESimpleLemma.toString();
                List<ClassificationCoreLabel> listOfClassificationPerWord = CoreAnnotate.generate(pipeline, sentence);

                //get pos, ner
                for (ClassificationCoreLabel ccl : listOfClassificationPerWord) {
                    if (ccl.getWord().equals(openIESimpleLemma.getSubject())) {
                        featureVector.setSubjectTag(ccl.getPos());
                        featureVector.setSubjectNER((WordType.getType(featureVector.getSubject(), POS.NOUN).equals(LexFile.NOUN_PERSON.getName().toUpperCase())) ? "PERSON" : ccl.getNer());
                    }
                    if (ccl.getWord().equals(openIESimpleLemma.getObject())) {
                        featureVector.setObjectTag(ccl.getPos());
                        featureVector.setObjectNer(ccl.getNer());
                    }
                    if (ccl.getWord().equals(openIESimpleLemma.getVerb())) {
                        featureVector.setVerbTag(ccl.getPos());
                    }
                }
                featureVector.setSubjectType(WordType.getType(featureVector.getSubject(), POS.NOUN));
                featureVector.setObjectType(WordType.getType(featureVector.getObject(), POS.NOUN));
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
