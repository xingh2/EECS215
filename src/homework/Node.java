package homework;

import java.util.*;

public class Node {
	int id;
	HashMap<Node, Integer> adj;//adjecent nodes
	//shortest path to all other nodes inculding distance and nextHop
	HashMap<Integer, Integer> distance;
	HashMap<Integer, Integer> nextHop;
	boolean active;
	Node(int i) {
		id = i;
		adj = new HashMap<Node, Integer>();
		distance = new HashMap<Integer, Integer>();
		nextHop = new HashMap<Integer, Integer>();
		distance.put(i, 0);
		nextHop.put(i, i);
		active=true;
	}
	
	public String toString() {
		String str = "================= "+id+" ===================\n";
		str+="nodeID\t\tDistance\tNext Hop\n";
		for (int i : distance.keySet()) { 
			str+=i+"\t\t"+distance.get(i)+"\t\t"+nextHop.get(i)+"\n";
		}
		return str;
	}
}
