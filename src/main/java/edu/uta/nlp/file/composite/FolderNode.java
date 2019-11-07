package edu.uta.nlp.file.composite;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hxy
 */
public class FolderNode extends AbstractFileNode{

    List<AbstractFileNode> nodeList = new ArrayList<>();

    public FolderNode(File file) {
        super(file);
    }

    @Override
    public void addNode(AbstractFileNode node) throws Exception {
        nodeList.add(node);
    }

    @Override
    public List<File> getAllFiles() {

        List<File> allFiles = new ArrayList<>();

        allFiles.add(file);

        for(AbstractFileNode node : nodeList) {
            allFiles.addAll(node.getAllFiles());
        }

        return allFiles;
    }

    public List<AbstractFileNode> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<AbstractFileNode> nodeList) {
        this.nodeList = nodeList;
    }
}
