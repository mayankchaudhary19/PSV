package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.UserSession.UserSessionActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.List;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditAccountActivity extends AppCompatActivity {
    CircleImageView profileImg;
    Button updateChanges,changePassword,logout,removePhoto;
    ImageView imgBack,imgLogout;
    EditText firstName,lastName,email,phone,companyName,officeAddress;
    private Uri photoUri;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static Pattern usrNamePtrn = Pattern.compile("^[a-z_-]{6,14}$");
//    public static final Pattern VALID_FIRST_AND_LAST_NAME_REGEX =
//            Pattern.compile("^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}", Pattern.CASE_INSENSITIVE);

    public EditAccountActivity(){

    }

//    public EditAccountActivity(String email,)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        getSupportActionBar().hide();
        Intent mIntent = getIntent();
        String color = mIntent.getStringExtra("color");
        updateStatusBarColor(color);

        init();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
                Dexter.withActivity(EditAccountActivity.this)
                        .withPermissions(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        ).withListener(new MultiplePermissionsListener() {
                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if(report.areAllPermissionsGranted()){
                            selectImage();
                        }else{
                            Toast.makeText(EditAccountActivity.this, "Please allow Permissions!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
                }).check();
            }
        });

        removePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoUri= null;
                profileImg.setImageResource(R.drawable.ic_user_profile);


            }
        });



        updateChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setError(null);
                phone.setError(null);
                if (email.getText().toString().isEmpty()) {
                    email.setError("Required!");
                    return;
                }
                if (phone.getText().toString().isEmpty()) {
                    phone.setError("Required!");
                    return;
                }

                if (!VALID_EMAIL_ADDRESS_REGEX.matcher(email.getText().toString()).find()) {
                    email.setError("Invalid  E-mail");
                    return;
                }
                if (phone.getText().toString().length() != 10) {
                    phone.setError("Invalid Phone Number");
                    return;
                }

            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }
//    Context context;

//    public Context getContext() {
//        return context;
//    }

    private void selectImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setActivityMenuIconColor(ContextCompat.getColor(this, R.color.colorPrimaryCropper))
                .setActivityTitle("Profile Picture")
                .setAspectRatio(1,1)
                .setFixAspectRatio(true)
                .start(this);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                photoUri = result.getUri();

                Glide
                        .with(this)
                        .load(photoUri)
                        .centerCrop()
                        .placeholder(R.drawable.ic_user_profile)
                        .into(profileImg);



            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(EditAccountActivity.this, error.getMessage() , Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void init(){
        profileImg=findViewById(R.id.profile_image);
        updateChanges=findViewById(R.id.btn_update_changes);
        changePassword=findViewById(R.id.btn_change_pass);
        logout=findViewById(R.id.btn_logout);
        removePhoto=findViewById(R.id.btn_removePhoto);
        firstName=findViewById(R.id.first_name);
        lastName=findViewById(R.id.last_name);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        companyName=findViewById(R.id.company_name);
        officeAddress=findViewById(R.id.office_address);
        imgBack=findViewById(R.id.img_back);
        imgLogout=findViewById(R.id.img_logout);

    }


    public void updateStatusBarColor(String color){
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor(color));
    }

//    @Override
//    public void onBackPressed() {
//       super.onBackPressed();
//        moveTaskToBack(true);
//    }
}
