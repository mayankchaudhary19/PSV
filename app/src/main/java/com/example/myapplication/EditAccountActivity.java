package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.UserSession.UserSessionActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditAccountActivity extends AppCompatActivity {
    private static final String TAG ="EditAccountActivity__" ;
    CircleImageView profileImg;
    Button updateChanges,changePassword,logout,removePhoto;
    ImageView imgBack,imgLogout;
    EditText firstName,lastName,email,phone,companyName,officeAddress;
    private Uri photoUri;
    private String url;
private ConstraintLayout progress_layout;
    private StorageReference storage;
    private FirebaseAuth firebaseAuth;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static Pattern usrNamePtrn = Pattern.compile("^[a-z_-]{6,14}$");
    //private static String emaill="null";
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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        storage=FirebaseStorage.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();
        progress_layout.setVisibility(View.VISIBLE);
        db.collection( "users/"+FirebaseAuth.getInstance().getUid()+"/details").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments()) {
                    phone.setText(documentSnapshot.getString("phone"));
                    email.setText(documentSnapshot.getString("username"));
                    companyName.setText(documentSnapshot.getString("companyName"));
                    officeAddress.setText(documentSnapshot.getString("officeAddress"));
                    firstName.setText(documentSnapshot.getString("firstName"));
                    lastName.setText(documentSnapshot.getString("lastName"));
                    progress_layout.setVisibility(View.INVISIBLE);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditAccountActivity.this, "Failed to Update Changes!", Toast.LENGTH_SHORT).show();


            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EditAccountActivity.this,MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent=new Intent(EditAccountActivity.this,UserSessionActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        removePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoUri= null;
                profileImg.setImageResource(R.drawable.ic_user_profile);


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
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent=new Intent(EditAccountActivity.this,UserSessionActivity.class);
                startActivity(intent);
                finishAffinity();
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
updateDetails();
                updateChanges();
            }
        });
    }

//

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

    private void updateChanges() {

        if (photoUri != null) {
            final StorageReference ref = storage.child("profiles/" + firebaseAuth.getCurrentUser().getUid() + ".jpg");
            UploadTask uploadTask = ref.putFile(photoUri);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    // Continue with the task to get the download URL
                    return ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            url = uri.toString();
                        }
                    });
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
//                        Uri downloadUri = task.getResult();

//                        url = ref.getDownloadUrl().toString();
//upladeDetails()
                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(EditAccountActivity.this, error, Toast.LENGTH_SHORT).show();
                        // Handle failures
                        // ...
                    }
                }
            });


        }

    }

//        if (!firstName.getText().toString().isEmpty()) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("firstName", firstName);
//            FirebaseFirestore.getInstance().collection("users/" + FirebaseAuth.getInstance().getUid() + "/details").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                @Override
//                public void onComplete(@NonNull Task<DocumentReference> task) {
//                    Toast.makeText(EditAccountActivity.this, "Done", Toast.LENGTH_SHORT).show();
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(EditAccountActivity.this, "BHOOO", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        }


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
        progress_layout=findViewById(R.id.progress_layout);

    }


    public void updateStatusBarColor(String color){
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor(color));
    }



    private void updateDetails(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        progress_layout.setVisibility(View.VISIBLE);
        DocumentReference ref_doc = db.document("users/" + FirebaseAuth.getInstance().getUid() + "/details");
        ref_doc.update("phone",phone);
        ref_doc.update("email",email);
        ref_doc.update("firstName",firstName);
        ref_doc.update("lastName",lastName);
        ref_doc.update("companyName",companyName);
        ref_doc.update("officeAddress",officeAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
//                    progress_layout.setVisibility(View.VISIBLE);
//                    FirebaseFirestore db = FirebaseFirestore.getInstance();
//                    db.collection( "users/"+FirebaseAuth.getInstance().getUid()+"/details").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                        @Override
//                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                            for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments()) {
//                                phone.setText(documentSnapshot.getString("phone"));
//                                email.setText(documentSnapshot.getString("username"));
//                                companyName.setText(documentSnapshot.getString("companyName"));
//                                officeAddress.setText(documentSnapshot.getString("officeAddress"));
//                                firstName.setText(documentSnapshot.getString("firstName"));
//                                lastName.setText(documentSnapshot.getString("lastName"));
//                                progress_layout.setVisibility(View.INVISIBLE);
//                            }
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(EditAccountActivity.this, "Failed to Update Changes!", Toast.LENGTH_SHORT).show();
//
//
//                        }
//                    });
                    Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "There was some problem", Toast.LENGTH_SHORT).show();
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

//    @Override
//    public void onBackPressed() {
//       super.onBackPressed();
//        moveTaskToBack(true);
//    }
}
