package com.zb.service.room.vo;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.DBObject;

/**
 * @author brannZ
 *
 */
public class GameFriendsList extends GameUserList {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7396009874151166895L;
	private List<DBObject> flist = new ArrayList<>();
	private int maleIndex;
	private int femaleIndex;
	private int maleBook;
	private int femaleBook;

	public int getMaleBook() {
		return maleBook;
	}

	public void setMaleBook(int maleBook) {
		this.maleBook = maleBook;
	}

	public int getFemaleBook() {
		return femaleBook;
	}

	public void setFemaleBook(int femaleBook) {
		this.femaleBook = femaleBook;
	}

	public int getMaleIndex() {
		return maleIndex;
	}

	public void setMaleIndex(int maleIndex) {
		this.maleIndex = maleIndex;
	}

	public int getFemaleIndex() {
		return femaleIndex;
	}

	public void setFemaleIndex(int femaleIndex) {
		this.femaleIndex = femaleIndex;
	}

	public List<DBObject> getFlist() {
		return flist;
	}

	public void setFlist(List<DBObject> flist) {
		this.flist = flist;
	}

}
