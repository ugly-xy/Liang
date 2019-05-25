package com.zb.web.api;

import java.text.ParseException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import com.zb.common.utils.DateUtil;
import com.zb.core.web.ReMsg;
import com.zb.service.ImageService;

@Controller
@RequestMapping(value = "/sys")
public class ImgCtl extends BaseCtl {
	static final Logger log = LoggerFactory.getLogger(ImgCtl.class);

	@Autowired
	ImageService imageService;

	@ResponseBody
	@RequestMapping("/zjJiangZhuang")
	public ReMsg jiangZhuang(String tmpBackPic, String txt,
			HttpServletRequest req) {
		return imageService.drawJiangZhuang(tmpBackPic, txt);
	}

	@ResponseBody
	@RequestMapping("/zjTongYong")
	public ReMsg zjTongYong(String tmpBackPic, String name, String sex,
			String age, String pic, String serialNumber, HttpServletRequest req) {
		return imageService.drawTongYong(tmpBackPic, name, sex, age, pic,
				serialNumber);
	}

	@ResponseBody
	@RequestMapping("/zjMingXing")
	public ReMsg zjMingXing(String type, String name, String content,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawMingXing(name, type, content, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjXingShiZheng")
	public ReMsg zjXingShiZheng(String type, String name, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawXingShiZheng(name, type, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjLOL")
	public ReMsg zjLoL(String type, String name, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawLoL(name, type, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjFangChanZheng")
	public ReMsg zjFangChanZheng(String type, String name, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawFangChanZheng(name, type, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjHaoCheDingDan")
	public ReMsg zjCarOrder(String type, String name, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawCarOrder(name, type, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjJieZhi")
	public ReMsg zjJieZhi(String name, String content, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawJieZhi(name, content, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjJiPiao")
	public ReMsg zjJiPiao(String name, String tmpBackPic, HttpServletRequest req) {
		return imageService.drawJiPiao(name, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjLianHeGuo")
	public ReMsg zjLianHeGuo(String name, String pingyin, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawLianHeGuo(name, pingyin, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjTaiQuanDao")
	public ReMsg zjTaiQuanDaoHei(String name, String serialNumber,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawTaiQuanDao(name, serialNumber, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjYaoQingHan")
	public ReMsg zjYaoQingHan(String name, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawYaoQingHan(name, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjYiMing")
	public ReMsg zjYiMing(String name, String tmpBackPic, HttpServletRequest req) {
		return imageService.drawYiMing(name, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjDuoShouDang")
	public ReMsg zjDuoShouDang(String date, String money, String per,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawDuoShouDang(date, money, per, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjJieHunZheng")
	public ReMsg zjJieHunZheng(String name, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawJieHunZheng(name, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjLiHunZheng")
	public ReMsg zjLiHunZheng(String name, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawLiHunZheng(name, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjFeiJiJiaShiZheng")
	public ReMsg zjFeiJi(String name, String timestamp, String pic, String sex,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawFeiJi(name, getTimestamp(timestamp), pic, sex,
				tmpBackPic);
	}

	Long getTimestamp(String timestamp) {
		try {
			return Long.parseLong(timestamp);
		} catch (Exception e) {
			try {
				return DateUtil.convertDate(timestamp, "yyyy-MM-dd").getTime();
			} catch (ParseException e1) {
				log.error("to long err:", e1);
			}
		}
		return System.currentTimeMillis();
	}

	@ResponseBody
	@RequestMapping("/zjYouTingJiaShiZheng")
	public ReMsg zjYouTing(String name, String timestamp, String pic,
			String sex, String tmpBackPic, HttpServletRequest req) {
		return imageService.drawYouTing(name, this.getTimestamp(timestamp),
				pic, sex, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjWuRenJiJiaShiZheng")
	public ReMsg zjWuRenJi(String name, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawWuRenJiaShi(name, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjBeiDaLuQuTongZhi")
	public ReMsg zjBeiDaLuQuTongZhi(String name, String xueyuan,
			String zhuanye, String tmpBackPic, HttpServletRequest req) {
		return imageService.drawBeiDa(name, xueyuan, zhuanye, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjJianQiaoLuQuTongZhi")
	public ReMsg zjJianQiaoLuQuTongZhi(String name, String pingyin, String pic,
			String gangwei, String tmpBackPic, HttpServletRequest req) {
		return imageService.drawJianQiao(name, pingyin, pic, gangwei,
				tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjQingHuaLuQuTongZhi")
	public ReMsg zjQingHuaLuQuTongZhi(String name, String zhuanye,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawQingHua(name, zhuanye, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjCaiKuangZheng")
	public ReMsg zjCaiKuangZheng(String name, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawCaiKuangZheng(name, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjXinWenLianBo")
	public ReMsg zjXinWenLianBo(String txt, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawXinWenLianBo(txt, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjQianShuiZheng")
	public ReMsg zjQianShuiZheng(String name, String pingyin,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawQianShuiZheng(name, pingyin, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjHuBanYiBiao")
	public ReMsg zjHuBanYiBiao(String name, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawHuBanYiBiao(name, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjChongQiWaWa")
	public ReMsg zjChongQiWaWa(String sheng, String shi, String qu, String lu,
			String name, String style, String tmpBackPic, HttpServletRequest req) {
		return imageService.drawChongQiWaWa(sheng, shi, qu, lu, name, style,
				tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjYouYiXiaoChuan")
	public ReMsg zjYouYiXiaoChuan(String ruguo, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawChongQiWaWa(ruguo, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjErWeiMaFaYan")
	public ReMsg zjErWeiMaFaYan(String name, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawErWeiMaFaYan(name, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjCfCangKu")
	public ReMsg drawCfCangKu(String name, String tmp, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawCfCangKu(name, tmp, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjCFTuFu")
	public ReMsg drawCFTuFu(String team, String name, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawCFTuFu(team, name, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjQQFeiChe")
	public ReMsg drawQQFeiChe(String name, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawQQFeiChe(name, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjTianTianXuanDouShi")
	public ReMsg drawTianTianXuanDouShi(String name, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawTianTianXuanDouShi(name, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjLaZhuBiaoBai")
	public ReMsg drawLaZhuBiaoBai(String name, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawLaZhuBiaoBai(name, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjWuRenJiBiaoBai")
	public ReMsg drawWuRenJiBiaoBai(String name, String content,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawWuRenJiBiaoBai(name, content, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjSanDaiQian")
	public ReMsg drawSanDaiQian(String name, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawSanDaiQian(name, tmpBackPic);
	}

	// 2016-4-25
	@ResponseBody
	@RequestMapping("/zjQBiChongZhi")
	public ReMsg drawQBi(String name, String content, String amount,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawQBiChongZhi(name, content, amount, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjGuangZhouTa")
	public ReMsg drawGuoZhouTa(String name, String content, String style,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawGuangZhouTa(name, content, style, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjJieHunZhengPlus")
	public ReMsg drawJieHunZhengPlus(String women, String man, String style,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawJieHunZhengPlus(women, man, style, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjMingXingJuPai")
	public ReMsg drawMingXingJuPai(String name, String content, String style,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawMingXingJuPaiTaTool(name, content, style,
				tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjNanMingXing")
	public ReMsg drawNanMingXing(String name, String content, String style,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawMingXingJuPaiTaTool(name, content, style,
				tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjNvMingXing")
	public ReMsg drawNvMingXing(String name, String content, String style,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawMingXingJuPaiTaTool(name, content, style,
				tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjTiYuMingXing")
	public ReMsg drawTiYuMingXing(String name, String content, String style,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawMingXingJuPaiTaTool(name, content, style,
				tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjQianMingZhao")
	public ReMsg drawQianMingZhao(String name, String content, String style,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawQianMingZhaoTool(name, content, style,
				tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjHuaiYun")
	public ReMsg drawHuaiYun(String name, String style, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawHuaiYunTool(name, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjHuaiYunChaoSheng")
	public ReMsg drawHuaiYunChaoSheng(String name, String sex, String age,
			String tmpBackPic, HttpServletRequest req) {
		return imageService
				.drawHuaiYunChaoShengTool(name, sex, age, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjHaoCheBiaoBai")
	public ReMsg drawHaoCheBiaoBai(String name, String content,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawHaoCheBiaoBaiTool(name, content, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjNCP3T2")
	public ReMsg drawNCP3T2(String t1, String t2, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawNaoCanDuiHuaTool(3, tmpBackPic, t1, t2);
	}

	@ResponseBody
	@RequestMapping("/zjNCP3T3")
	public ReMsg drawNCP3T2(String t1, String t2, String t3, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawNaoCanDuiHuaTool(3, tmpBackPic, t1, t2, t3);
	}

	@ResponseBody
	@RequestMapping("/zjNCP4T3")
	public ReMsg drawNCP4T3(String t1, String t2, String t3, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawNaoCanDuiHuaTool(4, tmpBackPic, t1, t2, t3);
	}

	@ResponseBody
	@RequestMapping("/zjNCP4T4")
	public ReMsg drawNCP4T4(String t1, String t2, String t3, String t4,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawNaoCanDuiHuaTool(4, tmpBackPic, t1, t2, t3, t4);
	}

	@ResponseBody
	@RequestMapping("/zjNCP5T4")
	public ReMsg drawNCP5T4(String t1, String t2, String t3, String t4,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawNaoCanDuiHuaTool(5, tmpBackPic, t1, t2, t3, t4);
	}

	@ResponseBody
	@RequestMapping("/zjNCP5T5")
	public ReMsg drawNCP5T4(String t1, String t2, String t3, String t4,
			String t5, String tmpBackPic, HttpServletRequest req) {
		return imageService.drawNaoCanDuiHuaTool(5, tmpBackPic, t1, t2, t3, t4,
				t5);
	}

	@ResponseBody
	@RequestMapping("/zjNCP6T5")
	public ReMsg drawNCP6T5(String t1, String t2, String t3, String t4,
			String t5, String tmpBackPic, HttpServletRequest req) {
		return imageService.drawNaoCanDuiHuaTool(6, tmpBackPic, t1, t2, t3, t4,
				t5);
	}

	@ResponseBody
	@RequestMapping("/zjNCP6T6")
	public ReMsg drawNCP6T6(String t1, String t2, String t3, String t4,
			String t5, String t6, String tmpBackPic, HttpServletRequest req) {
		return imageService.drawNaoCanDuiHuaTool(6, tmpBackPic, t1, t2, t3, t4,
				t5, t6);
	}

	@ResponseBody
	@RequestMapping("/zjMuQinJie")
	public ReMsg drawMuQinJie(String name, String content, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawMuQinJieTool(name, content, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjZhiYePeiXun")
	public ReMsg drawZhiYePeiXun(String name, String content,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawZhiYePeiXunTool(name, content, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjZhiPiao")
	public ReMsg drawZhiPiao(String name, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawZhiPiaoTool(name, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjXiaoYouZaoAn")
	public ReMsg drawXiaoYouZaoAn(String name, String addr, String pic,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawXiaoYouZaoAnTool(name, addr, pic, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjJiPiaoNew")
	public ReMsg drawXiaoYouZaoAn(String name, String enName, String fCity,
			String enFCity, String fc3, String tCity, String enTCity,
			String tc3, String style, String tmpBackPic, HttpServletRequest req) {
		return imageService.drawJiPiaoGuoJiTool(name, enName, fCity, enFCity,
				fc3, tCity, enTCity, tc3, style, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjWuShuDuanWei")
	public ReMsg drawWuShuDuanWeiTool(String name, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawWuShuDuanWeiTool(name, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjMingXingShiPin")
	public ReMsg drawMingXingShiPinTool(String myPic, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawMingXingShiPinTool(myPic, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjHunLi")
	public ReMsg drawHunLiTool(String women, String man, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawHunLiTool(women, man, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjWenShen")
	public ReMsg drawWenShenTool(String name, String style, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawWenShenTool(name, style, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjTuZi")
	public ReMsg drawTuZiTool(String myPic, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawTuZiTool(myPic, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjGongJiaoChe")
	public ReMsg drawGongJiaoCheTool(String content, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawGongJiaoCheTool(content, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjDanShenGou")
	public ReMsg drawDanShenGouTool(String myPic, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawDanShenGouTool(myPic, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjImgFilter")
	public ReMsg drawImgFilterTool(String myPic, String style,
			HttpServletRequest req) {
		return imageService.drawImgFilterTool(myPic, style);
	}

	@ResponseBody
	@RequestMapping("/zjYouHua")
	public ReMsg drawYouHuaTool(String name, String myPic,
			HttpServletRequest req) {
		return imageService.drawHuiHuaTool(name, myPic);
	}

	@ResponseBody
	@RequestMapping("/zjWeiMeiJianPan")
	public ReMsg drawWeiMeiJianPanTool(String content, String text,
			String myPic, String tmpBackPic, HttpServletRequest req) {
		return imageService.drawWeiMeiJianPanTool(content, text, myPic,
				tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjJiangBei")
	public ReMsg drawJiangBeiTool(String org, String name, String title,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawJiangBeiTool(org, name, title, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjJianTaoShu")
	public ReMsg drawJianTaoShuTool(String name, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawJianTaoShuTool(name, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjDaYinJiBiaoBai")
	public ReMsg drawDaYinJiBiaoBaiTool(String from, String to, String content,
			String myPic, String tmpBackPic, HttpServletRequest req) {
		return imageService.drawDaYinJiBiaoBaiTool(from, to, content, myPic,
				tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjTShirt")
	public ReMsg drawTShirtTool(String content, String myPic, String style,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawTShirtTool(content, myPic, style, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjChouNvXinWen")
	public ReMsg drawChouNvXinWenTool(String content, String name,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawChouNvXinWenTool(name, content, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjShiZiLuBiaoBai")
	public ReMsg drawShiZiLuBiaoBaiTool(String content, String name,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawShiZiLuBiaoBaiTool(name, content, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjLvXingZhao")
	public ReMsg drawLvXingZhaoTool(String myPic, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawLvYouZhaoTool(myPic, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjErWeiMa")
	public ReMsg drawErWeiMaTool(String content, String title,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawErWeiMaTool(title, content, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjSuMiao")
	public ReMsg drawSuMiaoTool(String name, String myPic, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawSuMiaoTool(name, myPic, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjLiuYi")
	public ReMsg drawLiuYiTool(String to, String from, String style,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawLiuYiTool(to, from, style, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjXinWenPlus")
	public ReMsg drawXinWenTool(String content, String myPic, String style,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawXinWenTool(content, myPic, style, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjQianDaDaDe")
	public ReMsg drawQianDaDaDeTool(String to, String content, String from,
			String style, String tmpBackPic, HttpServletRequest req) {
		return imageService.drawQianDaDaDeTool(to, content, from, style,
				tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjDieZhi")
	public ReMsg drawDieZhiTool(String name, String tmpBackPic,
			HttpServletRequest req) {
		return imageService.drawDieZhiTool(name, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/zjYanZhiCeShi")
	public ReMsg drawYanZhiCeShiTool(String myPic, String use,
			String tmpBackPic, HttpServletRequest req) {
		return imageService.drawYanZhiCeShiTool(myPic, use, tmpBackPic);
	}

	@ResponseBody
	@RequestMapping("/oneDraw")
	public ReMsg drawOneDraw(String tmpl, String tmpBackPic,
			HttpServletRequest req) {
		String one = req.getParameter("one");
		String count = req.getParameter("useCount");
		return imageService.drawOneTool(one, tmpl, tmpBackPic, count);
	}

	@ResponseBody
	@RequestMapping("/twoDraw")
	public ReMsg drawTwoDraw(String tmpl, String tmpBackPic,
			HttpServletRequest req) {
		String one = req.getParameter("one");
		String two = req.getParameter("two");
		String count = req.getParameter("useCount");
		return imageService.drawTwoTool(one, two, tmpl, tmpBackPic, count);
	}

	@ResponseBody
	@RequestMapping("/threeDraw")
	public ReMsg drawThreeDraw(String tmpl, String tmpBackPic,
			HttpServletRequest req) {
		String one = req.getParameter("one");
		String two = req.getParameter("two");
		String three = req.getParameter("three");
		String count = req.getParameter("useCount");
		return imageService.drawThreeTool(one, two, three, tmpl, tmpBackPic,
				count);
	}

	@ResponseBody
	@RequestMapping("/fourDraw")
	public ReMsg drawFourDraw(String tmpl, String tmpBackPic,
			HttpServletRequest req) {
		String one = req.getParameter("one");
		String two = req.getParameter("two");
		String three = req.getParameter("three");
		String four = req.getParameter("four");
		String count = req.getParameter("useCount");
		return imageService.drawFourTool(one, two, three, four, tmpl,
				tmpBackPic, count);
	}

	@ResponseBody
	@RequestMapping("/fiveDraw")
	public ReMsg drawFiveDraw(String tmpl, String tmpBackPic,
			HttpServletRequest req) {
		String one = req.getParameter("one");
		String two = req.getParameter("two");
		String three = req.getParameter("three");
		String four = req.getParameter("four");
		String five = req.getParameter("five");
		String count = req.getParameter("useCount");
		return imageService.drawFiveTool(one, two, three, four, five, tmpl,
				tmpBackPic, count);
	}

	@ResponseBody
	@RequestMapping("/draw")
	public ReMsg draw(HttpServletRequest req) {
		return imageService.drawTool(req);
	}

}
