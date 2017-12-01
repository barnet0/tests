package com.digiwin.ecims.platforms.kaola.bean.domain.itembak;

/**
 * 
 * @author cjp 2017/5/26
 *
 */
public class ItemCategory {

	private long id;
	private long item_id;
	private long business_id;
	private long category_id;
	private long rank;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getItem_id() {
		return item_id;
	}

	public void setItem_id(long item_id) {
		this.item_id = item_id;
	}

	public long getBusiness_id() {
		return business_id;
	}

	public void setBusiness_id(long business_id) {
		this.business_id = business_id;
	}

	public long getCategory_id() {
		return category_id;
	}

	public void setCategory_id(long category_id) {
		this.category_id = category_id;
	}

	public long getRank() {
		return rank;
	}

	public void setRank(long rank) {
		this.rank = rank;
	}

}
