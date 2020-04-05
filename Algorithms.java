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
		fringe.push(root);
		//int count = 0;
		//loop to build the tree
		while(!fringe.empty()) {
			Pancake tmp = fringe.pop();
			
			//heuristic(tmp);
			
			// check if reached the goal
			if(tmp.pancake.equals("4321")) {
				return tmp;
			}
			else {// if not, check if this node was checked before
				if(differentPancakes.add(tmp.pancake)) { //avoid same node
					//System.out.println(count);
					//count++;
					//System.out.println(tmp.pancake);
					tmp.fourFlip = flip("four",tmp);
					//tmp.fourFlip.g +=4;
					//System.out.println("fourFlip " + tmp.fourFlip.pancake);
					tmp.threeFlip = flip("three",tmp);
					//tmp.threeFlip.g +=3;
					//System.out.println("threeFlip " + tmp.threeFlip.pancake);
					tmp.twoFlip = flip("two",tmp);
					//tmp.twoFlip.g +=2;
					//System.out.println("twoFlip " + tmp.twoFlip.pancake);
					tmp.fourFlip.parents = tmp;
					tmp.threeFlip.parents = tmp;
					tmp.twoFlip.parents = tmp;
					fringe.push(tmp.fourFlip);
					fringe.push(tmp.threeFlip);
					fringe.push(tmp.twoFlip);
					
				}
			}/*
			System.out.println("_____________________________________HashSet: ");
			for(String i : differentPancakes) {
				System.out.println(i);
			}
			*/
		}
		
		return null;
	}
	
	//A* algorithm, h cost implemented, use priority Queue
	public Pancake aStar() {
		PriorityQueue<Pancake> fringe = new PriorityQueue<Pancake>(new AStarComparator());
		fringe.add(root); // initialize priority queue
		//similar to DFS
		while(!fringe.isEmpty()){
						
			Pancake tmp = fringe.poll();
			
			//heuristic(tmp);
			//System.out.println(tmp);
//			System.out.println(tmp.pancake);
//			System.out.println(tmp.g);
//			System.out.println(tmp.h);
//			
			if(tmp.pancake.equals("4321")) {
				return tmp;
			}
			else {
				tmp.fourFlip = flip("four",tmp);
				tmp.threeFlip = flip("three",tmp);
				tmp.twoFlip = flip("two",tmp);
				
				
				tmp.fourFlip.parents = tmp;
				tmp.threeFlip.parents = tmp;
				tmp.twoFlip.parents = tmp;
				
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
	public void PrintPancakePath(Pancake a) {
		ArrayList<Pancake> backTrackingPath = new ArrayList<Pancake>(); // store the paths from bottom to the root
		Pancake tmp = a;
		//System.out.println("The input is:" + a.pancake);
		//System.out.println("The input'parent is:" + a.parents.pancake);
		
		Pancake curr = new Pancake(tmp.pancake);
		//curr.g = tmp.g;
		backTrackingPath.add(curr);
		//tmp = tmp.parents;
		
		int gCount =0;
		//find parents and insert "|"
		while(tmp.parents!= null) {
			//Pancake tmpt = new Pancake(tmp.pancake);
			if(tmp.parents.twoFlip == tmp) {//top two flip
				tmp.pancake = tmp.parents.pancake.substring(0, 2) + "|" + tmp.parents.pancake.substring(2, 4);
				//tmp.parents.g +=2;
				gCount += 2;
			}
			else if(tmp.parents.threeFlip == tmp) {//top three flip
				tmp.pancake = tmp.parents.pancake.substring(0, 1) + "|" + tmp.parents.pancake.substring(1,4) ;
				//tmp.parents.g +=3;
				gCount += 3;
			}
			else if(tmp.parents.fourFlip == tmp) { //whole pancake flip
				tmp.pancake = "|" + tmp.parents.pancake ;
				//tmp.parents.g +=4;
				gCount += 4;
			}
			//tmpt.pancake = tmp.pancake;
			//tmpt.g = 
			
			//System.out.println(tmp.pancake);
			//Pancake tmpt = new Pancake(tmp.pancake);
			//tmpt.g += tmp.g;
			tmp.g = gCount;
			backTrackingPath.add(tmp);
			tmp = tmp.parents;
		}
				
//		System.out.println("The Paths are:");

//		for(Pancake i : backTrackingPath) {
//			//Pancake tmpt = backTrackingPath.get(i);
//			System.out.println(i.pancake+" g="+i.g+", h="+i.h);
//		}
//		System.out.println("__________");
//		
		//print the correct path of the tree reversely
		Collections.reverse(backTrackingPath);
		for(Pancake i : backTrackingPath) {
			//Pancake tmpt = backTrackingPath.get(i);
			int actualG = gCount - i.g;
			System.out.println(i.pancake+" g="+ actualG +", h="+i.h);
		}
	}
	
	
	public static void main(String[] args) throws IOException{
		
		//Pancake a = new Pancake("4321");
		//Algorithms b = new Algorithms(a);
		//b.PrintPancakePath(a);
		//b.flip("four", a);
		//String abcd = "abcd";
		//abcd = reverseString(abcd);
		//System.out.println(abcd.substring(1,4));
		
		//System.out.println(a.pancake);
//		flip("four",a);
//		System.out.println(a.pancake);
//		
//		
//		a.pancake = "4321";
//		flip("three",a);
//		System.out.println(a.pancake);
//		
//		a.pancake = "4321";
//		flip("two",a);
//		System.out.println(a.pancake);
//		a.pancake = "3214";
//		b.heuristic(a);
//		System.out.println(a.h);
		//b.PrintPancakePath(a);
		
		
		// read user input
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String input = reader.readLine();
		
//		Scanner scanner = new Scanner(System.in);
//	    String input = scanner.nextLine();   
//		
		//System.out.println("input " + input); 
		//get the four digit pancake
		String inputNum = input.substring(0,4);
		//set flag for choosing which algorithm
		String whichAlgorithm = input.substring(4,5);
		//System.out.println("inputNum " + inputNum); 
		//System.out.println("which Algorithm " + whichAlgorithm); 
		//initialize a pancake
		Pancake aPancake = new Pancake(inputNum);
		//System.out.println("First Pancake check " + aPancake.pancake);
		//initialize a pancake tree
		Algorithms aAlgorithm = new Algorithms(aPancake);
		if(whichAlgorithm.equals("a")) {
			//System.out.println("ASTARcheckcheck");
			aPancake = aAlgorithm.aStar();
			//System.out.println("ASTARcheckcheck");
		}
		else if(whichAlgorithm.equals("d")) {
			//System.out.println("DSFcheckcheck");
			aPancake = aAlgorithm.dFS();
			//aAlgorithm.PrintPancakePath(aAlgorithm.dFS());
			//System.out.println("DSFcheckcheck");
		}
		else {
			System.out.println("Please re-check the input");
		}
		//System.out.println("YES!!!!!!!!!!!!!!!!!"  + aPancake.pancake);
//		System.out.println("parents " + aPancake.parents.pancake);
//		System.out.println(aPancake.parents.twoFlip.pancake);
//		System.out.println(aPancake.parents.threeFlip.pancake);
//		System.out.println(aPancake.parents.fourFlip.pancake);
		//System.out.println(aPancake.parents.parents.pancake);
//		System.out.println(aPancake.twoFlip);
//		System.out.println(aPancake.threeFlip);
//		System.out.println(aPancake.fourFlip);
		//System.out.println(aPancake);
		//System.out.println("__________________________________");
		//aAlgorithm.PrintPancakePath(aPancake);
		aAlgorithm.PrintPancakePath(aPancake);
		
	}
	
	
	
}

