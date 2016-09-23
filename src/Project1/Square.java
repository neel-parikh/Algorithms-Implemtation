package Project1;

public class Square {
	
	private int length;
	
	public Square() {
		length = 1;
	}
	
	public Square(int len) {
		length = len;
	}
	
	public int getArea() {
		return length * length;
	}
	
}
