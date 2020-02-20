package com.example.upload.utils;

/**
 * 结果集对象
 * @author zhouhao
 *
 * @param <T>
 */
public class Result<T> {
	private Integer status;
	
	private String msg;
	
	private T data;

	private Result(){}
	
	public Result(T t){
		this.data = t;
	}

	/**
	 * 参数对象
	 */
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	/**
	 * 状态码
	 * 1	success
	 * 0	error
	 */
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 返回消息
	 */
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 操作成功
	 */
	public static <T> Result<T> ok(T t, String msg) {
		Result<T> result = new Result<T>();
		result.setStatus(CommUtil.HttpStatus.HTTP_200);
		result.setMsg(msg);
		result.setData(t);
		return result;
	}

	/**
	 * 操作成功
	 */
	public static <T> Result<T> ok(T t) {
		return ok(t, "success");
	}

	/**
	 * 操作成功
	 */
	public static <T> Result<T> ok() {
		return ok(null, "success");
	}

	/**
	 * 操作失败
	 */
	private static <T> Result<T> fail(T t, Integer status, String msg) {
		Result<T> result = new Result<>();
		result.setStatus(status);
		result.setMsg(msg);
		result.setData(t);
		return result;
	}

	/**
	 * 操作失败
	 */
	public static <T> Result<T> fail(String msg) {
		return fail(null, CommUtil.HttpStatus.HTTP_500, msg);
	}

	/**
	 * 操作失败
	 */
	public static <T> Result<T> fail(T t, String msg) {
		return fail(t, CommUtil.HttpStatus.HTTP_500, msg);
	}

	/**
	 * 操作失败
	 */
	public static <T> Result<T> fail(T t, Integer status) {
		return fail(t, status, "fail");
	}

	/**
	 * 操作失败
	 */
	public static <T> Result<T> fail(Integer status, String msg) {
		return fail(null, status, msg);
	}



	
}