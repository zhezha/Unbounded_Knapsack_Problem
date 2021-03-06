package UnboundedKnapsackProblem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * @author Zhao Zhengyang
 */
public class UnboundedKnapsackProblem {

	public static int totalWeight;
	public static List<Item> itemList;

	// Read file and parse strings to data.
	// ItemList is sorted according to valueWeightRatio
	public UnboundedKnapsackProblem(String filename) throws IOException {
		List<String> lines = FileIO.readFile(filename);
		totalWeight = Integer.parseInt(lines.get(0));
		itemList = sortByValueWeightRatio(getItemList(lines, totalWeight));
	}

	
	public static void main(String[] args) throws IOException {

		if (args.length != 1) {
			System.out.println("One input file name is needed!");
			return;
		}
		String filename = args[0];

		UnboundedKnapsackProblem p = new UnboundedKnapsackProblem(filename);
		p.knapsack(totalWeight, itemList);

	}

	
	// The main entry to solver.
	// Backtracking search solver is removed.
	public void knapsack(int totalWeight, List<Item> itemList) {

		int n = itemList.size();
		int[] weights = new int[n];
		int[] values = new int[n];

		for (int i = 0; i < n; i++) {
			weights[i] = itemList.get(i).weight;
			values[i] = itemList.get(i).value;
		}

		// weights and totalWeight are devided by their gcd
		int gcdWeights = ngcd(weights, weights.length);
		for (int i = 0; i < n; i++) {
			weights[i] /= gcdWeights;
		}
		totalWeight /= gcdWeights;

		solveDP(values, weights, totalWeight);
	}

	
	// solving with dynamic programming
	void solveDP(int[] values, int[] weights, int totalWeight) {

		int n = values.length;
		int[] c = new int[totalWeight + 1];
		// This array stores the items which are added to the packet.
		// p[j] = i indicates item i is added to make the total weight up to j.
		int[] p = new int[totalWeight + 1];

		// State transition formula:
		// c(j) = max{c(j), c(j - weights[i]) + values[i]}
		for (int i = 0; i < n; i++) {
			for (int j = weights[i]; j <= totalWeight; j++) {
				if (c[j - weights[i]] + values[i] > c[j]) {
					c[j] = c[j - weights[i]] + values[i];
					p[j] = i;
				}
			}
		}

		// This array stores the amount of each item in the best solution
		int[] bestAmountEachItem = new int[n];
		int minWeights = getMin(weights);
		// Get the amount of each item in the best solution.
		// i from n - 1 to 0, j from totalWeight to 0.
		// p[j] = i <=> C(W) = C(W - wi) + vi
		// next step: p[j - wi] = ? <=> C(W - wi) = C(W - wi - w?) + v?
		int j = totalWeight;
		while (j >= minWeights) {
			bestAmountEachItem[p[j]]++;
			j -= weights[p[j]];
		}

		String[] lines = formatOutput(bestAmountEachItem);
		FileIO.writeFile("output.txt", lines);
		
		System.out.println("Problem solved.");
	}

	
	// Format the output strings from solution
	String[] formatOutput(int[] bestNumEachItem) {

		List<String> list = new ArrayList<>();

		for (int i = 0; i < bestNumEachItem.length; i++) {
			Item c = itemList.get(i);

			StringBuilder sb = new StringBuilder();
			sb.append(c.name + ", ");
			sb.append(Integer.toString(bestNumEachItem[i]) + ", ");
			sb.append(Integer.toString(c.weight * bestNumEachItem[i]) + ", ");
			sb.append(Integer.toString(c.value * bestNumEachItem[i]) + "\r\n");
			list.add(sb.toString());
		}

		int bestTotalWeight = 0;
		int bestTotalValue = 0;
		for (int i = 0; i < bestNumEachItem.length; i++) {
			bestTotalWeight += itemList.get(i).weight * bestNumEachItem[i];
			bestTotalValue += itemList.get(i).value * bestNumEachItem[i];
		}
		String s = Integer.toString(bestTotalWeight) + ", "
				+ Integer.toString(bestTotalValue) + "\r\n";
		list.add(s);

		String[] output = new String[list.size()];
		output = list.toArray(output);
		return output;
	}

	
	// Get the minimum value of given array.
	int getMin(int[] array) {
		int result = Integer.MAX_VALUE;
		for (int i = 0; i < array.length; i++) {
			if (result > array[i]) {
				result = array[i];
			}
		}
		return result;
	}

	
	// Get the maximum value of given array.
	int getMax(int[] array) {
		int result = Integer.MIN_VALUE;
		for (int i = 0; i < array.length; i++) {
			if (result < array[i]) {
				result = array[i];
			}
		}
		return result;
	}

	
	// Sort the itemList according to value/weight.
	List<Item> sortByValueWeightRatio(List<Item> itemList) {
		List<Item> resultList = itemList;
		Comparator<Item> comparator = new Comparator<Item>() {
			@Override
			public int compare(Item c1, Item c2) {
				if (c1.ValueWeightRatio == c2.ValueWeightRatio) {
					return c1.weight - c2.weight;
				} else if (c1.ValueWeightRatio > c2.ValueWeightRatio) {
					return -1;
				} else {
					return 1;
				}
			}
		};
		Collections.sort(resultList, comparator);
		return resultList;
	}

	
	// Get the gcd of the first n elements in given array
	int ngcd(int[] array, int n) {
		if (n == 1)
			return array[0];

		return gcd(array[n - 1], ngcd(array, n - 1));
	}

	
	// Get the gcd of a and b
	int gcd(int a, int b) {
		if (a < b) {
			int tmp = a;
			a = b;
			b = tmp;
		}

		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}

	
	// Get itemList from reading strings
	List<Item> getItemList(List<String> lines, int totalWeight) {
		int numItems = lines.size();
		List<Item> list = new ArrayList<>();

		for (int i = 1; i < numItems; i++) {
			String[] s = lines.get(i).split(", ");

			Item c = new Item(i - 1, s[0], Integer.parseInt(s[1]),
					Integer.parseInt(s[2]), totalWeight);
			list.add(c);
		}

		return list;
	}

}