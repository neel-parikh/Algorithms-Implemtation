package Project1;

import java.util.Scanner;

public class Scores {
	
	public static void main(String[] args) {
		
		int totalMarks;
		Scanner in = new Scanner(System.in);
		
		String studentNames[] = new String[10];
		int score[][] = new int[studentNames.length][5];
		
		for(int i=0; i<studentNames.length; i++) {
			System.out.print("Enter student name["+ i +"]: ");
			studentNames[i] = in.next();
			for(int j=0; j<score[i].length; j++) {
				System.out.print("Enter quiz "+(j+1)+" score for "+studentNames[i]+": ");
				score[i][j] = in.nextInt();
			}
		}
		
		for(int i=0; i<studentNames.length; i++) {
			totalMarks = 0;
			for(int j=0; j<score[i].length; j++) {
				totalMarks+= score[i][j];
			}
			System.out.println("Name: "+studentNames[i]+" - Average Score: "+(totalMarks)/score[i].length);
		}
		
		in.close();
	}
}
