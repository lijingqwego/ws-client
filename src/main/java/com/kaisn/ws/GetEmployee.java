
package com.kaisn.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getEmployee", propOrder = {
    "arg0"
})
public class GetEmployee {

    protected Employee arg0;

    public Employee getArg0() {
        return arg0;
    }

    public void setArg0(Employee value) {
        this.arg0 = value;
    }

}
