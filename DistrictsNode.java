package com.phase3.phase3;

import java.util.ArrayList;

public class DistrictsNode {
    String district;
    ArrayList<String> locations;
    public DistrictsNode(String district) {
        this.district = district;
        this.locations = new ArrayList<>();
    }
    public DistrictsNode() {}
    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public ArrayList<String> getLocations() {
        return locations;
    }
    public int hashCode(){
        int sum=0;
        for(int i=0;i<district.length();i++){
            sum+=district.charAt(i);
        }
        return sum;
    }
}
