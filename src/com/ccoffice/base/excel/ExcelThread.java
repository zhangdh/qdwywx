package com.ccoffice.base.excel;

import java.io.File;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import com.ccoffice.util.cache.Cache;

public class ExcelThread extends Thread
{
  private String destFileName;
  private String templateFilepath;
  private String key;
  private String path;
  private String exportName;
  private String pre;
  private Map map;
  private Map beans;
  private XLSTransformer transformer = null;

  public void run()
  {
    File file;
    try{
      file = new File(this.destFileName);
      System.out.println("目标文件路径:" + this.destFileName);
      file.createNewFile();
      this.transformer.transformXLS(this.pre + this.templateFilepath, this.beans, this.destFileName);
      System.out.println("模板文件路径:" + this.pre + this.templateFilepath);
      System.out.println("web文件路径:" + this.pre + this.path + "/excelDestFile/" + 
        this.exportName + "_" + this.key + ".xls");

      Cache.setUserInfo((String)this.map.get("yhid"), this.key, this.path + "/excelDestFile/" + 
        this.exportName + "_" + this.key + ".xls");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String getDestFileName()
  {
    return this.destFileName;
  }

  public void setDestFileName(String destFileName) {
    this.destFileName = destFileName;
  }

  public String getTemplateFilepath() {
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

  public String getPath() {
    return this.path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getExportName() {
    return this.exportName;
  }

  public void setExportName(String exportName) {
    this.exportName = exportName;
  }

  public String getPre() {
    return this.pre;
  }

  public void setPre(String pre) {
    this.pre = pre;
  }

  public Map getMap() {
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
}
