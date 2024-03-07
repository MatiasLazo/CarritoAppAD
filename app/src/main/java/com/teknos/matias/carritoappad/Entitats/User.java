package com.teknos.matias.carritoappad.Entitats;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class User {
    private String username;
    private String name;
    private String lastname;
    private String password;
    private ArrayList<CreditCard> creditCards;
    private ArrayList<Product> products;
    private float balance;
    public User(){

    }
    public User (JSONObject jsonObject) {
        try {
            this.name = jsonObject.getString("name");
            this.lastname = jsonObject.getString("lastname");
            this.password = jsonObject.getString("password");
            this.username = jsonObject.getString("username");
            this.products = new ArrayList<>();
            this.creditCards = new ArrayList<>();
            this.balance = 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray("products");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Product jsonProduct = null;
        if (jsonArray != null){
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    jsonProduct = new Product(jsonArray.getJSONObject(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                products.add(jsonProduct);
            }
        }
        jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray("creditCards");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CreditCard jsonCard = null;
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    jsonCard = new CreditCard(jsonArray.getJSONObject(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                creditCards.add(jsonCard);

            }
        }
    }

    public ArrayList<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(ArrayList<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", creditCards=" + creditCards +
                ", products=" + products +
                '}';
    }
}
