import java.util.*;
import java.io.*;

public class Algorithms {
	
	Pancake root; // root node of the pancake tree

	public Algorithms(Pancake a) {
		root = a; //start to establish a tree
		heuristic(a); //update the heuristic for each step	
	}	
	
	// DFS algorithm, use stack
	//tested
	public Pancake dFS() {
		// in order to avoid same node in the tree 
		HashSet<String> differentPancakes =  new HashSet<String>(); 
		// build the fringe, a lifo stack
		Stack<Pancake> fringe = new Stack<Pancake>();
		fringe.push(root);// initialize the fringe
		while(!fringe.empty()) {
			Pancake tmp = fringe.pop();
			if(tmp.pancake.equals("4321")) {//if match the goal
				return tmp;
			}
			else {// if not, check if this node was checked before
				if(differentPancakes.add(tmp.pancake)) { //avoid same node
					//set children and their parents than push into the stack
					tmp.fourFlip = flip("four",tmp);
					tmp.threeFlip = flip("three",tmp);
					tmp.twoFlip = flip("two",tmp);
					tmp.fourFlip.parents = tmp;
					tmp.threeFlip.parents = tmp;
					tmp.twoFlip.parents = tmp;
					fringe.push(tmp.fourFlip);
					fringe.push(tmp.threeFlip);
					fringe.push(tmp.twoFlip);
					
				}
			}
		}
		return null;
	}
	
	//A* algorithm, h cost implemented, use priority Queue
	//tested
	public Pancake aStar() {
		PriorityQueue<Pancake> fringe = new PriorityQueue<Pancake>(new AStarComparator());
		fringe.add(root); // initialize priority queue
		//similar to DFS
		while(!fringe.isEmpty()){			
			Pancake tmp = fringe.poll();
			if(tmp.pancake.equals("4321")) {
				return tmp;
			}
			else {
				//set children
				tmp.fourFlip = flip("four",tmp);
				tmp.threeFlip = flip("three",tmp);
				tmp.twoFlip = flip("two",tmp);
				//set parents				
				tmp.fourFlip.parents = tmp;
				tmp.threeFlip.parents = tmp;
				tmp.twoFlip.parents = tmp;
				//add them into the priority queue
				fringe.add(tmp.fourFlip);
				fringe.add(tmp.threeFlip);
				fringe.add(tmp.twoFlip);	
			}
		}
		return null;
	}
	
	//get the heuristic cost 
	//tested
	public void heuristic(Pancake a) {
		for(int i = 0; i < 4; i++) {
			//loop to find the biggest one which is not in place
			String tmp = String.valueOf(4-i);
			if(!a.pancake.substring(i, i+1).equals(tmp)) {
				a.h = Integer.valueOf(tmp);
				break;
			}
		}
	}
	
	//helper function for flip function
	//tested
	public String reverseString(String input) {
		//reverse a string
		StringBuilder tmp = new StringBuilder();
		tmp.append(input);
		tmp = tmp.reverse();
		input = tmp.toString();
		return input;
	}
	
	//flip the pancake based on the cases
	//tested
	public Pancake flip(String flag, Pancake input) {
		Pancake a = new Pancake (input.pancake);
		a.g = input.g;
		//a.h = input.h;
		heuristic(a);
		switch(flag) {
			case "four": //whole pancake flip
				a.pancake = reverseString(a.pancake); 
				a.g +=4;
				break;
			case "three"://top three flip
				a.pancake = a.pancake.substring(0,1) + reverseString(a.pancake.substring(1,4));
				a.g +=3;
				break;
			case "two": //top two flip
				a.pancake = a.pancake.substring(0,2) + reverseString(a.pancake.substring(2,4));
				a.g +=2;
				break;
		}
		return a;
	}
	
	//print the path of pancake tree
	//tested
	public void PrintPancakePath(Pancake a) {
		ArrayList<Pancake> backTrackingPath = new ArrayList<Pancake>(); // store the paths from bottom to the root
		Pancake tmp = a;
		
		//add the goal to the arraylist
		Pancake curr = new Pancake(tmp.pancake);
		backTrackingPath.add(curr);
		
		int gCount =0;
		//find parents and insert "|"
		while(tmp.parents!= null) {
			if(tmp.parents.twoFlip == tmp) {//top two flip
				tmp.pancake = tmp.parents.pancake.substring(0, 2) + "|" + tmp.parents.pancake.substring(2, 4);
				gCount += 2;
			}
			else if(tmp.parents.threeFlip == tmp) {//top three flip
				tmp.pancake = tmp.parents.pancake.substring(0, 1) + "|" + tmp.parents.pancake.substring(1,4) ;
				gCount += 3;
			}
			else if(tmp.parents.fourFlip == tmp) { //whole pancake flip
				tmp.pancake = "|" + tmp.parents.pancake ;
				gCount += 4;
			}

			tmp.g = gCount;
			backTrackingPath.add(tmp);
			tmp = tmp.parents;
		}
				
	
		//print the correct path of the tree reversely
		Collections.reverse(backTrackingPath);
		for(Pancake i : backTrackingPath) {
			int actualG = gCount - i.g;
			System.out.println(i.pancake+" g="+ actualG +", h="+i.h);
		}
	}
	
	
	public static void main(String[] args) throws IOException{
		// read user input
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String input = reader.readLine();
		
		//get the pancake
		String inputNum = input.substring(0,4);
		//set flag for choosing which algorithm
		String whichAlgorithm = input.substring(4,5); 
		//initialize a pancake
		Pancake aPancake = new Pancake(inputNum);
		//initialize a pancake tree
		Algorithms aAlgorithm = new Algorithms(aPancake);
		if(whichAlgorithm.equals("a")) { //A*
			aPancake = aAlgorithm.aStar();
		}
		else if(whichAlgorithm.equals("d")) { //DFS
			aPancake = aAlgorithm.dFS();
		}
		else {
			System.out.println("Please re-check the input");
		}
		//print the path
		aAlgorithm.PrintPancakePath(aPancake);
		
	}
	
	
	
}

