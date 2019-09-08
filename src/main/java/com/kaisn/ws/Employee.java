
package com.kaisn.ws;

import com.kaisn.pojo.FieldMeta;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "employee", propOrder = {
    "address",
    "birth",
    "descText",
    "email",
    "empId",
    "empName",
    "gender",
    "offset",
    "rows"
})
public class Employee {

    @FieldMeta(flag = true)
    protected String address;

    @FieldMeta(flag = true)
    protected String birth;

    @FieldMeta(flag = true)
    protected String descText;

    @FieldMeta(flag = true)
    protected String email;

    @FieldMeta(flag = true)
    protected String empId;

    @FieldMeta(flag = true)
    protected String empName;

    @FieldMeta(flag = true)
    protected String gender;

    @FieldMeta
    protected int offset;

    @FieldMeta
    protected int rows;

    public String getAddress() {
        return address;
    }

    public void setAddress(String value) {
        this.address = value;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String value) {
        this.birth = value;
    }

    public String getDescText() {
        return descText;
    }

    public void setDescText(String value) {
        this.descText = value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String value) {
        this.empId = value;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String value) {
        this.empName = value;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String value) {
        this.gender = value;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int value) {
        this.offset = value;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int value) {
        this.rows = value;
    }

}
