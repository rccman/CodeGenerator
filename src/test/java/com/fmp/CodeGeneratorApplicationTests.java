package com.fmp;


import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

import org.junit.Test;

import com.fmp.service.controller.generatorController;
import com.fmp.service.utils.CommUtil;
import com.mysql.jdbc.Connection;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class CodeGeneratorApplicationTests {

	@Test
	public void contextLoads() {
		String databaseName = "hzfbm";
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://172.16.13.62:3306/";
        String username = "root";
        String password = "123456";
        String author = "韩仁洪";
        try {
			Class.forName(driver);
			Connection conn = (Connection) DriverManager.getConnection(url+databaseName, username, password);
			DatabaseMetaData databaseMetaData = conn.getMetaData();
			ResultSet tableRet = databaseMetaData.getTables(null, "%","%",new String[]{"TABLE"});
			//conn.close();
			while(tableRet.next()){
				System.out.println(tableRet.getString("TABLE_NAME"));
				//generatorController.codeGenerate(databaseName, tableRet.getString("TABLE_NAME"), databaseName, url, username, password,author);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//System.out.println(CommUtil.formatName("qishou_na_wo"));
	}

}
