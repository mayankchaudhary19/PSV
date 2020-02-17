package com.example.myapplication.UserSession;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {



    public LoginFragment() {
        // Required empty public constructor
    }

    private EditText username_or_phone,password;
    private TextView forgot_password,sign_up__txt;
    private Button login;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((UserSessionActivity)getActivity()).updateStatusBarColor("#F5977A");
        return inflater.inflate(R.layout.fragment_login, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);



        sign_up__txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UserSessionActivity)getActivity()).setFragment(new RegisterFragment());
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UserSessionActivity) getActivity()).setFragment(new ForgotPasswordFragment());
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username_or_phone.setError(null);
                password.setError(null);

                if(username_or_phone.getText().toString().isEmpty()){
                    username_or_phone.setError("Required!");
                    return;
                }

                if (password.getText().toString().isEmpty()){
                    password.setError("Required!");
                    return;
                }

            }
        });

    }
    private void init(View view){
        username_or_phone=view.findViewById(R.id.username_or_phone);
        password=view.findViewById(R.id.password);
        forgot_password=view.findViewById(R.id.forgot_password);
        sign_up__txt=view.findViewById(R.id.sign_up_txt);
        login=view.findViewById(R.id.btn_login);


    }
}
