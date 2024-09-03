package com.phase3.phase3;

public class Martyr implements Comparable<Martyr>{
    private String name, gender, district, location;
    private int age;

    public Martyr(String name, String gender, String district, String location, int age) {
        super();
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.district = district;
        this.location = location;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int compareTo(Martyr o) {
        int comp = district.compareTo(o.district);
        if(comp!=0) return comp;
        return name.compareToIgnoreCase(o.getName());
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getData() {
        return name+","+age+","+gender;
    }
    public boolean equals(Object o) {
        if(o instanceof Martyr) return name.equals(((Martyr)o).getName());
        return false;
    }
    public String toString() {
        return name;
    }
    public String getDistrict(){
        return district;
    }
    public void setDistrict(String district){
        this.district = district;
    }
    public String getLocation(){
        return location;
    }
    public void setlocation(String location){
        this.location = location;
    }
}

