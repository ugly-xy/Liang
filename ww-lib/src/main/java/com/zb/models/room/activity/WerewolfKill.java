package com.zb.models.room.activity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zb.models.room.Activity;
import com.zb.service.room.vo.IdentitySetupVO;

public class WerewolfKill extends Activity {

	private static final long serialVersionUID = -2849472570646215073L;

	private long[][] statusTable;// = new long[2][12]; // 发言顺序
	private long[] seatUserTable;// = new long[12]; // 座次顺序
	private Map<Long, Integer> actors = new LinkedHashMap<>();
	private Map<Integer, Long> defendMap = new HashMap<>(); // <day,uid>
	private Map<Long, Long> killMap = new HashMap<>(); // <killer,bekiller>
	private Map<Integer, Long> checkMap = new HashMap<>(); // <day,uid>
	private Map<Long, Integer> saveMap = new HashMap<>(); // <uid,day>
	private Map<Long, Integer> poisonMap = new HashMap<>(); // <uid,day>
	private List<Long> wolves;// 狼
	private List<Long> villages;// 村民
	private Long prophet; // 预言家
	private Long witch;// 女巫
	private Long hunter; // 猎人
	private Long defender; // 守卫
	private Long sb; // 白痴
	private Long lastWordUser;
	private int day; // 第几天
	private boolean shotted; // 开枪过
	private int hunterDeadType;
	private Long speaker;
	private Long speaked;
	private boolean sbBeVoted; // 白痴被投过
	private Long dead; // 被杀人
	private int type; // 游戏类型
	private Map<Integer, Integer> identities;
	private int winType; // 游戏结果
	private IdentitySetupVO identitySetup; // 临时变量
	private Set<Integer> robSet;// 临时变量

	public Long getSpeaked() {
		return speaked;
	}

	public void setSpeaked(Long speaked) {
		this.speaked = speaked;
	}

	public int getWinType() {
		return winType;
	}

	public void setWinType(int winType) {
		this.winType = winType;
	}

	public Map<Integer, Integer> getIdentities() {
		return identities;
	}

	public void setIdentities(Map<Integer, Integer> identities) {
		this.identities = identities;
	}

	public Set<Integer> getRobSet() {
		return robSet;
	}

	public void setRobSet(Set<Integer> robSet) {
		this.robSet = robSet;
	}

	public IdentitySetupVO getIdentitySetup() {
		return identitySetup;
	}

	public void setIdentitySetup(IdentitySetupVO identitySetup) {
		this.identitySetup = identitySetup;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Long getDead() {
		return dead;
	}

	public void setDead(Long dead) {
		this.dead = dead;
	}

	public boolean isSbBeVoted() {
		return sbBeVoted;
	}

	public void setSbBeVoted(boolean sbBeVoted) {
		this.sbBeVoted = sbBeVoted;
	}

	// private Set<Integer> idens = new HashSet<>();

	public Map<Long, Integer> getSaveMap() {
		return saveMap;
	}

	public Map<Long, Integer> getPoisonMap() {
		return poisonMap;
	}

	public void setSaveMap(Map<Long, Integer> saveMap) {
		this.saveMap = saveMap;
	}

	public void setPoisonMap(Map<Long, Integer> poisonMap) {
		this.poisonMap = poisonMap;
	}

	public Map<Integer, Long> getDefendMap() {
		return defendMap;
	}

	public void setDefendMap(Map<Integer, Long> defendMap) {
		this.defendMap = defendMap;
	}

	public Long getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Long speaker) {
		this.speaker = speaker;
	}

	public Long getLastWordUser() {
		return lastWordUser;
	}

	public int getHunterDeadType() {
		return hunterDeadType;
	}

	public void setHunterDeadType(int hunterDeadType) {
		this.hunterDeadType = hunterDeadType;
	}

	public void setLastWordUser(Long lastWordUser) {
		this.lastWordUser = lastWordUser;
	}

	public boolean isShotted() {
		return shotted;
	}

	public void setShotted(boolean shotted) {
		this.shotted = shotted;
	}

	public Map<Integer, Long> getCheckMap() {
		return checkMap;
	}

	public void setCheckMap(Map<Integer, Long> checkMap) {
		this.checkMap = checkMap;
	}

	public Map<Long, Long> getKillMap() {
		return killMap;
	}

	public void setKillMap(Map<Long, Long> killMap) {
		this.killMap = killMap;
	}

	public long[] getSeatUserTable() {
		return seatUserTable;
	}

	public long[][] getStatusTable() {
		return statusTable;
	}

	public void setStatusTable(long[][] statusTable) {
		this.statusTable = statusTable;
	}

	public void setSeatUserTable(long[] seatUserTable) {
		this.seatUserTable = seatUserTable;
	}

	public int getDay() {
		return day;
	}

	public List<Long> getWolves() {
		return wolves;
	}

	public List<Long> getVillages() {
		return villages;
	}

	public Long getProphet() {
		return prophet;
	}

	public Long getWitch() {
		return witch;
	}

	public Long getHunter() {
		return hunter;
	}

	public Long getDefender() {
		return defender;
	}

	public Long getSb() {
		return sb;
	}

	public void setWolves(List<Long> wolves) {
		this.wolves = wolves;
	}

	public void setVillages(List<Long> villages) {
		this.villages = villages;
	}

	public void setProphet(Long prophet) {
		this.prophet = prophet;
	}

	public void setWitch(Long witch) {
		this.witch = witch;
	}

	public void setHunter(Long hunter) {
		this.hunter = hunter;
	}

	public void setDefender(Long defender) {
		this.defender = defender;
	}

	public void setSb(Long sb) {
		this.sb = sb;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public Map<Long, Integer> getActors() {
		return actors;
	}

	public void setActors(Map<Long, Integer> actors) {
		this.actors = actors;
	}

	public void delActor(Long aid) {
		actors.remove(aid);
	}

	public void putActor(Long aid, Integer status) {
		actors.put(aid, status);
	}

	public void removeActor(Long aid) {
		actors.remove(aid);
	}
}