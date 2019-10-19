package edu.uta.nlp.file;

import edu.uta.nlp.util.FileUtil;
import edu.uta.nlp.util.PropertiesUtil;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * @author hxy
 */
public class CSVFile {


    public static void writeToFeatureVector(String content, String filename) throws Exception{

        String rootDir = PropertiesUtil.getPropery("result.dir");

        DateTime dt = new DateTime(new Date());
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd-HH-mm");
        String outputFilename = rootDir + "\\" + filename + "-" + dt.toString(dtf) + ".csv";

        FileUtil.creatTxtFile(outputFilename);
        FileUtil.writeTxtFile(outputFilename, content);

        System.out.println("\nSave file:"+ outputFilename);
    }
}
