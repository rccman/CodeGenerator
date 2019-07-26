var ajaxParam = {};//全局变量
var tempCallBack;
var beforeRequest = function(){
	return true;
};
var afterRequest = function(responseText, statusText){
	if('success'!=statusText){
		//alert("请求失败，疑似404错误");
	}else{
		tempCallBack(responseText);
	}
};
var completeCallBack = function(){
//	layer.close(layer_shade);
};
var afterRequestError = function(jqXHR, textStatus) {
	var statusCode = jqXHR.status;
	//layer.alert("错误码：" + statusCode + ",  错误信息：" + textStatus, 2);
};

var settings = {
		beforeSend: beforeRequest,  //提交前的回调函数  
		success: afterRequest,      	//提交后的回调函数  
		error: afterRequestError,      	//提交后的回调函数  
		complete:completeCallBack,
		contentType: "application/json; charset=utf-8",
		dataType: "json",           	//html(默认), xml, script, json...接受服务端返回的类型  
		timeout: 180000,  	        //超时时间
		async: false,				//默认关闭异步执行，设置为同步执行
};



/**
 * ajaxPost提交，默认为同步，可指定同步或异步
 */
var ajaxPost = function(action,params,callBack,isAsync,completeCallBack,isLoading){
	
	if('object'==typeof(params)){
		//传入json参数
		settings.data = JSON.stringify(params);
	}else{
		//serialize方法会自动进行url编码，此处需要解码
		settings.data = convert2Json(params);
		//settings.contentType = "application/x-www-form-urlencoded; charset=utf-8";
		//传入表单formId
	}
	tempCallBack = callBack;
	settings.type = "post";
	settings.url = CRMUrl(action);
	settings.contentType = "application/json; charset=utf-8";
	settings.complete = completeCallBack;
	settings.beforeSend = function(){
		if(!isAsync){
//		layer_shade = layer.msg('处理中。。。', {
//				  icon: 16
//				  ,shade: 0.3
//			});
		}
	}
	if(isAsync!=undefined){
		settings.async = isAsync;
	}
	$.ajax(settings);  
};

/**
 * 异步提交contentType为"application/x-www-form-urlencoded; charset=utf-8";的数据
 */
var ajaxPostParam = function(action,params,callBack,isAsync){
	
	tempCallBack = callBack;
	
	settings.data =params;
	settings.type = "post";
	settings.url = CRMUrl(action);
	settings.contentType = "application/x-www-form-urlencoded; charset=utf-8";
	async: false				//默认关闭异步执行，设置为同步执行

	
	if(isAsync!=undefined){
		settings.async = isAsync;
	}
	$.ajax(settings);
};


/**
 * ajaxGet请求，默认为异步请求
 */
var ajaxGet = function(action,callBack,isAsync){
	
	tempCallBack = callBack;
	settings.type = "get";
	settings.url = CRMUrl(action);
	if(isAsync!=undefined){
		settings.async = isAsync;
	}
	$.ajax(settings);   
};


//将表单提交的数据转换为json
function convert2Json(param){
	
	var formData = decodeURIComponent($("#"+param).serialize().replaceAll(/\+/g,"%20"),true);
	
	var jsonStr = '{';
	var params = formData.split("&");
	
	$.each(params,function(index,element){
		var kv = element.split("=");
		var key = kv[0];
		var value = kv[1];
		if($("#"+kv[0]).hasClass("TimeStamp")  && $.isNotBlank(value)){
			if("--"!=value){
				jsonStr += '"' + kv[0] + '"' + ':' + '"' + kv[1].replace("+", " ").toTimetamp() + '",';
			}
		}else{
			jsonStr += '"' + kv[0] + '"' + ':' + '"' + kv[1] + '",';
		}
	});
	jsonStr = jsonStr.substring(0, jsonStr.length -1);
	
	jsonStr += '}';
	
	return jsonStr;
};

//将表单提交的数据转换为js对象
function convert2Obj(param){
	
	var formData = decodeURIComponent($("#"+param).serialize().replaceAll(/\+/g,"%20"),true);
	
	var obj = {};
	var params = formData.split("&");
	
	$.each(params,function(index,element){
		var kv = element.split("=");
		var key = kv[0];
		var value = kv[1];
		
		if($("#"+key).hasClass("TimeStamp") && $.isNotBlank(value)){
			if("--"!=value){
				value = value.replace("+", " ").toTimetamp();
			}else{
				value="";
			}
			
		}
		
		obj[key] = value;
		
	});

	return obj;
};

function CRMUrl(action){
	if(action.substring(0,1)=="/"){
	// if(action.substring(0,1)=="/CodeGenerator/"){
		action = action.substring(1,action.length)
	}
	//webRoot暂时为跟路径，如果改变的话此处也要同步
	var webRoot = "/CodeGenerator/";
    var curPath=window.document.location.href;
    var pathName=window.document.location.pathname;
    var pos=curPath.indexOf(pathName);
    var localhostPaht=curPath.substring(0,pos);
    return(localhostPaht + webRoot + action);
}