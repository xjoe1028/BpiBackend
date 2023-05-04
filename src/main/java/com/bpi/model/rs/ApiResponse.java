package com.bpi.model.rs;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString(of = { "code", "message" })
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "innerBuilder")
public class ApiResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public static <T> ApiResponseBuilder<T> builder() {
		return (ApiResponseBuilder<T>) innerBuilder();
	}

	@SuppressWarnings("unchecked")
	public static <T> ApiResponseBuilder<T> builder(T data) {
		return (ApiResponseBuilder<T>) innerBuilder().data(data);
	}

	/**
	 * Code and message builder method.
	 * 
	 * @param <T>
	 * @param code
	 * @param message
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> ApiResponseBuilder<T> builder(String code, String message) {
		return (ApiResponseBuilder<T>) innerBuilder().code(code).message(message);
	}

	@Getter
	@Setter
	private transient T data;

	@Builder.Default
	@Getter
	private String code = "0000";

	@Builder.Default
	@Getter
	private String message = "Success!!";
	
	@JsonIgnore
	public boolean isSuccess() {
		return "0000".equals(code);
	}
}
