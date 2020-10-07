package com.algo.csmean.model.bean;

import java.util.List;

public class QualifiedCluster {
	
	private String clusterName;
	private List<Double> similarity;
	private Integer featureMatched;
	private Double featureMatchedSum;
	
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	public List<Double> getSimilarity() {
		return similarity;
	}
	public void setSimilarity(List<Double> similarity) {
		this.similarity = similarity;
	}
	public Integer getFeatureMatched() {
		return featureMatched;
	}
	public void setFeatureMatched(Integer featureMatched) {
		this.featureMatched = featureMatched;
	}
	public Double getFeatureMatchedSum() {
		return featureMatchedSum;
	}
	public void setFeatureMatchedSum(Double featureMatchedSum) {
		this.featureMatchedSum = featureMatchedSum;
	}
	
}
