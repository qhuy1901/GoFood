package com.example.myapplication.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class UserSession
{
    // Lớp này lưu thông tin user đang đăng nhập vào ổ nhớ tạm

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public UserSession(Context context)
    {
        sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveUser(User user)
    {
        clearSession();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("user", json);
        editor.apply();
    }

    public User getUser()
    {
        User user = new User();
        String serializedObject = sharedPreferences.getString("user", null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<User>(){}.getType();
            user = gson.fromJson(serializedObject, type);
        }
        return user;
    }

    public void clearSession()
    {
        String json = "";
        editor.putString("user", json);
        editor.apply();
    }
}
