package com.teknos.matias.carritoappad.Entitats;

import org.json.JSONException;
import org.json.JSONObject;

public class CreditCard {
    private String name;
    private int cardNumber;
    private String validThru;
    private String service;
    private int cvv;
    private String URL;

    public CreditCard(){
        name = "N/A";
        cardNumber = 0000000000;
        validThru= "N/A";
        service = "N/A";
        cvv = 000;
        URL = "https://www.transparentpng.com/thumb/credit-card/gaEUTn-black-credit-card-images-png.png";
    }
    public CreditCard(CreditCard creditCard){
        this.name = creditCard.getName();
        this.cardNumber = creditCard.getCardNumber();
        this.validThru = creditCard.getValidThru();
        this.service = creditCard.getService();
        this.cvv = creditCard.getCvv();
        this.URL = creditCard.getURL();

    }

    public CreditCard(JSONObject jsonObject){
        try {
            this.name = jsonObject.getString("name");
            this.cardNumber = jsonObject.getInt("cardNumber");
            this.validThru = jsonObject.getString("validThru");
            this.service = jsonObject.getString("service");
            this.cvv = jsonObject.getInt("cvv");
            setURL(this.service);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setURL(String service){
        String mastercard = "https://miro.medium.com/max/1400/1*vFE6xA9l_5CsyfqjPBrTxg.png";
        String visa = "https://www.ibercaja.es/public/img/tarjetas/tarjeta-visa-universal7.png";
        String blank =  "https://www.transparentpng.com/thumb/credit-card/gaEUTn-black-credit-card-images-png.png";

        if (service.equals("mastercard")){
            this.URL = mastercard;
        }
        else if(service.equals("visa")) {
            this.URL = visa;
        }
        else {
            this.URL = blank;
        }
    }
    public String getURL() {
        return URL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getValidThru() {
        return validThru;
    }

    public void setValidThru(String validThru) {
        this.validThru = validThru;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }
}
