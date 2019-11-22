package edu.uta.nlp.weka;

import edu.uta.nlp.constant.Constants;
import edu.uta.nlp.file.CSVFile;
import edu.uta.nlp.util.TextFileUtil;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.File;

/**
 * @author hxy
 */
public class WekaProcess {

    public static void getUseCase(File file) throws Exception{

        Model model = Model.getInstance();
        Classifier cls = model.getCls();
        CSVLoader csvLoader = new CSVLoader();
        csvLoader.setSource(file);
        Instances unlabeled = csvLoader.getDataSet();
        unlabeled.setClassIndex(unlabeled.numAttributes() - 1);

        StringBuilder sbLabel = new StringBuilder("subject,s-tag,s-ner,s-type,verb,v-tag,v-cat,v-extractUseCase,object,o-tag,o-ner,o-type,label\n");
        StringBuilder sbUseCase = new StringBuilder("Num,Use Case\n");
        int senNo = 0;

        for (int i = 0; i < unlabeled.numInstances(); i++) {
            double classification = cls.classifyInstance(unlabeled.instance(i));
            double classValue = unlabeled.instance(i).classValue();
            Instance instance = unlabeled.instance(i);
            String featureVector = "" + instance.toString();
            if (classification == 0.0 && classValue == 0.0) {
                featureVector = featureVector.replace(",NONE", ",1");
                sbUseCase.append((++senNo) + "," + instance.stringValue(0) + " " + instance.stringValue(4) + " "
                        + instance.stringValue(8) + "\n");
            } else {
                featureVector = featureVector.replace(",NONE", ",0");
            }
            sbLabel.append(featureVector + "\n");
        }

        //wirte to csv file
        CSVFile csvFile = CSVFile.getInstance();
        String fileName = TextFileUtil.getFileNameNoEx(file.getName());
        csvFile.writeToCSV(sbLabel.toString(), fileName, Constants.LABEL);
        csvFile.writeToCSV(sbUseCase.toString(), "Use Case-" + fileName, Constants.USE_CASE);
    }
}
