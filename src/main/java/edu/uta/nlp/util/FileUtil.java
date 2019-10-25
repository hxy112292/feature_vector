package edu.uta.nlp.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hxy
 */
public class FileUtil {


    /**
     * 创建文件
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
     * 写文件
     *
     * @param newStr
     *      新内容
     * @throws IOException
     */
    public static boolean writeTxtFile(String filePath, String newStr) throws IOException {
        // 先读取原有文件内容，然后进行写入操作
        boolean flag = false;
        String filein = newStr + "\r\n";
        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            // 文件路径
            File file = new File(filePath);
            // 将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();

            // 保存该文件原有的内容
            for (int j = 1; (temp = br.readLine()) != null; j++) {
                buf = buf.append(temp);
                // System.getProperty("line.separator")
                // 行与行之间的分隔符 相当于“\n”
                buf = buf.append(System.getProperty("line.separator"));
            }
            buf.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            flag = true;
        } catch (IOException e1) {
            // TODO 自动生成 catch 块
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
     * 获取文件夹下的文件路径
     *
     * @param folder， file
     *      新内容
     */
    public static String getFileWithRelativePath(File folder, File file) {
        return folder + File.separator + file.getName();
    }

    /**
     * 读取文件夹下的文件内容
     *
     * @param folder， file
     *      新内容
     */
    public static List<BufferedReader> readFileInFolder(File folder) throws Exception{

        List<BufferedReader> fileContent = new ArrayList<BufferedReader>();
        for (final File file : folder.listFiles()) {
            String filename = getFileWithRelativePath(folder, file);
            fileContent.add(new BufferedReader(new FileReader(new File(filename))));
        }

        return fileContent;
    }

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
