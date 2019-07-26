/**
 * 工具组件 对原有的工具进行封装，自定义某方法统一处理
 */

(function() {
	ly = {};
	ly.ajax = (function(params) {
		var pp = {
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.open({
					type : 1,
					title : "出错啦！",
					area : [ '95%', '95%' ],
					content : "<div id='layerError' style='color:red'>"
							+ XMLHttpRequest.responseText + "</div>"
				});
			}
		};
		$.extend(pp, params);
		$.ajax(pp);
	});
	ly.ajaxSubmit = (function(form, params) {// form 表单ID. params ajax参数
		var pp = {
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.open({
					type : 1,
					title : "出错啦！",
					area : [ '95%', '95%' ],
					content : "<div id='layerError' style='color:red'>"
							+ XMLHttpRequest.responseText + "</div>"
				});
			}
		};
		$.extend(pp, params);
		$(form).ajaxSubmit(pp);
	});
	CommnUtil = {
	    
	    isChinese:function(c){//判断字符是中文还是英文
	    	var reCh=/[u00-uff]/;  
	        return !reCh.test(c);
	    },
	    getStrLength:function(str){//获取字节长度
	    	var strlen = 0;
	    	for(var i = 0 ;i < str.length;i++) {
	    		var c = str[i];
	    		if(this.isChinese(c)){//中文
	    			strlen+=2;
	    		}else{
	    			strlen++;
	    		}
	    	}
	    	return strlen;
	    },
			
		getCurrentDateTime :function(){// 获取当前日期和时间，格式yy-MM-dd hh:mm:ss
			        var datetime = new Date();
			        var year = datetime.getFullYear();
			        var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
			        var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
			        var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
			        var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
			        var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
			        return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
			    },
		getCurrentDate :function(){// 获取当前日期，格式yy-MM-dd
			    	var datetime = new Date();
			    	var year = datetime.getFullYear();
			    	var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
			    	var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
			    	return year + "-" + month + "-" + date;
			    },
			
	    getDate	:function(dateStr,num) {//dateStr:日期，num多少天
				var d = dateStr.split("-");
				var da = new Date(d[0],d[1]-1,d[2]);
				da.setDate(da.getDate()+num);
				return da.format("yyyy-MM-dd");
		},
		plusDate:function(dateStr,milliSeconds){
			dateStr = dateStr.replace(/-/g,'/');
			var d = new Date(dateStr);
			d.setTime(d.getTime()+milliSeconds);
			return d.format('yyyy-MM-dd hh:mm:ss');
		},
			
	    onloadurl:function() {
	    	$("[data-url]").each(function () {
    			var tb = $(this);
    			//debugger;
    			//tb.html(CommnUtil.loadingImg());
    			//tb.load(rootPath+tb.attr("data-url"));
    			tb.html(CommnUtil.loadingImg());
    			tb.load(rootPath+tb.attr("data-url"));
    	    });
	    },
	    onloadurlbyid:function(id){
	    	var tb =  $("#"+id);
	    	tb.html(CommnUtil.loadingImg());
	    	$.ajaxSetup ({ cache: false });
			tb.load(rootPath+tb.attr("data-url"));
	    	
	    },
			
	    
		/**
		 * ajax同步请求 返回一个html内容 dataType=html. 默认为html格式 如果想返回json.
		 * CommnUtil.ajax(url, data,"json")
		 * 
		 */
		ajax : function(url, data, dataType) {
			if (!CommnUtil.notNull(dataType)) {
				dataType = "html";
			}
			var html = '没有结果!';
			// 所以AJAX都必须使用ly.ajax..这里经过再次封装,统一处理..同时继承ajax所有属性
			if (url.indexOf("?") > -1) {
				url = url + "&_t=" + new Date().getTime();
			} else {
				url = url + "?_t=" + new Date().getTime();
			}
			ly.ajax({
				type : "post",
				data : data,
				url : url,
				dataType : dataType,// 这里的dataType就是返回回来的数据格式了html,xml,json
				async : false,
				cache : false,// 设置是否缓存，默认设置成为true，当需要每次刷新都需要执行数据库操作的话，需要设置成为false
				success : function(data) {
					html = data;
				}
			});
			return html;
		},
		/**
		 * 判断某对象不为空..返回true 否则 false
		 */
		notNull : function(obj) {
			if (obj === null) {
				return false;
			} else if (obj === undefined) {
				return false;
			} else if (obj === "undefined") {
				return false;
			} else if (obj === "") {
				return false;
			} else if (obj === "[]") {
				return false;
			} else if (obj === "{}") {
				return false;
			} else {
				return true;
			}

		},

		/**
		 * 判断某对象不为空..返回obj 否则 ""
		 */
		notEmpty : function(obj) {
			if (obj === null) {
				return "";
			} else if (obj === undefined) {
				return "";
			} else if (obj === "undefined") {
				return "";
			} else if (obj === "") {
				return "";
			} else if (obj === "[]") {
				return "";
			} else if (obj === "{}") {
				return "";
			} else {
				return obj;
			}

		},
		loadingImg : function() {
			var html = '<div class="alert alert-warning">'
					+ '<button type="button" class="close" data-dismiss="alert">'
					+ '<i class="ace-icon fa fa-times"></i></button><div style="text-align:center">'
					+ '<img src="' + rootPath + '/resources/images/loading.gif"/><div>'
					+ '</div>';
			/*var html = '<div style="position:absolute;top:50%;left:50%">'
			+ '<img src="' + rootPath + '/resources/images/loading.gif"/><div>'*/
			return html;
		},
		/**
		 * html标签转义
		 */
		htmlspecialchars : function(str) {
			var s = "";
			if (str.length == 0)
				return "";
			for (var i = 0; i < str.length; i++) {
				switch (str.substr(i, 1)) {
				case "<":
					s += "&lt;";
					break;
				case ">":
					s += "&gt;";
					break;
				case "&":
					s += "&amp;";
					break;
				case " ":
					if (str.substr(i + 1, 1) == " ") {
						s += " &nbsp;";
						i++;
					} else
						s += " ";
					break;
				case "\"":
					s += "&quot;";
					break;
				case "\n":
					s += "";
					break;
				default:
					s += str.substr(i, 1);
					break;
				}
			}
		},
		/**
		 * in_array判断一个值是否在数组中
		 */
		in_array : function(array, string) {
			for (var s = 0; s < array.length; s++) {
				thisEntry = array[s].toString();
				if (thisEntry == string) {
					return true;
				}
			}
			return false;
		},
		getFullyears:function(obj){
			obj.empty();
			var d = new Date();
			obj.append("<option value='-1'>请选择</option>");
			var op = $("<option></option>").text(d.getFullYear()-1).val(d.getFullYear()-1);
            for (var i = 0; i < 4; i++) {
                var op = $("<option></option>").text(d.getFullYear()+i).val(d.getFullYear()+i);
                obj.append(op);
            }
		}
	};
})();
// 表单json格式化方法……不使用&拼接
(function($) {
	$.fn.serializeJson = function() {
		var serializeObj = {};
		var array = this.serializeArray();
		$(array).each(
				function() {
					if (serializeObj[this.name]) {
						if ($.isArray(serializeObj[this.name])) {
							serializeObj[this.name].push(this.value);
						} else {
							serializeObj[this.name] = [
									serializeObj[this.name], this.value ];
						}
					} else {
						serializeObj[this.name] = this.value;
					}
				});
		return serializeObj;
	};
	
	
	 $.fn.serializeMutiJson = function(){
         var jsonData1 = {};
         var serializeArray = this.serializeArray();
         // 先转换成{"id": ["12","14"], "name": ["aaa","bbb"], "pwd":["pwd1","pwd2"]}这种形式
         $(serializeArray).each(function () {
             if (jsonData1[this.name]!=undefined) {
                 if ($.isArray(jsonData1[this.name])) {
                     jsonData1[this.name].push(this.value);
                 } else {
                     jsonData1[this.name] = [jsonData1[this.name], this.value];
                 }
             } else {
                 jsonData1[this.name] = this.value;
             }
         });
         // 再转成[{"id": "12", "name": "aaa", "pwd":"pwd1"},{"id": "14", "name": "bb", "pwd":"pwd2"}]的形式
         var vCount = 0;
         // 计算json内部的数组最大长度
         for(var item in jsonData1){
             var tmp = $.isArray(jsonData1[item]) ? jsonData1[item].length : 1;
             vCount = (tmp > vCount) ? tmp : vCount;
         }

         if(vCount > 1) {
             var jsonData2 = new Array();
             for(var i = 0; i < vCount; i++){
                 var jsonObj = {};
                 for(var item in jsonData1) {
                     jsonObj[item] = jsonData1[item][i];
                 }
                 jsonData2.push(jsonObj);
             }
             return jsonData2;
         }else if(vCount==1){
             return eval("(" + "[" + JSON.stringify(jsonData1) + "]" + ")");
        	 //return [];
         }else if(vCount==0) {
        	 return serializeArray;
        	 
         }
     };
	
	
	
	
	Date.prototype.format = function(format) {
		var o = {
			"M+" : this.getMonth() + 1, // month
			"d+" : this.getDate(), // day
			"h+" : this.getHours(), // hour
			"m+" : this.getMinutes(), // minute
			"s+" : this.getSeconds(), // second
			"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
			"S" : this.getMilliseconds()// millisecond
		}
		if (/(y+)/.test(format)) {
			format = format.replace(RegExp.$1, (this.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		}
		for ( var k in o) {
			if (new RegExp("(" + k + ")").test(format)) {
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
						: ("00" + o[k]).substr(("" + o[k]).length));
			}
		}
		return format;
	}
})(jQuery);

//数组中是否包含某个值
Array.prototype.contains = function(obj) {
	var i  = this.length;
	while(i--){
		if(this[i]==obj) {
			return true;
		}
	}
	return false;
}

var __sp = String.prototype;

__sp.encodeURI = function () {
    return escape(this).replace(/\*/g, "%2A").replace(/\+/g, "%2B").replace(/-/g, "%2D").replace(/\./g, "%2E").replace(/\//g, "%2F").replace(/@/g, "%40").replace(/_/g, "%5F");
};
__sp.encodeHtml = function () {
    return this.replace(/\&/g, "&amp;").replace(/\>/g, "&gt;").replace(/\</g, "&lt;").replace(/\'/g, "&#039;").replace(/\"/g, "&quot;");
};

__sp.format = function (args) {
    if (arguments.length > 0) {
        var result = this;
        if (arguments.length == 1 && typeof (args) == "object") {
            for (var key in args) {
                var reg = new RegExp("({" + key + "})", "g");
                result = result.replace(reg, args[key]);
            }
        }
        else {
            for (var i = 0; i < arguments.length; i++) {
                if (arguments[i] == undefined) {
                    return "";
                }
                else {
                    var reg = new RegExp("({[" + i + "]})", "g");
                    result = result.replace(reg, arguments[i]);
                }
            }
        }
        return result;
    }
    else {
        return this;
    }
}
