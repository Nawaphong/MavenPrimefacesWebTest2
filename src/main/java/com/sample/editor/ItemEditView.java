package com.sample.editor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.sample.entity.Item;
import com.sample.entity.User;
import com.sample.jpa.ItemLocal;
import com.sample.jpa.UserLocal;

@SuppressWarnings("restriction")
@ManagedBean(name = "itemEditView")
@ViewScoped
public class ItemEditView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7907960160940793265L;

//	private Item userSeletedBean = new Item();
	private List<Item> itemList = new ArrayList<Item>();
	private Map<String,Integer> users = new LinkedHashMap<String,Integer>();
	private Map<Integer,String> userNames = new HashMap<Integer, String>();
	
	@ManagedProperty(value = "#{itemVM}")
	private ItemVM itemVM;

	@EJB
	private ItemLocal itemService;
	
	@EJB
	private UserLocal userService;

	@PostConstruct
	public void init() {
		reloadTable();
		itemVM.setItem(new Item());
		List<User> userList = userService.getUsers();
		for (User user : userList) {
			users.put(user.getName(),user.getId());
		}
	}
	
	public void reloadTable() {
		itemList = itemService.getItems();
	};
	
    public List<Item> getItemList() {
        return itemList;
    }
 
    public String addAction() {
    	itemService.createItem(itemVM.getItem());
    	itemVM.setItem(new Item());
    	reloadTable();
        return null;
    }
    
    public void onEdit(RowEditEvent event) {  
    	itemService.updateItem((Item) event.getObject());
        FacesMessage msg = new FacesMessage("Item Edited",((Item) event.getObject()).getName());  
        FacesContext.getCurrentInstance().addMessage(null, msg);
        reloadTable();
    }  
       
    public void onCancel(RowEditEvent event) {  
    	itemService.removeItem((Item) event.getObject());
        FacesMessage msg = new FacesMessage("Item Cancelled");   
        FacesContext.getCurrentInstance().addMessage(null, msg); 
        reloadTable();
    }

    public String getUserName(Integer userId){
    	String userName = userNames.get(userId);
    	if(userName == null){
    		List<User> userList = userService.getUsers();
    		for (User user : userList) {
				if(userId.equals(user.getId())){
					userName = user.getName();
					userNames.put(userId, userName);
					break;
				}
			}
    	}
    	return userName;
    }
    
	public Map<String,Integer> getUsers() {
		return users;
	}

	public ItemVM getItemVM() {
		return itemVM;
	}

	public void setItemVM(ItemVM itemVM) {
		this.itemVM = itemVM;
	}

	public ItemLocal getService() {
		return itemService;
	}

	public void setService(ItemLocal service) {
		this.itemService = service;
	}

	public UserLocal getUserService() {
		return userService;
	}

	public void setUserService(UserLocal userService) {
		this.userService = userService;
	}  
    
}
