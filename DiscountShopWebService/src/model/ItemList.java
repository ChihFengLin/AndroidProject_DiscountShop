package model;

import java.util.ArrayList;

public class ItemList {

	private ArrayList<Item> itemList;

	public ItemList() {
	itemList=new ArrayList<Item>();
	}

	public void insertItem(Item item){
		itemList.add(item);
	}
	
	public int getItemNum() {
		return itemList.size();
	}

	public Item getItem(int index) {
		return itemList.get(index);
	}
}
