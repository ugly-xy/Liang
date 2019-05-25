package com.zb.common.mongo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.zb.common.utils.JSONUtil;
import com.zb.models.User;

public class DboUtil {

	public static String getString(DBObject dbo, String field) {
		if (null != dbo) {
			Object obj = dbo.get(field);
			if (null != obj) {
				return obj.toString();
			}
		}
		return null;
	}

	public static <T> T get(DBObject dbo, String field, Class<T> c) {
		if (null != dbo) {
			Object obj = dbo.get(field);
			if (null != obj) {
				return (T) obj;
			}
		}
		return null;
	}

	public static Boolean getBool(DBObject dbo, String field) {
		String s = getString(dbo, field);
		if (null != s) {
			return Boolean.parseBoolean(s);
		}
		return null;
	}

	public static Integer getInt(DBObject dbo, String field) {
		String s = getString(dbo, field);
		if (null != s) {
			return Integer.parseInt(s);
		}
		return null;
	}

	public static Integer tryGetInt(DBObject dbo, String field) {
		String s = getString(dbo, field);
		if (null != s) {
			double temp = Double.valueOf(s);
			return (int) temp;
		}
		return null;
	}

	public static Long getLong(DBObject dbo, String field) {
		String s = getString(dbo, field);
		if (null != s) {
			return Long.parseLong(s);
		}
		return null;
	}

	public static Byte getByte(DBObject dbo, String field) {
		String s = getString(dbo, field);
		if (null != s) {
			return Byte.parseByte(s);
		}
		return null;
	}

	public static Short getShort(DBObject dbo, String field) {
		String s = getString(dbo, field);
		if (null != s) {
			return Short.parseShort(s);
		}
		return null;
	}

	public static Float getFloat(DBObject dbo, String field) {
		String s = getString(dbo, field);
		if (null != s) {
			return Float.parseFloat(s);
		}
		return null;
	}

	public static Double getDouble(DBObject dbo, String field) {
		String s = getString(dbo, field);
		if (null != s) {
			return Double.parseDouble(s);
		}
		return null;
	}

	public static <T> T toBean(DBObject dbo, Class<T> c) {
		if (null == dbo) {
			return null;
		}
		return JSONUtil.jsonToBean(dbo.toString(), c);
	}

	public static DBObject beanToDBO(Object o) {
		return (DBObject) JSON.parse(JSONUtil.beanToJson(o));
	}

	public static <T> T db2Bean(DBObject dbObject, Class<T> c) throws InstantiationException, IllegalAccessException, InvocationTargetException {
		T t = c.newInstance();
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			String varName = field.getName();
			// System.out.println(varName);
			Object object = dbObject.get(varName);
			if (object != null) {
				BeanUtils.setProperty(t, varName, object);
			}
		}
		Class<?> cC = c;
		while (true) {
			Class<?> parentClass = cC.getSuperclass();
			// System.out.println(parentClass.getName());
			if ("java.lang.Object".equals(parentClass.getName())) {
				break;
			}

			Field[] fields2 = parentClass.getDeclaredFields();
			for (Field field : fields2) {
				String varName = field.getName();
				Object object = dbObject.get(varName);
				if (object != null) {
					BeanUtils.setProperty(t, varName, object);
				}
			}
			cC = parentClass;
		}
		return t;
	}

	// /*
	// * wangzc dbobject to bean
	// */
	// public static <T> T db2Bean(DBObject dbObject, T bean) {
	//
	// Field[] fields = bean.getClass().getDeclaredFields();
	// for (Field field : fields) {
	// String varName = field.getName();
	// Object object = dbObject.get(varName);
	// if (object != null) {
	// try {
	// BeanUtils.setProperty(bean, varName, object);
	// } catch (Exception e) {
	// throw new RuntimeException("转换失败,JSON:" + dbObject + " !!!END!!!", e);
	// }
	// }
	// }
	// AbstractDocument document = new AbstractDocument();
	// Field[] fields2 = document.getClass().getDeclaredFields();
	// for (Field field : fields2) {
	// String varName = field.getName();
	// Object object = dbObject.get(varName);
	// if (object != null) {
	// try {
	// BeanUtils.setProperty(bean, varName, object);
	// } catch (Exception e) {
	// throw new RuntimeException("转换失败,JSON:" + dbObject + " !!!END!!!", e);
	// }
	// }
	// }
	// return bean;
	// }

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, InvocationTargetException {
		User u = new User();
		u.set_id(111L);
		u.setNickname("aaa");
		DBObject dbo = DboUtil.beanToDBO(u);
		User u2 = DboUtil.db2Bean(dbo, User.class);
		System.out.println(u2.get_id() + " " + u2.getNickname() + " " + u2.getCoin());

//		Task t = new Task();
//		t.set_id("aaa");
//		t.setUid(12123);
//		TaskModel tm = new TaskModel();
//		List<TaskModel> ls = new ArrayList<TaskModel>();
//		t.setTasks(ls);
//		DBObject dbo2 = DboUtil.beanToDBO(t);
//		Task t2 = DboUtil.db2Bean(dbo2, Task.class);
		//System.out.println(t2.get_id() + " " + t2.getUid() + " " + t2.getTasks());
		// Map m = new HashMap();
		// m.put("a", 1111);
		// m.put("b", 2);
		// DBObject dbo = new BasicDBObject("m", m);
		// dbo.put("int", 123.1);
		// Map cm = DboUtil.get(dbo, "m", Map.class);
		// System.out.println(cm.getInt("a"));
		// System.out.println(DboUtil.get(dbo, "int", Integer.class));
		// System.out.println(DboUtil.tryGetInt(dbo, "int"));
		// System.out.println(DboUtil.getInt(dbo, "int"));
	}

}
