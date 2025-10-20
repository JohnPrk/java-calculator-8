package calculator.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionValidator {

    public void validateExpression(String expression) {
        if (expression.trim().isEmpty()) {
            return;
        }
        validateFormat(expression);
    }

    public void validateTokens(Queue<String> tokens) {
        validateTokenPattern(tokens);
    }

    private void validateFormat(String expression) {
        if (expression.startsWith("//")) {
            Matcher matcher = Pattern.compile("//(.)(?:\\\\n|\\n)(.*)").matcher(expression);
            if (!matcher.matches()) {
                throw new IllegalArgumentException(ErrorCode.CUSTOM_DELIMITER_SYNTAX_ERROR.getMessage());
            }
            String customDelimiter = matcher.group(1);
            String numbersPart = matcher.group(2);
            String regex = String.format("^[0-9\\h,:%s-]*$", Pattern.quote(customDelimiter));
            if (!numbersPart.matches(regex)) {
                throw new IllegalArgumentException(ErrorCode.UNSUPPORTED_DELIMITER.getMessage());
            }
        } else {
            if (!expression.matches("^[0-9\\h,:-]*$")) {
                throw new IllegalArgumentException(ErrorCode.UNSUPPORTED_DELIMITER.getMessage());
            }
        }
    }

    private void validateTokenPattern(Queue<String> tokens) {
        if (tokens.size() == 1 && "0".equals(tokens.peek())) {
            return;
        }
        List<String> tokenList = new ArrayList<>(tokens);
        ;
        if (tokens.size() == 1) {
            String token = tokenList.get(0);
            if (!isNumber(token)) {
                throw new IllegalArgumentException(ErrorCode.INVALID_FORMAT.getMessage());
            }
            if (!isPositiveInteger(token)) {
                throw new IllegalArgumentException(ErrorCode.NOT_POSITIVE_INTEGER.getMessage());
            }
            return;
        }
        if (tokenList.size() % 2 == 0) {
            throw new IllegalArgumentException(ErrorCode.INVALID_FORMAT.getMessage());
        }
        for (int i = 0; i < tokenList.size(); i++) {
            String token = tokenList.get(i);
            if (i % 2 == 0) {
                if (!isNumber(token)) {
                    throw new IllegalArgumentException(ErrorCode.INVALID_FORMAT.getMessage());
                }
                if (!isPositiveInteger(token)) {
                    throw new IllegalArgumentException(ErrorCode.NOT_POSITIVE_INTEGER.getMessage());
                }
            } else {
                if (!"+".equals(token)) {
                    throw new IllegalArgumentException(ErrorCode.INVALID_FORMAT.getMessage());
                }
            }
        }
    }

    private boolean isPositiveInteger(String numberToken) {
        int number = Integer.parseInt(numberToken);
        return number > 0;
    }

    private boolean isNumber(String token) {
        return token.matches("-?\\d+");
    }
}