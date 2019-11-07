package edu.uta.nlp.database.command;

import edu.stanford.nlp.util.StringUtils;
import edu.uta.nlp.constant.DataBaseType;
import edu.uta.nlp.database.factory.AbstractDataBase;
import edu.uta.nlp.database.factory.DataBaseFactory;
import edu.uta.nlp.entity.FeatureVector;
import edu.uta.nlp.util.ResultSetToList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author hxy
 */
public class GetFeatureVector implements MysqlCmd {

    FeatureVector featureVector;

    private static Logger logger = LoggerFactory.getLogger(GetFeatureVector.class);

    public GetFeatureVector(FeatureVector featureVector) {
        this.featureVector = featureVector;
    }

    @Override
    public Object execute() throws Exception{

        StringBuilder sb = new StringBuilder("select * from feature_vector where 1 = 1");
        if(featureVector.getId() != null) {
            sb.append(" and id = " + featureVector.getId());
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getRequirement())) {
            sb.append(" and requirement = '" + featureVector.getRequirement() + "'");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getSubject())) {
            sb.append(" and subject = '" + featureVector.getSubject() + "'");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getSubjectNER())) {
            sb.append(" and subject_ner = '" + featureVector.getSubjectNER() + "'");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getSubjectTag())) {
            sb.append(" and subject_tag = '" + featureVector.getSubjectTag() + "'");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getSubjectType())) {
            sb.append(" and subject_type = '" + featureVector.getSubjectType() + "'");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getVerb())) {
            sb.append(" and verb = '" + featureVector.getVerb() + "'");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getVerbTag())) {
            sb.append(" and verb_tag = '" + featureVector.getVerbTag() + "'");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getVerbCat())) {
            sb.append(" and verb_cat = '" + featureVector.getVerbCat() + "'");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getVerbProcess())) {
            sb.append(" and verb_process = '" + featureVector.getVerbProcess() + "'");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getObject())) {
            sb.append(" and object = '" + featureVector.getObject() + "'");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getObjectNer())) {
            sb.append(" and object_ner = '" + featureVector.getObjectNer() + "'");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getObjectTag())) {
            sb.append(" and object_tag = '" + featureVector.getObjectTag() + "'");
        }
        if(!StringUtils.isNullOrEmpty(featureVector.getObjectType())) {
            sb.append(" and object_type = '" + featureVector.getObjectType() + "'");
        }

        logger.info("get feature vector: " + sb.toString());

        DataBaseFactory dataBaseFactory = new DataBaseFactory();
        AbstractDataBase database = dataBaseFactory.getDatabase(DataBaseType.DATABASE_TYPE_MYSQL);
        database.open();
        Object result = database.executeSqlCmd(sb.toString());
        List<FeatureVector> featureVectorList = ResultSetToList.exchangeData((ResultSet) result, FeatureVector.class);

        logger.info("get feature vector result: " + featureVectorList.toString());

        database.close();
        return featureVectorList;
    }
}
