package com.together.common.base;

/**
 * 通用工具常量定义类<p>
 * @author LingMin 
 * @date 2014-07-22<br>
 * @version 1.0<br>
 */
public final class CommonConstants {
	/** EXCEL文件版本号 **/
	public static final String EXCEL_VERSION_2003 = "2003";
	public static final String EXCEL_VERSION_2007 = "2007";
	/**身份证号码的校验位**/
	public static final int[] IDCardVi = {1,0,'X',9,8,7,6,5,4,3,2};
	/**身份证号码的加权因子**/
	public static final int[] IDCardWi = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1};
	/** 日期格式化样式HASHMAP对象 **/
	public static final java.util.Map<String, String> DATEMAP = new java.util.HashMap<String, String>();
	static {
		DATEMAP.put("^[1-2]\\d{3}[0-1]\\d{1}[0-3]\\d{1}$", "yyyyMMdd");
		DATEMAP.put("^[1-2]\\d{3}-[0-1]\\d{1}-[0-3]\\d{1}$", "yyyy-MM-dd");
		DATEMAP.put("^[1-2]\\d{3}/[0-1]\\d{1}/[0-3]\\d{1}$", "yyyy/MM/dd");
		DATEMAP.put("^[1-2]\\d{3}年[0-1]\\d{1}月[0-3]\\d{1}日$", "yyyy年MM月dd日");
		DATEMAP.put("^[1-2]\\d{3}[0-1]\\d{1}[0-3]\\d{1}[0-2]\\d{1}[0-6]\\d{1}[0-6]\\d{1}$", "yyyyMMddHHmmss");
		DATEMAP.put("^[1-2]\\d{3}-[0-1]\\d{1}-[0-3]\\d{1} [0-2]\\d{1}:[0-6]\\d{1}:[0-6]\\d{1}$", "yyyy-MM-dd HH:mm:ss");
		DATEMAP.put("^[1-2]\\d{3}/[0-1]\\d{1}/[0-3]\\d{1} [0-2]\\d{1}:[0-6]\\d{1}:[0-6]\\d{1}$", "yyyy/MM/dd HH:mm:ss");
		DATEMAP.put("^[1-2]\\d{3}年[0-1]\\d{1}月[0-3]\\d{1}日 [0-2]\\d{1}时[0-6]\\d{1}分[0-6]\\d{1}秒$", "yyyy年MM月dd日 HH时mm分ss秒");
	}
	/**身份证号码的地区编码**/
	public static final java.util.HashMap<String, String> IDCardCity = new java.util.HashMap<String, String>();
	static {
		IDCardCity.put("11", "北京"); IDCardCity.put("12", "天津");   IDCardCity.put("13", "河北");
		IDCardCity.put("14", "山西"); IDCardCity.put("15", "内蒙古"); IDCardCity.put("21", "辽宁");
		IDCardCity.put("22", "吉林"); IDCardCity.put("23", "黑龙江"); IDCardCity.put("31", "上海");
		IDCardCity.put("32", "江苏"); IDCardCity.put("33", "浙江");   IDCardCity.put("34", "安徽");
		IDCardCity.put("35", "福建"); IDCardCity.put("36", "江西");   IDCardCity.put("37", "山东");
		IDCardCity.put("41", "河南"); IDCardCity.put("42", "湖北");   IDCardCity.put("43", "湖南");
		IDCardCity.put("44", "广东"); IDCardCity.put("45", "广西");   IDCardCity.put("46", "海南");
		IDCardCity.put("50", "重庆"); IDCardCity.put("51", "四川");   IDCardCity.put("52", "贵州");
		IDCardCity.put("53", "云南"); IDCardCity.put("54", "西藏");   IDCardCity.put("61", "陕西");
		IDCardCity.put("62", "甘肃"); IDCardCity.put("63", "青海");   IDCardCity.put("64", "宁夏");
		IDCardCity.put("65", "新疆"); IDCardCity.put("71", "台湾");   IDCardCity.put("81", "香港");
		IDCardCity.put("82", "澳门"); IDCardCity.put("91", "国外");
	}
	/** MD5加密加权因子 **/
	public static final char[] MD5HexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','a', 'b', 'c', 'd', 'e', 'f' };
	/**金额的财务大写**/
	public static final String HanDiviStr[] = new String[]{"","拾","佰","仟","万","拾","佰","仟","亿","拾","佰","仟","万","拾","佰","仟","亿","拾","佰","仟","万","拾","佰","仟" };
	/**数字的大写模式**/
	public static final String HanDigiStr[] = new String[]{"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
	/** 星期常量 **/
	public static final String WEEK_STR_ARRAY[] = new String[]{ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
	/** 特殊字符过滤规则 **/
	public static final String SPECIAL_CHAR = "[`~!#$%^&*()+=|{}':;',\\[\\]<>/?~！『』#￥%……&*（）《》——+|{}【】‘；_：”“’。，、？×～·-]";
	/**SQL防注入字符串**/
	public static final String SQL_INJECTION = "'|like |exec |insert |select |delete |update |union |count |*|%|chr |mid |master |truncate |char |declare |; |or |+|,|=|'";
	/** 国历-星节 **/
	public static String[] LUNAR_WF = {"0520 母亲节", "0716 合作节", "0730 被奴役国家周"};
	/** 农历-前缀 **/
	public final static String[] LUNAR_HD = {"初", "十", "廿", "卅", "正", "腊", "冬", "闰"};
	/** 农历-数字 **/
	public final static String[] LUNAR_NM = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
	/** 农历-天干 **/
	public final static String[] LUNAR_TG = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
	/** 农历-地支 **/
	public final static String[] LUNAR_DZ = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
	/** 农历-属相 **/
	public final static String[] LUNAR_AN = {"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
	/** 农历-节气 **/
	public final static String[] LUNAR_FS = {"小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"};
	/** 农历-节气常量 **/
	public final static int[]    LUNAR_FI = {0, 21208,  42467,  63836, 85337, 107014, 128867, 150921, 173149, 195551, 218072, 240693, 263343, 285989, 308563, 331033, 353350, 375494, 397447, 419210, 440795, 462224, 483532, 504758};
	/** 农历-节日 **/
	public final static String[] LUNAR_LF = {
		"0101*春节、弥勒佛诞", "0106 定光佛诞", "0115 元宵节", "0208 释迦牟尼佛出家",	"0215 释迦牟尼佛涅槃",	"0209 海空上师诞", "0219 观世音菩萨诞",	"0221 普贤菩萨诞", "0316 准提菩萨诞",
		"0404 文殊菩萨诞", "0408 释迦牟尼佛诞",	"0415 佛吉祥日——释迦牟尼佛诞生、成道、涅槃三期同一庆(即南传佛教国家的卫塞节)", "0505 端午节", "0513 伽蓝菩萨诞", "0603 护法韦驮尊天菩萨诞",
		"0619 观世音菩萨成道——此日放生、念佛，功德殊胜", "0707 七夕情人节", "0713 大势至菩萨诞",	"0715 中元节", "0724 龙树菩萨诞", "0730 地藏菩萨诞",	"0815 中秋节", "0822 燃灯佛诞",
		"0909 重阳节", "0919 观世音菩萨出家纪念日", "0930 药师琉璃光如来诞","1005 达摩祖师诞",	"1107 阿弥陀佛诞", "1208 释迦如来成道日，腊八节", "1224 小年", "1229 华严菩萨诞", "0100*除夕"};
	/** 国历-节日 **/
	public final static String[] LUNAR_NF = {
		"0101*元旦", "0214 情人节", "0308 妇女节", "0312 植树节", "0315 消费者权益日", "0401 愚人节", "0501*劳动节", "0504 青年节", "0509 郝维节", "0512 护士节", "0601 儿童节",
		"0701 建党节 香港回归纪念", "0801 建军节", "0808 父亲节", "0816 燕衔泥节", "0909 毛泽东逝世纪念", "0910 教师节", "0928 孔子诞辰", "1001*国庆节", "1006 老人节", "1024 联合国日",
		"1111 光棍节", "1112 孙中山诞辰纪念", "1220 澳门回归纪念", "1225 圣诞节", "1226 毛泽东诞辰纪念"
	};
	/** 农历-通量 **/
	public final static int[] LUNAR_CC = {
		0x4bd8, 0x4ae0, 0xa570, 0x54d5, 0xd260, 0xd950, 0x5554, 0x56af, 0x9ad0, 0x55d2, 0x4ae0, 0xa5b6, 0xa4d0, 0xd250, 0xd295, 0xb54f,
		0xd6a0, 0xada2, 0x95b0, 0x4977, 0x497f, 0xa4b0, 0xb4b5, 0x6a50, 0x6d40, 0xab54, 0x2b6f, 0x9570, 0x52f2, 0x4970, 0x6566, 0xd4a0,
		0xea50, 0x6a95, 0x5adf, 0x2b60, 0x86e3, 0x92ef, 0xc8d7, 0xc95f, 0xd4a0, 0xd8a6, 0xb55f, 0x56a0, 0xa5b4, 0x25df, 0x92d0, 0xd2b2,
		0xa950, 0xb557, 0x6ca0, 0xb550, 0x5355, 0x4daf, 0xa5b0, 0x4573, 0x52bf, 0xa9a8, 0xe950, 0x6aa0, 0xaea6, 0xab50, 0x4b60, 0xaae4,
		0xa570, 0x5260, 0xf263, 0xd950, 0x5b57, 0x56a0, 0x96d0, 0x4dd5, 0x4ad0, 0xa4d0, 0xd4d4, 0xd250, 0xd558, 0xb540, 0xb6a0, 0x95a6,
		0x95bf, 0x49b0, 0xa974, 0xa4b0, 0xb27a, 0x6a50, 0x6d40, 0xaf46, 0xab60, 0x9570, 0x4af5, 0x4970, 0x64b0, 0x74a3, 0xea50, 0x6b58,
		0x5ac0, 0xab60, 0x96d5, 0x92e0, 0xc960, 0xd954, 0xd4a0, 0xda50, 0x7552, 0x56a0, 0xabb7, 0x25d0, 0x92d0, 0xcab5, 0xa950, 0xb4a0,
		0xbaa4, 0xad50, 0x55d9, 0x4ba0, 0xa5b0, 0x5176, 0x52bf, 0xa930, 0x7954, 0x6aa0, 0xad50, 0x5b52, 0x4b60, 0xa6e6, 0xa4e0, 0xd260,
		0xea65, 0xd530, 0x5aa0, 0x76a3, 0x96d0, 0x4afb, 0x4ad0, 0xa4d0, 0xd0b6, 0xd25f, 0xd520, 0xdd45, 0xb5a0, 0x56d0, 0x55b2, 0x49b0,
		0xa577, 0xa4b0, 0xaa50, 0xb255, 0x6d2f, 0xada0, 0x4b63, 0x937f, 0x49f8, 0x4970, 0x64b0, 0x68a6, 0xea5f, 0x6b20, 0xa6c4, 0xaaef,
		0x92e0, 0xd2e3, 0xc960, 0xd557, 0xd4a0, 0xda50, 0x5d55, 0x56a0, 0xa6d0, 0x55d4, 0x52d0, 0xa9b8, 0xa950, 0xb4a0, 0xb6a6, 0xad50,
		0x55a0, 0xaba4, 0xa5b0, 0x52b0, 0xb273, 0x6930, 0x7337, 0x6aa0, 0xad50, 0x4b55, 0x4b6f, 0xa570, 0x54e4, 0xd260, 0xe968, 0xd520,
		0xdaa0, 0x6aa6, 0x56df, 0x4ae0, 0xa9d4, 0xa4d0, 0xd150, 0xf252, 0xd520
	};
}
