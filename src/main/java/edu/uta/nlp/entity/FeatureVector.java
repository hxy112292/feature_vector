package edu.uta.nlp.entity;

import java.util.Date;

/**
 * @author hxy
 */
public class FeatureVector implements Cloneable{

    private Integer id;
    private String requirement;
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
    private Date updateTime;
    private Date createTime;

    @Override
    public String toString() {
        return requirement + "," + subject + "," + subjectTag + "," + subjectNER + "," + subjectType + ","
                + verb + "," + verbTag + "," + verbCat + "," + verbProcess + ","
                + object + "," + objectTag + "," + objectNer + "," + objectType;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}
