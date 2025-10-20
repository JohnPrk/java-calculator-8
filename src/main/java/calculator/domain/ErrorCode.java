package calculator.domain;

public enum ErrorCode {

    INVALID_FORMAT("입력 형식이 잘못되었습니다. '숫자 혹은 숫자구분자숫자...구분자숫자' 형식이어야 합니다."),
    UNSUPPORTED_DELIMITER("허용되지 않은 구분자입니다. 쉼표(,), 콜론(:) 또는 커스텀 구분자만 사용할 수 있습니다."),
    CUSTOM_DELIMITER_SYNTAX_ERROR("커스텀 구분자 형식이 잘못되었습니다. '//구분자\n입력 문자열' 형식을 따라야 합니다."),
    NOT_POSITIVE_INTEGER("양의 정수만 허용됩니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
