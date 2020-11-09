import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Implementation of the Knapsack algorithm for COM2031
 * 
 */

public class KnapSack {

	public static class Item {
		private final String name;
		private final int value;
		private final int weight;

		public Item(final String name, final int value, final int weight) {
			this.weight = weight;
			this.value = value;
			this.name = name;
		}
		/**
		 * Convenience method for pretty printing of an item.
		 */
		@Override
		public String toString() {
			return "Item [name=" + name + ", value=" + value + ", weight=" + weight + "]";
		}
	}

	/** list of items to select from. */
	private final Item[] items;
	/** maximal weight this Knapsack can hold. */
	private final int maxWeight;

	/**
	 * creates a new instance of the Knapsack problem
	 */
	public KnapSack(final Item[] items, final int maxWeight) {

		this.items = items; 
		this.maxWeight = maxWeight;

	}

	/**
	 * 2D array for memoization. First index is number of items considered, and
	 * second index is current weight limited considered.
	 */
	private int[][] M;

	public int pack() {
		Arrays.sort(this.items, Comparator.comparingInt(weight -> this.items[0].weight));

		M = new int[items.length+1][this.maxWeight+1];

		for(int i = 1; i < items.length+1; i++){
			for(int w = 0; w < this.maxWeight+1; w++){
				if(this.items[i-1].weight > w){
					M[i][w] = M[i-1][w];
				}else{
					M[i][w] = Math.max(M[i-1][w], this.items[i-1].value + M[i-1][w-items[i-1].weight]);
				}
			}
		}

		return M[items.length][maxWeight];
	}


	/**
	 * Iteratively find which items to include from the table M of memoised OPT
	 * values.
	 * 
	 * @param numItems
	 *            for this many first items from items.
	 * @param weightLimit
	 *            with this remaining weight available
	 */
	
	private void findAndPrintSolution(final int numItems, final int weightLimit) {
		// TODO  use the table M to identify and print the items that should be packed 
		// for the optimal solution for the first numItems items 
		// and the remaining weight limit weightLimit 
	}


	/**
	 * pretty-print the array M 
	 * 		The M for a knapsack problem can be printed with: System.out.print(knapSack);
	 */
	@Override
	public String toString() {

		StringBuffer s = new StringBuffer();

		// Table Header:
		s.append("Weight:\t");
		for (int j = 0; j < maxWeight + 1; j++) {
			s.append(j + "\t");
		}
		s.append("\n");

		// Table Content:
		for (int i = 0; i < items.length + 1; i++) {
			s.append(i + " Items\t");
			for (int j = 0; j < maxWeight + 1; j++) {
				s.append(M[i][j] + "\t");
			}
			s.append("\n");
		}

		return s.toString();
	}	
	
	

public static void testKnapsack(String name, Item[] items, int maxWeight, int expected){
	KnapSack knapSack = new KnapSack(items, maxWeight);
	int maxValue = knapSack.pack();
    if(maxValue==expected) {
    	System.out.print(name+":  Pass ");
    }
    if(maxValue!=expected) {
    	System.out.println("*********************");
    	System.out.print(name+":  Fail:   ");
       	System.out.print(", your value is "+maxValue
       			+"\n");
       	System.out.println("Your array M is:");
       	System.out.print(knapSack);
       	System.out.print(", correct value is "+expected+"\n");
    	System.out.print("*********************");
    }
    System.out.print("\n");
	}
	
	

	public static void main(String[] args) {

		Item[] items = { new Item("1", 1, 1), new Item("2", 6, 2), new Item("3", 18, 5), new Item("4", 22, 6),
				new Item("5", 28, 7) };

		testKnapsack("test1", items, 11, 40);

		final KnapSack knapSack = new KnapSack(items, 11);
		final int maxValue = knapSack.pack();
		System.out.print(knapSack);
		
		
		Item[] items2 = {new Item("a", 30, 7), new Item("b", 42, 14), new Item("c", 12, 45), new Item("d", 43, 44), 
				new Item("e", 21, 23), new Item("f", 11, 40)};

		testKnapsack("test2", items2, 30, 72);
		testKnapsack("test3", items2, 60, 93);

		
		Item[] items3 = {new Item("a", 9, 42), new Item("b", 33, 45), new Item("c", 31, 46), new Item("d", 9, 12), 
				new Item("e", 38, 44), new Item("f", 32, 23)};
		
		testKnapsack("test4", items3, 30, 32);
		testKnapsack("test5", items3, 50, 41);
		testKnapsack("test6", items3, 80, 79);
		testKnapsack("test7", items3, 110, 80);
		testKnapsack("test8", items3, 150, 112);

		Item[] items4 = {new Item("a", 4, 8), new Item("b", 5, 27), new Item("c", 7, 30)};
		
		testKnapsack("test9", items4, 25, 4);
		testKnapsack("test10", items4, 30, 7);
		testKnapsack("test11", items4, 35, 9);
		
		Item[] items5 = {new Item("a", 48, 28), new Item("b", 50, 38), new Item("c", 4, 24), new Item("d", 2, 2), new Item("e", 9, 45), 
				new Item("f", 25, 1), new Item("g", 34, 17), new Item("h", 42, 27), new Item("i", 28, 22), new Item("j", 10, 13), 
				new Item("k", 5, 10), new Item("l", 49, 17), new Item("m", 35, 49), new Item("n", 49, 12), new Item("o", 49, 7), 
				new Item("p", 17, 46), new Item("q", 11, 38), new Item("r", 31, 26), new Item("s", 3, 12), new Item("t", 1, 20), 
				new Item("u", 28, 3), new Item("v", 19, 48), new Item("w", 42, 11), new Item("x", 41, 19), new Item("y", 24, 22), 
				new Item("z", 26, 4)};
		
		testKnapsack("test12", items5, 25, 153);
		testKnapsack("test13", items5, 30, 179);
		testKnapsack("test14", items5, 35, 193);
		testKnapsack("test15", items5, 40, 221);
		testKnapsack("test16", items5, 45, 226);
		testKnapsack("test17", items5, 50, 228);
		testKnapsack("test18", items5, 150, 435);
		testKnapsack("test19", items5, 350, 621);
		testKnapsack("test20", items5, 550, 681);
		
	}
}