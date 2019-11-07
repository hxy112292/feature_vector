package edu.uta.nlp.database.command;

import edu.stanford.nlp.util.StringUtils;
import edu.uta.nlp.constant.DataBaseType;
import edu.uta.nlp.database.factory.AbstractDataBase;
import edu.uta.nlp.database.factory.DataBaseFactory;
import edu.uta.nlp.entity.FeatureVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hxy
 */
public class InsertFeatureVector implements MysqlCmd{

    private FeatureVector featureVector;

    private static Logger logger = LoggerFactory.getLogger(InsertFeatureVector.class);

    public InsertFeatureVector(FeatureVector featureVector) {
        this.featureVector = featureVector;
    }

    @Override
    public Object execute() throws Exception {

        StringBuilder sb = new StringBuilder("insert into feature_vector(");
        if (!StringUtils.isNullOrEmpty(featureVector.getRequirement())) {
            sb.append("requirement,");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getSubject())) {
            sb.append("subject,");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getSubjectTag())) {
            sb.append("subject_tag,");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getSubjectNER())) {
            sb.append("subject_ner,");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getSubjectType())) {
            sb.append("subject_type,");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getVerb())) {
            sb.append("verb,");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getVerbTag())) {
            sb.append("verb_tag,");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getVerbCat())) {
            sb.append("verb_cat,");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getVerbProcess())) {
            sb.append("verb_process,");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getObject())) {
            sb.append("object,");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getObjectTag())) {
            sb.append("object_tag,");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getObjectNer())) {
            sb.append("object_ner,");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getObjectType())) {
            sb.append("object_type,");
        }

        sb.append("create_time) values (");

        if (!StringUtils.isNullOrEmpty(featureVector.getRequirement())) {
            sb.append("'" + featureVector.getRequirement() + "',");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getSubject())) {
            sb.append("'" + featureVector.getSubject() + "',");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getSubjectTag())) {
            sb.append("'" + featureVector.getSubject() + "',");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getSubjectNER())) {
            sb.append("'" + featureVector.getSubjectNER() + "',");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getSubjectType())) {
            sb.append("'" + featureVector.getSubjectType() + "',");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getVerb())) {
            sb.append("'" + featureVector.getVerb() + "',");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getVerbTag())) {
            sb.append("'" + featureVector.getVerbTag() + "',");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getVerbCat())) {
            sb.append("'" + featureVector.getVerbCat() + "',");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getVerbProcess())) {
            sb.append("'" + featureVector.getVerbProcess() + "',");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getObject())) {
            sb.append("'" + featureVector.getObject() + "',");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getObjectTag())) {
            sb.append("'" + featureVector.getObjectTag() + "',");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getObjectNer())) {
            sb.append("'" + featureVector.getObjectNer() + "',");
        }
        if (!StringUtils.isNullOrEmpty(featureVector.getObjectType())) {
            sb.append("'" + featureVector.getObjectType() + "',");
        }
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = simpleDate.format(new Date());
        sb.append("'" + timestamp + "')");

        logger.info("insert feature vector: " + sb.toString());

        DataBaseFactory dataBaseFactory = new DataBaseFactory();
        AbstractDataBase database = dataBaseFactory.getDatabase(DataBaseType.DATABASE_TYPE_MYSQL);
        database.open();
        Object result = database.executeSqlCmd(sb.toString());

        logger.info("insert feature vector result: " + result.toString());

        database.close();

        return result;
    }
}
