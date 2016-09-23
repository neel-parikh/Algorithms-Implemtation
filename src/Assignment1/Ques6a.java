package Assignment1;

public class Ques6a {
	public static void main(String[] args) {
		displaySeries(5);
	}
	
	public static void displaySeries(int n) {
		
		//Base Case
		if (n == 0) {
			System.out.print(n + " ");
		}
		//Recursive Case
		else {
			System.out.print(n + " ");
			displaySeries(n - 1);
			System.out.print(n + " ");
		}
	}
}
