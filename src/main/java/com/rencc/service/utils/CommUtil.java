package com.rencc.service.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommUtil {
    
    
    public static String formatName(String name) {
        String[] split = name.split("_");
          if (split.length > 1) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < split.length; i++) {
             String tempName="";
              if(i==0) {
                  tempName=split[0].substring(0, 1).toLowerCase() + split[0].substring(1, split[0].length());
                  sb.append(tempName);
                  continue;
                  }
              tempName= split[i].substring(0, 1).toUpperCase() + split[i].substring(1, split[i].length());
              sb.append(tempName);
            }
    
            return sb.toString();
          }
          String tempName = split[0].substring(0, 1).toLowerCase() + split[0].substring(1, split[0].length());
     return tempName;
    }

    public static String formatColumnType(String type){
        String formatType = "";
        switch (type) {
            case "BIT":
                formatType = "TINYINT";
                break;
            case "INT":
                formatType = "INTEGER";
                break;
            default:
                formatType = type;
                break;
        }
        return formatType;
    }

    public static String formatType(String type){
        String formatType = "";
        
        switch (type) {
            case "BIGINT":
                formatType = "Long";
                break;
        case "INT":
            formatType = "Integer";
            break;
        case "double":
            formatType = "Double";
            break;
        case "VARCHAR":
            formatType = "String";
            break;
        case "DATETIME":
            formatType = "Date";
            break;
        case "TINYINT":
            formatType = "Byte";
            break;
        case "DECIMAL":
            formatType = "BigDecimal";
            break;
        case "TIMESTAMP":
            formatType = "Timestamp";
            break;
        case "TEXT":
            formatType = "String";
            break;
        case "BIT":
            formatType = "Byte";
            break;
        default:
            formatType = type;
            break;
        }
        
        return formatType;
    }
    
    public static String formatUpperName(String name){
    	String maxChar = name.substring(0, 1).toUpperCase();
    	String str = maxChar + name.substring(1, name.length());
    	return str;
    }
    
    public static String fromDate() {
		DateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		return format.format(new Date());
	}
}
