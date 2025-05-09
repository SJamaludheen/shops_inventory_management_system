package com.onrender.requests;

import com.google.gson.Gson;
import jsonfactory.addProduct.AddProduct;
import jsonfactory.createOrder.CreateOrder;
import jsonfactory.updateProduct.UpdateProduct;
import jsonfactory.userLogin.UserLogin;

import java.io.FileReader;
import java.io.IOException;

public class RequestBuilder {
    public String objToJsonString(Object obj) {
        Gson gson = new Gson();
        //Java object to JSON - serialisation
        return gson.toJson(obj);
    }

    public UserLogin userLoginRequest() throws IOException {
        Gson gson = new Gson();
        //JSON to Java object, reading it from a file - deserialisation
        return gson.fromJson(new FileReader(System.getProperty("user.dir") + "/src/test/resources/requestpayloads/UserLogin.json"), UserLogin.class);
    }

    public AddProduct addProductRequest() throws IOException {
        Gson gson = new Gson();
        //JSON to Java object, reading it from a file - deserialisation
        return gson.fromJson(new FileReader(System.getProperty("user.dir") + "/src/test/resources/requestpayloads/AddProduct.json"), AddProduct.class);
    }

    public UpdateProduct updateProductRequest() throws IOException {
        Gson gson = new Gson();
        //JSON to Java object, reading it from a file - deserialisation
        return gson.fromJson(new FileReader(System.getProperty("user.dir") + "/src/test/resources/requestpayloads/UpdateProduct.json"), UpdateProduct.class);
    }

    public CreateOrder createOrderRequest() throws IOException {
        Gson gson = new Gson();
        //JSON to Java object, reading it from a file - deserialisation
        return gson.fromJson(new FileReader(System.getProperty("user.dir") + "/src/test/resources/requestpayloads/CreateOrder.json"), CreateOrder.class);
    }
}