package me.fangx.zhihu.modle.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

public class TestBean implements Parcelable {
    private String goods_project_id;
    private String gcode;
    private String project;
    private int threshold_code;
    private String threshold;
    private int cycle;
    private String cycle_code;
    private String operator;
    private Timestamp operator_time;

    private String building;
    private String goods_name;
    private String goods_code;
    private String goods_part;
    private int number;

    private String department;
    private String department_group;
    private int project_number;

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

    public int getProject_number() {
        return project_number;
    }

    public void setProject_number(int project_number) {
        this.project_number = project_number;
    }

    public String getGoods_project_id() {
        return goods_project_id;
    }

    public void setGoods_project_id(String goods_project_id) {
        this.goods_project_id = goods_project_id;
    }

    public String getGcode() {
        return gcode;
    }

    public void setGcode(String gcode) {
        this.gcode = gcode;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public int getThreshold_code() {
        return threshold_code;
    }

    public void setThreshold_code(int threshold_code) {
        this.threshold_code = threshold_code;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public String getCycle_code() {
        return cycle_code;
    }

    public void setCycle_code(String cycle_code) {
        this.cycle_code = cycle_code;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Timestamp getOperator_time() {
        return operator_time;
    }

    public void setOperator_time(Timestamp operator_time) {
        this.operator_time = operator_time;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_code() {
        return goods_code;
    }

    public void setGoods_code(String goods_code) {
        this.goods_code = goods_code;
    }

    public String getGoods_part() {
        return goods_part;
    }

    public void setGoods_part(String goods_part) {
        this.goods_part = goods_part;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    protected TestBean(Parcel in) {
        this.goods_project_id=in.readString();
        this.gcode=in.readString();
        this.project=in.readString();
        this.threshold_code=in.readInt();
        this.threshold=in.readString();
        this.cycle=in.readInt();
        this.cycle_code=in.readString();
        this.operator=in.readString();
//        this.operator_time=in.read();

        this.building=in.readString();
        this.goods_name=in.readString();
        this.goods_code=in.readString();
        this.goods_part=in.readString();

        this.number=in.readInt();

        this.department=in.readString();
        this.department_group=in.readString();
        this.project_number=in.readInt();

    }

    public static final Parcelable.Creator<TestBean> CREATOR = new Parcelable.Creator<TestBean>() {
        @Override
        public TestBean createFromParcel(Parcel in) {
            return new TestBean(in);
        }

        @Override
        public TestBean[] newArray(int size) {
            return new TestBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.goods_project_id);
        parcel.writeString(this.gcode);
        parcel.writeString(this.project);
        parcel.writeInt(this.threshold_code);
        parcel.writeString(this.threshold);
        parcel.writeInt(this.cycle);
        parcel.writeString(this.cycle_code);
        parcel.writeString(this.operator);
        parcel.writeString(this.building);
        parcel.writeString(this.goods_name);
        parcel.writeString(this.goods_code);
        parcel.writeString(this.goods_part);
        parcel.writeInt(this.number);
        parcel.writeString(this.department);
        parcel.writeString(this.department_group);
        parcel.writeInt(this.project_number);
    }
    public TestBean(){

    }
}
