package com.zb.service.room.vo;

import com.zb.models.room.activity.WerewolfKillActor;

public class IdentitySetupVO {

	private int villageCount;
	private int wolvesCount;
	private int prophetCount;
	private int witchCount;
	private int hunterCount;
	private int sbCount;
	private int defenderCount;

	public int getVillageCount() {
		return villageCount;
	}

	public int getWolvesCount() {
		return wolvesCount;
	}

	public int getProphetCount() {
		return prophetCount;
	}

	public int getWitchCount() {
		return witchCount;
	}

	public int getHunterCount() {
		return hunterCount;
	}

	public int getSbCount() {
		return sbCount;
	}

	public int getDefenderCount() {
		return defenderCount;
	}

	public void setDefenderCount(int defenderCount) {
		this.defenderCount = defenderCount;
	}

	public void setVillageCount(int villageCount) {
		this.villageCount = villageCount;
	}

	public void setWolvesCount(int wolvesCount) {
		this.wolvesCount = wolvesCount;
	}

	public void setProphetCount(int prophetCount) {
		this.prophetCount = prophetCount;
	}

	public void setWitchCount(int witchCount) {
		this.witchCount = witchCount;
	}

	public void setHunterCount(int hunterCount) {
		this.hunterCount = hunterCount;
	}

	public void setSbCount(int sbCount) {
		this.sbCount = sbCount;
	}

	public int getIdenCount(int iden) {
		switch (iden) {
		case WerewolfKillActor.WOLF:
			return this.getWolvesCount();
		case WerewolfKillActor.VILLAGE:
			return this.getVillageCount();
		case WerewolfKillActor.PROPHET:
			return this.getProphetCount();
		case WerewolfKillActor.WITCH:
			return this.getWitchCount();
		case WerewolfKillActor.HUNTER:
			return this.getHunterCount();
		case WerewolfKillActor.SB:
			return this.getSbCount();
		case WerewolfKillActor.DEFENDER:
			return this.getDefenderCount();
		}
		return 0;
	}

}
