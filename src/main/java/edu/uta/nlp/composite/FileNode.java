package edu.uta.nlp.composite;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * @author hxy
 */
public class FileNode extends AbstractFileNode{

    public FileNode(File file) {
        super(file);
    }

    @Override
    public List<File> getAllFiles() {
        return Collections.singletonList(file);
    }
}
