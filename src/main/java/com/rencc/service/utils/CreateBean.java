package com.rencc.service.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateBean {
    private Connection conn = null;
    public static String classPath = null;
    
    public CreateBean(){};
    
    public CreateBean(String url,String username,String password) throws Exception{
        String driver = "com.mysql.jdbc.Driver";
        Class.forName(driver);
        this.conn = (Connection) DriverManager.getConnection(url, username, password);
        classPath = Thread.currentThread().getContextClassLoader().getResource("templates").getPath();//this.getClass().getClassLoader().getResource("").toString();
        System.out.println("CreateBean文件地址："+classPath);
    }
    
    public String getClassNameByTableName(String tableName){
        String className = formatTableName(tableName);
        return className;
    }
    
    private String formatTableName(String tableName) {
        String[] split = tableName.split("_");
        if (split.length > 1) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < split.length; i++) {
                String className = split[i].substring(0, 1).toUpperCase() + split[i].substring(1, split[i].length());
                sb.append(className);
            }

            return sb.toString();
        }
        String className = split[0].substring(0, 1).toUpperCase() + split[0].substring(1, split[0].length());
        return className;
    }
    
    public String getBeanFeilds(String tableName,Map<String,String> map) throws SQLException {
        
        DatabaseMetaData databaseMetaData = conn.getMetaData();
        ResultSet colRet = databaseMetaData.getColumns(null,"%", tableName,"%");
        
        StringBuffer str = new StringBuffer();
        StringBuffer getset = new StringBuffer();
        while(colRet.next()) {
            String name = colRet.getString("COLUMN_NAME");
            if(map.containsKey(name)){
            	continue;
            }
            String type = colRet.getString("TYPE_NAME");
            String remark = colRet.getString("REMARKS");

            String maxChar = name.substring(0, 1).toUpperCase();
            type = CommUtil.formatType(type);
            name = CommUtil.formatName(name);
            str.append("\r\t/**\n\t * ").append(remark).append("\n\t **/");
            str.append("\r\t").append("private ").append(type + " ").append(name).append(";");
            String method = maxChar + name.substring(1, name.length());
            getset.append("\r\t").append("public ").append(type + " ").append("get" + method + "() {\r\t");
            getset.append("    return this.").append(name).append(";\r\t}");
            getset.append("\r\t").append("public void ").append("set" + method + "(" + type + " " + name + ") {\r\t");
            getset.append("    this." + name + "=").append(name).append(";\r\t}");
        }
       
        String argv = str.toString();
        String method = getset.toString();
        //System.out.println(argv + method);
        return argv + method;
    }

    public String getQryBeanFeilds(String tableName) throws SQLException {

        DatabaseMetaData databaseMetaData = conn.getMetaData();
        ResultSet colRet = databaseMetaData.getColumns(null,"%", tableName,"%");

        StringBuffer str = new StringBuffer();
        StringBuffer getset = new StringBuffer();
        int i =0 ;
        while(colRet.next()) {
            i++ ;
            String name = colRet.getString("COLUMN_NAME");
            String type = colRet.getString("TYPE_NAME");
            String remark = colRet.getString("REMARKS");
            String maxChar = name.substring(0, 1).toUpperCase();
            type = CommUtil.formatType(type);
            name = CommUtil.formatName(name);
            str.append("\r\t/**\n\t * ").append(remark).append("\n\t **/");
            str.append("\r\t").append("private ").append(type + " ").append(name).append(";");
            String method = maxChar + name.substring(1, name.length());
            getset.append("\r\t").append("public ").append(type + " ").append("get" + method + "() {\r\t");
            getset.append("    return this.").append(name).append(";\r\t}");
            getset.append("\r\t").append("public void ").append("set" + method + "(" + type + " " + name + ") {\r\t");
            getset.append("    this." + name + "=").append(name).append(";\r\t}");
        }

        String argv = str.toString();
        String method = getset.toString();
        //System.out.println(argv + method);
        return argv + method;
    }

    
    public List<ColumnData> getColumnDatas(String tableName,Map<String,String> map) throws SQLException {
        DatabaseMetaData databaseMetaData = conn.getMetaData();
        List<ColumnData> columnList = new ArrayList<ColumnData>();
        ResultSet colRet = databaseMetaData.getColumns(null,"%", tableName,"%");
        while(colRet.next()) {
            ColumnData cd = new ColumnData();
            String name = colRet.getString("COLUMN_NAME");
            if(map.containsKey(name)){
            	continue;
            }
            String type = colRet.getString("TYPE_NAME");
            cd.setColumnType(CommUtil.formatColumnType(type));
            type = CommUtil.formatType(type);
            cd.setColumnName(name);
            cd.setDataType(type);
            cd.setComment(colRet.getString("REMARKS"));
            columnList.add(cd);
        }
        return columnList;
    }
    
    public Map<String, Object> getAutoCreateSql(String tableName,Map<String,String> map) throws Exception {
        Map sqlMap = new HashMap();
        List<ColumnData> columnDatas = getColumnDatas(tableName,map);
        String columns = getColumnSplit(columnDatas);
        String formatColumns = getFormatColumnSplit(columnDatas);

        String inst_columns = getInst_ColumnSplit(columnDatas);

        String[] columnList = getColumnList(columns);
        String columnFields = getColumnFields(columns);
        String insert = getInsertSql(tableName,columnDatas,columnFields);
        String update = getUpdateSqlOld(tableName, columnList);
        String selectByWhere = getSelectByWhereSql(tableName, columnDatas);
        String update2 = getUpdateSql(tableName, columnDatas);
        String selectById = getSelectByIdSql(tableName, columnList, columnDatas);
        String delete = getDeleteSql(tableName, columnList,columnDatas);
        sqlMap.put("columnList", columnList);
        sqlMap.put("columnFields", columnFields);
        sqlMap.put("insert", insert);
        sqlMap.put("selectByWhere", selectByWhere);
        sqlMap.put("update", update2);
        sqlMap.put("delete", delete);
        sqlMap.put("selectById", selectById);
        return sqlMap;
    }

    private String getInsertSql(String tableName, List<ColumnData> columnDatas, String columnFields) {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ");
        sql.append(tableName);
        sql.append("\n\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n");
        for (ColumnData columnData : columnDatas) {
            sql.append("\t\t\t<if test=\""+columnData.getFormatColumnName()+"!=null\">"+columnData.getColumnName() + ",</if> \n");
        }
        sql.append("\t\t</trim>");
        sql.append("\n\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">\n");
        for (ColumnData columnData : columnDatas) {
            sql.append("\t\t\t<if test=\""+columnData.getFormatColumnName()+"!=null\">#{" + columnData.getFormatColumnName() + ",jdbcType="+CommUtil.formatColumnType(columnData.getColumnType())+"},</if> \n");
        }
        sql.append("\t\t</trim>");
        return sql.toString();
    }

    private String getSelectByWhereSql(String tableName, List<ColumnData> columnDatas) {
        StringBuffer select = new StringBuffer();
        select.append("select <include refid=\"column\" /> FROM "+tableName+" \n\t\t<where>\n");
        for (ColumnData columnData : columnDatas) {
            select.append("\t\t\t<if test=\""+CommUtil.formatName(columnData.getColumnName())+" != null\">\n" +
                    "\t\t\t\tAND "+columnData.getColumnName()+" = #{"+CommUtil.formatName(columnData.getColumnName())+",jdbcType="+CommUtil.formatColumnType(columnData.getColumnType())+"}\n" +
                    "\t\t\t</if>\n");
        }
        select.append("\t\t</where>");
        return select.toString();
    }

    public String getColumnSplit(List<ColumnData> columnList) throws SQLException {
        StringBuffer commonColumns = new StringBuffer();
        for (ColumnData data : columnList) {
            commonColumns.append(data.getColumnName() + "|");
        }
        return commonColumns.delete(commonColumns.length() - 1, commonColumns.length()).toString();
    }
    public String getInst_ColumnSplit(List<ColumnData> columnList) throws SQLException {
        StringBuffer commonColumns = new StringBuffer();
        for (ColumnData data : columnList) {
            if(!data.getColumnName().equals("id")&&!data.getColumnName().equals("ID")){
                commonColumns.append(data.getColumnName() + "|");
            }
        }
        return commonColumns.delete(commonColumns.length() - 1, commonColumns.length()).toString();
    }
    public String getFormatColumnSplit(List<ColumnData> columnList) throws SQLException {
        StringBuffer commonColumns = new StringBuffer();
        for (ColumnData data : columnList) {
            if(!data.getColumnName().equals("id")&&!data.getColumnName().equals("ID")){
                commonColumns.append(data.getFormatColumnName() + "|");
            }
        }
        return commonColumns.delete(commonColumns.length() - 1, commonColumns.length()).toString();
    }
    public String[] getColumnList(String columns) throws SQLException {
        String[] columnList = columns.split("[|]");
        return columnList;
    }
    public String getColumnFields(String columns) throws SQLException {
        String fields = columns;
        if ((fields != null) && (!"".equals(fields))) {
            fields = fields.replaceAll("[|]", ",");
        }
        return fields;
    }
    public String getUpdateSql(String tableName, List<ColumnData> columnsList) throws SQLException {
        StringBuffer sb = new StringBuffer();
        for (ColumnData columnData : columnsList) {

        }
        for (int i = 0; i < columnsList.size(); i++) {
            String column = columnsList.get(i).getColumnName();
            String type = columnsList.get(i).getColumnType();
            if (!"in_time".equalsIgnoreCase(column)) {
                sb.append("\t\t\t<if test=\""+CommUtil.formatName(column)+"!=null\">"+column + "=#{" + CommUtil.formatName(column) + ",jdbcType="+CommUtil.formatColumnType(type)+"},</if> \n");
            }
        }
        String update = "update " + tableName + "\n \t\t<set> \n" + sb.toString() + "\t\t</set>\n \t\t where " + columnsList.get(0).getColumnName() + " = #{" + CommUtil.formatName(columnsList.get(0).getColumnName()) + ",jdbcType="+CommUtil.formatColumnType(columnsList.get(0).getColumnType())+"}";
        return update;
    }

    public String getUpdateSqlOld(String tableName, String[] columnsList) throws SQLException {
        StringBuffer sb = new StringBuffer();

        for (int i = 1; i < columnsList.length; i++) {
            String column = columnsList[i];
            if (!"in_time".equalsIgnoreCase(column)) {
                if ("edit_time".equalsIgnoreCase(column))
                    sb.append(column + "=current_timestamp()");
                else {
                    sb.append(column + "=#{" + CommUtil.formatName(column) + "}");
                }
                if (i + 1 < columnsList.length)
                    sb.append(",");
            }
        }
        String update = "update " + tableName + " set " + sb.toString() + " where " + columnsList[0] + "=#{" + CommUtil.formatName(columnsList[0]) + "}";
        return update;
    }
    public String getSelectByIdSql(String tableName, String[] columnsList, List<ColumnData> colRet) throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append("select <include refid=\"column\" /> \n");
        sb.append("\t\tfrom ").append(tableName).append(" where ");
        sb.append(columnsList[0]).append(" = #{").append(CommUtil.formatName(columnsList[0])).append(",jdbcType=").append(CommUtil.formatColumnType(colRet.get(0).getColumnType())).append("}");
        return sb.toString();
    }
    public String getDeleteSql(String tableName, String[] columnsList, List<ColumnData> colRet) throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append("delete ");
        sb.append("\t from ").append(tableName).append(" where ");
        sb.append(columnsList[0]).append(" = #{").append(CommUtil.formatName(columnsList[0])).append(",jdbcType=").append(CommUtil.formatColumnType(colRet.get(0).getColumnType())).append("}");
        return sb.toString();
    }

}
