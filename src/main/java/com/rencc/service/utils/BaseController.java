package com.rencc.service.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * <p>Titile:BaseController</p>
 * @author hanrh
 * @date 2016年8月19日 上午10:10:08
 */
@Controller
public class BaseController {
	

    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
    
    protected HttpSession getSession() {
        return getRequest().getSession();
    }
    
	/**
	 * @Description: 获取页面传递的某一个数组值
	 * @author hanrh
	 * @date 2016年8月19日 上午10:11:00
	 * @return String[]
	 */
	public String[] getParaValues(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameterValues(key);
	}
	/**
	 * @Description: 获取页面传递的某一个参数值
	 * @author hanrh
	 * @date 2016年8月19日 上午10:10:47
	 * @return String
	 */
	public String getPara(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameter(key);
	}
	
	public Map<String, String> toReqMap(Map reqMap){
		Map<String, String> map = new HashMap<String, String>();
		for(Object key:reqMap.keySet()){
			Object obj = reqMap.get(key);
			String tmpKey = (String)key;
			if(obj!=null){
				if(obj instanceof String){
					String value = (String)obj;
					map.put(tmpKey, value);
				}else{
					String value = obj.toString();
					map.put(tmpKey, value);
				}
			}
		}
		return map;
	}
}