import java.util.Comparator;

public class AStarComparator implements Comparator<Pancake> {
	@Override
	public int compare(Pancake a, Pancake b) {
		// get the actual cost for each pancake and then compare them
		int actualCostA = a.g + a.h;
		int actualCostB = b.g + b.h;
		if(actualCostA != actualCostB) {
			return actualCostA - actualCostB;
		}else {
			return Integer.valueOf(b.pancake) - Integer.valueOf(a.pancake);
		}
		//return 0;
	}
}
