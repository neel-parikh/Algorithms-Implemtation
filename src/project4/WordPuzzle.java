package project4;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WordPuzzle {

	static char grid[][];
	static int listcount = 0, treecount = 0, mapcount = 0;
	static List<String> result = new ArrayList<>(); 
	static List<String> listDictionary = new LinkedList<String>();
	static AvlTree<String> treeDictionary = new AvlTree<>();
	static MyHashTable<String> mapDictionary = new MyHashTable<>();
	public static void main(String[] args) {
		int rows, cols;
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter number of rows: ");
		rows = sc.nextInt();
		System.out.print("Enter number of columns: ");
		cols = sc.nextInt();
		grid = new char[rows][cols];
		
		// Initial setup
		initialSetup();
		
		// Print the grid
		printGrid();
				
		// Check dictionary
		long start, end, time1,time2,time3;
		start = System.currentTimeMillis();
		checkWord("linkedlist");
		end = System.currentTimeMillis();
		time1 = (end - start);
		
		start = System.currentTimeMillis();
		checkWord("tree");
		end = System.currentTimeMillis();
		time2 = (end - start);
		
		start = System.currentTimeMillis();
		checkWord("hashmap");
		end = System.currentTimeMillis();
		time3 = (end - start);
		
		System.out.println("----------------------");
		System.out.println("Linked List Running time (msec): " + time1);
		System.out.println("Tree running time (msec): " + time2);
		System.out.println("Hash Map running time (msec): " + time3);
		
		/*System.out.println("List Count : "+listcount);
		System.out.println("Tree Count : "+treecount);
		System.out.println("Map Count : "+mapcount);*/
		sc.close();
	}
	
	// Initial setup to perform following things:
		// 1. Add dictionary to linked list, tree and hash map
		// 2. Fill the M x N grid with random characters
		private static void initialSetup() {
			dictionaryToDataStructure();
			fillGrid();
		}

		// Add dictionary to linked list, tree and HashMap
		private static void dictionaryToDataStructure() {
			try {
				String word;
				Scanner sf = new Scanner(new File("dictionary.txt"));
				while (sf.hasNextLine()) {
					word = sf.nextLine();
					listDictionary.add(word);
					treeDictionary.insert(word);
					mapDictionary.insert(word);
				}
				sf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Print the M x N grid of characters
		private static void printGrid() {
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					System.out.print(grid[i][j] + " ");
				}
				System.out.println();
			}
		}

		// Fill the M x N grid with random characters
		private static void fillGrid() {
			Random rc = new Random();
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					grid[i][j] = (char) (rc.nextInt(26) + 'a');
				}
			}
		}
	private static void checkWord(String type) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				checkDiagonal(i, j, type);
				checkHorizontal(i, j, type);
				checkVertical(i, j, type);
			}
		}
	}

	private static void checkHorizontal(int row, int col, String type) {
		StringBuilder ans = new StringBuilder("");
		String ansRev;
		ArrayList<String> ansList = new ArrayList<>();
		for (int j = col; j < grid[0].length; j++) {
			ans.append(grid[row][j]);
			if(!ansList.contains(ans))
				ansList.add(ans.toString());
			ansRev = getReverse(ans.toString());
			if(!ansList.contains(ansRev))
				ansList.add(ansRev);
		}
		
		ansList.sort(Comparator.comparing(String::length).reversed());
		searchWord(ansList,type,row,col,"Horizontal");
	}
	
	private static void checkVertical(int row, int col, String type) {
		StringBuilder ans = new StringBuilder("");
		String ansRev;
		ArrayList<String> ansList = new ArrayList<>();
		for (int i = row; i < grid.length; i++) {
			ans.append(grid[i][col]);
			if(!ansList.contains(ans))
				ansList.add(ans.toString());
			ansRev = getReverse(ans.toString());
			if(!ansList.contains(ansRev))
				ansList.add(ansRev);
		}
		
		ansList.sort(Comparator.comparing(String::length).reversed());
		searchWord(ansList,type,row,col,"Vertical");
	}

	private static void checkDiagonal(int row, int col, String type) {
		StringBuilder ans = new StringBuilder("");
		String ansRev;
		ArrayList<String> ansList = new ArrayList<>();
		for (int i = row, j = col; i < grid.length && j < grid[0].length; i++, j++) {
			ans.append(grid[i][j]);
			if(!ansList.contains(ans))
				ansList.add(ans.toString());
			ansRev = getReverse(ans.toString());
			if(!ansList.contains(ansRev))
				ansList.add(ansRev);
		}
		
		for (int i = row, j = col; i >= 0 && j < grid[0].length; i--, j++) {
			ans.append(grid[i][j]);
			if(!ansList.contains(ans))
				ansList.add(ans.toString());
			ansRev = getReverse(ans.toString());
			if(!ansList.contains(ansRev))
				ansList.add(ansRev);
		}
		ansList.sort(Comparator.comparing(String::length).reversed());
		searchWord(ansList,type,row,col, "Diagonal");
	}

	private static String getReverse(String string) {
		StringBuilder sb = new StringBuilder(string);
		return sb.reverse().toString();
	}
	
	private static void searchWord(ArrayList<String> ansList, String type,int row,int col, String pr) {
		for (String word : ansList) {
			if (type.equals("linkedlist")) {
				if (listDictionary.contains(word)) {
					listcount++;
					//break;
				}
			}
			else if(type.equals("tree")) {
				if(treeDictionary.contains(word)) {
					treecount++;
					//break;
				}
			}
			else if(type.equals("hashmap")) {
				if(mapDictionary.contains(word)) {
					System.out.println(pr+ " found at ("+row+", "+col+"): " + word);
					mapcount++;
					//break;
				}
			}
		}
	}
}
