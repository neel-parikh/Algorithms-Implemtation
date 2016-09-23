package Project1;

public class TestSquare {
	
	public static void main(String[] args) {
		
		Square sq1 = new Square();
		Square sq2 = new Square(6);
		
		System.out.println("Area of square 1(default constructor): "+sq1.getArea());
		System.out.println("Area of square 2(parameterized constructor): "+sq2.getArea());
	}
}
