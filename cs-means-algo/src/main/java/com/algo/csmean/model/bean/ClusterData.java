package com.algo.csmean.model.bean;

import java.util.List;

public class ClusterData {

	private String clusterName;
	private List<Double> centroid;
	private List<DataPoint> dataPoints;
	
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	public List<Double> getCentroid() {
		return centroid;
	}
	public void setCentroid(List<Double> centroid) {
		this.centroid = centroid;
	}
	public List<DataPoint> getDataPoints() {
		return dataPoints;
	}
	public void setDataPoints(List<DataPoint> dataPoint) {
		this.dataPoints = dataPoint;
	}
	
	
	
}
