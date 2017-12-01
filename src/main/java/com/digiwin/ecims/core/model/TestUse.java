package com.digiwin.ecims.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="test_use")
public class TestUse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private String id;

	@Column(name="name")
	private String name;

	@Column(name="share")
	private String share;
	
	@Column(name="share2")
	private String share2;
	
	public TestUse() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getShare() {
		return this.share;
	}

	public void setShare(String share) {
		this.share = share;
	}
	
	public String getshare2() {
		return this.share2;
	}

	public void setShare2(String share2) {
		this.share2 = share2;
	}

}