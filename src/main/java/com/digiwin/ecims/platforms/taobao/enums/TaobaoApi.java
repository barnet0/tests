package com.digiwin.ecims.platforms.taobao.enums;

public interface TaobaoApi {
	public static class tb {
		public static class trade {
			public static String getList = "taobao.tb.trade.getList";
			public static String getListById = "taobao.tb.trade.getListById";
			public static String getCount = "taobao.tb.trade.getCount";
			public static String getCountById = "taobao.tb.trade.getCountById";
			public static String getDetail = "taobao.tb.trade.getDetail";
		}

		public static class refund {
			public static String getList = "taobao.tb.refund.getList";
			public static String getListById = "taobao.tb.refund.getListById";
			public static String getCount = "taobao.tb.refund.getCount";
			public static String getCountById = "taobao.tb.refund.getCountById";
			public static String getDetail = "taobao.tb.refund.getDetail";
		}
	}

	public static class fx {
		public static class trade {
			public static String getList = "taobao.fx.trade.getList";
			public static String getListById = "taobao.fx.trade.getListById";
			public static String getCount = "taobao.fx.trade.getCount";
			public static String getCountById = "taobao.fx.trade.getCountById";
			public static String getDetail = "taobao.fx.trade.getDetail";
		}

		public static class refund {
			public static String getList = "taobao.fx.refund.getList";
			public static String getListById = "taobao.fx.refund.getListById";
			public static String getCount = "taobao.fx.refund.getCount";
			public static String getCountById = "taobao.fx.refund.getCountById";
			public static String getDetail = "taobao.fx.refund.getDetail";
		}
	}

	public static class item {
		public static String getList = "taobao.item.getList";
		public static String getListById = "taobao.item.getListById";
		public static String getCount = "taobao.item.getCount";
		public static String getCountById = "taobao.item.getCountById";
		public static String getDetail = "taobao.item.getDetail";
	}
}
