package com.project2.clusteringAlgos;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import com.project2.util.DataSet;

public class KMeans {

	public static Vector<DataSet> dataSet;
	public static Map<Integer, Integer> externalIndex;
	public static Vector<Integer> clusterIndex;
	public static int numberOfClusters;
	public static final int LOOP = 1000;
	public static final double E = 1E-8;
	public static Vector<DataSet> clusters;
	public static Vector<DataSet> improveClusters;
	public static Map<Integer, DataSet> clusterMap;
	public static Map<DataSet, Vector<DataSet>> op;
	public static Map<Integer, Vector<Integer>> clusteredGeneExpressions;
	public static Map<Integer, Integer> kmeansCluster;
	public static Iterator<Entry<Integer, Vector<Integer>>> iterator;

	public static DataSet temp;
	public static boolean flag;
	public static int loop;
	public static int count;
	public static int tempKey;
	public static int clusterNumber;
	public static double e;

	public void process() {

		clusters = new Vector<DataSet>();
		improveClusters = new Vector<DataSet>();
		clusterMap = new HashMap<Integer, DataSet>();
		op = new HashMap<DataSet, Vector<DataSet>>();
		kmeansCluster = new HashMap<Integer, Integer>();
		clusterNumber = 0;
		tempKey = 1;
		flag = true;
		loop = 0;
		count = 0;

		DataSet ds = new DataSet();
		dataSet = ds.readFile();
		externalIndex = ds.ExternalIndex(dataSet);
		clusterIndex = ds.ClusterIndex(dataSet);
		numberOfClusters = ds.NumberOfClusters(dataSet);

		for (int key : clusterIndex) {
			if (!clusterMap.containsKey(key)) {
				clusterMap.put(key, dataSet.get(count));
			}
			count++;
		}

		for (int key : clusterMap.keySet()) {
			clusters.add(clusterMap.get(key));
		}

		while (flag && loop < LOOP) {
			flag = false;

			op.clear();
			for (DataSet cluster : clusters) {
				op.put(cluster, new Vector<DataSet>());
			}

			for (DataSet input : dataSet) {

				temp = null;

				// error in Euclidean distance! Don't know how and what to compute!!
				for (DataSet key : op.keySet()) {
					if (temp == null
							|| input.EuclideanDistance(key) < input
									.EuclideanDistance(temp)) {
						temp = key;
					}
				}
				op.get(temp).add(input);
			}

			improveClusters = new Vector<DataSet>();

			for (DataSet cluster : clusters) {
				temp = cluster.average(op.get(cluster));
				improveClusters.add(temp);

				e = Math.abs(cluster.EuclideanDistance(temp));

				if (e > E && flag == false) {
					flag = true;
				}
			}
			clusters = new Vector<DataSet>(improveClusters);
			loop++;
		}

		for (DataSet tempDs : op.keySet()) {
			Vector<DataSet> tempOp = new Vector<DataSet>(op.get(tempDs));
			Vector<Integer> tempVector = new Vector<Integer>();
			String tempStr = "";
			for (int j = 0; j < tempOp.size(); j++) {
				DataSet g = tempOp.get(j);
				tempStr = tempStr + g.getGeneId() + ",";
				tempVector.add(g.getGeneId());
			}
			clusteredGeneExpressions.put(tempKey, tempVector);
			tempKey++;
		}

		iterator = clusteredGeneExpressions.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, Vector<Integer>> entry = (Entry<Integer, Vector<Integer>>) iterator
					.next();
			Vector<Integer> geneSequence = entry.getValue();
			for (int i = 0; i < geneSequence.size(); i++) {
				kmeansCluster.put(geneSequence.get(i), clusterNumber);
			}
			clusterNumber++;
		}
	}
}
