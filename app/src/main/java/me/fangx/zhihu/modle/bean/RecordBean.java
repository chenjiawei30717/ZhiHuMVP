package me.fangx.zhihu.modle.bean;

import java.sql.Timestamp;

public class RecordBean {
    private String department;
    private String department_group;
    private String part;
    private String okng;
    private String operator;
    private String record_id;
    private Timestamp record_create_time;
    private int number;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment_group() {
        return department_group;
    }

    public void setDepartment_group(String department_group) {
        this.department_group = department_group;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getOkng() {
        return okng;
    }

    public void setOkng(String okng) {
        this.okng = okng;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public Timestamp getRecord_create_time() {
        return record_create_time;
    }

    public void setRecord_create_time(Timestamp record_create_time) {
        this.record_create_time = record_create_time;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "RecordBean{" +
                "department='" + department + '\'' +
                ", department_group='" + department_group + '\'' +
                ", part='" + part + '\'' +
                ", okng='" + okng + '\'' +
                ", operator='" + operator + '\'' +
                ", record_id='" + record_id + '\'' +
                ", record_create_time=" + record_create_time +
                ", number=" + number +
                '}';
    }
}
