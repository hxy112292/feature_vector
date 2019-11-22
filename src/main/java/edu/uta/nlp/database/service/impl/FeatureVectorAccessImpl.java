package edu.uta.nlp.database.service.impl;

import edu.uta.nlp.entity.FeatureVector;

import java.util.List;

/**
 * @author hxy
 */
public interface FeatureVectorAccessImpl {

    public List<FeatureVector> selectFeatureVectorBySelective(FeatureVector featureVector) throws Exception;

    public int insertFeatureVector(FeatureVector featureVector) throws Exception;

    public int deleteFeatureVector(Integer id) throws Exception;

    public int updateFeatureVectorBySelective(FeatureVector featureVector) throws Exception;

}
