package com.algo.csmean.model.bean;

import java.util.List;

public class DataPoint {
  
	private List<Double> dataPoint;
	private Integer featureMatched;
	private Double euclideanDistance;

	public List<Double> getDataPoint() {
		return dataPoint;
	}

	public void setDataPoint(List<Double> dataPoint) {
		this.dataPoint = dataPoint;
	}

	public Integer getFeatureMatched() {
		return featureMatched;
	}

	public void setFeatureMatched(Integer featureMatched) {
		this.featureMatched = featureMatched;
	}

	public Double getEuclideanDistance() {
		return euclideanDistance;
	}

	public void setEuclideanDistance(Double euclideanDistance) {
		this.euclideanDistance = euclideanDistance;
	}
	
	
	
}
