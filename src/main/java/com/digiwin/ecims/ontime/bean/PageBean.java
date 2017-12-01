package com.digiwin.ecims.ontime.bean;

import java.util.List;

@SuppressWarnings("unchecked")
public class PageBean<E> implements java.io.Serializable{
	/** 总记录数 */
	private int total;
	/** 分页结果 */
	private List<E> root;
	/** 开始页码 */
	private int start = 0;
	/** 每页多少 */
	private int limit = 15;
	/** 查询条件 */
	private String wheres;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<E> getRoot() {
		return root;
	}

	public void setRoot(List<E> root) {
		this.root = root;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getWheres() {
		return wheres;
	}

	public void setWheres(String wheres) {
		this.wheres = wheres;
	}

}