package th.ac.kmitl.a59070090;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {
    Button logintBtn;
    Button registerBtn;
    private SQLiteDatabase myDB;
    ArrayList<User> userList = new ArrayList<>();
    String _userId;
    String _password;

    EditText password;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    logintBtn = getView().findViewById(R.id.login_login_btn);
    registerBtn = getView().findViewById(R.id.login_register_btn);

        SharedPreferences spShow1 = getContext().getSharedPreferences("name", MODE_PRIVATE);
        Log.d("Home",spShow1.getString("name","0"));
        String namePreference = spShow1.getString("name","0");
        //Check ว่ามีการ login ไว้แล้วหรือยัง โดยการดูว่ามี userID อยู่ใน shareมั้ย ถ้ามีอยู่แล้วให้ไปหน้า
        if (!(namePreference.equals("0"))){
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new HomeFragement())
                    .addToBackStack(null)
                    .commit();
        }




        logintBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText userIDEdit = getView().findViewById(R.id.login_user);
            EditText passwordEdit = getView().findViewById(R.id.login_password);
            _userId = userIDEdit.getText().toString();
            _password = passwordEdit.getText().toString();


            //database
            //open to use db
            myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
            myDB.execSQL(
                    "CREATE TABLE IF NOT EXISTS users (userID VARCHAR(15) PRIMARY KEY  , name VARCHAR(20), age INTEGER, password VARCHAR(20))"
            );

            String userId,name,password;
            int age;

            //query data
            Cursor db_query = myDB.rawQuery("SELECT * FROM users WHERE userId ='"+_userId+"'AND password = '"+_password+"'", null);

            //add data from database => weightList  เอาจากดาต้าเบส มาเก็บไว้ในอาเรย์ลิสที่สร้างไว้
            while(db_query.moveToNext()) {
                userId = db_query.getString(0);
                name = db_query.getString(1);
                age = db_query.getInt(2);
                password = db_query.getString(3);


                userList.add(new User(userId,name,age,password));
            }
            db_query.close();
            myDB.close();

            //กรณีที่ไม่เติมอะไรปเลย
            if (_userId.isEmpty() || _password.isEmpty()){

                Toast.makeText(getActivity(), "Please fill out this form"
                        , Toast.LENGTH_SHORT)
                        .show();
            }

            //กรณีที่ไม่เจอในเทเบิ้ล
            else if (userList.isEmpty()){
                Toast.makeText(getActivity(), "Invalid user or password"
                        , Toast.LENGTH_SHORT)
                        .show();
            }
            else{ //กรณีที่ผ่าน แปลว่า UserId , password ตรงกับที่มีในระบบ
                SharedPreferences.Editor sp = getContext().getSharedPreferences("name",Context.MODE_PRIVATE).edit();
                sp.putString("username", _userId).apply();
                sp.commit();


                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new HomeFragement())
                        .addToBackStack(null)
                        .commit();
            }
        }
    });

    registerBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new RegisterFragment())
                    .addToBackStack(null)
                    .commit();
        }
    });
    }
}
