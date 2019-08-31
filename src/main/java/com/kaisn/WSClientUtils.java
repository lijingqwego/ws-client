package com.kaisn;

import com.kaisn.ws.Employee;
import com.kaisn.ws.EmployeeService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.List;

public class WSClientUtils {


    private static EmployeeService employeeService = null;

    public static EmployeeService getInstance() {

        try {
            if(employeeService != null){
                return employeeService;
            }
            //创建WSDL的URL，注意不是服务地址
            URL url = new URL("http://localhost:8080/emp-web/webservice/EmployeeService?wsdl");
            // 指定命名空间和服务名称
            QName qName = new QName("http://impl.service.kaisn.com/", "EmployeeService");
            Service service = Service.create(url, qName);
            // 通过getPort方法返回指定接口
            employeeService = service.getPort(EmployeeService.class);
            return employeeService;
        }catch (Exception e){
            return null;
        }
    }
}
