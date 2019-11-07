package edu.uta.nlp.database.service;

import edu.uta.nlp.database.command.*;
import edu.uta.nlp.database.service.impl.FeatureVectorAccessImpl;
import edu.uta.nlp.entity.FeatureVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author hxy
 */
public class FeatureVectorMysqlAccess implements FeatureVectorAccessImpl {

    private static Logger logger = LoggerFactory.getLogger(FeatureVector.class);

    @Override
    public List<FeatureVector> selectFeatureVectorBySelective(FeatureVector featureVector) throws Exception{

        GetFeatureVector getFeatureVector = new GetFeatureVector(featureVector);

        return (List<FeatureVector>) getFeatureVector.execute();
    }

    @Override
    public int insertFeatureVector(FeatureVector featureVector) throws Exception {

        InsertFeatureVector insertFeatureVector = new InsertFeatureVector(featureVector);

        return (int) insertFeatureVector.execute();
    }

    @Override
    public int deleteFeatureVector(Integer id) throws Exception {

        DeleteFeatureVector deleteFeatureVector = new DeleteFeatureVector(id);

        return (int) deleteFeatureVector.execute();
    }

    @Override
    public int updateFeatureVectorBySelective(FeatureVector featureVector) throws Exception {

        UpdateFeatureVector updateFeatureVector = new UpdateFeatureVector(featureVector);

        return (int) updateFeatureVector.execute();
    }
}
