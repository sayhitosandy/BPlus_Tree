package BPlusTreeBottomUp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

public class BPlusTreeBottomUp {

	static ArrayList<Integer> input = new ArrayList<>();
	static ArrayList<ArrayList<Integer>> level = new ArrayList<>();
	static ArrayList<ArrayList<Node>> Tree = new ArrayList<>();

	public static void readFile() {
		try {
			Scanner scan = new Scanner(new File("src/final.txt"));
			while (scan.hasNextInt()) {
				input.add(scan.nextInt());
			}
			level.add(input);
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String... args) throws IOException {
		Scanner s = new Scanner(System.in);
		int degree = 0;
		System.out.println("Enter Order: ");
		if (s.hasNextInt()) {
			degree = s.nextInt();
		}
		s.close();

		long startTime = System.currentTimeMillis();
		degree--;
		readFile();

		ArrayList<Integer> list = input;

		int k = degree;
		while (list.size() > degree) {
			list = new ArrayList<>();
			for (int i = 0; i < input.size(); i += k) {
				list.add(input.get(i));
			}
			level.add(list);
			k = k * degree;
		}

		Writer wr = new FileWriter("src/BottomUpOutput.txt");

		for (int i = level.size() - 1; i >= 0; i--) {
			wr.write(("Level " + i + ": ").toString());
			for (int j = 0; j < level.get(i).size(); j++) {
				wr.write((level.get(i).get(j) + " ").toString());
				if ((j + 1) % degree == 0) {
					wr.write((" # ").toString());
				}
			}
			wr.write("\n");
		}

		wr.close();

		long endTime = System.currentTimeMillis();
		System.out.println("Total Time Taken = " + (endTime - startTime) + " ms");

		Node node = new Node();
		ArrayList<Node> listNodes = new ArrayList<>();

		for (int i = level.size() - 1; i >= 0; i--) {
			listNodes = new ArrayList<>();
			node = new Node();
			for (int j = 0; j < level.get(i).size(); j++) {
				node.elements.add(level.get(i).get(j));
				if (node.elements.size() == degree) {
					listNodes.add(node);
					node = new Node();
				}
			}
			if (node.elements.isEmpty() == false) {
				listNodes.add(node);
			}
			Tree.add(listNodes);
		}

		for (int i = 0; i < Tree.size() - 1; i++) {
			int tmp = 0;
			for (int j = 0; j < Tree.get(i).size(); j++) {
				if (tmp < Tree.get(i + 1).size()) {
					Tree.get(i).get(j).pointers.add(Tree.get(i + 1).get(tmp++));
				}
				if (tmp < Tree.get(i + 1).size()) {
					Tree.get(i).get(j).pointers.add(Tree.get(i + 1).get(tmp++));
				}
			}
		}

		// prints pointers
		// for (int i = 0; i < Tree.size(); i++) {
		// for (int j = 0; j < Tree.get(i).size(); j++) {
		// for (int t = 0; t < Tree.get(i).get(j).pointers.size(); t++) {
		// System.out.print(Tree.get(i).get(j).pointers.get(t).elements);
		// }
		// }
		// System.out.println();
		// }

		// for (int i = 0; i < level.size(); i++) {
		// System.out.println(level.get(i));
		// }
	}
}

class Node {
	ArrayList<Integer> elements;
	ArrayList<Node> pointers;

	public Node() {
		elements = new ArrayList<>();
		pointers = new ArrayList<>();
	}

}
