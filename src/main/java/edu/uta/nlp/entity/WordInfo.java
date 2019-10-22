package edu.uta.nlp.entity;

/**
 * @author hxy
 */
public class WordInfo {

    private String gloss;

    private String lexFileName;

    private String type;

    @Override
    public String toString() {
        return "{" + gloss + "," + lexFileName + "," + type +"}";
    }

    public String getGloss() {
        return gloss;
    }

    public void setGloss(String gloss) {
        this.gloss = gloss;
    }

    public String getLexFileName() {
        return lexFileName;
    }

    public void setLexFileName(String lexFileName) {
        this.lexFileName = lexFileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
