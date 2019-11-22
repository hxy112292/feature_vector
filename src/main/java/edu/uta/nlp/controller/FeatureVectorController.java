package edu.uta.nlp.controller;

import edu.mit.jwi.item.POS;
import edu.stanford.nlp.util.StringUtils;
import edu.uta.nlp.constant.Constants;
import edu.uta.nlp.constant.SynsetType;
import edu.uta.nlp.database.service.FeatureVectorMysqlAccess;
import edu.uta.nlp.database.service.impl.FeatureVectorAccessImpl;
import edu.uta.nlp.entity.ClassificationCoreLabel;
import edu.uta.nlp.entity.FeatureVector;
import edu.uta.nlp.entity.OpenIESimpleLemma;
import edu.uta.nlp.file.CSVFile;
import edu.uta.nlp.file.FilePath;
import edu.uta.nlp.file.ScanFile;
import edu.uta.nlp.iterator.Aggregate;
import edu.uta.nlp.iterator.Iterator;
import edu.uta.nlp.tool.NlpTool;
import edu.uta.nlp.tool.stanford.StanfordTool;
import edu.uta.nlp.util.StrUtil;
import edu.uta.nlp.util.TextFileUtil;
import edu.uta.nlp.weka.WekaProcess;
import edu.uta.nlp.wordnet.Vcat;
import edu.uta.nlp.wordnet.WordNetApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hxy
 */
public class FeatureVectorController {

    private static final Logger logger = LoggerFactory.getLogger(FeatureVectorController.class);

    public static void extractUseCase() throws Exception{

        Aggregate<File> fileAggregate =  new Aggregate();
        fileAggregate.addAll(ScanFile.getFiles(new File(FilePath.getRequirementPath())));
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
            // create a table in mysql
            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHHmmss");
            String tableName = TextFileUtil.getFileNameNoEx(file.getName()) + "_" + simpleDate.format(new Date());
            tableName = StrUtil.filterTableName(tableName);
            FeatureVectorAccessImpl featureVectorAccess = new FeatureVectorMysqlAccess(tableName);
            //csv file header
            StringBuilder sb = new StringBuilder("subject,s-tag,s-ner,s-type,verb,v-tag,v-cat,v-extractUseCase,object,o-tag,o-ner,o-type,label\n");
            while ((line = bufferedReader.readLine()) != null) {

                line = line.replaceAll("\\([^\\)]*\\)","");

                logger.info("#R" + (++sentNo) + ": " + line);

                Aggregate<FeatureVector> featureVectorAggregate =  new Aggregate();

                NlpTool nlptool = new StanfordTool();

                //use openIE to get subject, verb, object gross.
                featureVectorAggregate.addAll(nlptool.selectOpenIEGross(line));
                Iterator<FeatureVector> featureVectorIterator = featureVectorAggregate.getIterator();


                while(featureVectorIterator.hasNext()) {
                    FeatureVector featureVector = (FeatureVector) featureVectorIterator.next();

                    //use openIE to get subject, verb, object lemma.
                    OpenIESimpleLemma openIESimpleLemma = nlptool.selectOpenIELemma(featureVector);
                    String sentence = openIESimpleLemma.toString();
                    Aggregate<ClassificationCoreLabel> coreLabelAggregate = new Aggregate();
                    coreLabelAggregate.addAll(nlptool.generatePosTag(sentence));
                    Iterator<ClassificationCoreLabel> coreLabelIterator = coreLabelAggregate.getIterator();

                    //get pos, ner
                    while (coreLabelIterator.hasNext()) {
                        ClassificationCoreLabel ccl = (ClassificationCoreLabel) coreLabelIterator.next();
                        if (ccl.getWord().equals(openIESimpleLemma.getSubject())) {
                            featureVector.setSubjectTag(ccl.getPos());
                            featureVector.setSubjectNER((WordNetApi.getWordType(featureVector.getSubject(), POS.NOUN).equals("NOUN_PERSON")) ? "PERSON" : ccl.getNer());
                        }
                        if (ccl.getWord().equals(openIESimpleLemma.getObject())) {
                            featureVector.setObjectTag(ccl.getPos());
                            featureVector.setObjectNer(ccl.getNer());
                        }
                        if (ccl.getWord().equals(openIESimpleLemma.getVerb())) {
                            featureVector.setVerbTag(ccl.getPos());
                        }
                    }

                    //get type, extractUseCase
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
                    featureVector.setLabel("NONE");
                    sb.append(StrUtil.chRegex(featureVector.toString()) + "\n");

                    featureVectorAccess.insertFeatureVector(featureVector);
                }
            }
            //wirte to csv file
            CSVFile csvFile = CSVFile.getInstance();
            String fileName = TextFileUtil.getFileNameNoEx(file.getName());
            String filePath = csvFile.writeToCSV(sb.toString(), fileName + "-FeatureVector", Constants.NO_LABEL);

            //extract use case by weka
            WekaProcess.getUseCase(new File(filePath));
        }
    }
}
