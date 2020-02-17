package com.example.myapplication.UserSession;


import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {


    public RegisterFragment() {
        // Required empty public constructor
    }
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private TextView companyName,login_txt;
    private EditText username,phone,password,confirm_password;
    private ProgressBar progressbar;
    private Button register;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

//        NOTE HAVE TO CHECK
        ((UserSessionActivity) getActivity()).updateStatusBarColor("#20000000");

// for Company name gradient
        TextPaint paint = companyName.getPaint();
        float width = paint.measureText("Puran Singh Verma");

        Shader textShader = new LinearGradient(0, 0, width, companyName.getTextSize(),
                new int[]{
                        Color.parseColor("#F5977A"),
                        Color.parseColor("#8457D5"),
                }, null, Shader.TileMode.CLAMP);
        companyName.setTextColor(Color.parseColor("#8242F8"));
        companyName.getPaint().setShader(textShader);



//login fragment on already have an acc. txt
        login_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UserSessionActivity)getActivity()).setFragment(new LoginFragment());
            }
        });
//OTP fragment on register btn
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username.setError(null);
                phone.setError(null);
                password.setError(null);
                confirm_password.setError(null);
                if (username.getText().toString().isEmpty()) {
                    username.setError("Required!");
                    return;
                }
                if (phone.getText().toString().isEmpty()) {
                    phone.setError("Required!");
                    return;
                }
                if (password.getText().toString().isEmpty()) {
                    password.setError("Required!");
                    return;
                }
                if (confirm_password.getText().toString().isEmpty()) {
                    confirm_password.setError("Required!");
                    return;
                }
                if (!VALID_EMAIL_ADDRESS_REGEX.matcher(username.getText().toString()).find()) {
                    username.setError("Invalid  E-mail");
                    return;
                }
                if (phone.getText().toString().length() != 10) {
                    phone.setError("Invalid Phone Number");
                    return;
                }
                if (!password.getText().toString().equals(confirm_password.getText().toString())){
                    confirm_password.setError("Password Mismatched!");
                    return;
                }

                createAccount();
            }

        });

    }

    private void init(View view)
    {
        companyName= view.findViewById(R.id.company_name);
        login_txt=view.findViewById(R.id.login_txt);
        username=view.findViewById(R.id.username);
        phone=view.findViewById(R.id.phone);
        password=view.findViewById(R.id.password);
        confirm_password=view.findViewById(R.id.confirm_password);
        progressbar=view.findViewById(R.id.progressBar);
        register=view.findViewById(R.id.btn_register);

    }

    private void createAccount(){
        progressbar.setVisibility(View.VISIBLE);
        mAuth.fetchSignInMethodsForEmail(username.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if (task.isSuccessful()) {
                        if (task.getResult().getSignInMethods().isEmpty()) {
                            ((UserSessionActivity)getActivity()).setFragment(new OTPFragment());
                        }
                        else {
                            username.setError("Username already exists!");
                            progressbar.setVisibility(View.INVISIBLE);
                        }
                }
                else {
                    String error=task.getException().getMessage();
                    Toast.makeText(getContext(),error, Toast.LENGTH_SHORT).show();

                }
                progressbar.setVisibility(View.INVISIBLE);
            }
        });
    }




}


