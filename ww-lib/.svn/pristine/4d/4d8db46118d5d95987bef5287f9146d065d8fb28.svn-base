package com.zb.service.task;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.VipMap;
import com.zb.service.BaseService;
import com.zb.service.UserService;
import com.zb.service.article.ArticleService;
import com.zb.service.usertask.UserTaskService;

@Aspect
@Component
public class TaskInterceptor extends BaseService {

	@Autowired
	UserTaskService userTaskService;

	@Autowired
	ArticleService articleService;

	@Autowired
	UserService userService;

	@AfterReturning(pointcut = "@annotation(ta)", returning = "ret")
	public void jobsDone(JoinPoint jp, TaskAspect ta, Object ret) {
		ReMsg rm = (ReMsg) ret;
		if (rm.getCode() == ReCode.OK.reCode() || rm.getCode() == ReCode.ORDER_OPENED.reCode()) {
			userTaskService.doUserTask(super.getUid(), ta.value().getType(), 1);
		}
	}

	@AfterReturning(pointcut = "@annotation(tas)", returning = "ret")
	public void jobsDone(JoinPoint jp, TaskAspects tas, Object ret) {
		ReMsg rm = (ReMsg) ret;
		if (rm.getCode() == ReCode.OK.reCode() || rm.getCode() == ReCode.ORDER_OPENED.reCode()) {
			TaskAspect[] arr = tas.value();
			for (int i = 0; i < arr.length; i++) {
				userTaskService.doUserTask(super.getUid(), arr[i].value().getType(), 1);
			}
		}
	}

	@Around("execution(public * com.zb.service.UserService.changePoint(long,int,int,long))")
	public void vipPointBuff(ProceedingJoinPoint jp) throws Throwable {
		Object[] args = jp.getArgs();
		int point = (int) jp.getArgs()[2];
		long uid = (long) jp.getArgs()[0];
		Integer vip = DboUtil.getInt(userService.findById(uid), "vip");
		double buff = VipMap.getBuff(null == vip ? 0 : vip);
		point += point * buff;
		jp.getArgs()[2] = point;
		jp.proceed(args);
	}

}
