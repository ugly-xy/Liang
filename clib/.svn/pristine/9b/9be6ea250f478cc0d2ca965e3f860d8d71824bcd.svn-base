package com.we.common.utils;

import java.util.ArrayList;
import java.util.List;

public class InterestsUtil {
	private static List<Object> inter = new ArrayList<Object>();
	static {
		class Interests {
			private String cate;
			private int cateId;
			private String list;

			public String getCate() {
				return cate;
			}

			public void setCate(String cate) {
				this.cate = cate;
			}

			public int getCateId() {
				return cateId;
			}

			public void setCateId(int cateId) {
				this.cateId = cateId;
			}

			public String getList() {
				return list;
			}

			public void setList(String list) {
				this.list = list;
			}

			public Interests(String cate, int cateId, String list) {
				super();
				this.cate = cate;
				this.cateId = cateId;
				this.list = list;
			}

			public Interests() {
				super();
			}
		}
		Interests xingge = new Interests("你的性格介绍?", 1,
				"幽默,乐观,低调,善良,完美主义,逗比,阳光,夜猫子,偏执狂,温柔,张扬,浪漫,八卦,中二,宅,感性,内向,吃货,萌,小清新,理性,铲屎官,肌肉,文艺,前卫,外貌协会,胆小,矫情,耿直,呆,睡不醒");
		Interests aihao = new Interests("你的兴趣爱好?", 2,
				"王者荣耀,泡吧,LOL,动漫,摄影,写作,烘培,手工,二次元,球迷,旅行,看电影,酒,桌游,游戏,自驾,收藏,逛街,NBA,绿植,麻将,绘画,追剧,唱歌,看书");
		Interests yundong = new Interests("你喜欢的运动?", 3,
				"足球,篮球,乒乓球,网球,台球,垂钓,羽毛球,游泳,瑜伽,跆拳道,射箭,武术,高尔夫,骑行,爬山,跑步,赛车,露营,攀岩,舞蹈,滑板,田径,徒步,滑雪");
		inter.add(xingge);
		inter.add(aihao);
		inter.add(yundong);
	}

	public static List<Object> getInterests() {
		return inter;
	}
}
