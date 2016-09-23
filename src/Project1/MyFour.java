package Project1;

public class MyFour<T> {

	public static void main(String[] args) {
		
		MyFour<String> my1 = new MyFour<String>("Hello", "Hello", "Hello", "Hello");
		System.out.println("--------- String ---------");
		System.out.println(my1);
		System.out.println(my1.allEquals());
		
		System.out.println("--------- Integer --------- ");
		MyFour<Integer> my2 = new MyFour<Integer>(1, 2, 3, 4);
		System.out.println(my2);
		System.out.println(my2.allEquals());
		
		System.out.println("--------- Integer after Left Shift ---------");
		my2.shiftLeft();
		System.out.println(my2);
	}
	
	private T item1, item2, item3, item4;
	
	public MyFour(T i1, T i2, T i3, T i4) {
		item1 = i1;
		item2 = i2;
		item3 = i3;
		item4 = i4;
	}
	
	public boolean allEquals() {
		if (item1.equals(item2) && item2.equals(item3) && item3.equals(item4))
			return true;
		else
			return false;
	}
	
	public void shiftLeft() {
		T itemp = item1;
		item1=item2;
		item2=item3;
		item3=item4;
		item4=itemp;
	}
	
	public String toString() {
		return "(" + item1 + ", " + item2 + ", " + item3 + ", " + item4 + ")";
	}
}
