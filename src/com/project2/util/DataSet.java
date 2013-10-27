package com.project2.util;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Vector;

public class DataSet {
	private int geneId;
	private int groundTruth;
	private Vector<Double> expressions = new Vector<Double>();
	private int numberOfGenes;

	public DataSet(int geneId, int groundTruth, Vector<Double> expressions) {
		super();
		this.geneId = geneId;
		this.groundTruth = groundTruth;
		this.expressions = expressions;
	}

	public DataSet(int geneId, Vector<Double> expressions) {
		super();
		this.geneId = geneId;
		this.expressions = expressions;
	}

	public DataSet() {
		// TODO Auto-generated constructor stub
	}

	public int getGeneId() {
		return geneId;
	}

	public void setGeneId(int geneId) {
		this.geneId = geneId;
	}

	public int getGroundTruth() {
		return groundTruth;
	}

	public void setGroundTruth(int groundTruth) {
		this.groundTruth = groundTruth;
	}

	public Vector<Double> getExpressions() {
		return expressions;
	}

	public void setExpressions(Vector<Double> expressions) {
		this.expressions = expressions;
	}

	public Vector<DataSet> readFile() { // to read contents of the file
		System.out.println("Enter the file path \t");
		@SuppressWarnings("resource")
		//Scanner scanner = new Scanner(System.in);
		String filePath = "/Users/akshayviswanathan/Documents/workspace/DM Workspace/Project2_Hadoop/src/com/project2/clusteringAlgos/iyer.txt"; 
				//scanner.nextLine();
		Vector<DataSet> dataSet = new Vector<DataSet>();
		try {
			FileInputStream fileInputStream = null;
			BufferedInputStream bufferedInputStream = null;
			DataInputStream dataInputStream = null;

			File file = new File(filePath);
			fileInputStream = new FileInputStream(file);
			bufferedInputStream = new BufferedInputStream(fileInputStream);
			dataInputStream = new DataInputStream(bufferedInputStream);

			while (dataInputStream.available() != 0) {
				String row = dataInputStream.readLine();
				String[] col = row.split("\\t");
				DataSet ds = new DataSet();
				ds.geneId = Integer.parseInt(col[0]);
				ds.groundTruth = Integer.parseInt(col[1]);
				for (int i = 2; i < col.length; i++) {
					double exp = Double.parseDouble(col[i]);
					ds.expressions.add(exp);
				}
				dataSet.add(ds);
			}

			fileInputStream.close();
			bufferedInputStream.close();
			dataInputStream.close();

		} catch (Exception er) {
			er.printStackTrace();
		}

		return dataSet;
	}

	public DataSet average(Vector<DataSet> ds) {

		Vector<Double> colAvg = new Vector<Double>();
		double avg;
		int k = ds.get(0).expressions.size();
		for (int col = 0; col<k; col++) {
			avg = 0;
			for (int row = 0; row < ds.size(); row++) {
				avg += ds.get(row).expressions.get(col);
			}
			colAvg.add(col, avg / ds.size());
		}
		return new DataSet(-1, colAvg);
	}

	public double EuclideanDistance(DataSet ds) {
		double distance = 0;

		for (int i = 0; i < ds.expressions.size(); i++) {
			double temp = this.expressions.get(i) - ds.getExpressions().get(i);
			//System.out.println(this.getExpressions().get(i) + " **** " + ds.getExpressions().get(i));
			distance += (temp * temp);
		}
		return Math.sqrt(distance);
	}

	public int NumberOfClusters(Vector<DataSet> ds) {
		int cluster = 0;

		for (int i = 0; i < ds.size(); i++) {
			if (cluster < ds.get(i).groundTruth) {
				cluster = ds.get(i).groundTruth;
			}
		}
		return cluster;
	}

	public Map<Integer, Integer> ExternalIndex(Vector<DataSet> ds) {
		Map<Integer, Integer> ei = new TreeMap<Integer, Integer>();

		for (int i = 0; i < ds.size(); i++) {
			if (ds.get(i).groundTruth > 0) {
				ei.put(ds.get(i).geneId, ds.get(i).groundTruth);
			}
		}
		return ei;
	}

	public Vector<Integer> ClusterIndex(Vector<DataSet> ds) {
		Map<Integer, Integer> ei = ExternalIndex(ds);
		Iterator<Entry<Integer, Integer>> iterator = ei.entrySet().iterator();

		Vector<Integer> ci = new Vector<Integer>();

		while (iterator.hasNext()) {
			Entry<Integer, Integer> entry = (Entry<Integer, Integer>) iterator.next();
			ci.add(entry.getValue());
		}
		return ci;
	}
	
	

	/*
	 * public static void main(String[] args) { DataSet ds = new DataSet();
	 * ArrayList<DataSet> dataSet = ds.readFile();
	 * 
	 * System.out.println("Data Set Length = " + dataSet.size()+"\n"); for (int
	 * i = 0; i < dataSet.size(); i++) {
	 * System.out.println(dataSet.get(i).geneId + "\t" +
	 * dataSet.get(i).groundTruth + "\t" + dataSet.get(i).expressions.get(0) +
	 * "\t" + dataSet.get(i).expressions.get(1) + "\t" +
	 * dataSet.get(i).expressions.get(2) + "\t" +
	 * dataSet.get(i).expressions.get(3) + "\t" +
	 * dataSet.get(i).expressions.get(4) + "\t" +
	 * dataSet.get(i).expressions.get(5) + "\t" +
	 * dataSet.get(i).expressions.get(6) + "\t" +
	 * dataSet.get(i).expressions.get(7) + "\t" +
	 * dataSet.get(i).expressions.get(8) + "\t" +
	 * dataSet.get(i).expressions.get(9) + "\t" +
	 * dataSet.get(i).expressions.get(10) + "\t" +
	 * dataSet.get(i).expressions.get(11) + "\n"); }
	 * 
	 * }
	 */

}
