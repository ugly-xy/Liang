package com.zb.models.finance;

import java.util.Date;

import com.zb.common.utils.DateUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.models.AbstractDocument;

public class Order extends AbstractDocument {

	public static final int FAILED = -1;
	public static final int CREATE = 1;
	public static final int PADED = 2;
	public static final int OPENED = 3;

	public static final int COIN = 1;
	public static final int GOODS = 2;
	public static final int PACK = 3;
	public static final int THIRD = 4;// 与我们联运的第三方订单

	public static final int COIN_RATIO = 100;

	public static final String THIRD_WX_APP = "wx";
	public static final String THIRD_ALI_APP = "ali";
	public static final String THIRD_UNION_PAY_APP = "uni";
	public static final String THIRD_APPLE_PAY_APP = "apple";
	public static final String THIRD_MIDAS_PAY_APP = "midas";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1253943538773764911L;

	private String no;// 订单号

	// private int type;// 订单类型 coin ，goods ，pack ，third

	private String productId;// 为 goods pack的ID

	private long buyUid;// 购买人

	private long recUid;// 到账用户id

	private String payType;// 支付渠道

	private String thirdNo;// 第三方返回订单号

	private int count;// 数量，份数（虚拟币数量）

	private int amount;// 总金额（人民币:分）

	private String discountNo;// 折扣券

	private int discountType;// 折扣券类型

	private int discount;// 折扣

	private int finalAmount;// 最终金额（人民币：分）

	private int status = CREATE;// 订单状态

	private long createTime;// 创建时间

	private long payTime;// 支付时间
	private long openTime;// 开通时间
	private String title;// 商品名
	private String body;// 商品详情
	private String appCode;
	private Long adminId;

	private ThirdOrderExt thirdOrderExt;// 第三方商户订单扩展

	public Order() {

	}

	public Order(Long id, String productId, long buyUid, long recUid,
			String payType, int amount, String discountNo, int discountType,
			int discount, int finalAmount, int count, String title, String body,String appCode) {
		this(id, null, productId, buyUid, recUid, payType, amount, discountNo,
				discountType, discount, finalAmount, count, title, body,appCode);
		int len = id.toString().length();
		String nos = RandomUtil.randomNum(10 - len);
		this.no = DateUtil.dateFormatyyyyMMdd(new Date()) + nos + id;
	}
	
	public Order(Long id, String orderNo, String productId, long buyUid,
			long recUid, String payType, int amount, String discountNo,
			int discountType, int discount, int finalAmount, int count,
			String title, String body,String appCode) {
		this._id = id;
		this.no = orderNo;
		this.productId = productId;
		this.buyUid = buyUid;
		this.recUid = recUid;
		this.payType = payType;
		this.amount = amount;
		this.discountNo = discountNo;
		this.discountType = discountType;
		this.discount = discount;
		this.finalAmount = finalAmount;
		this.createTime = System.currentTimeMillis();
		this.updateTime = createTime;
		this.count = count;
		this.title = title;
		this.body = body;
		this.appCode = appCode;
	}

	

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public long getBuyUid() {
		return buyUid;
	}

	public void setBuyUid(long buyUid) {
		this.buyUid = buyUid;
	}

	public long getRecUid() {
		return recUid;
	}

	public void setRecUid(long recUid) {
		this.recUid = recUid;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getDiscountNo() {
		return discountNo;
	}

	public void setDiscountNo(String discountNo) {
		this.discountNo = discountNo;
	}

	public int getDiscountType() {
		return discountType;
	}

	public void setDiscountType(int discountType) {
		this.discountType = discountType;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(int finalAmount) {
		this.finalAmount = finalAmount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getThirdNo() {
		return thirdNo;
	}

	public void setThirdNo(String thirdNo) {
		this.thirdNo = thirdNo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public long getPayTime() {
		return payTime;
	}

	public void setPayTime(long payTime) {
		this.payTime = payTime;
	}

	public long getOpenTime() {
		return openTime;
	}

	public void setOpenTime(long openTime) {
		this.openTime = openTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public ThirdOrderExt getThirdOrderExt() {
		return thirdOrderExt;
	}

	public void setThirdOrderExt(ThirdOrderExt thirdOrderExt) {
		this.thirdOrderExt = thirdOrderExt;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

}
