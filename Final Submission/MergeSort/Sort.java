package MergeSort;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Sort {

	static public int no_buffer;
	static public int no_entries;
	static public ArrayList<Integer> val;
	static public Integer count, count2 = 1;
	static public ArrayList<String> file_name = new ArrayList<>();

	public static void merge(Integer start) throws IOException {
		Scanner[] scanner = new Scanner[no_buffer];
		for (int i = 0; i < no_buffer - 1; i++) {
			if (i < file_name.size()) {
				scanner[i] = new Scanner(new File(file_name.get(i)));
			}
		}
		ArrayList<Integer> merge_list = new ArrayList<Integer>(no_entries / no_buffer);
		ArrayList<Integer> file_no = new ArrayList<Integer>(no_entries / no_buffer);
		ArrayList<String> name = new ArrayList<String>(no_entries / no_buffer);
		for (int i = 0; i < no_buffer - 1; i++) {
			if (scanner[i] != null && scanner[i].hasNextInt()) {
				merge_list.add(scanner[i].nextInt());
				file_no.add(i);
				name.add(file_name.get(i));
			}
		}
		while (merge_list.isEmpty() == false) {
			int m = Collections.min(merge_list);
			int index = merge_list.indexOf(m);
			int fl = file_no.get(index);
			String s = name.get(index);
			merge_list.remove(index);
			file_no.remove(index);
			name.remove(index);
			Writer wr = new FileWriter((count2).toString() + ".txt", true);
			if (!file_name.contains((count2).toString() + ".txt")) {
				file_name.add((count2).toString() + ".txt");
			}
			wr.append(m + "\n");
			wr.close();

			if (scanner[fl].hasNext()) {
				merge_list.add(scanner[fl].nextInt());
				file_no.add(fl);
				name.add(s);
			} else {
				file_name.remove(s);
				File file = new File(s);
				file.delete();
			}
		}

	}

	public static void write_file(Integer fileno) throws IOException {
		Writer wr = new FileWriter((count2).toString() + ".txt");
		file_name.add((count2).toString() + ".txt");
		for (int i = 0; i < val.size(); i++) {
			wr.write(val.get(i).toString() + "\n");
		}
		count2++;
		wr.close();
	}

	public static void init_readfile(Scanner scan) throws IOException {
		try {
			int n = 0;
			val = new ArrayList<Integer>();
			while (scan.hasNextInt() && n < no_buffer) {
				val.add(scan.nextInt());
				n++;
			}
			Collections.sort(val);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws IOException {
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter Buffer Size: ");
		no_buffer = reader.nextInt();
		System.out.print("Enter No. of Entries: ");
		no_entries = reader.nextInt();
		Scanner scanner = new Scanner(new File("src/input.txt"));
		while (scanner.hasNextInt()) {
			init_readfile(scanner);
			write_file(count2);
		}
		count = count2 - 1;
		int start = 1;
		while (count != 1) {
			merge(start);
			count = count - (no_buffer - 1) + 1;
			count2++;
			start += no_buffer - 1;
		}
		count2--;
		File file = new File(file_name.get(0));
		File newFile = new File("src/final.txt");
		file.renameTo(newFile);
		System.out.println("Output File : final.txt");
		reader.close();
	}

}
