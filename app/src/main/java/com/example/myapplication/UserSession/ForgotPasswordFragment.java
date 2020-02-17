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

import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPasswordFragment extends Fragment {


    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    private TextView forgot_pass_heading;
    private EditText reg_mail_or_phone;
    private ProgressBar progressbar;
    private Button reset_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((UserSessionActivity)getActivity()).updateStatusBarColor("#20000000");
        init(view);
        TextPaint paint = forgot_pass_heading.getPaint();
        float width = paint.measureText("Verification");

        Shader textShader = new LinearGradient(0, 0, width, forgot_pass_heading.getTextSize(),
                new int[]{
                        Color.parseColor("#F5977A"),
                        Color.parseColor("#8457D5"),
                }, null, Shader.TileMode.CLAMP);
        forgot_pass_heading.setTextColor(Color.parseColor("#8242F8"));
        forgot_pass_heading.getPaint().setShader(textShader);

//        login_txt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((UserSessionActivity)getActivity()).setFragment(new LoginFragment());
//            }
//        });

    }
    private void init(View view){
        forgot_pass_heading=view.findViewById(R.id.forgot_pass_heading);
        reg_mail_or_phone=view.findViewById(R.id.registered_mail_or_phone);
        progressbar=view.findViewById(R.id.progressBar);
        reset_btn=view.findViewById(R.id.resend_btn);


    }
}
