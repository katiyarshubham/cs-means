package com.algo.csmean.model.response;

import java.util.List;

import com.algo.csmean.model.bean.ClusterData;

public class ClusterDataResponse {
	
    private Integer numberOfCluster;
	private Double euclideanDistanceSum;
	private long totalMiliSecondTime;
	private List<ClusterData> clusters;
	
	public Integer getNumberOfCluster() {
		return numberOfCluster;
	}
	public void setNumberOfCluster(Integer numberOfCluster) {
		this.numberOfCluster = numberOfCluster;
	}
	public List<ClusterData> getClusters() {
		return clusters;
	}
	public void setClusters(List<ClusterData> clusters) {
		this.clusters = clusters;
	}
	public Double getEuclideanDistanceSum() {
		return euclideanDistanceSum;
	}
	public void setEuclideanDistanceSum(Double euclideanDistanceSum) {
		this.euclideanDistanceSum = euclideanDistanceSum;
	}
	public long getTotalMiliSecondTime() {
		return totalMiliSecondTime;
	}
	public void setTotalMiliSecondTime(long totalMiliSecondTime) {
		this.totalMiliSecondTime = totalMiliSecondTime;
	}

	
	
}
