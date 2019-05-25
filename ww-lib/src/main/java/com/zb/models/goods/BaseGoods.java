package com.zb.models.goods;

import java.io.Serializable;

public class BaseGoods implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;// 名字
	private int type;// GoodType
	private int amountType;//
	private String unit;// 单位
	private int count = 1;// 每单位数量
	private String pic;// 物品图片
	private int price = 0;

	public BaseGoods() {

	}

	public BaseGoods(int id, String name, GoodsType type, AmountType amountType, Unit unit, String pic, int price) {
		this.id = id + type.getT() * 10000;
		this.name = name;
		this.type = type.getT();
		this.amountType = amountType.getT();
		this.unit = unit.name();
		this.count = unit.getTimes();
		this.pic = pic;
		this.price = price;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getAmountType() {
		return amountType;
	}

	public void setAmountType(int amountType) {
		this.amountType = amountType;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public enum GoodsType {
		PROP(0), // 道具 快进卡 |小喇叭|金喇叭
		MONEY(1), // 金币
		GIFT(2), // 礼物：鲜花|鸡蛋
		EXP(3), // 经验
		CAR(4);
		private int t;

		private GoodsType(int t) {
			this.t = t;
		}

		public int getT() {
			return t;
		}

	}

	public enum AmountType {
		// DEF(0),
		ONLY(1), // 独立的
		TIME(2), // 时间累加
		AMOUNT(3), // 数量增加
		ONLY_TIME(4);// 单个有时间限制
		private int t;

		private AmountType(int t) {
			this.t = t;
		}

		public int getT() {
			return t;
		}
	}

	public enum Unit {

		币(1), 天(1000 * 3600 * 24), 张(1), 朵(1), 个(1), 次(1), 点(1), 辆(1);

		private int times = 1;

		private Unit(int times) {
			this.times = times;
		}

		public int getTimes() {
			return times;
		}

	}

	public interface G {
		BaseGoods getV();
	}

	public enum Money implements G {
		COIN(1, "金币", GoodsType.MONEY, AmountType.AMOUNT, Unit.币, "");

		BaseGoods v;

		private Money(int id, String name, GoodsType type, AmountType amountType, Unit unit, String pic) {
			this.v = new BaseGoods(id, name, type, amountType, unit, pic, 0);
		}

		public BaseGoods getV() {
			return v;
		}
	}

	public enum Prop implements G {
		DICE(1, "骰子", GoodsType.PROP, AmountType.AMOUNT, Unit.次, "", 0)
		, FAST_CARD(2, "快进卡", GoodsType.PROP,AmountType.AMOUNT, Unit.张, "", 20)
		, HORN(3, "喇叭", GoodsType.PROP, AmountType.AMOUNT, Unit.个, "",0)
		, GOLDEN_HORN(4, "金喇叭", GoodsType.PROP, AmountType.AMOUNT, Unit.个, "", 10)
		, EGG(5, "鸡蛋", GoodsType.PROP, AmountType.AMOUNT, Unit.个, "", 10)
		, GOODMAN(6, "好人", GoodsType.PROP, AmountType.AMOUNT, Unit.个, "", 10)
		, WOLF(7, "狼人", GoodsType.PROP, AmountType.AMOUNT, Unit.个, "", 10)
		, KISS(8, "么么哒", GoodsType.PROP, AmountType.AMOUNT, Unit.个, "", 10)
		, SLIPPER(9, "拖鞋", GoodsType.PROP, AmountType.AMOUNT, Unit.个, "", 10);
		BaseGoods v;

		private Prop(int id, String name, GoodsType type, AmountType amountType, Unit unit, String pic, int price) {
			this.v = new BaseGoods(id, name, type, amountType, unit, pic, price);
		}

		public BaseGoods getV() {
			return v;
		}
	}

	public enum Gift implements G {
		FLOWER(1, "鲜花", GoodsType.GIFT, AmountType.AMOUNT, Unit.朵, "", 10), EGG(2, "鸡蛋", GoodsType.GIFT,
				AmountType.AMOUNT, Unit.个, "", 10);
		BaseGoods v;

		private Gift(int id, String name, GoodsType type, AmountType amountType, Unit unit, String pic, int price) {
			this.v = new BaseGoods(id, name, type, amountType, unit, pic, price);
		}

		public BaseGoods getV() {
			return v;
		}
	}

	public enum Car implements G {
		G55(1, "奔驰G55", GoodsType.CAR, AmountType.AMOUNT, Unit.辆, "", 10);
		BaseGoods v;

		private Car(int id, String name, GoodsType type, AmountType amountType, Unit unit, String pic, int price) {
			this.v = new BaseGoods(id, name, type, amountType, unit, pic, price);
		}

		public BaseGoods getV() {
			return v;
		}
	}

	public enum Exp implements G {
		POINT(1, "经验值", GoodsType.EXP, AmountType.AMOUNT, Unit.点, ""), VIP(2, "VIP经验值", GoodsType.EXP,
				AmountType.AMOUNT, Unit.点, "");
		BaseGoods v;

		private Exp(int id, String name, GoodsType type, AmountType amountType, Unit unit, String pic) {
			this.v = new BaseGoods(id, name, type, amountType, unit, pic, 0);
		}

		public BaseGoods getV() {
			return v;
		}

	}

	// private static Map<BaseGoods.GoodsType, ArrayList<BaseGoods.G>> allbgs =
	// new HashMap<BaseGoods.GoodsType, ArrayList<BaseGoods.G>>();
	// static{
	// allbgs.put(GoodsType.PROP, Prop.values());
	// }
	// public static Map<BaseGoods.GoodsType, ArrayList<BaseGoods.G>>
	// getAllBgs(){
	//
	// }
	public static void main(String[] args) {
		System.out.println(Unit.天.name() + " " + Unit.valueOf("天").times);
		System.out.println(AmountType.AMOUNT.ordinal() + " " + AmountType.AMOUNT.name());
		GoodsType[] ats = GoodsType.values();
		for (GoodsType at : ats) {
			System.out.println(at);
		}

		System.out.println(Unit.valueOf(""));
	}
}
