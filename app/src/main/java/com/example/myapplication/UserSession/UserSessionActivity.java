package com.example.myapplication.UserSession;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.myapplication.R;

public class UserSessionActivity extends AppCompatActivity {

    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_session);
        getSupportActionBar().hide();

        frameLayout =findViewById(R.id.framelayout);
        setFragment(new LoginFragment());
    }

    public void updateStatusBarColor(String color){
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor(color));
//        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryRegister));
    }

    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.fade_out,android.R.anim.slide_in_left,android.R.anim.fade_out);

        if(fragment instanceof ForgotPasswordFragment || fragment instanceof OTPFragment ){
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();

    }
}
