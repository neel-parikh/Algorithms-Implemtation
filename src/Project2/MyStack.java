package Project2;

import java.util.ArrayList;

// Generic MyStack class implementation using ArrayList
public class MyStack<E> {

	private ArrayList<E> li = new ArrayList<>();

	public int size() {
		return li.size() - 1;
	}

	public E top() {
		return li.get(size());
	}

	public void push(E val) {
		li.add(val);
	}

	public E pop() {
		return li.remove(size());
	}

	public boolean isEmpty() {
		return li.isEmpty();
	}

	// Testing MyStack class implementation
	public static void main(String[] args) {
		MyStack<Character> st = new MyStack<>();
		String check = "[({})]";

		System.out.println("Checking for input: " + check);
		for (int i = 0; i < check.length(); i++) {
			if (check.charAt(i) == '{' || check.charAt(i) == '(' || check.charAt(i) == '[') {
				st.push(check.charAt(i));
			} else if ((check.charAt(i) == '}' && st.top() == '{') 
					|| (check.charAt(i) == ']' && st.top() == '[')
					|| (check.charAt(i) == ')' && st.top() == '(')) {
				st.pop();
			} else {
				System.out.println("Invalid !!");
				break;
			}
		}
		System.out.println("Valid");
	}
}