package com.example.myapplication.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CartSession
{
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public CartSession(Context context)
    {
        sharedPreferences = context.getSharedPreferences("cart", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void addToCart(CartItem cartItem)
    {
        List<CartItem> cart = getCart();
        if(cart == null)
        {
            cart = new ArrayList<>();
            cart.add(cartItem);
        }
        else
        {
            int index = isExit(cartItem.product.getProductId());
//            Log.d("Huy", index + " - index");
            if(index == -1)
            {
                cart.add(cartItem);
            }
            else
            {
                cart.get(index).quantity++;
            }
        }
        saveCart(cart);
    }

    private void saveCart(List<CartItem> cart)
    {
        Gson gson = new Gson();
        String json = gson.toJson(cart);
        editor.putString("cart", json);
        editor.apply();
    }

    public List<CartItem> getCart(){
        List<CartItem> arrayItems = new ArrayList<>();
        String serializedObject = sharedPreferences.getString("cart", null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<CartItem>>(){}.getType();
            arrayItems = gson.fromJson(serializedObject, type);
        }
        return arrayItems;
    }

    public void removeItem(String productId)
    {
        List<CartItem> cart = getCart();
        int index = isExit(productId);
        if(index != -1)
        {
            cart.remove(index);
            saveCart(cart);
        }
    }

    public void removeAllItem()
    {
        List<CartItem> cart = getCart();
        cart.clear();
        saveCart(cart);
    }

    private int isExit(String productId)
    {
        List<CartItem> cart = getCart();
        for(int i = 0; i < cart.size(); i++)
        {
//            Log.d("Huy", cart.get(i).product.getProductId() + " -##- " + productId);
            if(cart.get(i).product.getProductId().equals(productId))
                return i;
        }
        return -1;
    }

    public void updateQuantity(String productId, int newQuantity)
    {
        if(newQuantity == 0)
        {
            removeItem(productId);
        }
        else
        {
            List<CartItem> cart = getCart();
            int index = isExit(productId);
            if(index != -1)
            {
                cart.get(index).quantity = newQuantity;
                saveCart(cart);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int getTotal()
    {
        if(getCart() == null)
            return 0;
        int sum =  getCart().stream().mapToInt(o -> o.product.getPrice() * o.quantity).sum();
        return  sum;
    }
}
