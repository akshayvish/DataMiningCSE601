package com.project2.clustering.Aglomerative;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import com.project2.util.DataSet;

public class AgloCluster {
	private HashMap<DataSet,Integer> clusterVec = new HashMap<DataSet,Integer>();
	double euclidDistance;
	
	public HashMap<DataSet, Integer> getClusterVec() {
		return clusterVec;
	}
	public void setClusterVec(HashMap<DataSet, Integer> clusterVec) {
		this.clusterVec = clusterVec;
	}
	public double getEuclidDistance() {
		return euclidDistance;
	}
	public void setEuclidDistance(double euclidDistance) {
		this.euclidDistance = euclidDistance;
	}
}
