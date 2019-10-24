package edu.uta.nlp.entity;

/**
 * @author hxy
 */
public class OpenIESimpleLemma {

    private String subject;

    private String verb;

    private String vcat;

    private String object;

    @Override
    public String toString() {
        return subject + " " + verb + " " + object + ".";
    }

    public String getVcat() {
        return vcat;
    }

    public void setVcat(String vcat) {
        this.vcat = vcat;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
}
