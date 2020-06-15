package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
    private CircleImageView profileImg;
    private CardView changePassword;
    private ConstraintLayout updatePersonalDetailsCL,updateCompanyProfileCL, addShippingAddressCL,progress_layout;
    private Button saveChangesPersonalDetails,saveChangesCompanyProfile,logout,viewAllAddressBtn,addNewAddressBtn;
    private ImageView imgBack,imgLogout,removeCircle,removeCross;
    private TextView email,phone,profileUserName,updatePersonalDetailsText,updateCompanyProfileText,addShippingAddressText;
    private EditText firstName,lastName,secondaryPhoneNumber,postalAddress,companyName,officeAddress,officeContactNumber;
    private Boolean UPDATE_PERSONAL_DETAILS_OPEN=false;
    private Boolean UPDATE_COMPANY_PROFILE_OPEN=false;
    private Boolean ADD_SHIPPING_ADDRESS_OPEN=false;
    public static final int MANAGE_ADDRESS=1;

    private Uri photoUri;
    private String url="";
    private StorageReference storage;
    private FirebaseAuth firebaseAuth;


//    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
//            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
//    private static Pattern usrNamePtrn = Pattern.compile("^[a-z_-]{6,14}$");

    AlertDialog.Builder builder;

    private void init(){

        profileImg=findViewById(R.id.profile_image);
        removeCircle=findViewById(R.id.imageViewCross);
        removeCross=findViewById(R.id.imageViewColorCross);
        profileUserName=findViewById(R.id.profile_title_name);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        updatePersonalDetailsText=findViewById(R.id.updatePersonalDetailsText);
        updateCompanyProfileText=findViewById(R.id.updateCompanyProfileText);
        updatePersonalDetailsCL=findViewById(R.id.updatePersonalDetailsConstraintLayout);
        updateCompanyProfileCL=findViewById(R.id.updateCompanyProfileConstraintLayout);
        firstName=findViewById(R.id.firstName);
        lastName=findViewById(R.id.lastName);
        secondaryPhoneNumber=findViewById(R.id.secondaryPhoneNumber);
        postalAddress=findViewById(R.id.postalAddress);
        saveChangesPersonalDetails=findViewById(R.id.btn_save_changesPersonalDetails);
        saveChangesCompanyProfile=findViewById(R.id.btn_save_changesCompanyProfile);
        changePassword=findViewById(R.id.changePassword);
        logout=findViewById(R.id.btn_logout);
        companyName=findViewById(R.id.companyName);
        officeAddress=findViewById(R.id.officeAddress);
        officeContactNumber=findViewById(R.id.officeContactNumber);
        imgBack=findViewById(R.id.img_back);
        imgLogout=findViewById(R.id.img_logout);
        progress_layout=findViewById(R.id.progress_layout);
        viewAllAddressBtn=findViewById(R.id.viewAllAddressBtn);
        addNewAddressBtn =findViewById(R.id.addNewAddressBtn);
        addShippingAddressText=findViewById(R.id.addShippingAddressText);
        addShippingAddressCL=findViewById(R.id.addShippingAddressConstraintLayout);
//        removePhoto=findViewById(R.id.btn_removePhoto);

    }

    public EditAccountActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_header_edit_account);
//        getSupportActionBar().hide();
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
        updateCompanyProfileCL.setVisibility(View.GONE);
        updatePersonalDetailsCL.setVisibility(View.GONE);
        addShippingAddressCL.setVisibility(View.GONE);
//////////////updatePersonalDetailsText to open card to edit personal Details
        updatePersonalDetailsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UPDATE_PERSONAL_DETAILS_OPEN) {
                    UPDATE_PERSONAL_DETAILS_OPEN = false;
                    updatePersonalDetailsText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_down_black_24dp, 0);
                    updatePersonalDetailsCL.setVisibility(View.GONE);
                } else {
                    UPDATE_PERSONAL_DETAILS_OPEN = true;
                    updatePersonalDetailsText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_up_black_24dp, 0);
                    updatePersonalDetailsCL.setVisibility(View.VISIBLE);

                }
            }
        });
//////////////updatePersonalDetailsText to open card to edit personal Details

//////////////updateCompanyProfileText to open card to edit company Details

        updateCompanyProfileText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UPDATE_COMPANY_PROFILE_OPEN) {
                    UPDATE_COMPANY_PROFILE_OPEN = false;
                    updateCompanyProfileText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_down_black_24dp, 0);
                    updateCompanyProfileCL.setVisibility(View.GONE);
                } else {
                    UPDATE_COMPANY_PROFILE_OPEN = true;
                    updateCompanyProfileText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_up_black_24dp, 0);
                    updateCompanyProfileCL.setVisibility(View.VISIBLE);
                }

            }
        });
//////////////updateCompanyProfileText to open card to edit company Details

//////////////AddShippingAddressText to open card to ShippingAddress Details
        addShippingAddressText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ADD_SHIPPING_ADDRESS_OPEN) {
                    ADD_SHIPPING_ADDRESS_OPEN = false;
                    addShippingAddressText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_down_black_24dp, 0);
                    addShippingAddressCL.setVisibility(View.GONE);
                } else {
                    ADD_SHIPPING_ADDRESS_OPEN = true;
                    addShippingAddressText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_up_black_24dp, 0);
                    addShippingAddressCL.setVisibility(View.VISIBLE);
                }

            }
        });

//////////////AddShippingAddressText to open card to ShippingAddress Details



        db.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(final DocumentSnapshot documentSnapshot) {
////////UpdateProfileTitle

                if (documentSnapshot.getString("firstName" ) != null ) {
                    String firstNameString=documentSnapshot.getString("firstName");
                    String lastNameString=documentSnapshot.getString("lastName");
                    String fullNameString= firstNameString + " " + lastNameString;
                    profileUserName.setText(fullNameString);

                } else {
                    String str = documentSnapshot.getString("email");
                    String[] arrOfStr = str.split("@", 2);

                    for (String a : arrOfStr)
                        profileUserName.setText(arrOfStr[0]);
                }
                phone.setText(documentSnapshot.getString("phone"));
                email.setText(documentSnapshot.getString("email"));
////////UpdateProfileTitle

////////UpdatePersonalDetails
                firstName.setText(documentSnapshot.getString("firstName"));
                lastName.setText(documentSnapshot.getString("lastName"));
                postalAddress.setText(documentSnapshot.getString("postalAddress"));
                secondaryPhoneNumber.setText(documentSnapshot.getString("secondaryPhoneNumber"));
////////UpdatePersonalDetails

////////UpdateCompanyProfile
                companyName.setText(documentSnapshot.getString("companyName"));
                officeAddress.setText(documentSnapshot.getString("officeAddress"));
                officeContactNumber.setText(documentSnapshot.getString("officeContactNumber"));
////////UpdateCompanyProfile

////////SetProfilePicture

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
                            removeCircle.setVisibility(View.VISIBLE);
                            removeCross.setVisibility(View.VISIBLE);
//                            Toast.makeText(EditAccountActivity.this, "set", Toast.LENGTH_SHORT).show();
                            progress_layout.setVisibility(View.INVISIBLE);
                        } else {
                            Toast.makeText(EditAccountActivity.this, "Please Update Your Profile", Toast.LENGTH_SHORT).show();
                            progress_layout.setVisibility(View.INVISIBLE);
                        }

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditAccountActivity.this, "Failed to Update Changes!", Toast.LENGTH_SHORT).show();


            }
        });
////////SetProfilePicture


////////BackButton
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditAccountActivity.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


            }
        });
////////BackButton

////////LogoutButton&Image
        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                DBqueries.clearData();
                finish();
                Intent intent = new Intent(EditAccountActivity.this, UserSessionActivity.class);
                startActivity(intent);
                finishAffinity();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                DBqueries.clearData();
                finish();
                Intent intent = new Intent(EditAccountActivity.this, UserSessionActivity.class);
                startActivity(intent);
                finishAffinity();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


            }
        });
////////LogoutButton&Image

/////////RemoveProfilePicture
        removeCircle.setOnClickListener(new View.OnClickListener() {
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
                                        db.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).update(map);
                                        Toast.makeText(EditAccountActivity.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                        removeCircle.setVisibility(View.INVISIBLE);
                                        removeCross.setVisibility(View.INVISIBLE);
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
/////////RemoveProfilePicture


/////////ProfileImagePermissions

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

                Dexter.withActivity(EditAccountActivity.this)
                        .withPermissions(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        ).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            selectImage();

                        } else {
                            Toast.makeText(EditAccountActivity.this, "Please allow Permissions!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
                }).check();
            }
        });
/////////ProfileImagePermissions


/////////saveChangesPersonalDetails Button
        saveChangesPersonalDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!secondaryPhoneNumber.getText().toString().isEmpty()) {
                    secondaryPhoneNumber.setError(null);
                    if (secondaryPhoneNumber.getText().toString().length() != 10) {
                        secondaryPhoneNumber.setError("Invalid Phone Number");
                        return;
                    }

                }
                String firstNameTxt= firstName.getText().toString();
                String LastNameTxt= lastName.getText().toString();
                profileUserName.setText(firstNameTxt+" "+LastNameTxt);

                progress_layout.setVisibility(View.VISIBLE);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> map = new HashMap<>();
                map.put("firstName", firstNameTxt);
                map.put("lastName", LastNameTxt);
                map.put("secondaryPhoneNumber", secondaryPhoneNumber.getText().toString());
                map.put("postalAddress", postalAddress.getText().toString());

                db.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).update(map)
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
                                Log.w(TAG, "Error updating document : " + e.getMessage());
                                Toast.makeText(EditAccountActivity.this, "error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                progress_layout.setVisibility(View.GONE);
                            }
                        });

            }
        });
/////////saveChangesPersonalDetails Button

/////////saveChangesCompanyProfile Button

        saveChangesCompanyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!secondaryPhoneNumber.getText().toString().isEmpty()) {
                    officeContactNumber.setError(null);
                    if (officeContactNumber.getText().toString().length() != 10) {
                        officeContactNumber.setError("Invalid Phone Number");
                        return;
                    }
                }
                progress_layout.setVisibility(View.VISIBLE);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> map = new HashMap<>();
                map.put("companyName", companyName.getText().toString());
                map.put("officeAddress", officeAddress.getText().toString());
                map.put("officeContactNumber", officeContactNumber.getText().toString());

                db.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).update(map)
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
                                Log.w(TAG, "Error updating document : " + e.getMessage());
                                Toast.makeText(EditAccountActivity.this, "error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                progress_layout.setVisibility(View.GONE);
                            }
                        });

            }
        });
/////////saveChangesCompanyProfile Button

/////////viewAllAddress Button
        viewAllAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(EditAccountActivity.this,MyAddressActivity.class);
                intent.putExtra("MODE",MANAGE_ADDRESS);
                startActivity(intent);
            }
        });
/////////viewAllAddress Button

/////////AddNewAddress Button
        addNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(EditAccountActivity.this,AddAddressActivity.class);
                startActivity(intent);
            }
        });
/////////AddNewAddress Button


    }


//        saveChangesPersonalDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                email.setError(null);
//                secondaryPhoneNumber.setError(null);
////                if (email.getText().toString().isEmpty()) {
////                    email.setError("Required!");
////                    return;
////                }
////                if (phone.getText().toString().isEmpty()) {
////                    phone.setError("Required!");
////                    return;
////                }
//
//                if (!VALID_EMAIL_ADDRESS_REGEX.matcher(email.getText().toString()).find()) {
//                    email.setError("Invalid  E-mail");
//                    return;
//                }
//                if (phone.getText().toString().length() != 10) {
//                    phone.setError("Invalid Phone Number");
//                    return;
//                }
//
//                progress_layout.setVisibility(View.VISIBLE);
//                FirebaseFirestore db = FirebaseFirestore.getInstance();
////                FirebaseUser.getIdToken()
//
//                Map<String, Object> map = new HashMap<>();
////                map.put("email",email.getText().toString());
////                map.put("phone", phone.getText().toString());
//                map.put("firstName",firstName.getText().toString());
//                map.put("lastName", lastName.getText().toString());
//                map.put("companyName", companyName.getText().toString());
//                map.put("officeAddress", officeAddress.getText().toString());
//                db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).update(map)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
////                                Toast.makeText(EditAccountActivity.this, url, Toast.LENGTH_SHORT).show();
//                                Log.d(TAG, "DocumentSnapshot successfully updated!");
//                                progress_layout.setVisibility(View.GONE);
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.w(TAG, "Error updating document : "+ e.getMessage());
//                                Toast.makeText(EditAccountActivity.this, "error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
//                                progress_layout.setVisibility(View.GONE);
//                            }
//                        });




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




//            }
//        });


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

//uplDetails()
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
                removeCircle.setVisibility(View.VISIBLE);
                removeCross.setVisibility(View.VISIBLE);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                progress_layout.setVisibility(View.GONE);
                Exception error = result.getError();
                Toast.makeText(EditAccountActivity.this, error.getMessage() , Toast.LENGTH_SHORT).show();
            }
        }
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
