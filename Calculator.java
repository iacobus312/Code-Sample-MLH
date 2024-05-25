import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        // Reading in input and converting to array of Strings
        Scanner input = new Scanner(System.in);
        String math_input = input.nextLine();

        // Converting infix to postfix notation
        String postfix_math_input = Expression.toPostfix(math_input);
        System.out.println(postfix_math_input);

        // Doing computation on postfix notation
        boolean print = true;
        BCLinkStack<String> myStack = new BCLinkStack<>();
        for (String n : postfix_math_input.split(" ")) {
            if (Expression.isOperator(n)) {
                int y = Integer.parseInt(myStack.pop());
                int x = Integer.parseInt(myStack.pop());
                String result = Expression.operatorEval(n, x, y);
                if (result.equals("zero")) {
                    System.out.println("undef");
                    print = false;
                    break;
                } else {
                    myStack.push(result);
                }
            } else {
                myStack.push(n);
            }
        }
        if (print) {
            System.out.println(myStack.pop());
        }
    }
}

// infix to postfix implementation
class Expression {
    private static int rank(String op) {
        switch (op) {
            case "*":
            case "/":
                return 2;
            case "+":
            case "-":
                return 1;
            default:
                return -1;
        }
    }

    private static final String SPACE = " ";

    public static String toPostfix(String expr) {
        StringBuilder result = new StringBuilder();
        BCLinkStack<String> operators = new BCLinkStack<>();
        for (String token : expr.split("\\s+")) {
            if (rank(token) > 0) {
                while (!operators.isEmpty() &&
                        rank(operators.peek()) >= rank(token)) {
                    result.append(operators.pop() + SPACE);
                }
                operators.push(token);
            } else {
                result.append(token + SPACE);
            }
        }
        while (!operators.isEmpty()) {
            result.append(operators.pop() + SPACE);
        }
        return result.toString();
    }

    public static boolean isOperator(String n) {
        return (n.equals("-") || n.equals("+") || n.equals("/") || n.equals("*"));
    }

    public static String operatorEval(String operator, int num1, int num2) {
        if (operator.equals("+")) {
            int a = num1 + num2;
            return Integer.toString(a);
        } else if (operator.equals("-")) {
            int a = num1 - num2;
            return Integer.toString(a);
        } else if (operator.equals("*")) {
            int a = num1 * num2;
            return Integer.toString(a);
        } else {
            if (num2 == 0) {
                return "zero";
            }
            int a = num1 / num2;
            return Integer.toString(a);
        }
    }
}

// Generic Stack implementation
class BCLinkStack<E> {
    private class Node<T> {
        private T value;
        private Node<T> next;

        Node(T v, Node<T> n) {
            value = v;
            next = n;
        }
    }

    private int size;
    private Node<E> top;

    public BCLinkStack() {
        size = 0;
        top = null;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public E pop() {
        if (size == 0) {
            System.out.println("Cannot pop when Stack is empty.");
            return null;
        }
        E ret = top.value;
        top = top.next;
        size--;
        return ret;
    }

    public void push(E x) {
        Node<E> newTop = new Node<>(x, top);
        top = newTop;
        size++;
    }

    public int size() {
        return size;
    }

    public E peek() {
        return top.value;
    }

}
