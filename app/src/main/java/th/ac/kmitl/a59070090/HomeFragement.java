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
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new LoginFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });



    }
}
