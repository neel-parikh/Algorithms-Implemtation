package Assignment1;

public class Ques6b {
	public static void main(String[] args) {
		
		int arr[] = {1,3,5,7,9};
		System.out.println("Count of odd numbers is: " + countOdd(arr, 0));
	}
	
	public static int countOdd(int arr[], int pos) {
		
		//Base Case
		if (pos == arr.length)
			return 0;
		//Recursive Call
		else {
			if(arr[pos] % 2 != 0)
				return 1 + countOdd(arr, pos+1);
			else
				return countOdd(arr, pos+1);
		}
	}
}