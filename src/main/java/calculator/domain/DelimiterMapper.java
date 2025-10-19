package calculator.domain;

import java.util.HashMap;
import java.util.Map;

public class DelimiterMapper {
    private final Map<String, String> delimiterToOperatorMap;

    public DelimiterMapper() {
        delimiterToOperatorMap = new HashMap<>();
        initializeDefaultMappings();
    }

    private void initializeDefaultMappings() {
        delimiterToOperatorMap.put(",", "+");
        delimiterToOperatorMap.put(":", "+");
    }

    public void addCustomDelimiter(String delimiter, String operation) {
        delimiterToOperatorMap.put(delimiter, operation);
    }

    public String getOperatorForDelimiter(String delimiter) {
        return delimiterToOperatorMap.get(delimiter);
    }

    public boolean containsDelimiter(String delimiter) {
        return delimiterToOperatorMap.containsKey(delimiter);
    }
}