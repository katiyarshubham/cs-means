package com.algo.csmean.serviceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.algo.csmean.model.bean.ClusterData;
import com.algo.csmean.model.bean.DataPoint;
import com.algo.csmean.model.bean.QualifiedCluster;
import com.algo.csmean.model.response.ClusterDataResponse;
import com.algo.csmean.service.CSMeanService;


@Service
public class CSMeanServiceImpl implements CSMeanService{

	private Integer shouldMatchFeature=null;
	private Double lowerStrictness=null;
	private Double upperStrictness=null;
	private Integer featureCount=null;
	private Integer clusterCount=0;
	private Double euclideanDistanceSum=null;
	HashMap<String, ClusterData> clusterMap = new HashMap<String, ClusterData>();
	
	final static Logger logger=Logger.getLogger(CSMeanServiceImpl.class); 
	
	@Override
	public ClusterDataResponse createClusters(BufferedReader br, Integer noOfFeature, Double clusterStrictness) throws Exception {
		featureCount=noOfFeature;
		clusterCount=0;
		setFeatureMatch_Strictness(noOfFeature,clusterStrictness);
		String line="";
		while((line=br.readLine())!=null) {
			String [] data= line.split(",");
			
			//getting the data points from buffer reader for a row
			List<Double> dataPoint = new ArrayList<>();
			for(String i:data) {
				Double feature = Double.parseDouble(i);
				dataPoint.add(feature);
			}
			if(clusterCount==0) {
				createNewCluster(dataPoint);
				
			}else {
				QualifiedCluster qualifiedCluster = getQualifiedCluster(dataPoint);
				if(qualifiedCluster==null) createNewCluster(dataPoint);
				else {
					addToQualifiedCluster(qualifiedCluster, dataPoint);
					updateCentroid(qualifiedCluster);
				}
			}
			calculateEuclideanDistance();
		}
		ClusterDataResponse clusterDataResponse = new ClusterDataResponse();
		clusterDataResponse.setNumberOfCluster(clusterCount);
		List<ClusterData> clusterdata= new ArrayList<>();
		clusterMap.forEach((k, v) -> clusterdata.add(v));
		clusterDataResponse.setEuclideanDistanceSum(euclideanDistanceSum);
		clusterDataResponse.setClusters(clusterdata);
		clusterMap.clear();
		return clusterDataResponse;
	}

	private void calculateEuclideanDistance() {
		euclideanDistanceSum=(double) 0;
		for (Map.Entry mapElement : clusterMap.entrySet()) {
			ClusterData clusterData = (ClusterData)mapElement.getValue();
			Double euclideanDistance = (double) 0;
			List<DataPoint> updatedDp= new ArrayList<>(); 
			for(DataPoint dp:clusterData.getDataPoints()) {
				for(int i=0;i<featureCount;i++) {
					euclideanDistance = euclideanDistance + Math.pow(clusterData.getCentroid().get(i)-dp.getDataPoint().get(i),2);
				}
				euclideanDistance=Math.sqrt(euclideanDistance);
				dp.setEuclideanDistance(euclideanDistance);
				euclideanDistanceSum=euclideanDistanceSum+euclideanDistance;
				updatedDp.add(dp);
			}
			clusterData.setDataPoints(updatedDp);
			clusterMap.put(clusterData.getClusterName(), clusterData);
		}
		
	}

	private void updateCentroid(QualifiedCluster qualifiedCluster) {
		ClusterData clusterData = clusterMap.get(qualifiedCluster.getClusterName());
		List<Double> newCentroid = new ArrayList<>();
		for(int i=0;i<featureCount;i++) {
			Double sum=(double) 0;
			for(DataPoint j:clusterData.getDataPoints()) {
				sum = sum+j.getDataPoint().get(i);
			}
			newCentroid.add(sum/clusterData.getDataPoints().size());
		}
		clusterData.setCentroid(newCentroid);
		clusterMap.put(qualifiedCluster.getClusterName(), clusterData);
		
	}

	private void addToQualifiedCluster(QualifiedCluster qualifiedCluster, List<Double> dataPoint) {
		DataPoint dp = new DataPoint();
		dp.setFeatureMatched(qualifiedCluster.getFeatureMatched());
		dp.setDataPoint(dataPoint);
		clusterMap.get(qualifiedCluster.getClusterName()).getDataPoints().add(dp);
	}

	private QualifiedCluster getQualifiedCluster(List<Double> dataPoint) {
		List<QualifiedCluster> qualifiedClusters = new ArrayList<>();
		 for (Map.Entry mapElement : clusterMap.entrySet()) {
			 Integer matchedFeaturesCount=0;
			 Double matchedSimilaritySum=(double) 0;
			 List<Double> similarity = new ArrayList<>();
			 ClusterData clusterData = (ClusterData)mapElement.getValue();
			 for(int i=0;i<featureCount;i++) {
				 Double sm = (dataPoint.get(i)*100)/clusterData.getCentroid().get(i);
				 similarity.add(sm);
				 if(Double.compare(lowerStrictness, sm) <= 0 && Double.compare(upperStrictness, sm) >= 0) {
					 matchedFeaturesCount++;
					 matchedSimilaritySum=matchedSimilaritySum+sm;
				 }
			 }
			 if(matchedFeaturesCount>=shouldMatchFeature) {
				 QualifiedCluster cluster = new QualifiedCluster();
				 cluster.setClusterName(clusterData.getClusterName());
				 cluster.setFeatureMatched(matchedFeaturesCount);
				 cluster.setSimilarity(similarity);
				 cluster.setFeatureMatchedSum(matchedSimilaritySum);
				 qualifiedClusters.add(cluster);
			 }
		 }
		 if(qualifiedClusters.size()==0) return null;
		 else if(qualifiedClusters.size()==1) return qualifiedClusters.get(0);
		 else if(qualifiedClusters.size()>1) {
			 List<QualifiedCluster> highestMatchingCluster = new ArrayList<>();
			 highestMatchingCluster.add(qualifiedClusters.get(0));
			 for(QualifiedCluster cluster:qualifiedClusters) {
				 if(cluster.getFeatureMatched()>highestMatchingCluster.get(0).getFeatureMatched()) {
					 highestMatchingCluster.remove(0);
					 highestMatchingCluster.add(cluster);
				 }else if(cluster.getFeatureMatched()==highestMatchingCluster.get(0).getFeatureMatched()) {
					 highestMatchingCluster.add(cluster);
				 }
			 }
			 if(highestMatchingCluster.size()==1) return highestMatchingCluster.get(0);
			 else if(highestMatchingCluster.size()>1) {
				 QualifiedCluster highestSimilarityMatch = highestMatchingCluster.get(0);
				 for(QualifiedCluster cluster:qualifiedClusters) {
					 if(Double.compare(cluster.getFeatureMatchedSum(), highestSimilarityMatch.getFeatureMatchedSum()) > 0) {
						 highestSimilarityMatch = cluster;
					 }
				 }
				 return highestSimilarityMatch;
			 }
		 }
		 return null;
	}

	private void createNewCluster(List<Double> dataPoint) {
		Integer clusterNumber=clusterCount+1;
		String clusterName="C"+clusterNumber.toString();
		ClusterData clusterData=new ClusterData();
		List<DataPoint> dataPoints=new ArrayList<>();
		DataPoint dataPoint1=new DataPoint();
		dataPoint1.setDataPoint(dataPoint);
		dataPoints.add(dataPoint1);
		clusterData.setClusterName(clusterName);
		clusterData.setCentroid(dataPoint);
		clusterData.setDataPoints(dataPoints);
		clusterMap.put(clusterName, clusterData);
		clusterCount++;
	}

	private void setFeatureMatch_Strictness(Integer noOfFeature, Double clusterStrictness) {
		shouldMatchFeature = (int)(noOfFeature*clusterStrictness)/100;
		lowerStrictness = clusterStrictness;
		upperStrictness = 200-clusterStrictness;
	}

}
