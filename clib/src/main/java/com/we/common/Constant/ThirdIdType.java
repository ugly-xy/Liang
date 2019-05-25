package com.we.common.Constant;

public enum ThirdIdType {
	TELEGRAM(1), KAKAO(2), TWITTER(3);
	private int thirdIDType = 0;

	private ThirdIdType(int thirdIDType) {
		this.thirdIDType = thirdIDType;
	}

	public int getType() {
		return thirdIDType;
	}

}
