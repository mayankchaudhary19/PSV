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
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OTPFragment extends Fragment {

    private TextView verification_txt,otp_sent_txt;
    private EditText enter_otp ;
    private Button verify;
    public OTPFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_otp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((UserSessionActivity)getActivity()).updateStatusBarColor("#20000000");
        init(view);
        TextPaint paint = verification_txt.getPaint();
        float width = paint.measureText("Verification");

        Shader textShader = new LinearGradient(0, 0, width, verification_txt.getTextSize(),
                new int[]{
                        Color.parseColor("#F5977A"),
                        Color.parseColor("#8457D5"),
                }, null, Shader.TileMode.CLAMP);
        verification_txt.setTextColor(Color.parseColor("#8242F8"));
        verification_txt.getPaint().setShader(textShader);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void init(View view){
        verification_txt=view.findViewById(R.id.verification_txt);
        otp_sent_txt=view.findViewById(R.id.otp_sent_txt);
        enter_otp=view.findViewById(R.id.enter_otp);
        verify=view.findViewById(R.id.verify_btn);

    }
}
