package calculator.unit;

import calculator.domain.Calculator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    @DisplayName("양수와 :[colon] 구분자를 가지는 문자열인 경우 구분자를 기준으로 분리한 각 숫자의 합을 계산할 수 있다.")
    void calculateWithDigitNumbersAndColonDelimiters() {
        Assertions.assertThat(calculator.calculate("1:1:1")).isEqualTo(3);
    }

    @Test
    @DisplayName("양수와 ,[comma] 구분자를 가지는 문자열인 경우 구분자를 기준으로 분리한 각 숫자의 합을 계산할 수 있다.")
    void calculateWithDigitNumbersAndCommaDelimiters() {
        Assertions.assertThat(calculator.calculate("1,1")).isEqualTo(2);
    }

    @Test
    @DisplayName("양수와 여러 구분자(,[comma] + .[colon])를 가지는 문자열인 경우 구분자를 기준으로 분리한 각 숫자의 합을 계산할 수 있다.")
    void calculateWithDigitNumbersAndMixedDelimiters() {
        Assertions.assertThat(calculator.calculate("1,1:1")).isEqualTo(3);
    }

    @Test
    @DisplayName("한 자리 수가 넘는 양수와 구분자를 가지는 문자열인 경우 구분자를 기준으로 분리한 각 숫자의 합을 계산할 수 있다.")
    void calculateWithMultiDigitNumbersAndDelimiters() {
        Assertions.assertThat(calculator.calculate("12,111,1")).isEqualTo(124);
    }

    @Test
    @DisplayName("양수와 커스텀 구분자를 가지는 문자열인 경우 구분자를 기준으로 분리한 각 숫자의 합을 계산할 수 있다.")
    void calculateWithDigitNumbersAndCustomDelimiters() {
        Assertions.assertThat(calculator.calculate("//;\n1;2;3")).isEqualTo(6);
    }

    @Test
    @DisplayName("양수와 커스텀 구분자를 포함한 여러 구분자를 가지는 문자열인 경우 구분자를 기준으로 분리한 각 숫자의 합을 계산할 수 있다.")
    void calculateWithDigitNumbersAndCustomAndMixedDelimiters() {
        Assertions.assertThat(calculator.calculate("//;\\n1,2;3:4")).isEqualTo(10);
    }

    @Test
    @DisplayName("구분자 없이 양수만 입력되었을 경우 경우 양수의 값을 반환한다.")
    void returnNumberInCaseOfOnlyInputNumber() {
        Assertions.assertThat(calculator.calculate("1")).isEqualTo(1);
    }

    @Test
    @DisplayName("예외적으로 빈 값이 입력되었을 경우 0을 반환한다.")
    void returnZeroInCaseOfInputIsBlank() {
        Assertions.assertThat(calculator.calculate("")).isEqualTo(0);
    }

    @Test
    @DisplayName("커스텀 구분자를 등록했지만 구분자 없이 양수만 입력되었을 경우 양수의 값을 반환한다.")
    void returnNumberDespiteNotEnrolledCustomDelimitersOnlyNumber() {
        Assertions.assertThat(calculator.calculate("//;\\n11")).isEqualTo(11);
    }
}
