package bulletin_board.bulletin_board.exception;

public enum MemberExceptionType {
    ALREADY_EXIST_ID(600, "이미 존재하는 아이디입니다."),
    ALREADY_EXIST_NICKNAME(601, "이미 존재하는 닉네임입니다."),
    ALREADY_EXIST_ID_AND_NICKNAME(602, "이미 존재하는 아이디와 닉네임입니다."),
    NOT_EXIST_ID(603, "등록된 아이디가 아닙니다."),
    NOT_MATCH_ID_AND_PASSWORD(604,"아이디와 비밀번호가 일치하지 않습니다.");

    private int errorCode;
    private String errorMessage;

    MemberExceptionType(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
