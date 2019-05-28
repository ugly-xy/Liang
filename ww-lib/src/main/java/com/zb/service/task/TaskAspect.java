package com.zb.service.task;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zb.models.usertask.Task;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(TaskAspects.class)
public @interface TaskAspect {
	public enum TaskType {
		ADDFRIENDS(Task.ADD_FRIEND), 
		GIVEFLOWER(Task.FLOWER), 
		UPLOADCOVER(Task.UPLOADING_COVER), 
		BINDPHONE(Task.BINDING_PHONE), 
		SHARE(Task.SHARE_QQWECHAT),
		PRAISE(Task.ITUNES_5_STAR),
		DRAWSOMETHING(Task.DROWSTH), 
		ISUNDER(Task.UNDERCOVER_ISUNDER), 
		NOTUNDER(Task.UNDERCOVER_NOTUNDER), 
		DICE(Task.DICE),
		PAYMONEY(Task.PAY_MONEY),;
//		CRICLEPUBLISH(Task.CRICLE_PUBLISH), 
//		CRICLECOMMENT(Task.CRICLE_COMMENT), 
//		CRICLEBECOMMENT(Task.CRICLE_BE_COMMENT),
//		CRICLESHARE(Task.CRICLE_SHARE),;
		private TaskType(int[][] type) {
			this.type = type;
		}
		int[][] type;
		public int[][] getType() {
			return type;
		}
	}
	TaskType value();
}

