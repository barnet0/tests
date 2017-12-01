package com.digiwin.ecims.core.model;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

import com.taobao.api.domain.Area;


/**
 * The persistent class for the standard_area database table.
 * 
 */
@Entity
@Table(name="standard_area")
@NamedQuery(name="StandardArea.findAll", query="SELECT s FROM StandardArea s")
/*
 * 新增@DynamicUpdate(true)注解标记，如果没有发生变化的字段，在更新时，就不会进行update， 如果不加，就更新所有字段
 */
@DynamicUpdate(true)
public class StandardArea implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8970547237134362605L;

	@Id
	@Column(name="id", length=50)
	private Long id;

	@Column(name="name", length=50)
	private String name;

	@Column(name="parent_id", length=50)
	private Long parentId;

	@Column(name="type", length=50)
	private Long type;

	@Column(name="zip", length=50)
	private String zip;

	public StandardArea() {
	}

	public StandardArea(Area area) {
		this.id = area.getId();
		this.name = area.getName();
		this.parentId = area.getParentId();
		this.type = area.getType();
		this.zip = area.getZip();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getType() {
		return this.type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}