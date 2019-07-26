package com.rencc.service.controller;

import java.io.File;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.VelocityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.rencc.service.utils.BaseResult;
import com.rencc.service.utils.ColumnData;
import com.rencc.service.utils.CommUtil;
import com.rencc.service.utils.CommonPageParser;
import com.rencc.service.utils.CreateBean;
import com.mysql.jdbc.Connection;

@Controller
@RequestMapping("/")
public class generatorController {
	
	private String databaseName;
	private String driver; 
	private String url; 
	private String username;
	private String password; 
	private String author;
	private static String systemid = "hzfh";
	
	@RequestMapping("/")
	public String mainUI(Model model){
		return "main";
	}

	/**
	 * 初始化数据库链接
	 * @param map
	 * @return
	 */
	@RequestMapping(value="getConn", method=RequestMethod.POST)
	@ResponseBody
	public BaseResult getConn(@RequestBody Map<String,String> map){
		BaseResult result = BaseResult.errorInstance();
		databaseName = map.get("database");
        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://"+map.get("service")+"/";
        username = map.get("uid");
        password = map.get("pwd");
        author = map.get("Author");
		this.systemid = map.get("systemid").toString();
        try {
			Class.forName(driver);
			Connection conn = (Connection) DriverManager.getConnection(url+databaseName, username, password);
			DatabaseMetaData databaseMetaData = conn.getMetaData();
			ResultSet tableRet = databaseMetaData.getTables(null, "%","%",new String[]{"TABLE"});
			List lt = new ArrayList();
			while(tableRet.next()){
				lt.add(tableRet.getString("TABLE_NAME"));
			}
			result.setResultList(lt);
        } catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 根据数据库获取所有的表
	 * @param modle
	 * @return
	 */
	@RequestMapping(value="getColumnByTableName", method=RequestMethod.POST)
	@ResponseBody
	public BaseResult getColumnByTableName(Model modle){
		BaseResult result = BaseResult.errorInstance();
		String tableName = getPara("tableName");
        try {
        	CreateBean createBean = new CreateBean(url+databaseName, username, password);
        	List<ColumnData> list = createBean.getColumnDatas(tableName,new HashMap<String, String>());
			result.setResultList(list);
        } catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 批量生产代码
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="doGenerator", method=RequestMethod.POST)
	@ResponseBody
	public BaseResult doGenerator(@RequestBody Map<String,String[]> map) throws Exception{
		BaseResult result = BaseResult.errorInstance();
		String[] tables = map.get("tables");
		for(String s : tables){
			codeGenerate(s,new HashMap<String, String>());
		}
		result.setErrCode("0000");
		result.setErrDesc("代码生成成功！");
		if(tables.length == 0){
			result.setErrDesc("请选择生成的表！");
		}
		return result;
	}
	/**
	 * 单表生产代码
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="doGeneratorByTablename", method=RequestMethod.POST)
	@ResponseBody
	public BaseResult doGeneratorByTablename(@RequestBody Map<String,String[]> map) throws Exception{
		BaseResult result = BaseResult.errorInstance();
		String[] fields = map.get("fields");
		String tablename = getPara("tablename");
		Map<String,String> m = new HashMap<String, String>();
		for(String s : fields){
			m.put(s, s);
		}
		codeGenerate(tablename, m);
		result.setErrCode("0000");
		result.setErrDesc("代码生成成功！");
		if("".equals(tablename)){
			result.setErrDesc("请选择生成的表！");
		}
		return result;
	}
	/**
	 * 生产代码
	 * @param tableName
	 * @param map
	 * @throws Exception
	 */
	public void codeGenerate(String tableName,Map<String,String> map) throws Exception{
        CreateBean createBean = new CreateBean(url+databaseName, username, password);
        //createBean.getBeanFeilds(tableName);
        String className = createBean.getClassNameByTableName(tableName);
        String lowerName = CommUtil.formatName(className);
        String databaseNameFormat = CommUtil.formatName(databaseName);
        String packPath = "com"+File.separator+"hzfh"+File.separator+this.systemid+File.separator;
        String configPath1 =databaseName+File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator;
        String configPath2 =databaseName+File.separator+ "src"+File.separator+"main"+File.separator+"resources"+File.separator;
        String configPath3 = configPath2 +"templates"+File.separator;
        String configPath4 = configPath2+"static"+File.separator;
        String controllerPath = configPath1+packPath + "controller"+File.separator + className+"Controller.java";
        String beanPath = configPath1+packPath + "entity"+File.separator + className+"Bean.java";
        String paramPath = configPath1+packPath +"api"+File.separator+ "entity"+ File.separator+ className+"Param.java";
        String daoPath = configPath1+packPath + "dao"+File.separator + className+"Dao.java";
        String servicePath = configPath1+packPath + "service"+File.separator + className+"Service.java";
        String serviceImplPath = configPath1+packPath + "service"+File.separator+"impl"+File.separator + className+"ServiceImpl.java";

        String mapperXmlPath = configPath2+"mapper"+File.separator+this.systemid +File.separator + className+"Mapper.xml";



        VelocityContext context = new VelocityContext();
        
        context.put("time", CommUtil.fromDate());
        context.put("className", className);
        context.put("commPackage", "com.hzfh");
        context.put("databaseName", this.systemid);
        context.put("lowerName", lowerName);
        context.put("tableName", tableName);
        context.put("feilds", createBean.getBeanFeilds(tableName,map));
		context.put("Queryfeilds", createBean.getQryBeanFeilds(tableName));
        context.put("author", author);
        context.put("pageHead", "${pageHead}");
        context.put("PageVar", "${PageVar}");
        context.put("bootstrap", "$table.bootstrapTable({");
        
        Map sqlMap = createBean.getAutoCreateSql(tableName,map);
        context.put("columnDatas", createBean.getColumnDatas(tableName,map));
        context.put("SQL", sqlMap);
        //windows
        String pckPath = "E:"+File.separator+"Shared file"+File.separator+"code"+File.separator;
        //linux
        //String pckPath = File.separator+"usr"+File.separator+"local"+File.separator+"share"+File.separator+"code"+File.separator;
        CommonPageParser.WriterPage(context, "Entity.java", pckPath, beanPath);
        CommonPageParser.WriterPage(context, "EntityParam.java", pckPath, paramPath);
        CommonPageParser.WriterPage(context, "EntityController.java", pckPath, controllerPath);
        CommonPageParser.WriterPage(context, "EntityDao.java", pckPath, daoPath);
        CommonPageParser.WriterPage(context, "EntityService.java", pckPath, servicePath);
        CommonPageParser.WriterPage(context, "EntityServiceImpl.java", pckPath, serviceImplPath);
        CommonPageParser.WriterPage(context, "EntityMapper.xml", pckPath, mapperXmlPath);
    }
	
	public String getPara(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameter(key);
	}
	
	public String[] getParaValues(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameterValues(key);
	}
}
