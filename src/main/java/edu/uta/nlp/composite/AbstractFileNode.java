package edu.uta.nlp.composite;

import java.io.File;
import java.util.List;

/**
 * @author hxy
 */
public abstract class AbstractFileNode {

    protected File file;

    public AbstractFileNode(File file) {
        this.file = file;
    }

    public void addNode(AbstractFileNode node) throws Exception{
        throw new Exception("Invalid exception");
    }

    public abstract List<File> getAllFiles();

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
