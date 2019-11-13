package fpt.com.virtualoutfitroom.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseResult<T> implements Serializable {
    @SerializedName("result-code")
    private int resultCode;

    private String message;

    private boolean success;

    private boolean error;

    private T data;

    public ResponseResult() {
    }


    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
