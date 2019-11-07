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
public class UpdateFeatureVector implements MysqlCmd{

    private FeatureVector featureVector;

    private static Logger logger = LoggerFactory.getLogger(UpdateFeatureVector.class);

    public UpdateFeatureVector(FeatureVector featureVector) {
        this.featureVector = featureVector;
    }

    @Override
    public Object execute() throws Exception {

        StringBuilder sb = new StringBuilder("update feature_vector set ");
        if(!StringUtils.isNullOrEmpty(featureVector.getRequirement())) {
            sb.append("requirement = '" + featureVector.getRequirement() + "',");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getSubject())) {
            sb.append("subject = '" + featureVector.getSubject() + "',");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getSubjectNER())) {
            sb.append("subject_ner = '" + featureVector.getSubjectNER() + "',");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getSubjectTag())) {
            sb.append("subject_tag = '" + featureVector.getSubjectTag() + "',");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getSubjectType())) {
            sb.append("subject_type = '" + featureVector.getSubjectType() + "',");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getVerb())) {
            sb.append("verb = '" + featureVector.getVerb() + "',");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getVerbTag())) {
            sb.append("verb_tag = '" + featureVector.getVerbTag() + "',");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getVerbCat())) {
            sb.append("verb_cat = '" + featureVector.getVerbCat() + "',");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getVerbProcess())) {
            sb.append("verb_process = '" + featureVector.getVerbProcess() + "',");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getObject())) {
            sb.append("object = '" + featureVector.getObject() + "',");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getObjectNer())) {
            sb.append("object_ner = '" + featureVector.getObjectNer() + "',");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getObjectTag())) {
            sb.append("object_tag = '" + featureVector.getObjectTag() + "',");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getObjectType())) {
            sb.append("object_type = '" + featureVector.getObjectType() + "',");
        }
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = simpleDate.format(new Date());
        sb.append("update_time = '" + timestamp + "'");
        sb.append("where id='" + featureVector.getId() + "'");

        logger.info("update feature vector: " + sb.toString());

        DataBaseFactory dataBaseFactory = new DataBaseFactory();
        AbstractDataBase database = dataBaseFactory.getDatabase(DataBaseType.DATABASE_TYPE_MYSQL);
        database.open();
        Object result = database.executeSqlCmd(sb.toString());

        logger.info("update feature vector result: " + result.toString());

        database.close();
        return result;
    }
}
