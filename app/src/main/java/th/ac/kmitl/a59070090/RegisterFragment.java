package th.ac.kmitl.a59070090;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
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

public class RegisterFragment extends Fragment {

    Button registerBtn;
    String userID;
    String name;
    String age;
    String password;
    int ageInt;
    private SQLiteDatabase myDB;
    ArrayList<User> userList = new ArrayList<>();

    ContentValues cv;
    public RegisterFragment(){
        cv = new ContentValues();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container,false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        EditText _userId = getView().findViewById(R.id.register_user);
        final EditText _name = getView().findViewById(R.id.register_name);
        EditText _age = getView().findViewById(R.id.register_age);
        EditText _password = getView().findViewById(R.id.register_password);

        userID = _userId.getText().toString();
        name = _name.getText().toString();
        age = _age.getText().toString();
        ageInt = Integer.parseInt(age);
        password = _password.getText().toString();

        registerBtn = getView().findViewById(R.id.register_rergister_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //เชคความยาวไอดี
               if (userID.length() <6 || userID.length()>12){
                   Toast.makeText(getActivity(), "Save กรุณากรอกข้อมูลให้มีความยาวระหว่าง 6 -12 ตัวอักษร"
                           , Toast.LENGTH_SHORT)
                           .show();
               }
               //ดูว่ามันมีทั้งชื่อและนามสกุลมั้ย
               else if( !(name.contains(" "))) {
                   Toast.makeText(getActivity(), "กรุณากรอกทั้งชื่อและนามสกุล"
                           , Toast.LENGTH_SHORT)
                           .show();
               }
               //ดูว่าเอจเป็นตัวเลขไหม และพาสเวิดต้องมีความยาวมากกว่า 6
               else if (Integer.parseInt(age) > 10 && Integer.parseInt(age) < 80 ){
                   Toast.makeText(getActivity(), "กรุณากรอกข้อมูลให้ถูกต้อง"
                           , Toast.LENGTH_SHORT)
                           .show();
               }
               else if (password.length() <6 ){
                   Toast.makeText(getActivity(), "กรุณากรอกรหัสผ่านให้มากกว่า 6 ตัว"
                           , Toast.LENGTH_SHORT)
                           .show();
               }
               else{ //ถ้าผ่านให้แอดเข้า database

                   //open to use db
                   myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
                   myDB.execSQL(
                           "CREATE TABLE IF NOT EXISTS users (userID VARCHAR(15) PRIMARY KEY  , name VARCHAR(20), age INTEGER, password VARCHAR(20))"
                   );

                   User item = new User();
                   item.setContent(userID,name,ageInt,password);
                   cv = item.getContent();

                   //เพ่ิ่มข้อมูลลงในตารางของชื่อคนนั้นๆที่เข้ามา
                   myDB.insert("users", null, cv);
                   Log.d("REGISTER", "INSERT ALREADY");


                   //เก็บชื่อไว้เพื่อไปแสดงหน้า home
                   SharedPreferences.Editor sp = getContext().getSharedPreferences("name",Context.MODE_PRIVATE).edit();
                   sp.putString("name", name).apply();
                   sp.commit();


                   Toast.makeText(getActivity(), "Register Success"
                           , Toast.LENGTH_SHORT)
                           .show();


                    //สมัครสมาชิกเสร็จย้ายไปหน้า login
                   getActivity().getSupportFragmentManager()
                           .beginTransaction()
                           .replace(R.id.main_view, new LoginFragment())
                           .addToBackStack(null)
                           .commit();
               }
            }
        });
    }
}
