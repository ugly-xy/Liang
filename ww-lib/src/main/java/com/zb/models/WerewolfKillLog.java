package com.zb.models;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.zb.models.room.activity.WerewolfKillActor;

/**
 * @author brannZ
 *
 */
@Document
public class WerewolfKillLog implements Serializable {

	private static final long serialVersionUID = -133475452152369428L;

	public WerewolfKillLog() {
	}

	public WerewolfKillLog(long id, int actorCount, boolean win, int identity) {
		long now = System.currentTimeMillis();
		this._id = id + "-" + actorCount;
		this.actorCount = actorCount;
		this.updateTime = now;
		this.createTime = now;
		this.offlineCount = 0;
		if (win) {
			this.winCnt = 1;
		}
		this.allCnt = 1;
		this.uid = id;
		switch (identity) {
		case WerewolfKillActor.WOLF:
			this.wolfWin = win ? 1 : 0;
			this.wolf = 1;
			break;
		case WerewolfKillActor.VILLAGE:
			this.villageWin = win ? 1 : 0;
			this.village = 1;
			break;
		case WerewolfKillActor.PROPHET:
			this.prophetWin = win ? 1 : 0;
			this.prophet = 1;
			break;
		case WerewolfKillActor.WITCH:
			this.witch = win ? 1 : 0;
			this.witch = 1;
			break;
		case WerewolfKillActor.HUNTER:
			this.hunterWin = win ? 1 : 0;
			this.hunter = 1;
			break;
		case WerewolfKillActor.SB:
			this.sbWin = win ? 1 : 0;
			this.sb = 1;
			break;
		case WerewolfKillActor.DEFENDER:
			this.defenderWin = win ? 1 : 0;
			this.defender = 1;
			break;
		}
	}

	private long uid;
	private String _id;
	private long updateTime;
	private long createTime;
	private Integer actorCount;
	private Integer wolf;
	private Integer wolfWin;
	private Integer village;
	private Integer villageWin;
	private Integer prophet;
	private Integer prophetWin;
	private Integer sb;
	private Integer sbWin;
	private Integer witch;
	private Integer witchWin;
	private Integer hunter;
	private Integer hunterWin;
	private Integer defender;
	private Integer defenderWin;
	private Integer offlineCount;
	private Integer winCnt;
	private Integer allCnt;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public Integer getOfflineCount() {
		return offlineCount;
	}

	public void setOfflineCount(Integer offlineCount) {
		this.offlineCount = offlineCount;
	}

	public Integer getDefender() {
		return defender;
	}

	public Integer getDefenderWin() {
		return defenderWin;
	}

	public void setDefender(Integer defender) {
		this.defender = defender;
	}

	public void setDefenderWin(Integer defenderWin) {
		this.defenderWin = defenderWin;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public String get_id() {
		return _id;
	}

	public long getCreateTime() {
		return createTime;
	}

	public Integer getWolf() {
		return wolf;
	}

	public Integer getWolfWin() {
		return wolfWin;
	}

	public Integer getVillage() {
		return village;
	}

	public Integer getVillageWin() {
		return villageWin;
	}

	public Integer getProphet() {
		return prophet;
	}

	public Integer getProphetWin() {
		return prophetWin;
	}

	public Integer getSb() {
		return sb;
	}

	public Integer getSbWin() {
		return sbWin;
	}

	public Integer getWitch() {
		return witch;
	}

	public Integer getWitchWin() {
		return witchWin;
	}

	public Integer getHunter() {
		return hunter;
	}

	public Integer getHunterWin() {
		return hunterWin;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public Integer getActorCount() {
		return actorCount;
	}

	public void setActorCount(Integer actorCount) {
		this.actorCount = actorCount;
	}

	public void setWolf(Integer wolf) {
		this.wolf = wolf;
	}

	public void setWolfWin(Integer wolfWin) {
		this.wolfWin = wolfWin;
	}

	public void setVillage(Integer village) {
		this.village = village;
	}

	public void setVillageWin(Integer villageWin) {
		this.villageWin = villageWin;
	}

	public void setProphet(Integer prophet) {
		this.prophet = prophet;
	}

	public void setProphetWin(Integer prophetWin) {
		this.prophetWin = prophetWin;
	}

	public void setSb(Integer sb) {
		this.sb = sb;
	}

	public void setSbWin(Integer sbWin) {
		this.sbWin = sbWin;
	}

	public void setWitch(Integer witch) {
		this.witch = witch;
	}

	public void setWitchWin(Integer witchWin) {
		this.witchWin = witchWin;
	}

	public void setHunter(Integer hunter) {
		this.hunter = hunter;
	}

	public void setHunterWin(Integer hunterWin) {
		this.hunterWin = hunterWin;
	}

	public Integer getWinCnt() {
		return winCnt;
	}

	public void setWinCnt(Integer winCnt) {
		this.winCnt = winCnt;
	}

	public Integer getAllCnt() {
		return allCnt;
	}

	public void setAllCnt(Integer allCnt) {
		this.allCnt = allCnt;
	}

	public int getAllCount() {
		return (null == this.wolf ? 0 : this.wolf) + (null == this.village ? 0 : this.village)
				+ (null == this.prophet ? 0 : this.prophet) + (null == this.witch ? 0 : this.witch)
				+ (null == this.hunter ? 0 : this.hunter) + (null == this.sb ? 0 : this.sb)
				+ (null == this.defender ? 0 : this.defender);
	}

	public int getAllWinCount() {
		return (null == this.wolfWin ? 0 : this.wolfWin) + (null == this.villageWin ? 0 : this.villageWin)
				+ (null == this.prophetWin ? 0 : this.prophetWin) + (null == this.witchWin ? 0 : this.witchWin)
				+ (null == this.hunterWin ? 0 : this.hunterWin) + (null == this.sbWin ? 0 : this.sbWin)
				+ (null == this.defenderWin ? 0 : this.defenderWin);
	}

	public int getAllRate() {
		int count = getAllCount();
		int win = getAllWinCount();
		int rate = count == 0 ? 0 : (win * 100 / count);
		return rate;
	}

}
