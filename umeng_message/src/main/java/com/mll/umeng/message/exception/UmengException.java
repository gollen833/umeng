package com.mll.umeng.message.exception;

import java.io.IOException;
import java.util.Properties;

/**
 * @author:walter
 * @date:2016/4/8
 * @comment:友盟异常
 *
 */
public class UmengException extends RuntimeException {

    private static Properties apiErrorProps = null;
    private static Object lock = new Object();

    public static enum ErrorType{
        sys_error,http_error,api_error;
    }

    private ErrorType errorType;
    /**
     * api错误码
     */
    private String errorCode;
//    private int httpCode;

    private UmengException(){}
    private UmengException(Throwable t){
        super(t);
    }

    public static UmengException newSysError(Throwable t){
        UmengException pushException = new UmengException(t);
        pushException.errorType = ErrorType.sys_error;
        return pushException;
    }
    /*public static PushException newHttpError(int httpCode){
        PushException pushException = new PushException();
        pushException.errorType = ErrorType.http_error;
        pushException.httpCode = httpCode;
        return pushException;
    }*/
    public static UmengException newApiError(String errorCode){
        UmengException pushException = new UmengException();
        pushException.errorType = ErrorType.api_error;
        pushException.errorCode = errorCode;
        return pushException;
    }

    public String getErrorCode() {
        return errorCode;
    }
    public ErrorType getErrorType() {
        return errorType;
    }
    /*public int getHttpCode() {
        return httpCode;
    }*/

    public String getApiErrorMessage(){
        if(apiErrorProps == null){
            synchronized(lock){
                if (apiErrorProps==null){
                    try{
                        apiErrorProps = new Properties();
                        apiErrorProps.load(UmengException.class.getClassLoader().getResourceAsStream("umeng_apierrors.properties"));
                        return apiErrorProps.getProperty(this.errorCode);
                    }catch (IOException e){
                        throw new RuntimeException("读取友盟api错误码文件失败");
                    }
                }
            }
        }
        return apiErrorProps.getProperty(this.errorCode, this.errorCode);
    }

    @Override
    public String toString() {
        if (this.errorType == ErrorType.sys_error){
            StringBuffer stackTrace = new StringBuffer();
            for (StackTraceElement element:this.getStackTrace()){
                stackTrace.append(element.toString());
                stackTrace.append("\r\n");
            }
            return "系统异常:" + stackTrace;
        }

        return "api异常:errorCode(" + errorCode + ":"+getApiErrorMessage()+")";
    }

}


