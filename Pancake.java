public class Pancake {
	// a Pancake is a tree node of a pancake tree
	String pancake; // string for representing each level of a pancake 
	int g; //actual cost
	int h; //heuristic function
	Pancake parents; // tree node which represent the upper level of the tree
	//three children nodes
	Pancake twoFlip; //tree node of flip top two pancakes
	Pancake threeFlip; //tree node of flip top three pancakes
	Pancake fourFlip; //tree node of flip all four pancakes
	
	
	public Pancake(String input) {
		this.pancake = input;
		this.g = 0;
		this.h = 0;
	}
	
	/*
	//setters
	public void setParents(Pancake a) {
		this.parents = a;
	}
	
	public void setTwoFlips(Pancake a) {
		this.twoFlip = a;
	}

	public void setThreeFlips(Pancake a) {
		this.threeFlip = a;
	}

	public void setFourFlip(Pancake a) {
		this.FourFlip = a;
	}

	//getters
	public Pancake getParents() {
		return this.parents;
	}
	
	public Pancake getTwoFilps() {
		return this.twoFlip;
	}
	
	public Pancake getThreeFlips() {
		return this.threeFlip;
	}
	
	public Pancake getFourFlip() {
		return this.FourFlip;
	}
	
	*/
}
