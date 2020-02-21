package com.example.myapplication.UserSession;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {



    public LoginFragment() {
        // Required empty public constructor
    }
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private EditText username_or_phone,password;
    private TextView forgot_password_txt,sign_up__txt;
    private Button login;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressbar;

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

                ((UserSessionActivity) getActivity()).setFragment(new RegisterFragment());
            }
        });

        forgot_password_txt.setOnClickListener(new View.OnClickListener() {
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


                if (username_or_phone.getText().toString().isEmpty()) {
                    username_or_phone.setError("Required!");
                    return;

                }

                if (password.getText().toString().isEmpty()) {
                    password.setError("Required!");
                    return;
                }

                if (VALID_EMAIL_ADDRESS_REGEX.matcher(username_or_phone.getText().toString()).find()) {
                    login(username_or_phone.getText().toString());

                } else if (username_or_phone.getText().toString().matches("\\d{10}")) {
                    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                    firebaseFirestore.collection("users/"+FirebaseAuth.getInstance().getUid()+"/details").whereEqualTo("phone", username_or_phone.getText().toString())
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<DocumentSnapshot> document =task.getResult().getDocuments();
                                if(document.isEmpty()){
                                    Toast.makeText(getContext(),FirebaseAuth.getInstance().getUid() , Toast.LENGTH_SHORT).show();
                                    username_or_phone.setError("Phone no. not registered!");
                                    return;

                                }else{
                                    String username =document.get(0).get("username").toString();
                                    login(username);

                                }
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    username_or_phone.setError("Invalid Email or Phone");

                }
            }
        });
    }

    private void init(View view){
        username_or_phone=view.findViewById(R.id.username_or_phone);
        password=view.findViewById(R.id.password);
        forgot_password_txt=view.findViewById(R.id.forgot_password_txt);
        sign_up__txt=view.findViewById(R.id.sign_up_txt);
        login=view.findViewById(R.id.btn_login);
        progressbar=view.findViewById(R.id.progressBar);


    }

    private void login(String username){
        progressbar.setVisibility(View.VISIBLE);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(username,password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent mainIntent =new Intent(getContext(), MainActivity.class);
                    startActivity(mainIntent);
                    getActivity().finish();

                }
                else{ String error=task.getException().getMessage();
                    Toast.makeText(getContext(),error,  Toast.LENGTH_SHORT).show();
                    progressbar.setVisibility(View.INVISIBLE);

                }
            }
        });
    }
}
