package homework;

import java.io.*;
import java.util.*;

public class Knapsack {
	
    public int knapSack(String fileName, String writeTo) {
    	//read data from file
    	ArrayList<String> lines = new ArrayList<String>();
		BufferedReader br = null;
		try {
				String currentLine;
				br = new BufferedReader(new FileReader(fileName));
				while ((currentLine = br.readLine()) != null) { 
					lines.add(currentLine);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null)br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		int W = Integer.parseInt(lines.get(0));
		int n = lines.size()-1;
		int[] w = new int[n];
		int[] v = new int[n];
		for(int i=1;i<=n;i++) {
			String[] strs = lines.get(i).split(" ");
			w[i-1] = Integer.parseInt(strs[1]);
			v[i-1] = Integer.parseInt(strs[2]);
		}
    	
		//caculate the optimal values
    	int[][] opt = new int[n+1][W+1];
    	for(int i=1;i<=n;i++) {
    		for(int j=1;j<=W;j++) {
    			//optimal value for object 1,2,...,i with weight=j
    			opt[i][j] = opt[i-1][j];
    			for(int k=1;k*w[i-1]<=j;k++) {
    				//optimal value when there are k object i
    				int value = k*v[i-1] + opt[i-1][j-k*w[i-1]]; 
    				if(value>opt[i][j])
    					opt[i][j] = value;
    			}
    		}    		
    	}
    	
    	//print data structures that stores the optimal values
		System.out.print("i\\w");
		for(int j=0;j<=W;j++) {
			System.out.print("\t"+j);
		}
		System.out.println();
		for(int i=0;i<=n;i++){
			System.out.print(i);
			for(int j=0;j<=W;j++) {
				System.out.print("\t"+opt[i][j]);
			}
			System.out.println();
		}
    	
    	//find list of items that gives optimal values
    	int i= n;
    	int j = W;
    	String result = "";
    	while(i>0) {
    		if(opt[i][j]==opt[i-1][j]) {
    			i--;
    			continue;
    		}
    		int k=0;
    		for(k=0;(opt[i][j]-k*v[i-1])!=opt[i-1][j-k*w[i-1]];k++) {
    			result = i+" "+result;
    		}
    		i--;
    		j-=k*w[i];
    	}
    	
    	//write result
    	BufferedWriter bufferWritter=null;
    	try{
		    	FileWriter fileWritter = new FileWriter(writeTo);
		    	bufferWritter = new BufferedWriter(fileWritter);
		    	bufferWritter.write(opt[n][W]+"\r\n"+result);
    		}catch(IOException e){
		    	e.printStackTrace();
    		} finally {
    			try {
    				if (bufferWritter != null)bufferWritter.close();
    			} catch (IOException ex) {
    				ex.printStackTrace();
    			}
    		}	

    	return opt[n][W];
    }
}
