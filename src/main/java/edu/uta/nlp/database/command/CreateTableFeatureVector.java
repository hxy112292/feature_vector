package edu.uta.nlp.database.command;

import edu.uta.nlp.constant.Constants;
import edu.uta.nlp.database.factory.AbstractDataBase;
import edu.uta.nlp.database.factory.DataBaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hxy
 */
public class CreateTableFeatureVector implements MysqlCmd{

    private String tableName;

    private static Logger logger = LoggerFactory.getLogger(CreateTableFeatureVector.class);

    public CreateTableFeatureVector(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public Object execute() throws Exception {

        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS`" + tableName + "` (");
        sb.append("`id` int(11) NOT NULL AUTO_INCREMENT,");
        sb.append("`subject` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,");
        sb.append("`subject_tag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,");
        sb.append("`subject_ner` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,");
        sb.append("`subject_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,");
        sb.append("`verb` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,");
        sb.append("`verb_tag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,");
        sb.append("`verb_cat` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,");
        sb.append("`verb_process` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,");
        sb.append("`object` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,");
        sb.append("`object_tag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,");
        sb.append("`object_ner` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,");
        sb.append("`object_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,");
        sb.append("`label` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,");
        sb.append("`create_time` datetime(0) NULL DEFAULT NULL,");
        sb.append("`update_time` datetime(0) NULL DEFAULT NULL,");
        sb.append("PRIMARY KEY (`id`) USING BTREE");
        sb.append(") ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;");

        logger.info("create table " + tableName + ": " + sb.toString());

        DataBaseFactory dataBaseFactory = new DataBaseFactory();
        AbstractDataBase database = dataBaseFactory.getDatabase(Constants.DATABASE_TYPE_MYSQL);
        Object result = database.operation(sb.toString());

        logger.info("create table " + tableName + " result: " + result.toString());

        return result;
    }
}
