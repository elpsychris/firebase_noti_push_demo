package push_noti.model;

public class Result {
    private ReturnCode isSuccess;
    private String descr = "";

    public enum ReturnCode {
        SUCCESS(0), FAILED(-1);
        private int returnCode;

        ReturnCode(int returnCode) {
            this.returnCode = returnCode;
        }
    }

    public Result(ReturnCode isSuccess, String descr) {
        this.isSuccess = isSuccess;
        this.descr = descr;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public ReturnCode getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(ReturnCode isSuccess) {
        this.isSuccess = isSuccess;
    }
}
