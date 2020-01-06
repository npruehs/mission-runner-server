package de.npruehs.missionrunner.server;

public class NetworkResponse<T> {
    private Error error;
    private boolean success;
    private T data;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> NetworkResponse<T> newSuccessResponse(T data) {
    	NetworkResponse<T> response = new NetworkResponse<T>();
    	response.setError(null);
    	response.setSuccess(true);
    	response.setData(data);
    	return response;
    }
    
    public static <T> NetworkResponse<T> newErrorResponse(int errorCode, String errorMessage) {
    	NetworkResponse<T> response = new NetworkResponse<T>();
    	response.setError(new Error(errorCode, errorMessage));
    	response.setSuccess(false);
    	response.setData(null);
    	return response;
    }
    
    public static class Error {
        private String errorMessage;
        private int errorCode;

        public Error() {
        }
        
        public Error(int errorCode, String errorMessage) {
        	this.errorCode = errorCode;
        	this.errorMessage = errorMessage;
        }
        
        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }
    }
}
