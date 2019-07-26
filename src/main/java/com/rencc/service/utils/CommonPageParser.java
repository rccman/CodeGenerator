package com.rencc.service.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class CommonPageParser
{
  private static VelocityEngine ve;
  private static final String CONTENT_ENCODING = "UTF-8";

  private static boolean isReplace = true;

  static
  {
    try
    {
      String templateBasePath = FilePathUtil.getRealFilePath(CreateBean.classPath);
      System.out.println("模板地址："+templateBasePath);
      Properties properties = new Properties();
      properties.setProperty("resource.loader", "file");
      properties.setProperty("file.resource.loader.description", "Velocity File Resource Loader");
      properties.setProperty("file.resource.loader.path", templateBasePath);
      properties.setProperty("file.resource.loader.cache", "true");
      properties.setProperty("file.resource.loader.modificationCheckInterval", "30");
      properties.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.Log4JLogChute");
      properties.setProperty("runtime.log.logsystem.log4j.logger", "org.apache.velocity");
      properties.setProperty("directive.set.null.allowed", "true");
      VelocityEngine velocityEngine = new VelocityEngine();
      velocityEngine.init(properties);
      ve = velocityEngine;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void WriterPage(VelocityContext context, String templateName, String fileDirPath, String targetFile)
  {
    try
    {
      System.out.println("1地址："+fileDirPath + targetFile);
      File file = new File(fileDirPath + targetFile);
      System.out.println("2地址："+fileDirPath + targetFile);
      if (!file.exists()) {
        new File(file.getParent()).mkdirs();
      }else if (isReplace) {
        System.out.println("\u66FF\u6362\u6587\u4EF6:" + file.getAbsolutePath());
      }

      Template template = ve.getTemplate(templateName, CONTENT_ENCODING);
      FileOutputStream fos = new FileOutputStream(file);
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, CONTENT_ENCODING));
      template.merge(context, writer);
      writer.flush();
      writer.close();
      fos.close();
      System.out.println("\u751F\u6210\u6587\u4EF6\uFF1A" + file.getAbsolutePath());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}