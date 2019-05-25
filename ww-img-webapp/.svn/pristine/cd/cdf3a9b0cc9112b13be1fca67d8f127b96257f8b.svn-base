package com.zb.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.common.Constant.ReCode;
import com.zb.core.web.ReMsg;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.*;
import com.zb.service.image.certificate.JiangBeiTool;
import com.zb.service.image.certificate.WuShuDuanWeiTool;
import com.zb.service.image.certificate.ZhiYePeiXunTool;
import com.zb.service.image.game.*;
import com.zb.service.image.love.*;
import com.zb.service.image.nc.NaoCanDuiHuaTool;
import com.zb.service.image.spoof.*;
import com.zb.service.image.tuhao.*;
import com.zb.service.image.genius.*;
import com.zb.service.image.holiday.*;
import com.zb.service.image.i.*;

@Service
public class ImageService {

	static final Logger log = LoggerFactory.getLogger(ImageService.class);

	@Autowired
	StorageService storageService;

	public ReMsg drawJiangZhuang(String tmpBackPic, String txt) {
		String path = null;
		JiangZhuangTool jzt = new JiangZhuangTool(storageService);
		try {
			path = jzt.drawImg(txt, tmpBackPic);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("JiangZhuangTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawTongYong(String tmpBackPic, String name, String sex,
			String age, String headPath, String serialNumber) {
		String path = null;
		TongYongTool tyt = new TongYongTool(storageService);
		try {
			path = tyt.drawImg(name, sex, age, tmpBackPic, headPath,
					serialNumber);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("TongYongTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawXingShiZheng(String name, String type, String tmpPath) {
		String path = null;
		XingshiZhengTool tyt = new XingshiZhengTool(storageService);
		try {
			path = tyt.drawImg(name, type, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("XingShiZhengTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawMingXing(String name, String type, String content,
			String tmpPath) {
		String path = null;
		MingXingTool tyt = new MingXingTool(storageService);
		try {
			path = tyt.drawImg(name, type, content, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("MingXingTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawLoL(String name, String type, String tmpPath) {
		String path = null;
		LoLTool tyt = new LoLTool(storageService);
		try {
			path = tyt.drawImg(name, type, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("LoLTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawFangChanZheng(String name, String type, String tmpPath) {
		String path = null;
		FangChanZhengTool tyt = new FangChanZhengTool(storageService);
		try {
			path = tyt.drawImg(name, type, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("FangChanZhengTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawCarOrder(String name, String type, String tmpPath) {
		String path = null;
		CarOrderTool tyt = new CarOrderTool(storageService);
		try {
			path = tyt.drawImg(name, type, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("CarOrderTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawJieZhi(String name, String content, String tmpPath) {
		String path = null;
		JieZhiTool tyt = new JieZhiTool(storageService);
		try {
			path = tyt.drawImg(name, content, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("JieZhiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawJiPiao(String name, String tmpPath) {
		String path = null;
		JiPiaoTool tyt = new JiPiaoTool(storageService);
		try {
			path = tyt.drawImg(name, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("JiPiaoTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawLianHeGuo(String name, String pingyin, String tmpPath) {
		String path = null;
		LianHeGuoTool tyt = new LianHeGuoTool(storageService);
		try {
			path = tyt.drawImg(name, pingyin, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("LianHeGuoTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawTaiQuanDao(String name, String serialNumber, String tmpPath) {
		String path = null;
		TaiQuanDaoTool tyt = new TaiQuanDaoTool(storageService);
		try {
			path = tyt.drawImg(name, serialNumber, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("TaiQuanDaoHeiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawYaoQingHan(String name, String tmpPath) {
		String path = null;
		YaoQingHanTool tyt = new YaoQingHanTool(storageService);
		try {
			path = tyt.drawImg(name, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("YaoQingHanTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawYiMing(String name, String tmpPath) {
		String path = null;
		YiMingTool tyt = new YiMingTool(storageService);
		try {
			path = tyt.drawImg(name, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("YiMingTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawDuoShouDang(String date, String money, String per,
			String tmpPath) {
		String path = null;
		DuoShouDangTool tyt = new DuoShouDangTool(storageService);
		try {
			path = tyt.drawImg(date, money, per, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("DuoShouDangTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawFeiJi(String name, Long timestamp, String pic, String sex,
			String tmpPath) {
		String path = null;
		FeiJiTool tyt = new FeiJiTool(storageService);
		try {
			path = tyt.drawImg(name, timestamp, pic, sex, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("FeiJiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawJieHunZheng(String name, String tmpPath) {
		String path = null;
		JieHunZhengTool tyt = new JieHunZhengTool(storageService);
		try {
			path = tyt.drawImg(name, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("JieHunZhengTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawLiHunZheng(String name, String tmpPath) {
		String path = null;
		LiHunZhengTool tyt = new LiHunZhengTool(storageService);
		try {
			path = tyt.drawImg(name, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("LiHunZhengTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawWuRenJiaShi(String name, String tmpPath) {
		String path = null;
		WuRenJiaShiTool tyt = new WuRenJiaShiTool(storageService);
		try {
			path = tyt.drawImg(name, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("WuRenJiaShiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawYouTing(String name, Long timestamp, String pic,
			String sex, String tmpPath) {
		String path = null;
		YouTingTool tyt = new YouTingTool(storageService);
		try {
			path = tyt.drawImg(name, timestamp, pic, sex, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("YouTingTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawBeiDa(String name, String xueyuan, String zhuanye,
			String tmpPath) {
		String path = null;
		BeiJingDaXueTool tyt = new BeiJingDaXueTool(storageService);
		try {
			path = tyt.drawImg(name, xueyuan, zhuanye, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("BeiJingDaXueTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawCaiKuangZheng(String name, String tmpPath) {
		String path = null;
		CaiKuangZhengTool tyt = new CaiKuangZhengTool(storageService);
		try {
			path = tyt.drawImg(name, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("CaiKuangTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawJianQiao(String name, String pingyin, String pic,
			String gangwei, String tmpPath) {
		String path = null;
		JianQiaoTool tyt = new JianQiaoTool(storageService);
		try {
			path = tyt.drawImg(name, pingyin, gangwei, pic, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("JianQiaoTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawQianShuiZheng(String name, String pingyin, String tmpPath) {
		String path = null;
		QianShuiZhengTool tyt = new QianShuiZhengTool(storageService);
		try {
			path = tyt.drawImg(name, pingyin, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("QianShuiZhengTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawQingHua(String name, String zhuanye, String tmpPath) {
		String path = null;
		QingHuaDaXueTool tyt = new QingHuaDaXueTool(storageService);
		try {
			path = tyt.drawImg(name, zhuanye, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("QingHuaDaXueTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawXinWenLianBo(String txt, String tmpPath) {
		String path = null;
		XinWenLianBoTool tyt = new XinWenLianBoTool(storageService);
		try {
			path = tyt.drawImg(txt, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("XinWenLianBoTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawHuBanYiBiao(String txt, String tmpPath) {
		String path = null;
		HuBanYiBiaoTool tyt = new HuBanYiBiaoTool(storageService);
		try {
			path = tyt.drawImg(txt, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("HuBanYiBiaoTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawChongQiWaWa(String sheng, String shi, String qu,
			String lu, String name, String style, String tmpPath) {
		String path = null;
		ChongQiWaWaTool tyt = new ChongQiWaWaTool(storageService);
		try {
			path = tyt.drawImg(sheng, shi, qu, lu, name, style, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("drawChongQiWaWa err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawChongQiWaWa(String ruguo, String tmpPath) {
		String path = null;
		YouYiXiaoChuanTool tyt = new YouYiXiaoChuanTool(storageService);
		try {
			path = tyt.drawImg(ruguo, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (IOException e) {
			log.error("drawChongQiWaWa err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawErWeiMaFaYan(String name, String tmpPath) {
		String path = null;
		ErWeiMaFaYanTool tyt = new ErWeiMaFaYanTool(storageService);
		try {
			path = tyt.drawImg(name, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawErWeiMaFaYan err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawCfCangKu(String name, String tmp, String tmpPath) {
		String path = null;
		CfCangKuTool tyt = new CfCangKuTool(storageService);
		try {
			path = tyt.drawImg(name, tmp, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawCfCangKu err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawCFTuFu(String team, String name, String tmpPath) {
		String path = null;
		CFTuFuTool tyt = new CFTuFuTool(storageService);
		try {
			path = tyt.drawImg(team, name, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawCFTuFu err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawQQFeiChe(String name, String tmpPath) {
		String path = null;
		QQFeiCheTool tyt = new QQFeiCheTool(storageService);
		try {
			path = tyt.drawImg(name, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawQQFeiChe err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawTianTianXuanDouShi(String name, String tmpPath) {
		String path = null;
		TianTianXuanDouShiTool tyt = new TianTianXuanDouShiTool(storageService);
		try {
			path = tyt.drawImg(name, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawTianTianXuanDouShi err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawLaZhuBiaoBai(String name, String tmpPath) {
		String path = null;
		LaZhuBiaoBaiTool tyt = new LaZhuBiaoBaiTool(storageService);
		try {
			path = tyt.drawImg(name, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawLaZhuBiaoBai err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawWuRenJiBiaoBai(String name, String content, String tmpPath) {
		String path = null;
		WuRenJiBiaoBaiTool tyt = new WuRenJiBiaoBaiTool(storageService);
		try {
			path = tyt.drawImg(name, content, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawWuRenJiBiaoBai err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawSanDaiQian(String name, String tmpPath) {
		String path = null;
		SanDaiQianTool tyt = new SanDaiQianTool(storageService);
		try {
			path = tyt.drawImg(name, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawSanDaiQian err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	// 2016-04-25

	public ReMsg drawQBiChongZhi(String name, String content, String amount,
			String tmpPath) {
		String path = null;
		QBiChongZhiTool tyt = new QBiChongZhiTool(storageService);
		try {
			path = tyt.drawImg(name, content, amount, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawQBiChongZhi err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawGuangZhouTa(String name, String content, String style,
			String tmpPath) {
		String path = null;
		GuangZhouTaTool tyt = new GuangZhouTaTool(storageService);
		try {
			path = tyt.drawImg(name, content, style, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawGuangZhouTa err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawJieHunZhengPlus(String women, String man, String style,
			String tmpPath) {
		String path = null;
		JieHunZhengPlusTool tyt = new JieHunZhengPlusTool(storageService);
		try {
			path = tyt.drawImg(women, man, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawJieHunZhengPlus err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawMingXingJuPaiTaTool(String name, String content,
			String style, String tmpPath) {
		String path = null;
		MingXingJuPaiTaTool tyt = new MingXingJuPaiTaTool(storageService);
		try {
			path = tyt.drawImg(name, content, style, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawMingXingJuPaiTaTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawQianMingZhaoTool(String name, String content,
			String style, String tmpPath) {
		String path = null;
		QianMingZhaoTool tyt = new QianMingZhaoTool(storageService);
		try {
			path = tyt.drawImg(name, content, style, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawQianMingZhaoTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawHuaiYunTool(String name, String tmpPath) {
		String path = null;
		HuaiYunTool tyt = new HuaiYunTool(storageService);
		try {
			path = tyt.drawImg(name, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawHuaiYunTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawHuaiYunChaoShengTool(String name, String sex, String age,
			String tmpPath) {
		String path = null;
		HuaiYunChaoShengTool tyt = new HuaiYunChaoShengTool(storageService);
		try {
			path = tyt.drawImg(name, sex, age, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawHuaiYunChaoShengTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawHaoCheBiaoBaiTool(String name, String content,
			String tmpPath) {
		String path = null;
		HaoCheBiaoBaiTool tyt = new HaoCheBiaoBaiTool(storageService);
		try {
			path = tyt.drawImg(name, content, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawHaoCheBiaoBaiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawNaoCanDuiHuaTool(int count, String tmpPath,
			String... talks) {
		String path = null;
		NaoCanDuiHuaTool tyt = new NaoCanDuiHuaTool(storageService);
		try {
			path = tyt.drawImg(talks, tmpPath, count);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawNaoCanDuiHuaTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawMuQinJieTool(String name, String content, String tmpPath) {
		String path = null;
		MuQinJieTool tyt = new MuQinJieTool(storageService);
		try {
			path = tyt.drawImg(name, content, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawMuQinJieTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawZhiYePeiXunTool(String name, String content, String tmpPath) {
		String path = null;
		ZhiYePeiXunTool tyt = new ZhiYePeiXunTool(storageService);
		try {
			path = tyt.drawImg(name, content, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawZhiYePeiXunTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawZhiPiaoTool(String name, String tmpPath) {
		String path = null;
		ZhiPiaoTool tyt = new ZhiPiaoTool(storageService);
		try {
			path = tyt.drawImg(name, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawZhiPiaoTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawXiaoYouZaoAnTool(String name, String addr, String pic,
			String tmpPath) {
		String path = null;
		XiaoYouZaoAnTool tyt = new XiaoYouZaoAnTool(storageService);
		try {
			path = tyt.drawImg(name, addr, pic, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawXiaoYouZaoAnTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawJiPiaoGuoJiTool(String name, String enName, String fCity,
			String enFCity, String fc3, String tCity, String enTCity,
			String tc3, String style, String tmpPath) {
		String path = null;
		JiPiaoGuoJiTool tyt = new JiPiaoGuoJiTool(storageService);
		try {
			path = tyt.drawImg(name, enName, fCity, enFCity, fc3, tCity,
					enTCity, tc3, style, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawJiPiaoGuoJiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawWuShuDuanWeiTool(String name, String tmpPath) {
		String path = null;
		WuShuDuanWeiTool tyt = new WuShuDuanWeiTool(storageService);
		try {
			path = tyt.drawImg(name, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawWuShuDuanWeiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawMingXingShiPinTool(String myPic, String tmpPath) {
		String path = null;
		MingXingShiPinTool tyt = new MingXingShiPinTool(storageService);
		try {
			path = tyt.drawImg(myPic, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawWuShuDuanWeiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawHunLiTool(String women, String man, String tmpPath) {
		String path = null;
		HunLiTool tyt = new HunLiTool(storageService);
		try {
			path = tyt.drawImg(women, man, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawWuShuDuanWeiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawWenShenTool(String name, String style, String tmpPath) {
		String path = null;
		WenShenTool tyt = new WenShenTool(storageService);
		try {
			path = tyt.drawImg(name, style, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawWenShenWeiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawGongJiaoCheTool(String content, String tmpPath) {
		String path = null;
		GongJiaoCheTool tyt = new GongJiaoCheTool(storageService);
		try {
			path = tyt.drawImg(content, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawGongJiaoCheTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawTuZiTool(String myPic, String tmpPath) {
		String path = null;
		TuZiTool tyt = new TuZiTool(storageService);
		try {
			path = tyt.drawImg(myPic, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawWuShuDuanWeiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawDanShenGouTool(String myPic, String tmpPath) {
		String path = null;
		DanShenGouTool tyt = new DanShenGouTool(storageService);
		try {
			path = tyt.drawImg(myPic, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawWuShuDuanWeiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawImgFilterTool(String myPic, String style) {
		String path = null;
		ImgFilterTool tyt = new ImgFilterTool(storageService);
		try {
			path = tyt.drawImg(myPic, style);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawWuShuDuanWeiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawHuiHuaTool(String name, String myPic) {
		String path = null;
		YouHuaTool tyt = new YouHuaTool(storageService);
		try {
			path = tyt.drawImg(name, myPic);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawWuShuDuanWeiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawWeiMeiJianPanTool(String content, String text,
			String myPic, String tmpPath) {
		String path = null;
		WeiMeiJianPanTool tyt = new WeiMeiJianPanTool(storageService);
		try {
			path = tyt.drawImg(content, text, myPic, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawWuShuDuanWeiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawJiangBeiTool(String org, String name, String title,
			String tmpPath) {
		String path = null;
		JiangBeiTool tyt = new JiangBeiTool(storageService);
		try {
			path = tyt.drawImg(org, name, title, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawWuShuDuanWeiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawJianTaoShuTool(String name, String tmpPath) {
		String path = null;
		JianTaoShuTool tyt = new JianTaoShuTool(storageService);
		try {
			path = tyt.drawImg(name, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawWuShuDuanWeiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawDaYinJiBiaoBaiTool(String from, String to, String content,
			String myPic, String tmpPath) {
		String path = null;
		DaYinJiBiaoBaiTool tyt = new DaYinJiBiaoBaiTool(storageService);
		try {
			path = tyt.drawImg(from, to, content, myPic, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawWuShuDuanWeiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawTShirtTool(String content, String myPic, String style,
			String tmpPath) {
		String path = null;
		TShirtBiaoBaiTool tyt = new TShirtBiaoBaiTool(storageService);
		try {
			path = tyt.drawImg(content, myPic, style, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawWuShuDuanWeiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawChouNvXinWenTool(String name, String content,
			String tmpPath) {
		String path = null;
		ChouNvXinWenTool tyt = new ChouNvXinWenTool(storageService);
		try {
			path = tyt.drawImg(name, content, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawWuShuDuanWeiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawShiZiLuBiaoBaiTool(String name, String content,
			String tmpPath) {
		String path = null;
		ShiZiLuKouTool tyt = new ShiZiLuKouTool(storageService);
		try {
			path = tyt.drawImg(name, content, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawWuShuDuanWeiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawLvYouZhaoTool(String myPic, String tmpPath) {
		String path = null;
		LvYouZhaoTool tyt = new LvYouZhaoTool(storageService);
		try {
			path = tyt.drawImg(myPic, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawWuShuDuanWeiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawErWeiMaTool(String title, String content, String tmpPath) {
		String path = null;
		ErWeiMaTool tyt = new ErWeiMaTool(storageService);
		try {
			path = tyt.drawImg(title, content);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawWuShuDuanWeiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawSuMiaoTool(String name, String myPic, String tmpPath) {
		String path = null;
		SuMiaoTool tyt = new SuMiaoTool(storageService);
		try {
			path = tyt.drawImg(name, myPic, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawSuMiaoTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawLiuYiTool(String to, String from, String style,
			String tmpPath) {
		String path = null;
		LiuYiTool tyt = new LiuYiTool(storageService);
		try {
			path = tyt.drawImg(to, from, style, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawLiuYiTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawXinWenTool(String content, String myPic, String style,
			String tmpPath) {
		String path = null;
		XinWenTool tyt = new XinWenTool(storageService);
		try {
			path = tyt.drawImg(myPic, content, style, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawSuMiaoTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawQianDaDaDeTool(String to, String content, String from,
			String style, String tmpPath) {
		String path = null;
		QianDaDaDeTool tyt = new QianDaDaDeTool(storageService);
		try {
			path = tyt.drawImg(to, content, from, style, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawQianDdDaDeTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawDieZhiTool(String name, String tmpPath) {
		String path = null;
		DieZhiTool tyt = new DieZhiTool(storageService);
		try {
			path = tyt.drawImg(name, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawQianDdDaDeTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawYanZhiCeShiTool(String myPic, String use, String tmpPath) {
		String path = null;
		YanZhiTool tyt = new YanZhiTool(storageService);
		try {
			path = tyt.drawImg(myPic, use, tmpPath);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("drawQianDdDaDeTool err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawOneTool(String one, String tmpl, String tmpPath,
			String count) {
		String path = null;
		Class<?> tool = null;
		try {
			tool = Class.forName(tmpl);
			OneDraw draw = (OneDraw) tool.newInstance();
			draw.setStorageService(storageService);
			path = draw.draw(one, tmpPath, count);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("one " + tool != null ? tool.getName() : "" + " err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawTwoTool(String one, String two, String tmpl,
			String tmpPath, String count) {
		String path = null;
		Class<?> tool = null;
		try {
			tool = Class.forName(tmpl);
			TwoDraw draw = (TwoDraw) tool.newInstance();
			draw.setStorageService(storageService);
			path = draw.draw(one, two, tmpPath, count);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("two " + tool.getName() + " err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawThreeTool(String one, String two, String three,
			String tmpl, String tmpPath, String count) {
		String path = null;
		Class<?> tool = null;
		try {
			tool = Class.forName(tmpl);
			ThreeDraw draw = (ThreeDraw) tool.newInstance();
			draw.setStorageService(storageService);
			path = draw.draw(one, two, three, tmpPath, count);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("Three " + tool.getName() + " err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawFourTool(String one, String two, String three,
			String four, String tmpl, String tmpPath, String count) {
		String path = null;
		Class<?> tool = null;
		try {
			tool = Class.forName(tmpl);
			FourDraw draw = (FourDraw) tool.newInstance();
			draw.setStorageService(storageService);
			path = draw.draw(one, two, three, four, tmpPath, count);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("Four " + tool.getName() + " err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawFiveTool(String one, String two, String three,
			String four, String five, String tmpl, String tmpPath, String count) {
		String path = null;
		Class<?> tool = null;
		try {
			tool = Class.forName(tmpl);
			FiveDraw draw = (FiveDraw) tool.newInstance();
			draw.setStorageService(storageService);
			path = draw.draw(one, two, three, four, five, tmpPath, count);
			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("Five " + tool.getName() + " err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg drawTool(HttpServletRequest req) {
		String path = null;
		Class<?> tool = null;
		try {
			String tmpl = req.getParameter("tmpl");
			String tmpBackPic = req.getParameter("tmpBackPic");
			String style = req.getParameter("style");
			String count = req.getParameter("useCount");
			String one = req.getParameter("one");
			String two = req.getParameter("two");
			String three = req.getParameter("three");
			String four = req.getParameter("four");
			String five = req.getParameter("five");

			tool = Class.forName(tmpl);
			
//			 Class<?> inter[]=null;//声明一个对象数组
//			 inter=tool.getInterfaces();//获取类实现的所有接口
//			 for(int i=0;i<inter.length;i++){
//				 if(inter[i] instac)
//			  System.out.println(inter[i].getName());//打印出完整的包名+接口
//			 }
			 

			if (five != null) {
				if (style != null) {
					FiveStyleDraw draw = (FiveStyleDraw) tool.newInstance();
					draw.setStorageService(storageService);
					path = draw.draw(one, two, three, four, five, style,
							tmpBackPic, count);
				} else {
					FiveDraw draw = (FiveDraw) tool.newInstance();
					draw.setStorageService(storageService);
					path = draw.draw(one, two, three, four, five, tmpBackPic,
							count);
				}
			} else if (four != null) {
				if (style != null) {
					FourStyleDraw draw = (FourStyleDraw) tool.newInstance();
					draw.setStorageService(storageService);
					path = draw.draw(one, two, three, four, style, tmpBackPic,
							count);
				} else {
					FourDraw draw = (FourDraw) tool.newInstance();
					draw.setStorageService(storageService);
					path = draw.draw(one, two, three, four, tmpBackPic, count);
				}
			} else if (three != null) {
				if (style != null) {
					ThreeStyleDraw draw = (ThreeStyleDraw) tool.newInstance();
					draw.setStorageService(storageService);
					path = draw.draw(one, two, three, style, tmpBackPic, count);
				} else {
					ThreeDraw draw = (ThreeDraw) tool.newInstance();
					draw.setStorageService(storageService);
					path = draw.draw(one, two, three, tmpBackPic, count);
				}
			} else if (two != null) {
				if (style != null) {
					TwoStyleDraw draw = (TwoStyleDraw) tool.newInstance();
					draw.setStorageService(storageService);
					path = draw.draw(one, two, style, tmpBackPic, count);
				} else {
					TwoDraw draw = (TwoDraw) tool.newInstance();
					draw.setStorageService(storageService);
					path = draw.draw(one, two, tmpBackPic, count);
				}

			} else if (one != null) {
				if (style != null) {
					OneStyleDraw draw = (OneStyleDraw) tool.newInstance();
					draw.setStorageService(storageService);
					path = draw.draw(one, style, tmpBackPic, count);
				} else {
					OneDraw draw = (OneDraw) tool.newInstance();
					draw.setStorageService(storageService);
					path = draw.draw(one, tmpBackPic, count);
				}
			}

			return new ReMsg(ReCode.OK, path);
		} catch (Exception e) {
			log.error("Five " + tool.getName() + " err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public static void main(String[] args) {
	}

}
