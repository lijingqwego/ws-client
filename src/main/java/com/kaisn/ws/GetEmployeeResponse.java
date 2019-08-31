
package com.kaisn.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getEmployeeResponse", propOrder = {
    "_return"
})
public class GetEmployeeResponse {

    @XmlElement(name = "return")
    protected Employee _return;

    public Employee getReturn() {
        return _return;
    }

    public void setReturn(Employee value) {
        this._return = value;
    }

}
