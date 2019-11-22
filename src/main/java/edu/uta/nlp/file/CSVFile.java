package edu.uta.nlp.file;

import edu.uta.nlp.constant.Constants;
import edu.uta.nlp.util.TextFileUtil;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;

/**
 * @author hxy
 */
public class CSVFile {

    private static final Logger logger = LoggerFactory.getLogger(CSVFile.class);

    private static volatile CSVFile instance;

    public static synchronized CSVFile getInstance() {
        if(instance == null) {
            instance = new CSVFile();
        }
        return instance;
    }


    public String  writeToCSV(String content, String filename, String label) throws Exception{

        String rootDir;
        if(label.equals(Constants.LABEL)) {
            rootDir = FilePath.getLabeledResultPath();
        } else if(label.equals(Constants.NO_LABEL)){
            rootDir = FilePath.getUnLabeledResultPath();
        } else {
            rootDir = FilePath.getUseCasePath();
        }

        DateTime dt = new DateTime(new Date());
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd-HH-mm");
        String outputFilename = rootDir + File.separator + filename + "." + dt.toString(dtf) + ".csv";

        TextFileUtil.creatTxtFile(outputFilename);
        TextFileUtil.writeTxtFile(outputFilename, content);

        logger.info("Save file:"+ outputFilename);

        return outputFilename;
    }
}
