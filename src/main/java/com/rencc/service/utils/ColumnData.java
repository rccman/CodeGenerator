package com.rencc.service.utils;


public class ColumnData{
    private String columnName;
    private String dataType;
    private String columnType;
    private String comment;

    public String getFormatColumnUpperName() {
        return columnName!=null?CommUtil.formatUpperName(CommUtil.formatName(columnName)):"";
    }
    public String getFormatColumnName() {
        return columnName!=null?CommUtil.formatName(columnName):"";
    }
    public String getColumnName()
    {
        return columnName;
    }
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    public String getDataType() {
        return dataType;
    }
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    public String getColumnType() {
        return columnType;
    }
    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }
    
    public boolean isTimeField()
    {
        if ("Date".equals(this.dataType) || "Timestamp".equals(this.dataType))
            return true;
        else
            return false;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}