package cn.edu.nju.iselab.mnist.vo;

import javax.xml.ws.Response;

/**
 * The response value object to represent the operation is success or failed
 * @param <T> the data type of data, which carries the previous operation results
 */
public class ResponseVo<T> {

    private int status = 200;

    private String reason;

    private boolean success;

    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    private ResponseVo(int status, String reason, boolean success, T data) {
        this.status = status;
        this.reason = reason;
        this.success = success;
        this.data = data;
    }

    private ResponseVo() {
    }

    /**
     * Build a failed response, in which the status is <i>200</i>, the success is true, the reason is null and the data are given.
     * @param d the data carried by this response, it can be passed to other object after getting the previous operation results
     * @param <T> the type of data
     * @return  A response construct by builders, it is a success response
     */
    public static <T> ResponseVo<T> ok(T d) {
        return new Builder()
               .status(200)
               .success(true)
               .reason(null)
               .build(d);
    }

    /**
     * The builder of the response object, it is a chain of builders, in which providing methods for every field of response.
     */
    public static class Builder {

        private int status = 200;

        private String reason;

        private boolean success;

        /**
         * Given a status code of response.
         * @param status the status of the response, <pre>200</pre> is a legal operation represented by the response, others are error
         * @return The chain of the builder
         */
        public Builder status(int status) {
            this.status = status;
            return this;
        }

        /**
         * Given a success indicating the operation being success or not.
         * @param success success or not, it is success when its value is <pre>true</pre>, failed when <pre>failed</pre>
         * @return The chain of the builder
         */
        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        /**
         * Given a reason indicating the reason when failed.
         * @param reason the reason why this operation is failed
         * @return The chain of the builder
         */
        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        /**
         * Build a response config by this chain of builders.
         * @param data the data carried by this response, it can be passed to other object after getting the previous operation results
         * @param <T> the type of data
         * @return A response construct by builders
         */
        public <T> ResponseVo<T> build(T data) {
            ResponseVo<T> response = new ResponseVo<T>();
            response.status = status;
            response.success = success;
            response.reason = reason;
            response.data = data;
            return response;
        }
    }

    /**
     * Build a failed response, in which the status is <i>500</i>, the success is false, and other fields are given.
     * @param reason the reason why this operation is failed
     * @param d the data carried by this response, it can be passed to other object after getting the previous operation results
     * @param <T> the type of data
     * @return A response construct by builders, it is a failed response
     */
    public static <T> ResponseVo<T> failed(String reason, T d) {
        return failed(reason, 500, d);
    }

    /**
     * Build a failed response, in which the status is <i>500</i>, the success is false, the data is an empty object and other fields are given.
     * @param reason the reason why this operation is failed
     * @return A response construct by builders, it is a failed response
     */
    public static ResponseVo<Object> failed(String reason) {
        return failed(reason, new Object());
    }

    /**
     * Build a failed response, in which the success is false and other fields are given.
     * @param reason the reason why this operation is failed
     * @param status the status of the response, <pre>200</pre> is a legal operation represented by the response, others are error
     * @param d the data carried by this response, it can be passed to other object after getting the previous operation results
     * @param <T> the type of data
     * @return A response construct by builders, it is a failed response
     */
    public static <T> ResponseVo<T> failed(String reason, int status, T d) {
        return new Builder()
                .status(status)
                .reason(reason)
                .success(false)
                .build(d);
    }

    /**
     * Build a failed response, in which the success is false, the data is an empty object and other fields are given.
     * @param reason the reason why this operation is failed
     * @param status the status of the response, <pre>200</pre> is a legal operation represented by the response, others are error
     * @return A response construct by builders, it is a failed response
     */
    public static ResponseVo<Object> failed(String reason, int status) {
        return failed(reason, status, new Object());
    }
}
