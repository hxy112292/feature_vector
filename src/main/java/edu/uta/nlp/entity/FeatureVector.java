package edu.uta.nlp.entity;

/**
 * @author hxy
 */
public class FeatureVector {

    private String subject;
    private String subjectTag;
    private String subjectNER;
    private String subjectType;
    private String verb;
    private String verbTag;
    private String verbCat;
    private String verbProcess;
    private String object;
    private String objectTag;
    private String objectNer;
    private String objectType;
    private Integer label;

    @Override
    public String toString() {
        return subject + "," + subjectTag + "," + subjectNER + "," + subjectType + ","
                + verb + "," + verbTag + "," + verbCat + "," + verbProcess + ","
                + object + "," + objectTag + "," + objectNer + "," + objectType + ","
                + label;
    }

    public Integer getLabel() {
        return label;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubjectTag() {
        return subjectTag;
    }

    public void setSubjectTag(String subjectTag) {
        this.subjectTag = subjectTag;
    }

    public String getSubjectNER() {
        return subjectNER;
    }

    public void setSubjectNER(String subjectNER) {
        this.subjectNER = subjectNER;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public String getVerbTag() {
        return verbTag;
    }

    public void setVerbTag(String verbTag) {
        this.verbTag = verbTag;
    }

    public String getVerbCat() {
        return verbCat;
    }

    public void setVerbCat(String verbCat) {
        this.verbCat = verbCat;
    }

    public String getVerbProcess() {
        return verbProcess;
    }

    public void setVerbProcess(String verbProcess) {
        this.verbProcess = verbProcess;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getObjectTag() {
        return objectTag;
    }

    public void setObjectTag(String objectTag) {
        this.objectTag = objectTag;
    }

    public String getObjectNer() {
        return objectNer;
    }

    public void setObjectNer(String objectNer) {
        this.objectNer = objectNer;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }
}
