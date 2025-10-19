package calculator;

import calculator.domain.Calculator;
import calculator.domain.CalculatorInput;
import calculator.domain.CalculatorOutput;

public class Application {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        CalculatorOutput.printInputGuide();
        String expressionWithNumberAndOperator = CalculatorInput.input();
        int result = calculator.calculate(expressionWithNumberAndOperator);
        CalculatorOutput.printResult(result);
    }
}
