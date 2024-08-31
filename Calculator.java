import java.util.Scanner;
import java.util.Stack;

public class Calculator {
    public static int evaluateExpression(String expression) {
        Stack<Integer> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                int num = 0;
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    num = num * 10 + (expression.charAt(i) - '0');
                    i++;
                }
                i--; // Adjust for the extra increment
                operands.push(num);
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (operators.peek() != '(') {
                    int op2 = operands.pop();
                    int op1 = operands.pop();
                    char operator = operators.pop();
                    operands.push(performOperation(op1, op2, operator));
                }
                operators.pop(); // Remove the '('
            } else if (isOperator(c)) {
                while (!operators.isEmpty() && precedence(c) <= precedence(operators.peek())) {
                    int op2 = operands.pop();
                    int op1 = operands.pop();
                    char operator = operators.pop();
                    operands.push(performOperation(op1, op2, operator));
                }
                operators.push(c);
            }
        }

        while (!operators.isEmpty()) {
            int op2 = operands.pop();
            int op1 = operands.pop();
            char operator = operators.pop();
            operands.push(performOperation(op1, op2, operator));
        }

        return operands.pop();
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static int precedence(char c) {
        if (c == '+' || c == '-') {
            return 1;
        } else if (c == '*' || c == '/') {
            return 2;
        }
        return -1;
    }

    private static int performOperation(int op1, int op2, char operator) {
        switch (operator) {
            case '+':
                return op1 + op2;
            case '-':
                return op1 - op2;
            case '*':
                return op1 * op2;
            case '/':
                return op1 / op2;
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an arithmetic expression: ");
        String expression = scanner.nextLine();
        int result = evaluateExpression(expression);
        System.out.println("Result: " + result);
    }
}

