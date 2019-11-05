package edu.uta.nlp.file;

import edu.uta.nlp.util.FileUtil;
import edu.uta.nlp.util.PropertiesUtil;
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


    public void writeToFeatureVector(String content, String filename) throws Exception{

        String rootDir = FilePath.getResultPath();

        DateTime dt = new DateTime(new Date());
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd-HH-mm");
        String outputFilename = rootDir + File.separator + filename + "-" + dt.toString(dtf) + ".csv";

        FileUtil.creatTxtFile(outputFilename);
        FileUtil.writeTxtFile(outputFilename, content);

        logger.info("Save file:"+ outputFilename);
    }
}
