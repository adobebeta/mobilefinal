package th.ac.kmitl.a59070090;

import android.content.ContentValues;
import android.content.Context;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.util.ArrayList;

public class ProfileSetupFragment extends Fragment {
    String userID;
    String name;
    String age;
    String password;
    int ageInt;
    private SQLiteDatabase myDB;
    ArrayList<User> userList = new ArrayList<>();

    ContentValues cv;

    public ProfileSetupFragment() {
        cv = new ContentValues();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_setup, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        EditText _userId = getView().findViewById(R.id.profile_user);
        EditText _name = getView().findViewById(R.id.profile_name);
        EditText _age = getView().findViewById(R.id.profile_age);
        EditText _password = getView().findViewById(R.id.profile_password);
        final EditText _quote = getView().findViewById(R.id.profile_quote);
        Button saveBtn = getView().findViewById(R.id.profile_saveBtn);

        userID = _userId.getText().toString();
        name = _name.getText().toString();
        age = _age.getText().toString();
        ageInt = Integer.parseInt(age);
        password = _password.getText().toString();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userID.length() < 6 || userID.length() > 12) {
                    Toast.makeText(getActivity(), "Save กรุณากรอกข้อมูลให้มีความยาวระหว่าง 6 -12 ตัวอักษร"
                            , Toast.LENGTH_SHORT)
                            .show();
                }
                //ดูว่ามันมีทั้งชื่อและนามสกุลมั้ย
                else if (!(name.contains(" "))) {
                    Toast.makeText(getActivity(), "กรุณากรอกทั้งชื่อและนามสกุล"
                            , Toast.LENGTH_SHORT)
                            .show();
                }
                //ดูว่าเอจเป็นตัวเลขไหม และพาสเวิดต้องมีความยาวมากกว่า 6
                else if (Character.isDigit(ageInt) || password.length() < 6) {
                    Toast.makeText(getActivity(), "กรุณากรอกข้อมูลให้ถูกต้อง"
                            , Toast.LENGTH_SHORT)
                            .show();
                } else { //ถ้าผ่านให้แอดเข้า database

                    //open to use db
                    myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
                    myDB.execSQL(
                            "CREATE TABLE IF NOT EXISTS users (userID VARCHAR(15) PRIMARY KEY  , name VARCHAR(20), age INTEGER, password VARCHAR(20))"
                    );

                    User item = new User();
                    item.setContent(userID, name, ageInt, password);
                    cv = item.getContent();

                    //อัพเดตข้อมูลลงในตารางของชื่อคนนั้นๆที่เข้ามา
//                    myDB.insert("users", null, cv);
                    myDB.update("users", cv, "id=" + userID, null);
                    Log.d("Profile", "INSERT ALREADY");


                    //Write file Quote

                    saveFile(_quote.getText().toString(), getActivity());

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new HomeFragement())
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }

    //Write file Quote
    public void saveFile(String input, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(input);
            outputStreamWriter.close();
            Toast.makeText(getActivity(), "เซฟข้อมูลเรียบร้อย", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }
}
