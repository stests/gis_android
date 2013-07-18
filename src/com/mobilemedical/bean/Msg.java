package com.mobilemedical.bean;

public class Msg {
	/*
	 * 返回结果，true或者false
	 */
	private boolean type = false;
	/*
	 * 异步信息描述
	 */
	private String message;
	
	/*
	 * title,description用于同步页面的信息展现
	 */
	private String title;
	private String description;
	
	public Msg(){
		
	}
	
	public Msg(boolean type, String message) {
		super();
		this.type = type;
		this.message = message;
		
	}

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
