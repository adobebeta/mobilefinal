package th.ac.kmitl.a59070090;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import th.ac.kmitl.a59070090.Friend.MyfriendFragment;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragement extends Fragment{


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragement_home,container,false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




        TextView hello = getView().findViewById(R.id.hello);
        TextView quote = getView().findViewById(R.id.quote);
        Button profileBtn = getView().findViewById(R.id.profileSetBtn);
        Button myfriendBtn = getView().findViewById(R.id.myfriendBtn);
        Button signoutBtn = getView().findViewById(R.id.signoutBtn);


        SharedPreferences spShow = getContext().getSharedPreferences("name", MODE_PRIVATE);
        Log.d("LOGIN",spShow.getString("name","0"));
        String name = spShow.getString("name","0");

        hello.setText("Hello "+name); //ตั้งค่าว่า Hello ตามด้วยชื่อ

        //--------------------------------
        String quoteStr = "";
        try {
            InputStream inputStream = getActivity().openFileInput("config.txt");
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString).append("\n");
                }
                inputStream.close();
                quoteStr = stringBuilder.toString();
                quote.setText(quoteStr);
        }

        //-------------------------------------


        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new ProfileSetupFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        myfriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MyfriendFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ถ้าผู้ใช้กด signOut ให้ไปเคลียค่าใน sharePreference
                //โหมดแก้ไข
                SharedPreferences.Editor sp = getContext().getSharedPreferences("name", MODE_PRIVATE).edit();
                sp.putString("name", "").apply();
                sp.commit();


                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new LoginFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });



    } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
