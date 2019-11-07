package edu.uta.nlp.database.service;

import edu.uta.nlp.constant.DataBaseType;
import edu.uta.nlp.database.factory.AbstractDataBase;
import edu.uta.nlp.database.factory.DataBaseFactory;
import edu.uta.nlp.entity.FeatureVector;
import edu.uta.nlp.util.ResultSetToList;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author hxy
 */
public class FeatureVectorService {

    public void test() throws Exception{
        DataBaseFactory dataBaseFactory = new DataBaseFactory();

        AbstractDataBase database = dataBaseFactory.getDatabase(DataBaseType.DATABASE_TYPE_MYSQL);

        database.setDriver("com.mysql.jdbc.Driver");
        database.setUser("root");
        database.setPassword("root");
        database.setUrl("jdbc:mysql://localhost:3306/feature_vector");

        database.open();

        Object result = database.excuteSqlCmd("select * from feature_vector");

        List<FeatureVector> featureVectorList = ResultSetToList.exchangeData((ResultSet) result, FeatureVector.class);

        System.out.println(featureVectorList);

        database.close();
    }
}
