package com.kaisn.utils.xml;

import com.kaisn.WSClientUtils;
import com.kaisn.pojo.FieldMeta;
import com.kaisn.ws.Employee;
import com.kaisn.ws.EmployeeService;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ParseXmlUtil {

    private static Logger logger = Logger.getLogger(ParseXmlUtil.class);

    public static List<Object> readXML(Class<?> cls) {
        List<Object> objects = new ArrayList<Object>();
        SAXReader sr = new SAXReader();// 获取读取xml的对象。
        try {
            Document doc = sr.read("src/main/resources/employee.xml");// 得到xml所在位置。然后开始读取。并将数据放入doc中
            Element rootElement = doc.getRootElement();// 向外取数据，获取xml的根节点。
            Iterator<Element> it = rootElement.elementIterator();// 从根节点下依次遍历，获取根节点下所有子节点
            while (it.hasNext()) {// 遍历子节点
                Element next = it.next();
                Iterator<Element> iterator = next.elementIterator();
                while (iterator.hasNext()) {
                    Element element = iterator.next();
                    Iterator<Element> iter = element.elementIterator();
                    Object obj = cls.newInstance();
                    while (iter.hasNext()) {
                        Element ele = iter.next();
                        String fieldName = ele.getName();
                        String upperChar = fieldName.substring(0,1).toUpperCase();
                        String anotherStr = fieldName.substring(1);
                        String methodName = "set" + upperChar + anotherStr;
                        Object data = ele.getData();
                        Method method = cls.getMethod(methodName,String.class);
                        method.setAccessible(true);
                        method.invoke(obj, data);
                    }
                    objects.add(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objects;
    }

    public static void writeToXML(List<?> objects,String filePath,String fileName) throws Exception {

        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("root");
        Class<?> employeeClass = objects.get(0).getClass();
        String simpleName = employeeClass.getSimpleName();
        Element list = root.addElement(simpleName+"List");
        for (Object obj : objects) {
            Element row = list.addElement(employeeClass.getSimpleName());
            // 获取对象属性
            Field[] fields = employeeClass.getDeclaredFields();
            for (Field field: fields) {
                FieldMeta fieldMeta = field.getAnnotation(FieldMeta.class);
                if(fieldMeta.flag()) {
                    String name = field.getName();
                    // 私有属性必须设置访问权限
                    field.setAccessible(true);
                    Object resultValue = field.get(obj);
                    row.addElement(name).addText(String.valueOf(resultValue));
                }
            }
        }
        String xmlStr = "";
        xmlStr = document.asXML();
        try {
            Document dcmt = DocumentHelper.parseText(xmlStr);
            saveDocumentToFile(dcmt, filePath, fileName, true, "UTF-8");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static String getNowDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(new Date());
    }

    /**
     * 方法描述：<b>存储完整XML文件.</b></br>
     * 备 注: </br>
     * 创 建 人: zyl</br>
     * 创建日期:2013-3-18</br>
     *
     * @param document
     * @param xmlFilePath
     * @param xmlFileName
     * @param isTrimText
     * @param xmlEncoding
     */
    public static void saveDocumentToFile(Document document,
            String xmlFilePath, String xmlFileName, boolean isTrimText,
    String xmlEncoding) {
        if (document == null || xmlFilePath == null || "".equals(xmlFileName)) {
            return;
        }

        File file = new File(xmlFilePath);
        // 判断路径是否存在，不存在创建
        if (!file.exists()) {
            file.mkdirs();
        }
        // 保存文件
        OutputFormat format = null;

        if (isTrimText) {
            format = OutputFormat.createPrettyPrint();
        } else {
            format = DomXmlOutputFormat.createPrettyPrint();// 保留xml属性值中的回车换行
        }

        if (xmlEncoding != null) {
            format.setEncoding(xmlEncoding);// GBK
        } else {
            format.setEncoding("UTF-8");// UTF-8
        }

        try {
            XMLWriter xmlWriter = new XMLWriter(
                    new FileOutputStream(xmlFilePath + xmlFileName), format);// FileOutputStream可以使UTF-8生效
            xmlWriter.write(document);
            xmlWriter.close();
        } catch (IOException e) {
            logger.error("保存XML异常：" + e.getMessage());
            logger.error("正在保存的文件名是：" + xmlFileName);
        }
        // 存到文件中结束
    }

    public static void main(String args[]) throws Exception {

        EmployeeService employeeService = (EmployeeService) WSClientUtils.getInstance("EmployeeService",EmployeeService.class);
        List<Object> objects = readXML(Employee.class);
        for (int i = 0; i < objects.size(); i++) {
            Employee employee = (Employee) objects.get(i);
            boolean b = employeeService.addEmployee(employee);
            System.out.println(b);
        }

//        List<Employee> list = new ArrayList<Employee>();
//        Employee employee1 = new Employee();
//        employee1.setEmpId("001");
//        employee1.setEmpName("lijing");
//        employee1.setGender("男");
//        employee1.setAddress("江西吉安");
//        employee1.setBirth("1992-01-30");
//        employee1.setDescText("ha lou");
//        employee1.setEmail("1169318609@qq.com");
//
//        Employee employee2 = new Employee();
//        employee2.setEmpId("002");
//        employee2.setEmpName("kaisn");
//        employee2.setGender("女");
//        employee2.setAddress("江西南昌");
//        employee2.setBirth("1992-11-26");
//        employee2.setDescText("哈哈哈");
//        employee2.setEmail("116984293@qq.com");
//
//        list.add(employee1);
//        list.add(employee2);

        Employee employee = new Employee();
        employee.setOffset(0);
        employee.setRows(100);
        List<Employee> list = employeeService.getEmployeeList(employee);

        String filePath = "E:/xmlTest/" + getNowDay() + "/";
        String fileName = "Employee.xml";

        writeToXML(list,filePath,fileName);
    }

}
