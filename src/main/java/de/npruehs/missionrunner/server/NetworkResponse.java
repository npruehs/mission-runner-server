package de.npruehs.missionrunner.server;

import lombok.Getter;
import lombok.Setter;

public class NetworkResponse<T> {
    @Getter
    @Setter
	private Error error;
    
    @Getter
    @Setter
    private boolean success;
    
    @Getter
    @Setter
    private T data;

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
