package com.project2.clusteringAlgos;

import com.project2.clustering.Aglomerative.AglomerativeClustering;

public class MainClass {
	
	public static void main(String[] args) {
		//KMeans kmCluster = new KMeans();
		//kmCluster.process();
		
		AglomerativeClustering agc = new AglomerativeClustering();
		agc.process();
	}	

}
