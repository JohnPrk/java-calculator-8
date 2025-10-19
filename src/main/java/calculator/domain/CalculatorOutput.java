package calculator.domain;

public class CalculatorOutput {

    private static final String RESULT_PREFIX = "결과 : ";
    private static final String INPUT_GUIDE = "덧셈할 문자열을 입력해 주세요.";

    public static void printResult(int result) {
        System.out.println(RESULT_PREFIX + result);
    }

    public static void printInputGuide() {
        System.out.println(INPUT_GUIDE);
    }
}
