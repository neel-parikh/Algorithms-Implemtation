package Project1;

import java.util.Scanner;

public class EvaluateTemperature {
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter the temperature: ");
		int degree = in.nextInt();
		
		System.out.print("Enter C or F: ");
		String convert = in.next();
		
		if (convert.equals("C"))
			degree = (int)(degree * 1.8) + 32;
		
		if (degree < 0)
			System.out.println("Extremely cold");
		else if (degree >=0 && degree<=32)
			System.out.println("Very Cold");
		else if (degree >=33 && degree<=50)
			System.out.println("Cold");
		else if (degree >=51 && degree<=70)
			System.out.println("Mild");
		else if (degree >=71 && degree<=90)
			System.out.println("Warm");
		else if (degree >=91 && degree<=100)
			System.out.println("Hot");
		else if (degree > 100)
			System.out.println("Very Hot");
		
		in.close();
	}
}
