package com.rencc.service.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 基本返回数据<P>
 * 额外的返回数据放在items字段
 * 
 * @author hanmc
 * 2016年8月3日
 * @param <T>
 */
public class BaseResult<T> {

	private String errCode = "0000";
	private String errDesc;
	private Object items;
	private List<T> resultList = Collections.emptyList();
	private HashMap<String, Object> values = new HashMap<String, Object>();
	
	public static BaseResult getInstance(){
		return new BaseResult("0000","");
	}
	
	public static BaseResult errorInstance(){
		return new BaseResult("9999","");
	}
	
	public BaseResult() {
		super();
	}

	public BaseResult(String errCode, String errDesc) {
		super();
		this.errCode = errCode;
		this.errDesc = errDesc;
	}
	
	public HashMap<String, Object> getValues() {
		return values;
	}

	public void setValues(HashMap<String, Object> values) {
		this.values = values;
	}

	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrDesc() {
		return errDesc;
	}
	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}

	public Object getItems() {
		return items;
	}

	public void setItems(Object items) {
		this.items = items;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	@Override
	public String toString() {
		return "BaseResult [errCode=" + errCode + ", errDesc=" + errDesc
				+ ", items=" + items + "]";
	}
	
}
