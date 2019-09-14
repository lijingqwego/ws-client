package com.kaisn.controller;


import com.alibaba.fastjson.JSON;
import com.kaisn.WSClientUtils;
import com.kaisn.pojo.Msg;
import com.kaisn.utils.excel.ExcelUtils;
import com.kaisn.ws.Employee;
import com.kaisn.ws.EmployeeService;
import org.apache.activemq.command.ActiveMQDestination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

@Controller
@RequestMapping("/emp")
public class EmployeeController {

    private static Logger logger = Logger.getLogger(EmployeeController.class);

    private static final String SERVICE_NAME = "EmployeeService";

    @Autowired
    private JmsTemplate jmsTemplate;

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Msg getEmployeeList(HttpServletRequest request){
        List<Employee> employeeList = null;
        logger.debug("==========================");
        try {
            String empName = request.getParameter("empName");
            String page = request.getParameter("page");
            String limit = request.getParameter("limit");
            Employee employee = new Employee();
            employee.setEmpName(empName);
            employee.setOffset(Integer.parseInt(page)-1);
            employee.setRows(Integer.parseInt(limit));

            EmployeeService employeeService = (EmployeeService) WSClientUtils.getInstance(SERVICE_NAME,EmployeeService.class);

            employeeList = employeeService.getEmployeeList(employee);
        } catch (Exception e) {
            logger.error("获取列表失败！",e);
        }
        return Msg.success().add("list",employeeList);
    }

    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Msg addEmployee(HttpServletRequest request){
        boolean isSuccess=false;
        try {
            //接收参数
            String empName = request.getParameter("empName");
            String gender = request.getParameter("gender");
            String birth = request.getParameter("birth");
            String email = request.getParameter("email");
            String descText = request.getParameter("descText");
            String address = request.getParameter("address");
            //封装对象
            Employee employee = new Employee();
            employee.setEmpName(empName);
            employee.setGender(gender);
            employee.setBirth(birth);
            employee.setEmail(email);
            employee.setDescText(descText);
            employee.setAddress(address);

            sendMessage("add",employee);

            //EmployeeService employeeService = (EmployeeService) WSClientUtils.getInstance(SERVICE_NAME,EmployeeService.class);

            //添加数据
            //isSuccess = employeeService.addEmployee(employee);
        } catch (Exception e) {
            logger.error("添加数据失败！",e);
        }
        return Msg.success().add("result",isSuccess);
    }

    @ResponseBody
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public Msg delEmployee(HttpServletRequest request){
        boolean isSuccess=false;
        try {
            //接收参数
            String empId = request.getParameter("empId");
            //封装对象
            Employee employee = new Employee();
            employee.setEmpId(empId);

            sendMessage("del",employee);

//            EmployeeService employeeService = (EmployeeService) WSClientUtils.getInstance(SERVICE_NAME,EmployeeService.class);

            //删除数据
//            isSuccess = employeeService.delEmployee(employee);
        } catch (Exception e) {
            logger.error("删除数据失败！",e);
        }
        return Msg.success().add("result",isSuccess);
    }

    @ResponseBody
    @RequestMapping(value = "/get",method = RequestMethod.POST)
    public Msg getEmployee(HttpServletRequest request){
        Employee employee=null;
        try {
            //接收参数
            String empId = request.getParameter("empId");
            //封装对象
            Employee param = new Employee();
            param.setEmpId(empId);

            EmployeeService employeeService = (EmployeeService) WSClientUtils.getInstance(SERVICE_NAME,EmployeeService.class);

            //删除数据
            employee = employeeService.getEmployee(param);
        } catch (Exception e) {
            logger.error("获取数据失败！",e);
        }
        return Msg.success().add("result",employee);
    }

    @ResponseBody
    @RequestMapping(value = "/upd",method = RequestMethod.POST)
    public Msg updEmployee(HttpServletRequest request){
        boolean isSuccess=false;
        try {
            //接收参数
            String empId = request.getParameter("empId");
            String empName = request.getParameter("empName");
            String gender = request.getParameter("gender");
            String birth = request.getParameter("birth");
            String email = request.getParameter("email");
            String descText = request.getParameter("descText");
            String address = request.getParameter("address");
            //封装对象
            Employee employee = new Employee();
            employee.setEmpId(empId);
            employee.setEmpName(empName);
            employee.setGender(gender);
            employee.setBirth(birth);
            employee.setEmail(email);
            employee.setDescText(descText);
            employee.setAddress(address);

            sendMessage("upd",employee);

//            EmployeeService employeeService = (EmployeeService) WSClientUtils.getInstance(SERVICE_NAME,EmployeeService.class);

            //删除数据
//            isSuccess = employeeService.updEmployee(employee);
        } catch (Exception e) {
            logger.error("更新数据失败！",e);
        }
        return Msg.success().add("result",isSuccess);
    }

    @ResponseBody
    @RequestMapping(value = "/export",method = RequestMethod.GET)
    public Msg exportEmployee(HttpServletRequest request, HttpServletResponse response){
        List<Employee> employeeList = null;
        try {
            // 这里设置的文件格式是application/x-excel
            response.setContentType("application/x-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String("员工列表.xls".getBytes(), "ISO-8859-1"));
            ServletOutputStream outputStream = response.getOutputStream();

            EmployeeService employeeService = (EmployeeService) WSClientUtils.getInstance(SERVICE_NAME,EmployeeService.class);

            int count = employeeService.getEmployeeListCount(null);
            Employee employee = new Employee();
            employee.setOffset(0);
            employee.setRows(count);
            employeeList = employeeService.getEmployeeList(employee);
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("emps",employeeList);
            ExcelUtils.writeExcel(param,outputStream);
        } catch (Exception e) {
            logger.error("导出数据失败！",e);
        }
        return Msg.success().add("list",employeeList);
    }

    private void sendMessage(String operateType,Object data){

        Map<String, Object> messageMap = new HashMap<String, Object>();
        messageMap.put("operateType",operateType);
        messageMap.put("data",data);

        final String message = JSON.toJSONString(messageMap);

        jmsTemplate.send("emp-web-mq",new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });

        logger.error("发送成功!");
    }

}
