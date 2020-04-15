package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.UserSession.UserSessionActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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
    TextView email,phone;
    EditText firstName,lastName,companyName,officeAddress;
    private Uri photoUri;
    private String url="";
    private ConstraintLayout progress_layout;
    private StorageReference storage;
    private FirebaseAuth firebaseAuth;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static Pattern usrNamePtrn = Pattern.compile("^[a-z_-]{6,14}$");
    //private static String emaill="null";
//    public static final Pattern VALID_FIRST_AND_LAST_NAME_REGEX =
//            Pattern.compile("^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}", Pattern.CASE_INSENSITIVE);
    AlertDialog.Builder builder;
    public EditAccountActivity(){

    }

//    public EditAccountActivity(String email,)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_header_edit_account);
        getSupportActionBar().hide();
        Intent mIntent = getIntent();
        String color = mIntent.getStringExtra("color");
        updateStatusBarColor(color);

        init();

        builder = new AlertDialog.Builder(this);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
//        Toast.makeText(this, get, Toast.LENGTH_SHORT).show();
        progress_layout.setVisibility(View.VISIBLE);


        db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(final DocumentSnapshot documentSnapshot) {


                phone.setText(documentSnapshot.getString("phone"));
                email.setText(documentSnapshot.getString("email"));
                companyName.setText(documentSnapshot.getString("companyName"));
                officeAddress.setText(documentSnapshot.getString("officeAddress"));
                firstName.setText(documentSnapshot.getString("firstName"));
                lastName.setText(documentSnapshot.getString("lastName"));

                final StorageReference ref = storage.child("profiles/" + firebaseAuth.getCurrentUser().getUid() + ".jpg");
                ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            progress_layout.setVisibility(View.VISIBLE);
                            Glide
                                    .with(getApplicationContext())
                                    .load(task.getResult())
                                    .centerCrop()
                                    .placeholder(R.drawable.ic_user_profile)
                                    .into(profileImg);
//                            Toast.makeText(EditAccountActivity.this, "set", Toast.LENGTH_SHORT).show();
                            progress_layout.setVisibility(View.INVISIBLE);
                        } else {
                            Toast.makeText(EditAccountActivity.this, "Please Update Your Profile", Toast.LENGTH_SHORT).show();
                            progress_layout.setVisibility(View.INVISIBLE);
                        }

                    }
                });


//                String profile_url = (String) documentSnapshot.get("profile_url");

//                progress_layout.setVisibility(View.INVISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditAccountActivity.this, "Failed to Update Changes!", Toast.LENGTH_SHORT).show();


            }
        });


        email.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                builder.setMessage("                                                                                Do you want to change email address? ")
                        .setCancelable(false)
        //                        .setIcon(R.drawable.ic_delete_forever_black_24dp)
                        .setPositiveButton(Html.fromHtml("<font color='#FF7F27'>Yes</font>"), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

//                                 Get auth credentials from the user for re-authentication. The example below shows
//                                 email and password credentials but there are multiple possible providers,
//                                 such as GoogleAuthProvider or FacebookAuthProvider.
//                                AuthCredential credential = EmailAuthProvider
//                                        .getCredential(firebaseAuth.getCurrentUser().getEmail(),pass);
//
//                                 Prompt the user to re-provide their sign-in credentials
//                                user.reauthenticate(credential)
//                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {
//
//                                                if (task.isSuccessful()) {
//                                                    Log.d(TAG, "User re-authenticated.");
//                                                    firebaseAuth.fetchSignInMethodsForEmail(new_email.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
//                                                        @Override
//                                                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
//
//                                                            if (task.isSuccessful()) {
//
//                                                                try {
//                                                                    if (task.getResult().getSignInMethods().size() == 1) {
//                                                                        Log.d(TAG, "onComplete: This will return the signin methods");
//                                                                        Toast.makeText(EditAccountActivity.this, "The email is already exist", Toast.LENGTH_SHORT).show();
//
//
//                                                                    }else{
//                                                                        Log.d(TAG, "onComplete: Email is not present. User can change it");
//                                                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//                                                                        user.updateEmail(email.getText().toString())
//                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                                                    @Override
//                                                                                    public void onComplete(@NonNull Task<Void> task) {
//                                                                                        if (task.isSuccessful()) {
//                                                                                            Log.d(TAG, "User email address updated.");
//                                                                                            Toast.makeText(EditAccountActivity.this, "The email updated.", Toast.LENGTH_SHORT).show();
//
//                                                                                        }
//                                                                                    }
//                                                                                });
//
//                                                                    }
//                                                                }catch(NullPointerException e) {
//                                                                    Log.e(TAG, "onComplete: NullPointerException" + e.getMessage());
//                                                                }
//                                                            }
//
//                                                        }
//
//
//                                                    });
//
//
//                                                } else {
//                                                    Log.d(TAG, "onComplete: User re-authentication failed.");
//                                                }
//
//
//                                            }
//                                        });
//

                                                                }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();

                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle(" Alert!");
                alert.show();
                return false;
            }
        });

        phone.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                builder.setMessage("                                                                                Do you want to change phone number? ")
                        .setCancelable(false)
                        //                        .setIcon(R.drawable.ic_delete_forever_black_24dp)
                        .setPositiveButton(Html.fromHtml("<font color='#FF7F27'>Yes</font>"), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();

                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle(" Alert!");
                alert.show();
                return false;
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditAccountActivity.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(EditAccountActivity.this, UserSessionActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        removePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setMessage("                                                                                Do you want to delete profile picture?")
                        .setCancelable(false)
                        .setPositiveButton(Html.fromHtml("<font color='#FF7F27'>Yes</font>"), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                StorageReference ref = storage.child("profiles/" + firebaseAuth.getCurrentUser().getUid() + ".jpg");
                                ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        url = null;
                                        photoUri = null;
                                        profileImg.setImageResource(R.drawable.ic_user_profile);
                                        Map<String, Object> map = new HashMap<>();
                                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                                        map.put("profile_url", url);
                                        db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).update(map);
                                        Toast.makeText(EditAccountActivity.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                        // File deleted successfully
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        if (photoUri == null) {
                                            Toast.makeText(EditAccountActivity.this, "Already Deleted", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(EditAccountActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            // Uh-oh, an error occurred!
                                        }
                                    }
                                });

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();

                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle(" Alert!");
                alert.show();
                }
        });
//        removePhoto.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                builder.setMessage("                                                                                Do you want to delete profile picture?")
//                        .setCancelable(false)
////                        .setIcon(R.drawable.ic_delete_forever_black_24dp)
//                        .setPositiveButton(Html.fromHtml("<font color='#FF7F27'>Yes</font>"), new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                Toast.makeText(getApplicationContext(), "you choose yes action for alertbox",
//                                        Toast.LENGTH_SHORT).show();
//                                StorageReference ref = storage.child("profiles/" + firebaseAuth.getCurrentUser().getUid() + ".jpg");
//                                ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        url = null;
//                                        photoUri = null;
//                                        profileImg.setImageResource(R.drawable.ic_user_profile);
//                                        Map<String, Object> map = new HashMap<>();
//                                        FirebaseFirestore db = FirebaseFirestore.getInstance();
//                                        map.put("profile_url", url);
//                                        db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).update(map);
//                                        Toast.makeText(EditAccountActivity.this, "Successfully Removed", Toast.LENGTH_SHORT).show();
//                                        // File deleted successfully
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                       if (photoUri == null) {
//                                            Toast.makeText(EditAccountActivity.this, "Already Removed", Toast.LENGTH_SHORT).show();
//                                        } else {
//                                            Toast.makeText(EditAccountActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                                            // Uh-oh, an error occurred!
//                                        }
//                                    }
//                                });
//
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                //  Action for 'NO' Button
//                                dialog.cancel();
//                                Toast.makeText(getApplicationContext(), "you choose no action for alertbox",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                //Creating dialog box
//                AlertDialog alert = builder.create();
//                //Setting the title manually
//                alert.setTitle(" Alert!");
//                alert.show();
//            return false;}
//        });


        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Some security-sensitive actions—such as deleting an account, setting a primary email address, and changing a password—require that the user has recently signed in.
//                If you perform one of these actions, and the user signed in too long ago, the action fails and throws FirebaseAuthRecentLoginRequiredException. When this happens,
//                re-authenticate the user by getting new sign-in credentials from the user and passing the credentials to reauthenticate. For example:
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
                AuthCredential credential = EmailAuthProvider
                        .getCredential("user@example.com", "password1234");

//// Prompt the user to re-provide their sign-in credentials
//                user.reauthenticate(credential)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()) {
//                                                Log.d(TAG, "Password updated");
//                                            } else {
//                                                Log.d(TAG, "Error password not updated")
//                                            }
//                                        }
//                                    });
//                                } else {
//                                    Log.d(TAG, "Error auth failed");
//                                }
//                            }
//                        });


            }
        });

//        Changing password in firebase is bit tricky. it's not like what we usually do for changing password in server side scripting and database. to implement change password functionality in your app, first you need to get the user's email from FirebaseAuth or prompt user to input email and after that prompt the user to input old password because you can't retrieve user's password as Frank van Puffelen said. After that you need to reauthenticate that. Once reauthentication is done, if successful, you can use updatePassword(). I have added a sample below that i used for my own app. Hope, it will help you.





//        private FirebaseUser user;
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        final String email = user.getEmail();
//        AuthCredential credential = EmailAuthProvider.getCredential(email,oldpass);
//
//        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//                    user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if(!task.isSuccessful()){
//                                Snackbar snackbar_fail = Snackbar
//                                        .make(coordinatorLayout, "Something went wrong. Please try again later", Snackbar.LENGTH_LONG);
//                                snackbar_fail.show();
//                            }else {
//                                Snackbar snackbar_su = Snackbar
//                                        .make(coordinatorLayout, "Password Successfully Modified", Snackbar.LENGTH_LONG);
//                                snackbar_su.show();
//                            }
//                        }
//                    });
//                }else {
//                    Snackbar snackbar_su = Snackbar
//                            .make(coordinatorLayout, "Authentication Failed", Snackbar.LENGTH_LONG);
//                    snackbar_su.show();
//                }
//            }
//        });


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

                progress_layout.setVisibility(View.VISIBLE);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
//                FirebaseUser.getIdToken()

                Map<String, Object> map = new HashMap<>();
//                map.put("email",email.getText().toString());
//                map.put("phone", phone.getText().toString());
                map.put("firstName",firstName.getText().toString());
                map.put("lastName", lastName.getText().toString());
                map.put("companyName", companyName.getText().toString());
                map.put("officeAddress", officeAddress.getText().toString());
                db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).update(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(EditAccountActivity.this, url, Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "DocumentSnapshot successfully updated!");
                                progress_layout.setVisibility(View.GONE);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating document : "+ e.getMessage());
                                Toast.makeText(EditAccountActivity.this, "error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                progress_layout.setVisibility(View.GONE);
                            }
                        });

 //
//                FirebaseFirestore db = FirebaseFirestore.getInstance();
//                progress_layout.setVisibility(View.VISIBLE);
//                DocumentReference ref_doc = db.document("users");
//                Toast.makeText(EditAccountActivity.this,ref_doc.getId(), Toast.LENGTH_SHORT).show();
//                ref_doc.update("phone",phone);
//                ref_doc.update("email",email);
//                ref_doc.update("firstName",firstName);
//                ref_doc.update("lastName",lastName);
//                ref_doc.update("companyName",companyName);
//                ref_doc.update("officeAddress",officeAddress).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(EditAccountActivity.this, "Data Failed", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                updateDetails();

            }
        });
    }


    private void updateChanges() {

        if (photoUri != null) {

            final StorageReference ref = storage.child("profiles/" + firebaseAuth.getCurrentUser().getUid() + ".jpg");
            UploadTask uploadTask = ref.putFile(photoUri);


             uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        String error = task.getException().getMessage();
                        Toast.makeText(EditAccountActivity.this, error, Toast.LENGTH_SHORT).show();
                        progress_layout.setVisibility(View.GONE);
                    }
                    // Continue with the task to get the download URL
                    return ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            url = uri.toString();
                            Map<String, Object> map = new HashMap<>();
                            map.put("profile_url", url);
//                            Toast.makeText(EditAccountActivity.this, url, Toast.LENGTH_SHORT).show();
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).update(map);



                        }
                    });
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        progress_layout.setVisibility(View.GONE);

//upladeDetails()
                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(EditAccountActivity.this, error, Toast.LENGTH_SHORT).show();
                        progress_layout.setVisibility(View.GONE);
                        // Handle failures
                        // ...
                    }
                }
            });


        }

    }
    private void selectImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setActivityMenuIconColor(ContextCompat.getColor(this, R.color.colorPrimaryCropper))
                .setActivityTitle("Profile Picture")
                .setAspectRatio(1,1)
                .setFixAspectRatio(true)
                .start(this);
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
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                progress_layout.setVisibility(View.VISIBLE);
                photoUri = result.getUri();
                updateChanges();
                Glide
                        .with(this)
                        .load(photoUri)
                        .centerCrop()
                        .placeholder(R.drawable.ic_user_profile)
                        .into(profileImg);



            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                progress_layout.setVisibility(View.GONE);
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


//
//    private void updateDetails(){
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        progress_layout.setVisibility(View.VISIBLE);
//        DocumentReference ref_doc = db.document("users");
//        ref_doc.update("phone",phone);
//        ref_doc.update("email",email);
//        ref_doc.update("firstName",firstName);
//        ref_doc.update("lastName",lastName);
//        ref_doc.update("companyName",companyName);
//        ref_doc.update("officeAddress",officeAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {



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
////                    });
//                    Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getApplicationContext(), "There was some problem", Toast.LENGTH_SHORT).show();
//                }
//            }
//        })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error writing document", e);
//                    }
//                });
//    }

//    @Override
//    public void onBackPressed() {
//       super.onBackPressed();
//        moveTaskToBack(true);
//    }
}
