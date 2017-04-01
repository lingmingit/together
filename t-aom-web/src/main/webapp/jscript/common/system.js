/********************************************************/
/*文件名称：               system.js                                  */
/*功能描述：基于系统通用功能性操作的函数集锦，以供常规性通用性的方式使用                             */
/*制作日期：2014-10-31       版本号:V1.0                     */
/*制  作  者： LingMin                                    */
/********************************************************/

/**
 * 向JS的String对象中增加字符串串接功能<p>
 * @param str 要串接的字符串<br>
 * @returns 串接后的字符串<br>
 */
String.prototype.join = function(str){
	return this + str;
};

/**
 * 向JS的String对象中增加去掉字符串的全部空格<p>
 */
String.prototype.Trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
};

/**
 * 向JS的String对象中增加去掉字符串的左边空格<p>
 */
String.prototype.LTrim = function() {
	return this.replace(/(^\s*)/g, "");
};

/**
 * 向JS的String对象中增加去掉字符串的右边空格<p>
 */
String.prototype.RTrim = function() {
	return this.replace(/(\s*$)/g, "");
};

/**
 * 向JS的String对象中增加获取字符串的字节长度<p>
 * @returns 字符串的字节长度<br>
 */
String.prototype.byteLength = function() {
	return str.replace(/[^\x00-\xff]/gi,'xx').length;
};

/**
 * 全盘替换字符串中指定的字符信息<p>
 * @param replaced 被替换的字符<br>
 * @param replace  将替换的字符<br>
 */
String.prototype.replaceAll = function(replaced, replace) {
	return this.replace(new RegExp(replaced, 'g'), replace);
};

/**
 * 判断当前字符串为空<p>
 * @param str 字符串<br>
 */
function isEmpty(str) {
	return str == null || str == '';
}

/**
 * 判断当前字符串为空<p>
 * @param str 字符串<br>
 */
function isNotEmpty(str) {
	return !isEmpty(str);
}

/**
 * 获取工程的相对路径<p>
 */
function getWebRootPath() {
	//获取提交路径
	var temp = window.location.pathname;
	//截取工程路径名
	var path = temp.substring(0 ,temp.indexOf("/" , 2));
	if(path.indexOf("/") == -1){
		path = "/"+path;
	}
	return path;
}

/**
 * 将指定的URL设定为IE的首页<p>
 * @param obj IE对象<br>
 * @param url URL字符串<br>
 */
function setHomePage(obj, url) {
	try {
		obj.style.behavior = 'url' + '(#default#homepage)';
		obj.setHomePage(url);
	} catch (e) {
		if (window.netscape) {
			try {
				netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
			} catch (e) {
				alert("抱歉！您的浏览器不支持直接设为首页。请在浏览器地址栏输入“about:config”并回车然后将[signed.applets.codebase_principal_support]设置为“true”，点击“加入收藏”后忽略安全提示，即可设置成功。");
			}
			var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
			prefs.setCharPref('browser.startup.homepage', url);
		}
	}
}

/**
 * 显示指定的DIV控件<p>
 * @param div DIV索引值<br>
 */
function slideOpenDiv(div){
	 $("#"+div).stop(true,true).slideDown();
}


/**
 * 关闭指定的DIV控件<p>
 * @param div DIV索引值<br>
 */
function slideCloseDiv(div){
    $("#"+div).stop(true,true).slideUp();
}

/**
 * 将参数中的逗号去掉,并转换为Float<p>
 * @param value 要转换的数字<br>
 * @param num 保留位数<br>
 */
function convertStrToFloat(value,num){
	if(isNotEmpty(value) && value != 0){
		var reg=new RegExp(",|￥","g");
		value = new String(value);
		value = value.replace(reg,"");
		if(num == null || num == ""){
			value = parseFloat(value);
		}else if (!isNaN(num)){
			value = parseFloat(value).toFixed(num);
		}
	}else{
		value = 0;
	}
	return parseFloat(value);
}

/**
 * 日期字符串比较<p>
 * @param start
 * @param end
 */
function compareDate(start, end, startField, endField) {
	var rtnB = true;
	// 合法性验证
	if (isNotEmpty(start) && isNotEmpty(end) && isNotEmpty(startField) && isNotEmpty(endField)) {
		var startArr = start.split("-");
	    var starttime = new Date(startArr[0], startArr[1], startArr[2]);
	    var startLong = starttime.getTime();

	    var endArr = end.split("-");
	    var endtime = new Date(endArr[0], endArr[1], endArr[2]);
	    var endLong = endtime.getTime();
	    if (startLong >= endLong) {
	    	rtnB = false;
	    	alert('【'.join(startField).join('】不能大于【').join(endField).join('】,请确认后重新输入合法值！'));
	    }
	}
	return rtnB;
}

/**
 * 以居中的方式弹出对话框<p>
 * @param url 页面URL<br>
 * @param name 窗口名称<br>
 * @param width 窗口宽度<br>
 * @param height 窗口高度<br>
 */
function openWin(url, name, width, height) {
	var iTop = (window.screen.availHeight-30-height)/2;
	var iLeft = (window.screen.availWidth-10-width)/2;
	window.open(url,name,'height='+height+',innerHeight='+height+',width='+width+',innerWidth='+width+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=no,resizeable=no,location=no,status=no');
}

/**
 * 以MODAL窗口方式打开指定的URL<p>
 * @param url 页面URL<br>
 * @param width 窗口宽度<br>
 * @param height 窗口高度<br>
 */
function openModalWin(url, width, height, isScorll) {
	return window.showModalDialog(getWebRootPath() + url, 'child', 'dialogHeight:'.join(height).join('px;dialogWidth:').join(width).join('px;center:yes;status:no;scroll:').join(isScorll ? 'yes' : 'no'));
}

/**
 * 以Windows.open的方式弹出指定的窗口<p>
 * @param url 弹出窗口的路径<br>
 * @param name 窗口的显示名称<br>
 * @param width 弹出窗口的宽度<br>
 * @param height 弹出窗口的高度<br>
 * @param left 弹出窗口距左边的距离<br>
 * @param top  弹出窗口距上边的距离<br>
 * @param isScroll 是否显示滚动条<br>
 */
function openWindow(url, name, width, height, left, top, isScroll) {
	// 当输入参数left、top为空或0时，则居中显示弹出窗口
	if (left <= 0 || top <= 0) {
		left = (screen.availWidth - width) / 2;
		top = (screen.height - height) / 2;
	}
	// 弹出指定窗口
	window.open(
		url, "_blank", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars="
		.join(isScroll? "yes" : "no").join(",resizable=no,width=").join(width).join(",height=")
		.join(height).join(",left=").join(left).join(",top=").join(top)
	);

};