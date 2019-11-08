package edu.uta.nlp;

import edu.uta.nlp.controller.FeatureVectorGenerate;
import edu.uta.nlp.database.service.FeatureVectorMysqlAccess;
import edu.uta.nlp.database.service.impl.FeatureVectorAccessImpl;
import edu.uta.nlp.entity.FeatureVector;
import edu.uta.nlp.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hxy
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        try {
            FeatureVectorGenerate.process();
//            logger.info(StrUtil.chRegex("S_1234&12"));
//            FeatureVectorAccessImpl featureVectorAccess = new FeatureVectorMysqlAccess("team6_merged_requirements_20191107192541");
//
//            FeatureVector find = new FeatureVector();
//            find.setId(17);
//
//            System.out.println(featureVectorAccess.selectFeatureVectorBySelective(find));
//
//            FeatureVector update = new FeatureVector();
//            update.setId(17);
//            update.setSubject("user");
//
//            featureVectorAccess.updateFeatureVectorBySelective(update);
//            String s = "123&-_12ABC";
//            System.out.println(s.replaceAll("[^_a-zA-Z0-9]", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
