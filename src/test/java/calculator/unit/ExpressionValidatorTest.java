package calculator.unit;

import calculator.domain.ErrorCode;
import calculator.domain.ExpressionValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

class ExpressionValidatorTest {

    private final ExpressionValidator validator = new ExpressionValidator();


    @Test
    @DisplayName("양수와 여러 기본 구분자(, [comma], : [colon])를 가지는 문자열이면 예외가 발생하지 않는다.")
    void allowDefaultMixedDelimiters() {
        Assertions.assertThatCode(() -> validator.validateExpression("1,2:3"))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("양수와 커스텀 구분자를 가지는 문자열이면 예외가 발생하지 않는다.")
    void allowCustomDelimiter() {
        Assertions.assertThatCode(() -> validator.validateExpression("//;\n11;2;3"))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("양수와 커스텀 구분자 및 기본 구분자를 혼용하여 가지는 문자열이면 예외가 발생하지 않는다.")
    void allowCustomAndDefaultMixedWithRealNewline() {
        Assertions.assertThatCode(() -> validator.validateExpression("//;\n1,2;3:4"))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("지원하지 않는 구분자가 포함되면 예외가 발생한다.")
    void rejectUnsupportedDelimiterPipe() {
        Assertions.assertThatThrownBy(() -> validator.validateExpression("1|2"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.UNSUPPORTED_DELIMITER.getMessage());
    }

    @Test
    @DisplayName("커스텀 구분자의 형식이 틀리다면 예외가 발생한다.")
    void rejectCustomWithoutNewline() {
        Assertions.assertThatThrownBy(() -> validator.validateExpression("//;1;2;3"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.CUSTOM_DELIMITER_SYNTAX_ERROR.getMessage());
    }


    @Test
    @DisplayName("숫자와 '+'가 교대로 오는 올바른 패턴이면 예외가 발생하지 않는다")
    void validTokensAlternatingPattern() {
        Assertions.assertThatCode(() -> validator.validateTokens(new LinkedList<String>(List.of("1", "+", "2", "+", "3"))))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("시작이 숫자가 아닌 경우 예외가 발생한다.")
    void invalidStartsWithNotNumbers() {
        Assertions.assertThatThrownBy(() -> validator.validateTokens(new LinkedList<String>(List.of("+", "1"))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.INVALID_FORMAT.getMessage());
    }

    @Test
    @DisplayName("끝이 숫자가 아닌 경우 예외가 발생한다.")
    void invalidEndsWithNotNumbers() {
        Assertions.assertThatThrownBy(() -> validator.validateTokens(new LinkedList<String>(List.of("1", "+"))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.INVALID_FORMAT.getMessage());
    }

    @Test
    @DisplayName("숫자연산자숫자...연산자숫자의 형태가 아닌 경우 예외가 발생한다.")
    void invalidConsecutiveOperators() {
        Assertions.assertThatThrownBy(() -> validator.validateTokens(new LinkedList<String>(List.of("1", "+", "+", "2"))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.INVALID_FORMAT.getMessage());
    }

    @Test
    @DisplayName("연산자가 없는 연속 숫자의 경우 예외가 발생한다.")
    void invalidConsecutiveNumbers() {
        Assertions.assertThatThrownBy(() -> validator.validateTokens(new LinkedList<String>(List.of("1", "2"))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.INVALID_FORMAT.getMessage());
    }

    @Test
    @DisplayName("지원되지 않는 연산자면 예외가 발생한다.")
    void invalidUnsupportedOperatorInTokens() {
        Assertions.assertThatThrownBy(() -> validator.validateTokens(new LinkedList<String>(List.of("1", "-", "2"))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.INVALID_FORMAT.getMessage());
    }

    @Test
    @DisplayName("0이 들어가는 경우 예외가 발생한다.")
    void rejectZeroAsSecondOperand() {
        Assertions.assertThatThrownBy(() -> validator.validateTokens(new LinkedList<String>(List.of("1", "+", "0"))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.NOT_POSITIVE_INTEGER.getMessage());
    }

    @Test
    @DisplayName("음수가 들어가는 경우 예외가 발생한다.")
    void rejectNegativeAsFirstOperand() {
        Assertions.assertThatThrownBy(() -> validator.validateTokens(new LinkedList<String>(List.of("-2", "+", "3"))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.NOT_POSITIVE_INTEGER.getMessage());
    }
}
