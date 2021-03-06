package com.sample.editor;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sample.entity.Item;

@ManagedBean(name = "itemVM")
@ViewScoped
public class ItemVM implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6938747368852779906L;
	private Item item;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}
