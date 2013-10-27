package com.project2.clustering.Aglomerative;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.zip.Adler32;

import javax.xml.crypto.Data;

import com.project2.util.DataSet;


public class AglomerativeClustering {

	public  Vector<DataSet> dataSet;
	public  ArrayList<AgloCluster> agloCluster = new ArrayList<>();
	public  ArrayList<AgloCluster> finalClusters = new ArrayList<>();;
	public  HashMap<HashMap<DataSet, Integer>,Integer> euclidCluster = new HashMap<HashMap<DataSet, Integer>,Integer>(); 
	//public  Map<Integer, Integer> externalIndex;
	//public  Vector<Integer> clusterIndex;
	public  int numberOfClusters;
	
	public void process(){

		DataSet ds = new DataSet();
		dataSet = ds.readFile();
		//externalIndex = ds.ExternalIndex(dataSet);
		//clusterIndex = ds.ClusterIndex(dataSet);
		numberOfClusters = dataSet.size(); // For this algorithm we start with number of clusters as number of points.
		
		Iterator<DataSet> iter = dataSet.iterator();
		int count = 0;
		createClusterMap();
		recursiveClustering();
		
		printClusters();
		
		/*while(iter.hasNext()){
			count++;
			AgloCluster acTemp = new AgloCluster();
			Vector<DataSet> vec = new Vector<>();
			vec.add(iter.next());
			acTemp.setClusterVec(vec);
			acTemp.setEuclidDistance(0);
			agloClust.add(acTemp);
		}
		
		System.out.println(" clusters " + agloClust.size());
		recursiveClustering(agloClust);*/
	}
	
	void recursiveClustering(){
		
		double min = findMinimumDist();
		
		for(int i=0;i<agloCluster.size();i++){
			if(agloCluster.get(i).euclidDistance == min){
				AgloCluster agl = new AgloCluster();
				
				//Pull out last cluster
				HashMap<DataSet,Integer> newCluster = new HashMap<DataSet,Integer>(); 
						//finalClusters.get(finalClusters.size()).getClusterVec(); 

				//Pull out new nodes to add
				HashMap<DataSet,Integer> newNode = agloCluster.get(i).getClusterVec();
				Set<DataSet> keySet = newNode.keySet();
				Iterator<DataSet>iter = keySet.iterator();
				while(iter.hasNext()){
					newCluster.put(iter.next(),0);
				}
				
				agl.setClusterVec(newCluster);
				finalClusters.add(agl);
				agloCluster.remove(i);
			}
		}
	}
	
	void createClusterMap(){
		for(int i=0;i<dataSet.size()-1;i++){
			for(int j=1;j<dataSet.size();j++){
				AgloCluster ag = new AgloCluster();
				HashMap<DataSet,Integer> temp = new HashMap<DataSet,Integer>();
				temp.put(dataSet.get(i),i);
				temp.put(dataSet.get(j),j);
				//Find the similarity between each gene data with every other gene data in the set.
				Double euclidDist = dataSet.get(i).EuclideanDistance(dataSet.get(j));
				ag.setClusterVec(temp);
				ag.setEuclidDistance(euclidDist);
				//also maintain an arraylist of aglocluster objects for fast search.
				agloCluster.add(ag);
				//store the cluster pair and their euclidean distance.
				euclidCluster.put(temp, i+j);
			}
		}
	}
	
	public double findMinimumDist(){
		
		double[] euclideanArr = new double[agloCluster.size()];
		double min = 0;
		for(int i=0;i<agloCluster.size();i++){
			euclideanArr[i]=agloCluster.get(i).euclidDistance;
		}
		Arrays.sort(euclideanArr);
		for(int i=0;i<euclideanArr.length;i++){
			if(euclideanArr[i]>0.0){
				min = euclideanArr[i];
				break;
			}
		}
		return min;
	}
	
	public void printClusters(){
		for(int i=0; i<finalClusters.size();i++){
			
			Set<DataSet> temp =  finalClusters.get(i).getClusterVec().keySet();
			Iterator<DataSet> iter = temp.iterator(); 
			while(iter.hasNext()){
				System.out.print(iter.next().getGeneId() + " , ");
			}
		}
	}
}
