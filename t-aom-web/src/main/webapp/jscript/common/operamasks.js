/********************************************************/
/*文件名称：               AomUtils.js                                */
/*功能描述：基于AOM表现层的通用工具类                                                                                                         */
/*制作日期：2014-10-31       版本号:V1.0                     */
/*制  作  者： LingMin                                    */
/********************************************************/
/**
 * 基础资料状态格式化函数<p>
 */
function boolean_check_Formatter(value) {
	var path = getWebRootPath();
	//if (null != value) {
		if(value == "true" || value == 1 || value == "1"){
			value = "checked.gif";
		} else {
			value = "unchecked.gif";
		}
		return "<img src='" + path + "/console/images/" + value +"'/>";
	//}
}

/**
 * 格式化列表的序号行<p>
 */
function rowNumberFormatter(v, m , r, rIndex) {
	return '<span>' + (rIndex+1) + '</span>';
}

/**
 * 合计标题生成函数<p>
 * @param v 值<br>
 * @param params 参数<br>
 * @param data 数据<br>
 * @returns {String} 返回字符串<br>
 */
function totalCount(v, params, data) {                   
	params.style  += 'color:red;';                   
	return "合计:";            
}

/**
 * 某列求和，保留位数为2位(针对金额)<p>
 */
function sumAmount(v, params, data) {
	params.style  += 'color:green;';                
	if(typeof(v) == 'string'){
		v = new Number(convertStrToFloat(v,2));
	}else{
		v = new Number(convertStrToFloat(v,2));
	}
	return  v.toFixed(2);
}

/**
 * 合计分录某列的值<p>
 * @param store 数据对象<br>
 * @param fieldName 字段名称<br>
 * @parem scale 小数位数<br>
 * @returns 返回值<br>
 */
function sumGridColumn(store, fieldName, scale) {
	var totalAmount = 0;
	for(var i = 0; i < store.getCount() ; i++) {
		var perAmount = store.getAt(i).get(fieldName);
		if (isNotEmpty(perAmount)) {
			perAmount = convertStrToFloat(perAmount, scale);
			totalAmount = parseFloat(totalAmount) + parseFloat(perAmount);
		}
	}
	return totalAmount.toFixed(scale);
}

/**
 * 格式化分录附件控件<p>
 * @param value 值<br>
 * @param metadata 样式属性对象<br>
 * @param record 数据行对象<br>
 * @param index 行索引<br>
 */
function formatDownLoadURL(value, metadata, record, index) {
	var rtnS = "";
	// 构造附件管理URL
	var key = record.get("id");
	if  (isEmpty(key)) key = record.get("idColumn");
	var url = document.location.href.split('console/')[0];
	if (isNotEmpty(key) && key.indexOf("INIT") < 0) {
		rtnS = "<a href='#' onclick=\"openWin('" + url + "console/system/bd/downloadListUI.faces?DOWNLOAD_PARENT_KEY=" + key + "&ENABLE_UPLOAD_ATTACHMENT=TRUE" + "','附件管理',510,275);\">上传|下载</a>";
	}
	return rtnS;
}