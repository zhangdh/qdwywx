package com.ccoffice.base.excel;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

 
import com.ccoffice.util.tools.ConvertMap;



public class ExcelTool
{
  private Map beans = new HashMap();
  private XLSTransformer transformer = new XLSTransformer();
  private String templateFilepath = "";
  private String templateFileName = "";
  private String exportName = "";
  private int dataSize = 0;

  public ExcelTool(String templateFilepath, String exportName)
  {
    this.templateFilepath = templateFilepath;
    this.templateFileName = templateFilepath + ".xls";
    this.exportName = exportName;
  }

  public void addBeans(String beanName, Map beanMap)
  {
    if ((beanMap == null) || (beanMap.isEmpty()))
      this.beans.put(beanName, new Object());
    else
      this.beans.put(beanName, getVmBean(beanMap));
  }

  private DynaBean getVmBean(Map map)
  {
    DynaProperty[] properties = new DynaProperty[map.size()];
    Iterator it = map.keySet().iterator();
    int i = 0;
    String key = "";
    Object value = null;
    while (it.hasNext()) {
      key = (String)it.next();
      value = map.get(key);
      if (value == null)
        properties[i] = new DynaProperty(key, Object.class.getClass());
      else
        properties[i] = new DynaProperty(key, value.getClass());

      ++i;
    }
    BasicDynaClass dynaClass = new BasicDynaClass("vmBean", 
      BasicDynaBean.class, properties);
    DynaBean vmBean = null;
    try {
      vmBean = dynaClass.newInstance();
    }
    catch (Exception e1) {
      e1.printStackTrace();
    }
    Iterator it2 = map.keySet().iterator();
    while (it2.hasNext()) {
      key = (String)it2.next();
      value = map.get(key);
      vmBean.set(key, value);
    }
    return vmBean;
  }

  public void addLists(String listName, List<Map> dataList)
  {
    if ((dataList == null) || (dataList.size() == 0))
      return;

    List data = new ArrayList();
    for (Iterator localIterator = dataList.iterator(); localIterator.hasNext(); ) { Map map = (Map)localIterator.next();
      data.add(getVmBean(map));
    }
    this.dataSize += data.size();
    this.beans.put(listName, data);
    this.transformer.groupCollection(listName);
  }

  public void exportExcel(HttpServletResponse response, HttpServletRequest request)
  {
    try
    {
      if (this.dataSize > 2000) {
        String key =String.valueOf( new Date().getTime());
        String pre = request.getRealPath("/");
        String path = request.getContextPath();
        Map map = ConvertMap.getMap(request);
        String destFileName = request.getRealPath("/excelDestFile/" + 
          this.exportName + "_" + key + ".xls");
        response.sendRedirect(path + "/excel/wait.jsp?key=" + key);

        ExcelThread excellThread = new ExcelThread();
        excellThread.setBeans(this.beans);
        excellThread.setExportName(this.exportName);
        excellThread.setDestFileName(destFileName);
        excellThread.setMap(map);
        excellThread.setKey(key);
        excellThread.setPath(path);
        excellThread.setPre(pre);
        excellThread.setTemplateFilepath(this.templateFilepath);
        excellThread.setTransformer(this.transformer);
        excellThread.start(); return;
      }
      this.exportName = new String(this.exportName.getBytes("gb2312"), "iso8859-1");
      response.setContentType("application/vnd.ms-excel");
      response.setHeader("Content-Disposition", 
        "attachment; filename=" + this.exportName + ".xls");
      InputStream is = new BufferedInputStream(
        new FileInputStream(request.getRealPath("/") + this.templateFilepath));
      HSSFWorkbook workbook = (HSSFWorkbook)this.transformer.transformXLS(is, this.beans);
      OutputStream os = response.getOutputStream();
      workbook.write(os);
      is.close();
      os.flush();
      os.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
 

  public String getTemplateFileName() {
    return this.templateFileName;
  }

  public void setTemplateFileName(String templateFileName) {
    this.templateFileName = templateFileName;
  }
}