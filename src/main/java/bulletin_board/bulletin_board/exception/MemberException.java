package bulletin_board.bulletin_board.exception;

public class MemberException extends RuntimeException {
    private MemberExceptionType exceptionType;

    public MemberException(MemberExceptionType exceptionType){
        this.exceptionType = exceptionType;
    }
}
