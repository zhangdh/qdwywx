package com.ccoffice.base.excel;


import java.io.File;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import com.ccoffice.util.cache.Cache;


public class ExcelThreadV2 extends Thread
{
  private String webFilepath;
  private String templateFilepath;
  private String key;
  private String exportFilePath;
  private String yhid;
  private Map map;
  private Map beans;
  private XLSTransformer transformer = null;

  public void run()
  {
    File file;
    try
    {
      file = new File(this.exportFilePath);
      System.out.println("version 2: 导出文件路径：" + this.exportFilePath + " 模板文件路径：" + this.templateFilepath + " web访问路径：" + this.webFilepath);
      file.createNewFile();
      this.transformer.transformXLS(this.templateFilepath, this.beans, this.exportFilePath);
      Cache.setUserInfo(this.yhid, this.key, this.webFilepath);

      System.out.println("导出文件成功！");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String getTemplateFilepath()
  {
    return this.templateFilepath;
  }

  public void setTemplateFilepath(String templateFilepath) {
    this.templateFilepath = templateFilepath;
  }

  public String getKey() {
    return this.key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public Map getMap()
  {
    return this.map;
  }

  public void setMap(Map map) {
    this.map = map;
  }

  public Map getBeans() {
    return this.beans;
  }

  public void setBeans(Map beans) {
    this.beans = beans;
  }

  public XLSTransformer getTransformer() {
    return this.transformer;
  }

  public void setTransformer(XLSTransformer transformer) {
    this.transformer = transformer;
  }

  public String getWebFilepath()
  {
    return this.webFilepath;
  }

  public void setWebFilepath(String webFilepath)
  {
    this.webFilepath = webFilepath;
  }

  public String getExportFilePath()
  {
    return this.exportFilePath;
  }

  public void setExportFilePath(String exportFilePath)
  {
    this.exportFilePath = exportFilePath;
  }

  public String getYhid()
  {
    return this.yhid;
  }

  public void setYhid(String yhid)
  {
    this.yhid = yhid;
  }
}
