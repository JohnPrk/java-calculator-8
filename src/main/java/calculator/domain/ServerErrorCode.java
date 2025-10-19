package calculator.domain;

public enum ServerErrorCode {

    WRONG_OPERATION_TYPE("잘못된 사칙 연산자입니다.");

    private final String message;

    ServerErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
