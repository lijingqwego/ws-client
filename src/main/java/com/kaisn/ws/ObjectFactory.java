
package com.kaisn.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.kaisn.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddEmployeeResponse_QNAME = new QName("http://impl.service.kaisn.com/", "addEmployeeResponse");
    private final static QName _AddEmployee_QNAME = new QName("http://impl.service.kaisn.com/", "addEmployee");
    private final static QName _GetEmployeeResponse_QNAME = new QName("http://impl.service.kaisn.com/", "getEmployeeResponse");
    private final static QName _GetEmployee_QNAME = new QName("http://impl.service.kaisn.com/", "getEmployee");
    private final static QName _GetEmployeeList_QNAME = new QName("http://impl.service.kaisn.com/", "getEmployeeList");
    private final static QName _UpdEmployee_QNAME = new QName("http://impl.service.kaisn.com/", "updEmployee");
    private final static QName _UpdEmployeeResponse_QNAME = new QName("http://impl.service.kaisn.com/", "updEmployeeResponse");
    private final static QName _DelEmployeeResponse_QNAME = new QName("http://impl.service.kaisn.com/", "delEmployeeResponse");
    private final static QName _GetEmployeeListCountResponse_QNAME = new QName("http://impl.service.kaisn.com/", "getEmployeeListCountResponse");
    private final static QName _GetEmployeeListCount_QNAME = new QName("http://impl.service.kaisn.com/", "getEmployeeListCount");
    private final static QName _GetEmployeeListResponse_QNAME = new QName("http://impl.service.kaisn.com/", "getEmployeeListResponse");
    private final static QName _DelEmployee_QNAME = new QName("http://impl.service.kaisn.com/", "delEmployee");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.kaisn.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetEmployeeListCountResponse }
     * 
     */
    public GetEmployeeListCountResponse createGetEmployeeListCountResponse() {
        return new GetEmployeeListCountResponse();
    }

    /**
     * Create an instance of {@link DelEmployee }
     * 
     */
    public DelEmployee createDelEmployee() {
        return new DelEmployee();
    }

    /**
     * Create an instance of {@link GetEmployeeListCount }
     * 
     */
    public GetEmployeeListCount createGetEmployeeListCount() {
        return new GetEmployeeListCount();
    }

    /**
     * Create an instance of {@link GetEmployeeListResponse }
     * 
     */
    public GetEmployeeListResponse createGetEmployeeListResponse() {
        return new GetEmployeeListResponse();
    }

    /**
     * Create an instance of {@link DelEmployeeResponse }
     * 
     */
    public DelEmployeeResponse createDelEmployeeResponse() {
        return new DelEmployeeResponse();
    }

    /**
     * Create an instance of {@link GetEmployeeList }
     * 
     */
    public GetEmployeeList createGetEmployeeList() {
        return new GetEmployeeList();
    }

    /**
     * Create an instance of {@link UpdEmployee }
     * 
     */
    public UpdEmployee createUpdEmployee() {
        return new UpdEmployee();
    }

    /**
     * Create an instance of {@link GetEmployee }
     * 
     */
    public GetEmployee createGetEmployee() {
        return new GetEmployee();
    }

    /**
     * Create an instance of {@link UpdEmployeeResponse }
     * 
     */
    public UpdEmployeeResponse createUpdEmployeeResponse() {
        return new UpdEmployeeResponse();
    }

    /**
     * Create an instance of {@link AddEmployeeResponse }
     * 
     */
    public AddEmployeeResponse createAddEmployeeResponse() {
        return new AddEmployeeResponse();
    }

    /**
     * Create an instance of {@link AddEmployee }
     * 
     */
    public AddEmployee createAddEmployee() {
        return new AddEmployee();
    }

    /**
     * Create an instance of {@link GetEmployeeResponse }
     * 
     */
    public GetEmployeeResponse createGetEmployeeResponse() {
        return new GetEmployeeResponse();
    }

    /**
     * Create an instance of {@link Employee }
     * 
     */
    public Employee createEmployee() {
        return new Employee();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddEmployeeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.service.kaisn.com/", name = "addEmployeeResponse")
    public JAXBElement<AddEmployeeResponse> createAddEmployeeResponse(AddEmployeeResponse value) {
        return new JAXBElement<AddEmployeeResponse>(_AddEmployeeResponse_QNAME, AddEmployeeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddEmployee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.service.kaisn.com/", name = "addEmployee")
    public JAXBElement<AddEmployee> createAddEmployee(AddEmployee value) {
        return new JAXBElement<AddEmployee>(_AddEmployee_QNAME, AddEmployee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployeeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.service.kaisn.com/", name = "getEmployeeResponse")
    public JAXBElement<GetEmployeeResponse> createGetEmployeeResponse(GetEmployeeResponse value) {
        return new JAXBElement<GetEmployeeResponse>(_GetEmployeeResponse_QNAME, GetEmployeeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.service.kaisn.com/", name = "getEmployee")
    public JAXBElement<GetEmployee> createGetEmployee(GetEmployee value) {
        return new JAXBElement<GetEmployee>(_GetEmployee_QNAME, GetEmployee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployeeList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.service.kaisn.com/", name = "getEmployeeList")
    public JAXBElement<GetEmployeeList> createGetEmployeeList(GetEmployeeList value) {
        return new JAXBElement<GetEmployeeList>(_GetEmployeeList_QNAME, GetEmployeeList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdEmployee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.service.kaisn.com/", name = "updEmployee")
    public JAXBElement<UpdEmployee> createUpdEmployee(UpdEmployee value) {
        return new JAXBElement<UpdEmployee>(_UpdEmployee_QNAME, UpdEmployee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdEmployeeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.service.kaisn.com/", name = "updEmployeeResponse")
    public JAXBElement<UpdEmployeeResponse> createUpdEmployeeResponse(UpdEmployeeResponse value) {
        return new JAXBElement<UpdEmployeeResponse>(_UpdEmployeeResponse_QNAME, UpdEmployeeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DelEmployeeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.service.kaisn.com/", name = "delEmployeeResponse")
    public JAXBElement<DelEmployeeResponse> createDelEmployeeResponse(DelEmployeeResponse value) {
        return new JAXBElement<DelEmployeeResponse>(_DelEmployeeResponse_QNAME, DelEmployeeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployeeListCountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.service.kaisn.com/", name = "getEmployeeListCountResponse")
    public JAXBElement<GetEmployeeListCountResponse> createGetEmployeeListCountResponse(GetEmployeeListCountResponse value) {
        return new JAXBElement<GetEmployeeListCountResponse>(_GetEmployeeListCountResponse_QNAME, GetEmployeeListCountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployeeListCount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.service.kaisn.com/", name = "getEmployeeListCount")
    public JAXBElement<GetEmployeeListCount> createGetEmployeeListCount(GetEmployeeListCount value) {
        return new JAXBElement<GetEmployeeListCount>(_GetEmployeeListCount_QNAME, GetEmployeeListCount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployeeListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.service.kaisn.com/", name = "getEmployeeListResponse")
    public JAXBElement<GetEmployeeListResponse> createGetEmployeeListResponse(GetEmployeeListResponse value) {
        return new JAXBElement<GetEmployeeListResponse>(_GetEmployeeListResponse_QNAME, GetEmployeeListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DelEmployee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.service.kaisn.com/", name = "delEmployee")
    public JAXBElement<DelEmployee> createDelEmployee(DelEmployee value) {
        return new JAXBElement<DelEmployee>(_DelEmployee_QNAME, DelEmployee.class, null, value);
    }

}
