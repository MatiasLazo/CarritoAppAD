package com.teknos.matias.carritoappad.Entitats;

import org.json.JSONException;
import org.json.JSONObject;

public class Product {
    private int id;
    private String name;
    private String brand;
    private int measures;
    private int energeticValue;
    private String expirationDate;
    private int units;
    private float price;
    private String URL;
    private int quantity;

    public Product(int id, String name, String brand, int measures, int energeticValue, String expirationDate, int units, float price, String URL, int quantity) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.measures = measures;
        this.energeticValue = energeticValue;
        this.expirationDate = expirationDate;
        this.units = units;
        this.price = price;
        this.URL = URL;
        this.quantity = 0;
    }

    public Product(){
        this.quantity= 0;
    }

    public Product(JSONObject jsonObject){
        try {
            this.id = jsonObject.getInt("id");
            this.name = jsonObject.getString("name");
            this.brand = jsonObject.getString("brand");
            this.measures = jsonObject.getInt("measures");
            this.energeticValue = jsonObject.getInt("energeticValue");
            this.expirationDate = jsonObject.getString("expirationDate");
            this.units = jsonObject.getInt("units");
            this.price = (float) jsonObject.getDouble("price");
            this.URL = jsonObject.getString("URL");
            this.quantity = jsonObject.getInt("quantity");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getMeasures() {
        return measures;
    }

    public void setMeasures(int measures) {
        this.measures = measures;
    }

    public int getEnergeticValue() {
        return energeticValue;
    }

    public void setEnergeticValue(int energeticValue) {
        this.energeticValue = energeticValue;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
