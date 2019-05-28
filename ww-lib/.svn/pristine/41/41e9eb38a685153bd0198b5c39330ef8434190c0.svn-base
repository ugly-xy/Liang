package com.zb.models;

import java.util.ArrayList;
import java.util.List;

import com.zb.common.Constant.Const;
import com.zb.service.RankListService;
import com.zb.service.jobs.RankListJob;

public class TitleModel {

	public TitleModel() {
	}

	TitleModel(int type, String name, int premise, int val, int ttype, int sex, String detail, String url) {
		this.name = name;
		this.type = type;
		this.premise = premise;
		this.val = val;
		this.ttype = ttype;
		this.sex = sex;
		this.validity = System.currentTimeMillis() + val * Const.DAY;
		this.detail = detail;
		this.url = url;
	}

	TitleModel(int type, String name, int premise, int val, int ttype, String detail, String url) {
		this.name = name;
		this.type = type;
		this.premise = premise;
		this.val = val;
		this.ttype = ttype;
		this.validity = System.currentTimeMillis() + val * Const.DAY;
		this.detail = detail;
		this.url = url;
	}

	private int type;// 权重
	private String name;// 称号
	private long validity;// 有效期
	private int premise;//
	private int val;// 时间长度 天
	private int ttype;// 类型
	private int sex;// 称号性别
	private String detail;// 称号描述
	private String url;// 称号图片

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public long getValidity() {
		return validity;
	}

	public void setValidity(long validity) {
		this.validity = validity;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPremise() {
		return premise;
	}

	public int getVal() {
		return val;
	}

	public int getTtype() {
		return ttype;
	}

	public void setPremise(int premise) {
		this.premise = premise;
	}

	public void setVal(int val) {
		this.val = val;
	}

	public void setTtype(int ttype) {
		this.ttype = ttype;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public interface Title {
		TitleModel getModel();
	}

	/** 充值称号 */
	enum CoinEnum implements Title {
		_1(240, UserTitle.COIN, "挥金如土", 168, 1, "购买168元游戏礼包",
				"http://zim.zhuangdianbi.com/635899f282784cf9ae545c2fa245563c.png"),
		// _2(600, UserTitle.COIN, "腰缠万贯", 298, 2),
		_2(220, UserTitle.COIN, "一掷千金", 388, 2, "购买388元游戏礼包",
				"http://zim.zhuangdianbi.com/68bbfacb479bcf10f01b0ef50bcb5b4e.png"),

		_Coin_3(220, UserTitle.COIN, "富可敌国", 388, 2, "活动期间购买388元游戏礼包",
				"http://zim.zhuangdianbi.com/3f076fc4bdccfd2bbd0f3bd3fb9f00a5.png"),;

		CoinEnum(int type, int ttype, String name, int premise, int val, String detail, String url) {
			this.name = name;
			model = new TitleModel(type, name, premise, val, ttype, detail, url);
		}

		private TitleModel model;
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public TitleModel getModel() {
			return model;
		}
	}

	enum FlowerEnum implements Title {

		// _newyear_1(194, UserTitle.FLOWER, "小雪花", 30, 3, 0,
		// "12月25日花魁榜限定称号(第4-30名)",
		// "http://zim.zhuangdianbi.com/4b6c139a7dda7a84b116c733bde715e8.png"),
		//
		// _newyear_2(193, UserTitle.FLOWER, "雪精灵", 3, 7, 0,
		// "12月25日花魁榜限定称号(第2、3名)",
		// "http://zim.zhuangdianbi.com/bf31ae7cb604575de01900ace08a70c3.png"),
		//
		// _newyear_3(192, UserTitle.FLOWER, "白雪公主", 1, 20, 2,
		// "12月25日花魁榜限定称号(花魁或守护)",
		// "http://zim.zhuangdianbi.com/a85848b5ae65fe1f8d9631d68d6a4d2a.png"),
		//
		// _newyear_4(191, UserTitle.FLOWER, "白马王子", 1, 20, 1,
		// "12月25日花魁榜限定称号(花魁或守护)",
		// "http://zim.zhuangdianbi.com/da9399420d4bd42b4d8157427e360209.png"),

		// _Flower_1(184, UserTitle.FLOWER, "小雪橇", 30, 3, 0,
		// "12月24日花魁榜限定称号(第4-30名)",
		// "http://zim.zhuangdianbi.com/752b8fd38a3716c6f02f3015606d7975.png"),
		//
		// _Flower_2(183, UserTitle.FLOWER, "小麋鹿", 3, 7, 0,
		// "12月24日花魁榜限定称号(第2、3名)",
		// "http://zim.zhuangdianbi.com/c0edb79552570bb8b74e4cb207613b40.png"),
		//
		// _Flower_3(182, UserTitle.FLOWER, "冰雪公主", 1, 20, 2,
		// "12月24日花魁榜限定称号(花魁或守护)",
		// "http://zim.zhuangdianbi.com/36fc9aaf45bc5b1dd79db3e1a3c62144.png"),
		//
		// _Flower_4(181, UserTitle.FLOWER, "冰雪王子", 1, 20, 1,
		// "12月24日花魁榜限定称号(花魁或守护)",
		// "http://zim.zhuangdianbi.com/1966d1406726f3ca3b28adc62a0510b3.png"),

		_Flower_1(184, UserTitle.FLOWER, "玫瑰蓓蕾", 30, 3, 0, "2月14日花魁榜限定称号(第4-30名)",
				"http://zim.zhuangdianbi.com/6cd1d71b6eb4897402bac791636680a9.png"),

		_Flower_2(183, UserTitle.FLOWER, "玫瑰仙子", 3, 7, 0, "2月14日花魁榜限定称号(第2、3名)",
				"http://zim.zhuangdianbi.com/2c038ff2cf7ae9d4aec6819a4dbbdfef.png"),

		_Flower_3(182, UserTitle.FLOWER, "绝代佳人", 1, 20, 2, "2月14日花魁榜限定称号(花魁或守护)",
				"http://zim.zhuangdianbi.com/685c2568b25bfcd85f45ff3ef957d0bb.png"),

		_Flower_4(181, UserTitle.FLOWER, "玫瑰情圣", 1, 20, 1, "2月14日花魁榜限定称号(花魁或守护)",
				"http://zim.zhuangdianbi.com/cb0de71dbdb5d36ee6f10c419b032cfd.png"),

		_1(180, UserTitle.FLOWER, "花骨朵", 30, 1, 0, "当日花魁榜第4-30名",
				"http://zim.zhuangdianbi.com/2c3d76a7fdfd9128fd0756f986a1edc1.png"),

		_2(160, UserTitle.FLOWER, "花精灵", 3, 3, 0, "当日花魁榜第2、3名",
				"http://zim.zhuangdianbi.com/6e371195bad6961cd13045523c5a4817.png"),

		_3(140, UserTitle.FLOWER, "花仙子", 1, 7, 2, "当日花魁榜第1名(女生)",
				"http://zim.zhuangdianbi.com/7401585a1a8146e5ef7056224f78a78c.png"),

		_4(140, UserTitle.FLOWER, "花公子", 1, 7, 1, "当日花魁榜第1名(男生)",
				"http://zim.zhuangdianbi.com/20e27f7cded4ef5feef7c73c517f7bc7.png"),

		_5(120, UserTitle.FLOWER, "闭月羞花", 0, 30, 2, "一个自然月内累计3次登上花魁榜首的女生",
				"http://zim.zhuangdianbi.com/04cdfeaba97a1ca06a2a0d19038b1845.png"),

		_6(120, UserTitle.FLOWER, "风花雪月", 0, 30, 1, "一个自然月内累计3次登上花魁榜首的男生",
				"http://zim.zhuangdianbi.com/82d4284378f3d8995c00d64cb764668b.png"),;

		FlowerEnum(int type, int ttype, String name, int premise, int val, int sex, String detail, String url) {
			this.name = name;
			model = new TitleModel(type, name, premise, val, ttype, sex, detail, url);
		}

		private TitleModel model;
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public TitleModel getModel() {
			return model;
		}
	}

	/** 送花称号 */
	enum FlowerSendEnum implements Title {

		_1(340, UserTitle.FLOWER_SEND, "护花使者", 99999999, 3, 0, "情人节活动期间当日累计对某人送花52014朵",
				"http://zim.zhuangdianbi.com/9a32ad8f63d91968e735f7e57cf7776a.png"),;

		// _1(340, UserTitle.FLOWER_SEND, "驯鹿勋章", 10, 3, 0, "双旦活动期间当日送花第2-10名",
		// "http://zim.zhuangdianbi.com/bcc15c28367f3414ef327aacd238c223.png"),
		//
		// _2(320, UserTitle.FLOWER_SEND, "红帽勋章", 1, 3, 0, "双旦活动期间当日送花第1名",
		// "http://zim.zhuangdianbi.com/e0507c1c9de10e86f08f90b987d12b0a.png"),;

		FlowerSendEnum(int type, int ttype, String name, int premise, int val, int sex, String detail, String url) {
			this.name = name;
			model = new TitleModel(type, name, premise, val, ttype, sex, detail, url);
		}

		private TitleModel model;
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public TitleModel getModel() {
			return model;
		}
	}

	/** 收花称号 */
	enum FlowerRecvEnum implements Title {
		_1(440, UserTitle.FLOWER_RECV, "万人迷", 99999999, 3, 0, "情人节活动期间当日累计被某人送花52014朵",
				"http://zim.zhuangdianbi.com/c93d5074caf940b987c5b0e70612fe00.png"),;

		// _1(440, UserTitle.FLOWER_RECV, "圣诞宝宝", 10, 28, 0, "圣诞活动期间累计收花第2-10名",
		// "http://zim.zhuangdianbi.com/5749b4a2e088b83276f3a7de867c757a.png"),
		//
		// _2(420, UserTitle.FLOWER_RECV, "圣诞男神", 1, 28, 1, "圣诞活动期间累计收花第1名(男生)",
		// "http://zim.zhuangdianbi.com/40de14e5e8555a3c5445d1ad6df7881a.png"),
		//
		// _3(420, UserTitle.FLOWER_RECV, "圣诞女神", 1, 28, 2, "圣诞活动期间累计收花第1名(女生)",
		// "http://zim.zhuangdianbi.com/660c91e3ba35b95ce76086825ad0905f.png"),;

		FlowerRecvEnum(int type, int ttype, String name, int premise, int val, int sex, String detail, String url) {
			this.name = name;
			model = new TitleModel(type, name, premise, val, ttype, sex, detail, url);
		}

		private TitleModel model;
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public TitleModel getModel() {
			return model;
		}
	}

	/** 圈子文章收花称号 */
	enum FlowerGroupEnum implements Title {
		_1(520, UserTitle.FLOWER_GROUP, "白色恋人", 3, 7, 0, "白色情人节活动期间圈子当日发帖收花前3名",
				"http://zim.zhuangdianbi.com/97787bcda035c956d99711453fb0ef4e.png"),;

		// _1(520, UserTitle.FLOWER_GROUP, "圣诞精灵", 3, 3, 0, "圣诞圈子活动期间当日发帖收花前3名",
		// "http://zim.zhuangdianbi.com/6d56f05f4c42b9e2afee47a1ebed397f.png"),;

		FlowerGroupEnum(int type, int ttype, String name, int premise, int val, int sex, String detail, String url) {
			this.name = name;
			model = new TitleModel(type, name, premise, val, ttype, sex, detail, url);
		}

		private TitleModel model;
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public TitleModel getModel() {
			return model;
		}
	}

	/** 新年活动称号 */
	// enum NewyearRecvEnum implements Title {
	// _1(640, UserTitle.NEWYEAR, "冰封骑士", 10, 60, 0, "元旦活动期间累计收花第2-10名",
	// "http://zim.zhuangdianbi.com/f37657da6693b7597ee8471454333886.png"),
	//
	// _2(620, UserTitle.NEWYEAR, "雪域主宰", 1, 60, 0, "元旦活动期间累计收花第1名",
	// "http://zim.zhuangdianbi.com/290fa848a2eaa2895e48e156fc575b24.png"),;
	//
	// NewyearRecvEnum(int type, int ttype, String name, int premise, int val,
	// int sex, String detail, String url) {
	// this.name = name;
	// model = new TitleModel(type, name, premise, val, ttype, sex, detail,
	// url);
	// }
	//
	// private TitleModel model;
	// private String name;
	//
	// public String getName() {
	// return name;
	// }
	//
	// public void setName(String name) {
	// this.name = name;
	// }
	//
	// @Override
	// public TitleModel getModel() {
	// return model;
	// }
	// }

	/** 花魁乱斗赢金称号 */
	enum SakuranEnum implements Title {
		_1(300, UserTitle.SAKURAN_WIN, "赌 圣", 3, 2, 0, "花魁大乱斗当日赢金榜第3名",
				"http://zim.zhuangdianbi.com/95f059d751d624983307ba36a29e489a.png"),

		_2(200, UserTitle.SAKURAN_WIN, "赌 侠", 2, 2, 0, "花魁大乱斗当日赢金榜第2名",
				"http://zim.zhuangdianbi.com/b4597a2cb9137a0464f5344a5a78d943.png"),

		_3(100, UserTitle.SAKURAN_WIN, "赌 神", 1, 2, 0, "花魁大乱斗当日赢金榜第1名",
				"http://zim.zhuangdianbi.com/88c99c787d3508ae2d9cf25d74f9b58b.png"),;

		SakuranEnum(int type, int ttype, String name, int premise, int val, int sex, String detail, String url) {
			this.name = name;
			model = new TitleModel(type, name, premise, val, ttype, sex, detail, url);
		}

		private TitleModel model;
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public TitleModel getModel() {
			return model;
		}

	}

	/** 牛牛赢金称号 */
	enum CowEnum implements Title {
		_1(300, UserTitle.COW_WIN, "牛牛战士", 3, 1, 0, "牛牛当日赢金榜第3名", "http://zim.zhuangdianbi.com/341d98589ca222f69bd8cdc0f0753442.png"),

		_2(200, UserTitle.COW_WIN, "牛牛战将", 2, 1, 0, "牛牛当日赢金榜第2名", "http://zim.zhuangdianbi.com/732e809c4e6b6b6b51800a0ab96849b3.png"),

		_3(100, UserTitle.COW_WIN, "牛牛战神", 1, 2, 0, "牛牛当日赢金榜第1名", "http://zim.zhuangdianbi.com/02e1c7490ab60ba29874032f48a04f50.png"),;

		CowEnum(int type, int ttype, String name, int premise, int val, int sex, String detail, String url) {
			this.name = name;
			model = new TitleModel(type, name, premise, val, ttype, sex, detail, url);
		}

		private TitleModel model;
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public TitleModel getModel() {
			return model;
		}

	}

	/** 德州赢金称号 */
	enum TexasEnum implements Title {
		_1(300, UserTitle.TEXAS_WIN, "德扑健将", 3, 1, 0, "德州扑克当日赢金榜第3名", "http://zim.zhuangdianbi.com/9f970fa394a87431db4665172076f01a.png"),

		_2(200, UserTitle.TEXAS_WIN, "德扑高手", 2, 1, 0, "德州扑克当日赢金榜第2名", "http://zim.zhuangdianbi.com/c2b0cb4605cae7a83920ae688de742dd.png"),

		_3(100, UserTitle.TEXAS_WIN, "德扑大师", 1, 2, 0, "德州扑克当日赢金榜第1名", "http://zim.zhuangdianbi.com/60bef460b1dabd7bd0862cf5be43565d.png"),;

		TexasEnum(int type, int ttype, String name, int premise, int val, int sex, String detail, String url) {
			this.name = name;
			model = new TitleModel(type, name, premise, val, ttype, sex, detail, url);
		}

		private TitleModel model;
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public TitleModel getModel() {
			return model;
		}

	}

	/** 发放称号奖励时 获取称号的方法 所有的称号时间判断都放这里 */
	public static TitleModel getModel(int tType, int count, Integer sex) {
		int day = RankListService.getDay(RankListService.YESTERDAY);
		// 需要判断的是 活动期间冲388的特殊称号 214情人节花魁榜的特殊称号
		sex = null == sex || 0 == sex ? 2 : sex;
		TitleModel model = null;
		if (tType == UserTitle.FLOWER) {
			if (count == 0) {
				if (sex == 1) {
					model = FlowerEnum._6.getModel();
				} else {
					model = FlowerEnum._5.getModel();
				}
			} else if (count == 1) {
				if (sex == 1) {
					if (day == RankListJob.DAY_0214) {
						model = FlowerEnum._Flower_4.getModel();
					} else {
						model = FlowerEnum._4.getModel();
					}
				} else {
					if (day == RankListJob.DAY_0214) {
						model = FlowerEnum._Flower_3.getModel();
					} else {
						model = FlowerEnum._3.getModel();
					}
				}
			} else if (count > 1 && count < 4) {
				if (day == RankListJob.DAY_0214) {
					model = FlowerEnum._Flower_2.getModel();
				} else {
					model = FlowerEnum._2.getModel();
				}

			} else if (count < 31) {
				if (day == RankListJob.DAY_0214) {
					model = FlowerEnum._Flower_1.getModel();
				} else {
					model = FlowerEnum._1.getModel();
				}
			}
		} else if (tType == UserTitle.COIN) {
			if (count == 168) {
				model = CoinEnum._1.getModel();
			} else if (count == 388) {
				day = RankListService.getDay(RankListService.DAY);
				// 活动期间
				if (day >= 20180210 && day <= 20180218) {
					model = CoinEnum._Coin_3.getModel();
				} else {
					model = CoinEnum._2.getModel();
				}
			}
		} else if (tType == UserTitle.FLOWER_SEND) {
			model = FlowerSendEnum._1.getModel();
			// if (count == 1) {
			// model = FlowerSendEnum._2.getModel();
			// } else if (count < 11) {
			// model = FlowerSendEnum._1.getModel();
			// }
		} else if (tType == UserTitle.FLOWER_RECV) {
			model = FlowerRecvEnum._1.getModel();
			// if (count == 1) {
			// if (sex == 1) {
			// model = FlowerRecvEnum._2.getModel();
			// } else {
			// model = FlowerRecvEnum._3.getModel();
			// }
			// } else if (count < 11) {
			// model = FlowerRecvEnum._1.getModel();
			// }
		} else if (tType == UserTitle.FLOWER_GROUP) {
			if (count < 4) {
				model = FlowerGroupEnum._1.getModel();
			}
			// } else if (tType == UserTitle.NEWYEAR) {
			// if (count == 1) {
			// model = NewyearRecvEnum._2.getModel();
			// } else if (count < 11) {
			// model = NewyearRecvEnum._1.getModel();
			// }
		} else if (tType == UserTitle.SAKURAN_WIN) {
			if (count == 1) {
				model = SakuranEnum._3.getModel();
			} else if (count == 2) {
				model = SakuranEnum._2.getModel();
			} else if (count == 3) {
				model = SakuranEnum._1.getModel();
			}
		} else if (tType == UserTitle.COW_WIN) {
			if (count == 1) {
				model = CowEnum._3.getModel();
			} else if (count == 2) {
				model = CowEnum._2.getModel();
			} else if (count == 3) {
				model = CowEnum._1.getModel();
			}
		} else if (tType == UserTitle.TEXAS_WIN) {
			if (count == 1) {
				model = TexasEnum._3.getModel();
			} else if (count == 2) {
				model = TexasEnum._2.getModel();
			} else if (count == 3) {
				model = TexasEnum._1.getModel();
			}
		}
		if (model != null) {
			model.setValidity(System.currentTimeMillis() + model.getVal() * Const.DAY);
		}
		return model;
	}

	/** 后台添加称号 */
	public static TitleModel getModel(int tType, String name) {
		TitleModel model = null;
		if (tType == UserTitle.FLOWER) {
			if (name.equals("花骨朵")) {
				model = FlowerEnum._1.getModel();
			} else if (name.equals("花精灵")) {
				model = FlowerEnum._2.getModel();
			} else if (name.equals("花仙子")) {
				model = FlowerEnum._3.getModel();
			} else if (name.equals("花公子")) {
				model = FlowerEnum._4.getModel();
			} else if (name.equals("闭月羞花")) {
				model = FlowerEnum._5.getModel();
			} else if (name.equals("风花雪月")) {
				model = FlowerEnum._6.getModel();
			} else if (name.equals("绝代佳人")) {
				model = FlowerEnum._Flower_3.getModel();
			} else if (name.equals("玫瑰情圣")) {
				model = FlowerEnum._Flower_4.getModel();
			} else if (name.equals("玫瑰仙子")) {
				model = FlowerEnum._Flower_2.getModel();
			} else if (name.equals("玫瑰蓓蕾")) {
				model = FlowerEnum._Flower_1.getModel();
				// } else if (name.equals("白雪公主")) {
				// model = FlowerEnum._newyear_3.getModel();
				// } else if (name.equals("白马王子")) {
				// model = FlowerEnum._newyear_4.getModel();
				// } else if (name.equals("雪精灵")) {
				// model = FlowerEnum._newyear_2.getModel();
				// } else if (name.equals("小雪花")) {
				// model = FlowerEnum._newyear_1.getModel();
			}
		} else if (tType == UserTitle.COIN) {
			if (name.equals("挥金如土")) {
				model = CoinEnum._1.getModel();
			} else if (name.equals("一掷千金")) {
				model = CoinEnum._2.getModel();
			} else if (name.equals("富可敌国")) {
				model = CoinEnum._Coin_3.getModel();
			}
		} else if (tType == UserTitle.FLOWER_SEND) {// 圣诞送花
			if (name.equals("护花使者")) {
				model = FlowerSendEnum._1.getModel();
			}
			// if (name.equals("红帽勋章")) {
			// model = FlowerSendEnum._2.getModel();
			// } else if (name.equals("驯鹿勋章")) {
			// model = FlowerSendEnum._1.getModel();
			// }
		} else if (tType == UserTitle.FLOWER_RECV) {// 圣诞收花
			if (name.equals("万人迷")) {
				model = FlowerRecvEnum._1.getModel();
			}
			// if (name.equals("圣诞女神")) {
			// model = FlowerRecvEnum._3.getModel();
			// } else if (name.equals("圣诞男神")) {
			// model = FlowerRecvEnum._2.getModel();
			// } else if (name.equals("圣诞宝宝")) {
			// model = FlowerRecvEnum._1.getModel();
			// }
		} else if (tType == UserTitle.FLOWER_GROUP) {// 圣诞圈子
			if (name.equals("白色恋人")) {
				model = FlowerGroupEnum._1.getModel();
			}
			// } else if (tType == UserTitle.NEWYEAR) {// 新年活动
			// if (name.equals("雪域主宰")) {
			// model = NewyearRecvEnum._2.getModel();
			// } else if (name.equals("冰封骑士")) {
			// model = NewyearRecvEnum._1.getModel();
			// }
		} else if (tType == UserTitle.SAKURAN_WIN) {// 花魁乱斗
			if (name.equals("赌 神")) {
				model = SakuranEnum._3.getModel();
			} else if (name.equals("赌 侠")) {
				model = SakuranEnum._2.getModel();
			} else if (name.equals("赌 圣")) {
				model = SakuranEnum._1.getModel();
			}
		} else if (tType == UserTitle.COW_WIN) {// 牛牛
			if (name.equals("牛牛战神")) {
				model = CowEnum._3.getModel();
			} else if (name.equals("牛牛战将")) {
				model = CowEnum._2.getModel();
			} else if (name.equals("牛牛战士")) {
				model = CowEnum._1.getModel();
			}
		} else if (tType == UserTitle.TEXAS_WIN) {// 德州
			if (name.equals("德扑大师")) {
				model = TexasEnum._3.getModel();
			} else if (name.equals("德扑高手")) {
				model = TexasEnum._2.getModel();
			} else if (name.equals("德扑健将")) {
				model = TexasEnum._1.getModel();
			}
		}
		if (model != null) {
			model.setValidity(System.currentTimeMillis() + model.getVal() * Const.DAY);
		}
		return model;
	}

	private static Title[] getEnum(int ttype) {
		if (ttype == UserTitle.FLOWER) {
			return FlowerEnum.values();
		} else if (ttype == UserTitle.COIN) {
			return CoinEnum.values();
		} else if (ttype == UserTitle.FLOWER_SEND) {
			return FlowerSendEnum.values();
		} else if (ttype == UserTitle.FLOWER_RECV) {
			return FlowerRecvEnum.values();
		} else if (ttype == UserTitle.FLOWER_GROUP) {
			return FlowerGroupEnum.values();
			// } else if (ttype == UserTitle.NEWYEAR) {
			// return NewyearRecvEnum.values();
		} else if (ttype == UserTitle.SAKURAN_WIN) {
			return SakuranEnum.values();
		} else if (ttype == UserTitle.COW_WIN) {
			return CowEnum.values();
		} else if (ttype == UserTitle.TEXAS_WIN) {
			return TexasEnum.values();
		}
		return null;
	}

	// 花公子花仙子
	public static final String HXZ_HGZ = "http://zim.zhuangdianbi.com/121465c75a26bd7889d62b091dc84559.png";
	// 闭月羞花 风花雪月
	public static final String BYXH_FHXY = "http://zim.zhuangdianbi.com/148c21e17ccce198798709652f26d974.png";

	public static List<TitleModel> getAllTitleModel(Integer ttype, boolean admin) {
		List<TitleModel> list = new ArrayList<TitleModel>();
		Title[] titles = null;
		if (null == ttype || ttype <= 0) {// 全部titleModel
			if (null == ttype || Math.abs(ttype) != 1) {// 是否过滤掉鲜花称号
				titles = getEnum(UserTitle.FLOWER);
				for (int i = titles.length - 1; i >= 0; i--) {
					if (admin) {
						list.add(titles[i].getModel());
					} else {
						TitleModel model = titles[i].getModel();
						TitleModel modifyModel = null;
						if (model.getName().equals("花仙子")) {// 花公子花仙子合并
							modifyModel = new TitleModel(model.getType(), "花仙子&花公子", model.getPremise(), model.getVal(),
									model.getTtype(), model.getSex(), "当日花魁榜第1名(女生/男生)", HXZ_HGZ);
							list.add(modifyModel);
						} else if (model.getName().equals("闭月羞花")) {// 花公子花仙子合并
							modifyModel = new TitleModel(model.getType(), "闭月羞花&风花雪月", model.getPremise(),
									model.getVal(), model.getTtype(), model.getSex(), "一个自然月内累计三次登上花魁榜首的女生/男生",
									BYXH_FHXY);
							list.add(modifyModel);
						} else if (!model.getName().equals("花公子") && !model.getName().equals("风花雪月")) {
							list.add(model);
						}
					}
				}
			}
			titles = getEnum(UserTitle.COIN);
			for (int i = titles.length - 1; i >= 0; i--) {
				list.add(titles[i].getModel());
			}
			titles = getEnum(UserTitle.FLOWER_SEND);
			for (int i = titles.length - 1; i >= 0; i--) {
				list.add(titles[i].getModel());
			}
			titles = getEnum(UserTitle.FLOWER_RECV);
			for (int i = titles.length - 1; i >= 0; i--) {
				list.add(titles[i].getModel());
			}
			// titles = getEnum(UserTitle.NEWYEAR);
			// for (int i = titles.length - 1; i >= 0; i--) {
			// list.add(titles[i].getModel());
			// }
			titles = getEnum(UserTitle.FLOWER_GROUP);
			for (int i = titles.length - 1; i >= 0; i--) {
				list.add(titles[i].getModel());
			}
			titles = getEnum(UserTitle.SAKURAN_WIN);
			for (int i = titles.length - 1; i >= 0; i--) {
				list.add(titles[i].getModel());
			}
			titles = getEnum(UserTitle.COW_WIN);
			for (int i = titles.length - 1; i >= 0; i--) {
				list.add(titles[i].getModel());
			}
//			titles = getEnum(UserTitle.TEXAS_WIN);
//			for (int i = titles.length - 1; i >= 0; i--) {
//				list.add(titles[i].getModel());
//			}

		} else {// 指定类型titleModel
			titles = getEnum(ttype);
			if (null != titles) {
				for (int i = titles.length - 1; i >= 0; i--) {
					list.add(titles[i].getModel());
				}
			}
		}
		return list;
	}

	public static TitleModel getFlowerModel(int count, Integer sex) {
		return getModel(UserTitle.FLOWER, count, sex);
	}

	public static TitleModel getCoinModel(int count) {
		return getModel(UserTitle.COIN, count, 0);
	}

	public static TitleModel getUserTitleModel(int type, int count) {
		return getModel(type, count, 0);
	}

	public static TitleModel getUserTitleModel(int type, int count, int sex) {
		return getModel(type, count, sex);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof TitleModel) {
			if (this.getName().equals(((TitleModel) o).getName()))
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getName().hashCode();
	}

	public static void main(String[] args) {
		TitleModel tm = TitleModel.getModel(1, 1, 1);
		System.out.println(tm.getValidity());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TitleModel tm2 = TitleModel.getModel(1, 1, 1);
		System.out.println(tm2.getValidity());
		Title[] titles = getEnum(1);
		for (Title title : titles) {
			System.out.println(title.getModel().getDetail());
		}
	}

}