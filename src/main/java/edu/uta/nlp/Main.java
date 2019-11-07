package edu.uta.nlp;

import edu.uta.nlp.database.service.FeatureVectorMysqlAccess;
import edu.uta.nlp.database.service.impl.FeatureVectorAccessImpl;
import edu.uta.nlp.entity.FeatureVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hxy
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        try {
//            FeatureVectorGenerate.process();
            FeatureVectorAccessImpl featureVectorMysqlAccess = new FeatureVectorMysqlAccess();

            FeatureVector insert = new FeatureVector();
            insert.setRequirement("2");
            insert.setSubject("user");
            insert.setVerb("do");
            insert.setObject("something");
            featureVectorMysqlAccess.insertFeatureVector(insert);

            FeatureVector findCondition = new FeatureVector();
            findCondition.setSubject("user");
            featureVectorMysqlAccess.selectFeatureVectorBySelective(findCondition);

            FeatureVector update = new FeatureVector();
            update.setSubject("customer");
            update.setId(1);
            featureVectorMysqlAccess.updateFeatureVectorBySelective(update);

            featureVectorMysqlAccess.deleteFeatureVector(3);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
