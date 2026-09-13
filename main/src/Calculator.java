import java.util.Locale;
import java.util.Scanner;

public class Calculator {
    private final byte FIRST = 1;
    private final byte COMMAND = 2;
    private final byte SECOND = 3;
    private final byte CALCULATION = 4;
    private byte step = FIRST;
    private double firstValue;
    private double secondValue;
    private char operator;
    private boolean isCleared = false;
    private boolean isOff = false;

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        System.out.println("Калькулятор запущен. Команда S - выключение. Команда С - сброс.");

        final Scanner input = new Scanner(System.in).useLocale(Locale.US);
        while (!calculator.isOff) {
            calculator.read(input);
        }

        input.close();
    }

    private boolean isCommand(char value) {
        return value == 'C' || value == 'S' || value == 'c' || value == 's';
    }

    private void handleInput(char value) {
        if (isCommand(value)) {
            isCleared = value == 'C' || value == 'c';
            isOff = isOff || value == 'S' || value == 's';
        } else {
            System.out.println("Введено недопустимое значение.");
        }
    }

    private void read(Scanner input) {
        isCleared = false;

        switch (step) {
            case FIRST: {
                System.out.println("Пожалуйста, введите первое число. Дробные числа вводятся через . (точку): ");
                if (input.hasNextDouble()) {
                    firstValue = input.nextDouble();
                    step = COMMAND;
                } else if (input.hasNext()) {
                    char value = input.next().charAt(0);
                    handleInput(value);
                } else {
                    isOff = true;
                }
                break;
            }

            case COMMAND: {
                System.out.println("Пожалуйста, введите оператор. Поддерживаются только операторы +, -, *, / ");
                char value = input.hasNext() ? input.next().charAt(0) : 's';
                if (value == '+' || value == '-' || value == '*' || value == '/') {
                    operator = value;
                    step = SECOND;
                } else {
                    handleInput(value);
                }
                break;
            }

            case SECOND: {
                System.out.println("Пожалуйста, введите второе число. Дробные числа вводятся через . (точку): ");
                if (input.hasNextDouble()) {
                    double value = input.nextDouble();
                    boolean isDivisionByZeroError = operator == '/' && value == 0;

                    if (isDivisionByZeroError) {
                        System.out.println("На ноль делить нельзя, введите значение отличное от нуля");
                    } else {
                        secondValue = value;
                        step = CALCULATION;
                    }
                } else if (input.hasNext()) {
                    char value = input.next().charAt(0);
                    handleInput(value);
                } else {
                    isOff = true;
                }
                break;
            }

            case CALCULATION: {
                double result = calculate();
                System.out.println("Результат вычислений: " + result);
                firstValue = result;
                step = COMMAND;
                break;
            }

            default:
                if (input.hasNext()) {
                    char value = input.next().charAt(0);
                    handleInput(value);
                    isCleared = true;
                } else {
                    isOff = true;
                }
                break;
        }

        if (isCleared) {
            step = FIRST;
        }
    }

    public double add(double arg1, double arg2) {
        return arg1 + arg2;
    }

    public double subtract(double arg1, double arg2) {
        return arg1 - arg2;
    }

    public double multiply(double arg1, double arg2) {
        return arg1 * arg2;
    }

    public double divide(double arg1, double arg2) {
        return arg1 / arg2;
    }

    public double calculate() {
        double result = 0.0;
        switch (operator) {
            case '+':
                result = add(firstValue, secondValue);
                break;
            case '-':
                result = subtract(firstValue, secondValue);
                break;
            case '*':
                result = multiply(firstValue, secondValue);
                break;
            case '/':
                result = divide(firstValue, secondValue);
                break;
            default:
                break;
        }
        return result;
    }
}
