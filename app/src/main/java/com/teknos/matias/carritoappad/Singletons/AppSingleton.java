package com.teknos.matias.carritoappad.Singletons;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.teknos.matias.carritoappad.Entitats.CreditCard;
import com.teknos.matias.carritoappad.Entitats.Product;
import com.teknos.matias.carritoappad.Entitats.User;
import com.teknos.matias.carritoappad.WalletScreen;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppSingleton {

    private ArrayList<Product> lproducts;
    private char operation;
    private User user;
    FirebaseFirestore db =  FirebaseFirestore.getInstance();
    Context ctx;
    private HashMap<String,JSONObject> cloudUsers;
    private HashMap<String,JSONObject> cloudProducts;
    private boolean fromQR;
    private Product qrProduct;
    private Product currentProduct;
    private CreditCard  currentCreditCard;
    private ArrayList<CreditCard> lcreditCards;

    private static class SingletonInstance {

        private static AppSingleton INSTANCE = new AppSingleton();
    }


    public static AppSingleton getInstance() {
        return SingletonInstance.INSTANCE;
    }

    private AppSingleton() {
        operation = ' ';
        lproducts = new ArrayList<>();
        user = new User();
        cloudUsers = new HashMap<>();
        cloudProducts = new HashMap<>();
        fromQR = false;
        qrProduct = new Product();
        currentProduct = new Product();
        lcreditCards = new ArrayList<>();
        currentCreditCard = new CreditCard();
    }

    public char getOperation() {
        return operation;
    }

    public void setOperation(char operation) {
        this.operation = operation;
    }

    public ArrayList<Product> getLproducts() {
        return lproducts;
    }

    public void setLproducts(ArrayList<Product> lproducts) {
        this.lproducts = lproducts;
    }

    public Product getQrProduct() {
        return qrProduct;
    }

    public void setQrProduct(Product qrProduct) {
        this.qrProduct = qrProduct;
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }

    public void setCurrentProduct(Product currentProduct) {
        this.currentProduct = currentProduct;
    }

    public HashMap<String, JSONObject> getCloudUsers() {
        return cloudUsers;
    }

    public void setCloudUsers(HashMap<String, JSONObject> users) {
        this.cloudUsers = users;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Context getCtx() {
        return ctx;
    }

    public CreditCard getCurrentCreditCard() {
        return currentCreditCard;
    }

    public void setCurrentCreditCard(CreditCard currentCreditCard) {
        this.currentCreditCard = currentCreditCard;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public ArrayList<CreditCard> getLcreditCards() {
        return lcreditCards;
    }

    public void setLcreditCards(ArrayList<CreditCard> lcreditCards) {
        this.lcreditCards = lcreditCards;
    }

    public boolean isFromQR() {
        return fromQR;
    }

    public void setFromQR(boolean fromQR) {
        this.fromQR = fromQR;
    }

    public void readUsers(FirestoreCallback firestoreCallback){
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData() );
                                cloudUsers.put(document.getId(),new JSONObject(document.getData()));
                            }
                            firestoreCallback.onCallback(cloudUsers);

                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    public boolean checkRegister(String userName){
        boolean registered = false;

        for (JSONObject value : cloudUsers.values()) {
            User user = new User(value);
            if (user.getUsername().equals(userName)){
                registered = true;
                this.user= user;
                break;
            }
        }
        return registered;
    }

    public void readUserProducts(){
        ArrayList<Product> userProducts = user.getProducts();

        this.lproducts.addAll(userProducts);

    }
    public void readUserCreditCards(){
        ArrayList<CreditCard> creditCards = user.getCreditCards();

        this.lcreditCards.addAll(creditCards);

    }


    public void addProduct2( Product product) {
        DocumentReference ref = db.collection("users").document(user.getUsername());
        ref.update("products", FieldValue.arrayUnion(product));
        lproducts.add(product);

    }

    public void addCreditCard( CreditCard creditCard) {
        DocumentReference ref = db.collection("users").document(user.getUsername());
        ref.update("creditCards", FieldValue.arrayUnion(creditCard));
        lcreditCards.add(creditCard);
    }

    public interface FirestoreCallback{

        void onCallback (HashMap<String,JSONObject> hashMap);

    }
    public void addUser2(){
        db.collection("users").document(this.user.getUsername()).set(user);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(user);
        try {
            cloudUsers.put(this.user.getUsername(),new JSONObject(jsonStr));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(String field, String value){
        DocumentReference userRef = db.collection("users").document(user.getUsername());
        userRef
                .update(field,value )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error updating document", e);
                    }
                });
    }
    public void deleteCreditCard(){
        DocumentReference ref = db.collection("users").document(user.getUsername());
        ref.update("creditCards", FieldValue.arrayRemove(currentCreditCard));
        lcreditCards.remove(currentCreditCard);
    }
    public void deleteProduct(){
        DocumentReference ref = db.collection("users").document(user.getUsername());
        ref.update("products", FieldValue.arrayRemove(currentCreditCard));
        lproducts.remove(currentProduct);
    }
    public void deleteAllProducts(){
        DocumentReference ref = db.collection("users").document(user.getUsername());
        ref.update("products", FieldValue.arrayRemove(0,lproducts.size()));
        lproducts = new ArrayList<>();
    }

}