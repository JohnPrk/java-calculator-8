package calculator.domain;

import java.util.Objects;
import java.util.Queue;

public class Calculator {

    private final DelimiterMapper delimiterMapper;
    private final DelimiterProcessor delimiterProcessor;

    public Calculator() {
        this.delimiterMapper = new DelimiterMapper();
        this.delimiterProcessor = new DelimiterProcessor(delimiterMapper);
    }

    public int calculate(String expression) {
        Queue<String> tokens = delimiterProcessor.processExpression(expression);
        return evaluateExpression(tokens);
    }

    private int evaluateExpression(Queue<String> tokens) {
        String poll = tokens.poll();
        int result = Integer.parseInt(Objects.requireNonNull(poll));
        while (!tokens.isEmpty()) {
            String operator = tokens.poll();
            int nextNumber = Integer.parseInt(Objects.requireNonNull(tokens.poll()));
            result = applyOperation(result, nextNumber, operator);
        }
        return result;
    }

    private int applyOperation(int a, int b, String operator) {
        if (operator.equals("+")) {
            return a + b;
        }
        throw new IllegalArgumentException(ServerErrorCode.WRONG_OPERATION_TYPE.getMessage());
    }
}
