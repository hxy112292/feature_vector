package edu.uta.nlp.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hxy
 */
public class FileUtil {


    /**
     * create file
     *
     * @throws IOException
     */
    public static boolean creatTxtFile(String filepath) throws IOException {
        boolean flag = false;
        File filename = new File(filepath);
        if (!filename.exists()) {
            filename.createNewFile();
            flag = true;
        }
        return flag;
    }

    /**
     * write file
     *
     * @param newStr
     *
     * @throws IOException
     */
    public static boolean writeTxtFile(String filePath, String newStr) throws IOException {
        boolean flag = false;
        String filein = newStr + "\r\n";
        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();

            for (int j = 1; (temp = br.readLine()) != null; j++) {
                buf = buf.append(temp);
                buf = buf.append(System.getProperty("line.separator"));
            }
            buf.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            flag = true;
        } catch (IOException e1) {
            throw e1;
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return flag;
    }

    /**
     * get file path
     *
     * @param folder, file
     *
     */
    public static String getFileWithRelativePath(File folder, File file) {
        return folder + File.separator + file.getName();
    }

    /**
     * get file content
     *
     * @param folder, file
     *
     */
    public static List<BufferedReader> readFileInFolder(File folder) throws Exception{

        List<BufferedReader> fileContent = new ArrayList<BufferedReader>();
        for (final File file : folder.listFiles()) {
            String filename = getFileWithRelativePath(folder, file);
            fileContent.add(new BufferedReader(new FileReader(new File(filename))));
        }

        return fileContent;
    }

    /**
     * get file name without ex
     *
     * @param filename
     *
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }
}
