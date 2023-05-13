package com.example.debug;

public class toolModel {

    private int id;
    private int rate;
    private int rateNum;
    private String name;
    private String model;
    private String overview;
    private int cost;
    private String prodYear;

    public toolModel(int id, int rate, String name, String model, String overview, int cost, String prodYear,int rateNum) {
        this.id = id;
        this.rate = rate;
        this.name = name;
        this.model = model;
        this.overview = overview;
        this.cost = cost;
        this.prodYear = prodYear;
        this.rateNum = rateNum;
    }


    public int getId(){return id;}

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate/rateNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getProdYear() {
        return prodYear;
    }

    public void setProdYear(String prodYear) {
        this.prodYear = prodYear;
    }

    public int getRateNum() {
        return rateNum;
    }

    public void setRateNum(int rateNum) {
        this.rateNum = rateNum;
    }

    @Override
    public String toString() {
        return  "id:    " + id +
                "\nrate:    " + rate +"("+""+")"+
                "\nname:    " + name +
                "\nmodel:   " + model +
                "\n \noverview:    " + overview +
                "\ncost:   " + cost +
                "\nprodYear:    " + prodYear;
    }
    public String abstracttoString() {
        return  "\nrate:    " + rate +
                "\nname:    " + name +
                "\nmodel:   " + model;
    }
}

