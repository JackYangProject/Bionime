package com.bionime.bionimedemo.tools;


public class Myaqi  {
    private String siteName ;
    private String county ;
    private String aqi ;
    private String status ;
    private String pollutant ;

    public Myaqi(String siteName, String county,String aqi, String status, String pollutant){
        this.siteName = siteName;
        this.county = county;
        this.aqi = aqi;
        this.status = status;
        this.pollutant = pollutant;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getCounty() {
        return county;
    }

    public String getAqi() {
        return aqi;
    }

    public String getStatus() {
        return status;
    }

    public String getPollutant() {
        return pollutant;
    }
}
