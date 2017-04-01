/********************************************************/
/*文件名称：               message.js                                 */
/*功能描述：错误信息展示功能区                                                                                                                            */
/*制作日期：2014-10-31       版本号:V1.0                     */
/*制  作  者： LingMin                                    */
/********************************************************/

/**
 * 显示业务逻辑层提示信息<p>
 * @param divid 层关键字<br>
 * @param time 显示时长<br>
 */
function displayClientMessage(divid, time) {
	// 以DWR的方式获取业务逻辑层信息
	FrontUtils.getErrorMessageMap(function(data){
		var msg = ""; height = 0;
		if (data != null) {
			// 循环组装错误信息
			for(var key in data) {
				msg += "<li>"+data[key]+"</li>";
				height = height + 20 ;
			}
			// 显示错误信息
			displayDivContent(divid, height, msg);
			// 以固定时长的方式显示信息
			slideToggleDivMessage(divid, time);
			// 刷新验证码标签
			if (typeof($("#authCode")) == 'object') {
				$('#authCode').attr('src', getWebRootPath() + '/captcha.svl?' + Math.random());
			}
		}
	});
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
 * 根据指定的时间显示指定的DIV控件<p>
 * @param divid DIV索引值<br>
 * @param time 显示时长<br>
 */
function slideToggleDivMessage(divid ,time) {
	$("#" + divid).slideDown();
	$("#" + divid).stop(true,true).delay(time).slideUp(); 
}

/**
 * 显示后台错误信息<p>
 * @param div 错误信息DIV索引值<br>
 * @param height 信息显示高度<br>
 * @param message 组装后的错误信息<br>
 */
function displayDivContent(divid, height, message) {
	$("#" + divid).css("height", height);
	$("#" + divid).html(message);
}

