package com.zb.models.room.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;


public class DrawLogData implements Serializable {

	private static final long serialVersionUID = -2181895559200646532L;
	private List<Map> draws = new ArrayList<Map>();
	private String _id;
	public DrawLogData() {

	}

	public DrawLogData(String id, List<Map> draws) {
		this._id = id;
		this.draws = draws;
	}


	public List<Map> getDraws() {
		return draws;
	}

	public void setDraws(List<Map> draws) {
		this.draws = draws;
	}

	public void addDraw(Map m) {
		this.draws.add(m);
	}


	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}
}
