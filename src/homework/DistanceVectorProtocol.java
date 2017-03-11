package homework;

import java.io.*;
import java.util.*;

public class DistanceVectorProtocol {
	HashMap<Integer, Node> nList = new HashMap<Integer, Node>();
	DistanceVectorProtocol() {
		nList = new HashMap<Integer, Node>();
	}
	
	public void showNetwork() {
		for (Node node : nList.values()) 
		    System.out.println(node);
	}
	
	public boolean readGraph(String fileName) {
		nList = new HashMap<Integer, Node>();
		BufferedReader br;
		try {
		    br = new BufferedReader(new FileReader(fileName));
		    try {
		        String line;
		        while ( (line = br.readLine()) != null ) {
		            String[] strs = line.split(" ");
		            if(strs.length!=3) {
		            	continue;
		            }
		            int start = Integer.parseInt(strs[0]);
		            int end = Integer.parseInt(strs[1]);
		            int weight = Integer.parseInt(strs[2]);
		            Node startN = null;
		            Node endN = null;
		            if(nList.containsKey(start)) 
		            	startN = nList.get(start);
		            else {
		            	startN = new Node(start);
		            	nList.put(start, startN);
		            }
		            if(nList.containsKey(end)) 
		            	endN = nList.get(end);
		            else {
		            	endN = new Node(end);
		            	nList.put(end, endN);
		            }
		            if(!startN.adj.containsKey(endN) || startN.adj.get(endN)>weight) {
		            	startN.adj.put(endN, weight);
		            	startN.distance.put(end, weight);
		            	startN.nextHop.put(end, end);
		            }
		            if(!endN.adj.containsKey(startN) || endN.adj.get(startN)>weight) {
		            	endN.adj.put(startN, weight);
		            	endN.distance.put(start, weight);
		            	endN.nextHop.put(start, start);
		            }
		        }
		    }
		    catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		catch (FileNotFoundException e) {
		    System.out.println(e);
		    e.printStackTrace();
		}
		return true;
	}
	
	public void updateNeighbors(Node node) {
		for (Map.Entry<Node, Integer> entry : node.adj.entrySet()) { 
			Node adjN = entry.getKey();
			int weight = entry.getValue();
			for(Map.Entry<Integer, Integer> subentry : node.distance.entrySet()) { 
				int id = subentry.getKey();
				int dis = subentry.getValue();
				if(!adjN.distance.containsKey(id) || dis+weight<adjN.distance.get(id)) {
					adjN.active=true;
					adjN.distance.put(id, dis+weight);
					adjN.nextHop.put(id, node.id);
				}
			}
		}
		node.active = false;
	}
	
	public void protocol() {
		boolean existActive = true;
		while(existActive) {
			existActive = false;
			for (Node node : nList.values()) {
				if(node.active) {
					updateNeighbors(node);
					existActive = true;
				}
			}
		}
	}
	
	public static void main(String [] args) {
		String fileName = "D:/graph.txt";
		DistanceVectorProtocol dvp = new DistanceVectorProtocol();
		dvp.readGraph(fileName);
		dvp.protocol();
		dvp.showNetwork();
	}
}

