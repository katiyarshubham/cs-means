package com.algo.csmean.service;

import java.io.BufferedReader;

import com.algo.csmean.model.response.ClusterDataResponse;

public interface CSMeanService {

	ClusterDataResponse createClusters(BufferedReader br, Integer noOfFeature, Double clusterStrictness) throws Exception;

}
