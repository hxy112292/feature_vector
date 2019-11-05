package edu.uta.nlp.file;

import edu.uta.nlp.composite.AbstractFileNode;
import edu.uta.nlp.composite.FileNode;
import edu.uta.nlp.composite.FolderNode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hxy
 */
public class ScanFile {

    private static void scan(AbstractFileNode node) throws Exception{

        File folder = node.getFile();
        for(File file: folder.listFiles()) {
            if(file.isFile()) {
                FileNode fileNode = new FileNode(file);
                node.addNode(fileNode);
            }
            if(file.isDirectory()) {
                FolderNode folderNode = new FolderNode(file);
                node.addNode(folderNode);
                scan(folderNode);
            }
        }
    }

    public static List<File> getRequirements() throws Exception{
        File folder = new File(FilePath.getRequirementPath());
        AbstractFileNode noder = new FolderNode(folder);
        scan(noder);
        List<File> fileList = new ArrayList<>();
        fileList.addAll(noder.getAllFiles());
        return fileList;
    }
}
