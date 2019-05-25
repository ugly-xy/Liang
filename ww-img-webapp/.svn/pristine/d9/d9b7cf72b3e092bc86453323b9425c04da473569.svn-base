package com.zb.service.image;

import java.awt.image.BufferedImage;

import org.apache.commons.lang.StringUtils;

import com.zb.service.barcode.QRCodeUtil;
import com.zb.service.cloud.StorageService;

public class BarcodeVCardTool extends BaseTool {

	public BarcodeVCardTool(StorageService storageService) {
		super(storageService);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		String tmpPath = "http://imgzb.zhuangdianbi.com/57132dc20cf2df2e739ea731";
		StorageService storageService = new StorageService();
		BarcodeVCardTool tyt = new BarcodeVCardTool(storageService);
		tyt.debug = true;
		tyt.drawImg("鲍", "文科", "CTO", "baowenke@zhuang.bi", "13718780428",
				"010-11111111", "北京撞璧科技有限公司", "http://zhuang.bi", tmpPath);
	}

	/***
	 * @param ruguo
	 *            如果
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws Exception
	 */
	public String drawImg(String familyName, String name, String title,
			String email, String phone, String tel, String org, String url,
			String tmpPath) throws Exception {
		int left = 70;
		int height = 250;

		StringBuilder sb = new StringBuilder("BEGIN:VCARD\nVERSION:3.0\n");
		sb.append("N:").append(familyName).append(";").append(name)
				.append("\n");
		if (StringUtils.isNotBlank(email)) {
			sb.append("EMAIL:").append(email).append("\n");
		}
		if (StringUtils.isNotBlank(phone)) {
			sb.append("TEL;CELL:").append(phone).append("\n");
		}
		if (StringUtils.isNotBlank(tel)) {
			sb.append("TEL:").append(tel).append("\n");
		}
		if (StringUtils.isNotBlank(title)) {
			sb.append("TITLE:").append(title).append("\n");
		}
		if (StringUtils.isNotBlank(org)) {
			sb.append("ORG:").append(org).append("\n");
		}
		if (StringUtils.isNotBlank(url)) {
			sb.append("URL:").append(url).append("\n");
		}

		sb.append("END:VCARD\n");

		BufferedImage bi = QRCodeUtil.encodeBI(sb.toString());

		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(left).buildVerticalOffset(height);

		SimplePositions[] sp = { nameSP };
		BufferedImage[] bis = { bi };
		return super.getSaveFile(sp, bis, 0.9f, tmpPath);
	}
}
