package com.example.customadapterlistview.model;

public class MonHoc {
    private String name;
    private String desc;
    private int pic;

    public MonHoc(String name, String desc, int pic) {
        this.name = name;
        this.desc = desc;
        this.pic = pic;
    }

    public MonHoc(String desc) {
        this.desc = desc;
    }

    public MonHoc(MonHoc other) {
        this.name = other.name;
        this.desc = other.desc;
        this.pic = other.pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }
}
