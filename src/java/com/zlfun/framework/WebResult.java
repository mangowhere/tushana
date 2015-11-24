package com.zlfun.framework;

@WebEntity
public class WebResult {

    @WebParam("retcode")
    private int retCode;

    @WebParam("token")
    private String token;

    @WebParam("errormsg")
    private String errorMessage;

    public WebResult(String token) {
        this.retCode = 0;
        this.token = token;
        this.errorMessage = "OK";
    }

    public WebResult() {
        this.retCode = 0;
        this.token = "";
        this.errorMessage = "OK";
    }

    public WebResult(int retCode, String errorMessage) {
        this.retCode = retCode;
        this.errorMessage = errorMessage;
        this.token = "";
    }

    public WebResult(int retCode, String errorMessage, String token) {
        this.retCode = retCode;
        this.errorMessage = errorMessage;
        this.token = token;
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }
}
