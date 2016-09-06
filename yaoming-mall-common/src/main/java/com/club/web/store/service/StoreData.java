package com.club.web.store.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.club.web.store.vo.ClassifyVO;
import com.club.web.store.vo.CommentVO;
import com.club.web.store.vo.GoodsDetailVO;
import com.club.web.store.vo.GoodsSimpleVO;
import com.club.web.store.vo.GoodsSpecVO;
import com.club.web.store.vo.OrderGoodsVO;
import com.club.web.store.vo.OrderSimpleVO;

public class StoreData {
	
	public static void main(String[] args) {
		System.out.println(0x0000000010000001L+"");
	}
	
	public static LinkedHashMap<Long, ClassifyVO> classifyMap = new LinkedHashMap<Long, ClassifyVO>();
	public static long CLASSIFY_ID_BEGIN = 0x0000000010000000L;
	public static AtomicLong CLASSIFY_ID = new AtomicLong(CLASSIFY_ID_BEGIN);
	static {
		CLASSIFY_ID.incrementAndGet(); classifyMap.put(CLASSIFY_ID.get(), classify(CLASSIFY_ID.get(), "男装", "images/home_boy.png"));
		CLASSIFY_ID.incrementAndGet(); classifyMap.put(CLASSIFY_ID.get(), classify(CLASSIFY_ID.get(), "女装", "images/home_girl.png"));
		CLASSIFY_ID.incrementAndGet(); classifyMap.put(CLASSIFY_ID.get(), classify(CLASSIFY_ID.get(), "鞋包", "images/home_shoes.png"));
		CLASSIFY_ID.incrementAndGet(); classifyMap.put(CLASSIFY_ID.get(), classify(CLASSIFY_ID.get(), "母婴", "images/home_bady.png"));
		CLASSIFY_ID.incrementAndGet(); classifyMap.put(CLASSIFY_ID.get(), classify(CLASSIFY_ID.get(), "生活", "images/home_life.png"));
		CLASSIFY_ID.incrementAndGet(); classifyMap.put(CLASSIFY_ID.get(), classify(CLASSIFY_ID.get(), "电器", "images/home_electric.png"));
		CLASSIFY_ID.incrementAndGet(); classifyMap.put(CLASSIFY_ID.get(), classify(CLASSIFY_ID.get(), "居家", "images/home_work.png"));
		CLASSIFY_ID.incrementAndGet(); classifyMap.put(CLASSIFY_ID.get(), classify(CLASSIFY_ID.get(), "零食", "images/home_food.png"));
		CLASSIFY_ID.incrementAndGet(); classifyMap.put(CLASSIFY_ID.get(), classify(CLASSIFY_ID.get(), "化妆品", "images/home_boy.png"));
		CLASSIFY_ID.incrementAndGet(); classifyMap.put(CLASSIFY_ID.get(), classify(CLASSIFY_ID.get(), "皮具", "images/home_food.png"));
	}
	
	public static Date discountEndTime = new Date();
	
	public static LinkedHashMap<Long, GoodsDetailVO> goodsMap = new LinkedHashMap<Long, GoodsDetailVO>();
	public static long GOODS_ID_BEGIN = 0x0000000020000000L;
	public static AtomicLong GOODS_ID = new AtomicLong(GOODS_ID_BEGIN);
	static {
		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "奢宠优氧金纯眼霜", number(238), number(65), image(GOODS_ID.get(), "title_02.png"), number("5.0")), specs(spec("规格", "30g/盒")), classify(0x0000000010000009L), imagesTitle(images(GOODS_ID.get(), "title_01.jpg", "title_02.png", "title_03.png", "title_04.png")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), true, false, number(11), number(3511)));
		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "奢宠水漾无痕气垫BB霜", number(168), number(55), image(GOODS_ID.get(), "title_02.png"), number("5.0")), specs(spec("规格", "15g*2(替换芯)")), classify(0x0000000010000009L), imagesTitle(images(GOODS_ID.get(), "title_01.jpg", "title_02.png", "title_03.png")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), true, false, number(23), number(123513)));
		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "莲花胚胎干细胞原生液", number(168), number(50), image(GOODS_ID.get(), "title_02.png"), number("5.0")), specs(spec("规格", "30ml")), classify(0x0000000010000009L), imagesTitle(images(GOODS_ID.get(), "title_01.jpg", "title_02.png")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), true, false, number(32), number(13251)));
		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "莲花胚胎干细胞原液面膜", number(168), number(50), image(GOODS_ID.get(), "title_01.jpg"), number("5.0")), specs(spec("规格", "5片")), classify(0x0000000010000009L), imagesTitle(images(GOODS_ID.get(), "title_01.jpg", "title_02.jpg")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), false, true, number(24), number(1235)));
		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "水光针", number(128), number(38), image(GOODS_ID.get(), "title_01.jpg"), number("5.0")), specs(spec("规格", "1支/10ML")), classify(0x0000000010000009L), imagesTitle(images(GOODS_ID.get(), "title_01.jpg", "title_02.jpg")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), true, false, number(53), number(54)));
		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "液态超声刀", number(288), number(115), image(GOODS_ID.get(), "title_01.jpg"), number("5.0")), specs(spec("规格", "1支/10ML")), classify(0x0000000010000009L), imagesTitle(images(GOODS_ID.get(), "title_01.jpg", "title_02.jpg")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), true, false, number(34), number(2346)));
		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "玫瑰纯露", number(358), number(115), image(GOODS_ID.get(), "title_01.jpg"), number("5.0")), specs(spec("规格", "350ML/盒（大瓶250ml加小瓶100ml)")), classify(0x0000000010000009L), imagesTitle(images(GOODS_ID.get(), "title_01.jpg", "title_02.jpg")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), false, true, number(34), number(5245)));
		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "艾惠普负氢杯", number(1280), number(480), image(GOODS_ID.get(), "title_01.jpg"), number("5.0")), specs(spec("规格", "个")), classify(0x0000000010000009L), imagesTitle(images(GOODS_ID.get(), "title_01.jpg", "title_02.jpg")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), false, true, number(12), number(234)));
		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "福尚康阿胶糕", number(219), number(125), image(GOODS_ID.get(), "title_01.jpg"), number("5.0")), specs(spec("规格", "500g/50个/盒")), classify(0x0000000010000009L), imagesTitle(images(GOODS_ID.get(), "title_01.jpg", "title_02.jpg")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), false, true, number(24), number(2345)));
		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "石棹阿里山茶", number(260), number(117), image(GOODS_ID.get(), "title_01.jpg"), number("5.0")), specs(spec("规格", "150g")), classify(0x0000000010000009L), imagesTitle(images(GOODS_ID.get(), "title_01.jpg", "title_02.jpg")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), true, true, number(623), number(6234)));
		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "冻顶功夫茶", number(285), number(122), image(GOODS_ID.get(), "title_01.jpg"), number("5.0")), specs(spec("规格", "150g")), classify(0x0000000010000009L), imagesTitle(images(GOODS_ID.get(), "title_01.jpg", "title_02.jpg")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), false, true, number(12), number(2346)));
		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "纯古金茯", number(598), number(328), image(GOODS_ID.get(), "title_01.jpg"), number("5.0")), specs(spec("规格", "1kg")), classify(0x0000000010000009L), imagesTitle(images(GOODS_ID.get(), "title_01.jpg", "title_02.jpg")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), true, true, number(231), number(5425)));
		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "上品荷香", number(680), number(378), image(GOODS_ID.get(), "title_01.jpg"), number("5.0")), specs(spec("规格", "1kg")), classify(0x0000000010000009L), imagesTitle(images(GOODS_ID.get(), "title_01.jpg", "title_02.jpg")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), true, false, number(24), number(234623)));
		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "手筑茯砖", number(398), number(248), image(GOODS_ID.get(), "title_01.jpg"), number("5.0")), specs(spec("规格", "1kg")), classify(0x0000000010000009L), imagesTitle(images(GOODS_ID.get(), "title_01.jpg", "title_02.jpg")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), true, true, number(3), number(2345)));
		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "云顶茯茶", number(368), number(248), image(GOODS_ID.get(), "title_01.jpg"), number("5.0")), specs(spec("规格", "800g")), classify(0x0000000010000009L), imagesTitle(images(GOODS_ID.get(), "title_01.jpg", "title_02.jpg")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), false, true, number(123), number(6246)));
		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "日出东方", number(368), number(248), image(GOODS_ID.get(), "title_01.jpg"), number("5.0")), specs(spec("规格", "600g")), classify(0x0000000010000009L), imagesTitle(images(GOODS_ID.get(), "title_01.jpg", "title_02.jpg")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), false, false, number(23), number(234)));
		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "湖湘名片", number(698), number(460), image(GOODS_ID.get(), "title_01.jpg"), number("5.0")), specs(spec("规格", "800g")), classify(0x0000000010000009L), imagesTitle(images(GOODS_ID.get(), "title_01.jpg", "title_02.jpg")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), false, true, number(42), number(624623)));
		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "九五天尖", number(1280), number(668), image(GOODS_ID.get(), "title_01.jpg"), number("5.0")), specs(spec("规格", "300g")), classify(0x0000000010000009L), imagesTitle(images(GOODS_ID.get(), "title_01.jpg", "title_02.jpg")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), true, false, number(123), number(234)));
		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "大道茯茶", number(208), number(138), image(GOODS_ID.get(), "title_01.jpg"), number("5.0")), specs(spec("规格", "800g")), classify(0x0000000010000009L), imagesTitle(images(GOODS_ID.get(), "title_01.jpg", "title_02.jpg")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), true, false, number(321), number(23463)));
		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "伟人福", number(1980), number(1298), image(GOODS_ID.get(), "title_01.jpg"), number("5.0")), specs(spec("规格", "900g")), classify(0x0000000010000009L), imagesTitle(images(GOODS_ID.get(), "title_01.jpg", "title_02.jpg")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), true, true, number(421), number(345)));

		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "HEYDAY INSPIRED GOLDEN SLIVER IV 手工打造AJ4经典复刻鞋金银靴", number(4790), number(3599), image(GOODS_ID.get(), "title_01.jpg"), number("5.0")), specs(spec("颜色", "金色", "银色")), classify(0x000000001000000AL), imagesTitle(images(GOODS_ID.get(), "title_01.jpg", "title_02.jpg")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), true, true, number(41), number(267)));
		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "HEYDAY INSPIRED METAL RED VI 手工打造AJ6经典复刻红色电镀鞋", number(2499), number(1998), image(GOODS_ID.get(), "title_01.jpg"), number("5.0")), specs(spec("颜色", "红色")), classify(0x000000001000000AL), imagesTitle(images(GOODS_ID.get(), "title_01.jpg")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), true, true, number(21), number(7246)));
		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "HEYDAY PLEASURE BLACK SHELL III 手工打造鞋子一字拖鞋植鞣皮", number(2499), number(680), image(GOODS_ID.get(), "title_01.jpg"), number("5.0")), specs(spec("颜色", "白", "黑", "金", "黑白")), classify(0x000000001000000AL), imagesTitle(images(GOODS_ID.get(), "title_01.jpg", "title_02.jpg", "title_03.jpg", "title_04.jpg")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), true, true, number(47), number(2452)));
		GOODS_ID.incrementAndGet(); goodsMap.put(GOODS_ID.get(), detailGoods(simpleGoods(GOODS_ID.get(), "HEYDAY PLEASURE SHELL 手工打造经典复刻经典肌色中靴", number(4999), number(2599), image(GOODS_ID.get(), "title_01.jpg"), number("5.0")), specs(spec("颜色", "白")), classify(0x000000001000000AL), imagesTitle(images(GOODS_ID.get(), "title_01.jpg")), imagesDetail(images(GOODS_ID.get(), "detail_01.jpg")), true, true, number(4), number(45)));
	}

	public static enum OrderStatus{
		UNKNOW("错误"),
		WAIT_FOR_PAY("待付款"),
		WAIT_FOR_DELIVERY("待发货"),
		WAIT_FOR_RECEIVE("待收货"),
		WAIT_FOR_EVALUATE("待评价");
		
		private String chineseName;
		private OrderStatus(String chineseName){
			this.chineseName = chineseName;
		}
		public String getChineseName() {
			return chineseName;
		}
	}
	
	public static LinkedHashMap<Long, OrderSimpleVO> orderMap = new LinkedHashMap<Long, OrderSimpleVO>();
	public static long ORDER_ID_BEGIN = 0x0000000030000000L;
	public static AtomicLong ORDER_ID = new AtomicLong(ORDER_ID_BEGIN);
	static {
		ORDER_ID.incrementAndGet(); orderMap.put(ORDER_ID.get(), order(ORDER_ID.get(), OrderStatus.WAIT_FOR_PAY, orderGoods(GOODS_ID_BEGIN+1)));
		ORDER_ID.incrementAndGet(); orderMap.put(ORDER_ID.get(), order(ORDER_ID.get(), OrderStatus.WAIT_FOR_DELIVERY, orderGoods(GOODS_ID_BEGIN+2)));
		ORDER_ID.incrementAndGet(); orderMap.put(ORDER_ID.get(), order(ORDER_ID.get(), OrderStatus.WAIT_FOR_RECEIVE, orderGoods(GOODS_ID_BEGIN+3)));
		ORDER_ID.incrementAndGet(); orderMap.put(ORDER_ID.get(), order(ORDER_ID.get(), OrderStatus.WAIT_FOR_EVALUATE, orderGoods(GOODS_ID_BEGIN+4)));
		ORDER_ID.incrementAndGet(); orderMap.put(ORDER_ID.get(), order(ORDER_ID.get(), OrderStatus.WAIT_FOR_EVALUATE, orderGoods(GOODS_ID_BEGIN+5), orderGoods(GOODS_ID_BEGIN+6, 2)));
	}
	
	public static ArrayList<CommentVO> commentList = new ArrayList<CommentVO>();
	public static long COMMENT_ID_BEGIN = 0x0000000040000000L;
	public static AtomicLong COMMENT_ID = new AtomicLong(COMMENT_ID_BEGIN);
	static{
		COMMENT_ID.incrementAndGet(); commentList.add(comment(COMMENT_ID.get(), head("Yvonne.png"), "Yvonne", number(5), "2016-03-05 19:32", "真心不错，推荐购买！"));
		COMMENT_ID.incrementAndGet(); commentList.add(comment(COMMENT_ID.get(), head("Sigrid.png"), "Sigrid", number(5), "2016-03-03 17:42", "买买买！"));
		COMMENT_ID.incrementAndGet(); commentList.add(comment(COMMENT_ID.get(), head("Roxanne.png"), "Roxanne", number(5), "2016-02-01 02:22", "不错，推荐购买！"));
		COMMENT_ID.incrementAndGet(); commentList.add(comment(COMMENT_ID.get(), head("Valentina.png"), "Valentina", number(5), "2016-01-03 13:25", "推荐购买！"));
		COMMENT_ID.incrementAndGet(); commentList.add(comment(COMMENT_ID.get(), head("Lynn.png"), "Lynn", number(5), "2016-01-02 15:42", "嗯，真心不错，推荐购买！"));
		COMMENT_ID.incrementAndGet(); commentList.add(comment(COMMENT_ID.get(), head("Lilith.png"), "Lilith", number(5), "2016-01-01 12:22", "买！"));
		COMMENT_ID.incrementAndGet(); commentList.add(comment(COMMENT_ID.get(), head("Lilith.png"), "Lilith", number(5), "2015-09-01 13:52", "很好！"));
	}
	
	public static CommentVO comment(long id, String headImage, String name, BigDecimal rate, String datetime, String comment){
		return new CommentVO(id, headImage, name, rate, datetime, comment);
	}
	
	public static OrderGoodsVO orderGoods(long goodsId){
		return orderGoods(goodsId, 1);
	}
	public static OrderGoodsVO orderGoods(long goodsId, int count){
		GoodsDetailVO gdvo = goods(goodsId);
		return new OrderGoodsVO(gdvo, gdvo.isDiscountGoods() ? gdvo.getDiscount() : gdvo.getPrice(), count);
	}
	public static OrderGoodsVO orderGoods(long goodsId, BigDecimal price, int count){
		return new OrderGoodsVO(goods(goodsId), price, count);
	}
	public static OrderSimpleVO order(long orderId, OrderStatus status, OrderGoodsVO... list){
		return new OrderSimpleVO(orderId, status, list);
	}
	public static BigDecimal number(String s){
		return new BigDecimal(s);
	}
	public static BigDecimal number(long s){
		return new BigDecimal(s);
	}
	public static String[] images(long id, String... names){
		if(names==null)
			return new String[0];
		String[] result = new String[names.length];
		for (int i=0; i<names.length; i++)
			result[i] = image(id, names[i]);
		return result;
	}
	public static String head(String name){
		return "http://115.159.25.170:8080/zuitese/upload.do?getthumb=head/"+name;
	}
	public static String image(long id, String name){
		return "http://115.159.25.170:8080/zuitese/upload.do?getthumb=goods/"+id+"/"+name;
	}
	public static ClassifyVO classify(long classifyId, String title, String image){
		return new ClassifyVO(classifyId, title, image);
	}
	public static ClassifyVO classify(long classifyId){
		return classifyMap.get(classifyId);
	}
	public static GoodsSimpleVO simpleGoods(long goodsId, String title, BigDecimal price, BigDecimal discount, String coverImg, BigDecimal rate){
		return new GoodsSimpleVO(goodsId, title, price, discount, coverImg, rate);
	}
	public static GoodsDetailVO detailGoods(GoodsSimpleVO simpleData, List<GoodsSpecVO> specs, ClassifyVO classify, List<String> imagesTitles, List<String> imagesDetail, boolean isRecommend, boolean isHot, BigDecimal monthSell, BigDecimal stock){
		return new GoodsDetailVO(simpleData, specs, classify, imagesTitles, imagesDetail, isRecommend, isHot, monthSell, stock);
	}
	public static GoodsDetailVO goods(long goodsId){
		return goodsMap.get(goodsId);
	}
	public static List<String> imagesTitle(String... images) {
		List<String> list = new ArrayList<String>();
		if(images!=null)
			for (String image : images)
				if(image!=null)
					list.add(image);
		return list;
	}
	public static List<String> imagesDetail(String... images) {
		List<String> list = new ArrayList<String>();
		if(images!=null)
			for (String image : images)
				if(image!=null)
					list.add(image);
		return list;
	}
	public static GoodsSpecVO spec(String name, String... datas){
		List<String> dataList = new ArrayList<String>();
		if(datas!=null)
			for (String data : datas)
				if(data!=null)
					dataList.add(data);
		return new GoodsSpecVO(name, dataList);
	}
	
	public static List<GoodsSpecVO> specs(GoodsSpecVO... specs){
		List<GoodsSpecVO> list = new ArrayList<GoodsSpecVO>();
		if(specs!=null)
			for (GoodsSpecVO gsvo : specs)
				if(gsvo!=null)
					list.add(gsvo);
		return list;
	}
}
