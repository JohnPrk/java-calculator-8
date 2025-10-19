package calculator.domain;

import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DelimiterProcessor {
    private static final Pattern CUSTOM_DELIMITER_PATTERN = Pattern.compile("//(.)(?:\\\\n|\\n)(.*)");
    private final DelimiterMapper delimiterMapper;

    public DelimiterProcessor(DelimiterMapper delimiterMapper) {
        this.delimiterMapper = delimiterMapper;
    }

    public Queue<String> processExpression(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            Queue<String> emptyQueue = new LinkedList<>();
            emptyQueue.add("0");
            return emptyQueue;
        }
        String processedExpression = extractAndRegisterCustomDelimiter(expression);
        return parseTokens(processedExpression);
    }

    private String extractAndRegisterCustomDelimiter(String expression) {
        Matcher matcher = CUSTOM_DELIMITER_PATTERN.matcher(expression);
        if (matcher.find()) {
            String customDelimiter = matcher.group(1);
            String numbers = matcher.group(2);
            delimiterMapper.addCustomDelimiter(customDelimiter, "+");
            return numbers;
        }
        return expression;
    }

    private Queue<String> parseTokens(String expression) {
        Queue<String> tokens = new LinkedList<>();
        StringBuilder numberBuffer = new StringBuilder();
        for (char c : expression.toCharArray()) {
            String character = String.valueOf(c);
            if (isDelimiter(character)) {
                flushNumberBuffer(tokens, numberBuffer);
                tokens.add(delimiterMapper.getOperatorForDelimiter(character));
            } else {
                numberBuffer.append(c);
            }
        }
        flushNumberBuffer(tokens, numberBuffer);
        return tokens;
    }

    private void flushNumberBuffer(Queue<String> tokens, StringBuilder numberBuffer) {
        if (numberBuffer.length() > 0) {
            tokens.add(numberBuffer.toString());
            numberBuffer.setLength(0);
        }
    }

    private boolean isDelimiter(String str) {
        return delimiterMapper.containsDelimiter(str);
    }
}