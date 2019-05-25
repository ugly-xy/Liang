package com.zb.service.room.vo;

import java.util.Map;
import java.util.Set;

import com.zb.models.room.activity.WerewolfKillActorState;

public class WerewolfRoomInfo extends RoomInfo {

	private static final long serialVersionUID = -296834328729224627L;

	public WerewolfRoomInfo(int type) {
		super(type);
	}

	private int day;
	private int wkStatus;
	private Set<WerewolfKillActorState> actorsStates;
	private Long speaker;
	private Map<Integer, Integer> identities;
	private Map<Long, Long> killMap;
	private Map<Long, Boolean> checkMap;
	private Set<Long> poisonSet;
	private Set<Long> saveSet;
	private Long defendId;
	private Long lastDefendId;
	private boolean sbBeVoted;
	private Long dead;
	private Long todayCheck;

	public Long getTodayCheck() {
		return todayCheck;
	}

	public void setTodayCheck(Long todayCheck) {
		this.todayCheck = todayCheck;
	}

	public Long getDead() {
		return dead;
	}

	public void setDead(Long dead) {
		this.dead = dead;
	}

	public Set<Long> getSaveSet() {
		return saveSet;
	}

	public void setSaveSet(Set<Long> saveSet) {
		this.saveSet = saveSet;
	}

	public boolean isSbBeVoted() {
		return sbBeVoted;
	}

	public void setSbBeVoted(boolean sbBeVoted) {
		this.sbBeVoted = sbBeVoted;
	}

	public Long getLastDefendId() {
		return lastDefendId;
	}

	public void setLastDefendId(Long lastDefendId) {
		this.lastDefendId = lastDefendId;
	}

	public Long getDefendId() {
		return defendId;
	}

	public void setDefendId(Long defendId) {
		this.defendId = defendId;
	}

	public Set<Long> getPoisonSet() {
		return poisonSet;
	}

	public void setPoisonSet(Set<Long> poisonSet) {
		this.poisonSet = poisonSet;
	}

	public Map<Long, Boolean> getCheckMap() {
		return checkMap;
	}

	public void setCheckMap(Map<Long, Boolean> checkMap) {
		this.checkMap = checkMap;
	}

	public Map<Long, Long> getKillMap() {
		return killMap;
	}

	public void setKillMap(Map<Long, Long> killMap) {
		this.killMap = killMap;
	}

	public Map<Integer, Integer> getIdentities() {
		return identities;
	}

	public void setIdentities(Map<Integer, Integer> identities) {
		this.identities = identities;
	}

	public Long getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Long speaker) {
		this.speaker = speaker;
	}

	public Set<WerewolfKillActorState> getActorsStates() {
		return actorsStates;
	}

	public void setActorsStates(Set<WerewolfKillActorState> actorsStates) {
		this.actorsStates = actorsStates;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getWkStatus() {
		return wkStatus;
	}

	public void setWkStatus(int wkStatus) {
		this.wkStatus = wkStatus;
	}

}
