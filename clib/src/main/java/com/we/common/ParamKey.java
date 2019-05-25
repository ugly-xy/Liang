package com.we.common;

public abstract interface ParamKey {
	public static abstract interface In {
		public static final String page = System.getProperty("rest.page",
				"page");
		public static final String callback = System.getProperty(
				"rest.callback", "callback");
		public static final String size = System.getProperty("rest.size",
				"size");
		public static final int PAGE_DEFAULT = 1;
		public static final int SIZE_DEFAULT = Integer.getInteger(
				"default.size", 6).intValue();
		public static final int SIZE_MAX_ALLOW = 200;
	}

	public static abstract interface Out {
		public static final String code = System.getProperty("rest.code",
				"code");
		public static final String data = System.getProperty("rest.data",
				"data");
		public static final String rows = System.getProperty("rest.rows",
				"rows");
		public static final String pages = System.getProperty("rest.pages",
				"pages");
		public static final String exec = "exec";
		public static final String msg = "msg";
		public static final Integer SUCCESS = Integer.valueOf(1);
	}
}
