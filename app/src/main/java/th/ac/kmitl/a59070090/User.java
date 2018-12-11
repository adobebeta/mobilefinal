package th.ac.kmitl.a59070090;

import android.content.ContentValues;

public class User {
    private String userId;
    private String name;
    private int age;
    private String password;

    public User() {
    }

    public User(String userId, String name, int age, String password) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.password = password;
    }

    //ContenValues
    ContentValues row = new ContentValues();
    public ContentValues getContent() {
        return row;
    }

    public void setContent(String userId, String name, int age, String password) {
        this.row.put("userId", userId);
        this.row.put("name", name);
        this.row.put("age", age);
        this.row.put("password", password);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
