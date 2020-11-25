package com.example.automatedfoodorderingsystem.userPart;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.automatedfoodorderingsystem.Model.UserDatabase;
import com.example.automatedfoodorderingsystem.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    CardView cardView;
    TextView name, email, mobile;
    EditText password;
    Button uploadBtn, updateBtn;
    CircleImageView profileImg;
    DatabaseReference reference;

    FirebaseUser mUser;
    Uri filePath;
    Bitmap bitmap;
    String imageUrl;
    boolean imageSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ////////////// Hooks /////////////////
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
        uploadBtn = findViewById(R.id.uploadBtn);
        updateBtn = findViewById(R.id.updateBtn);


        profileImg = findViewById(R.id.profileImg);
        linearLayout = findViewById(R.id.linearLayout);
        cardView = findViewById(R.id.cardview);
        imageSelected = false;

        //////////// Firebase Hooks //////////////
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("UsersDatabase");

        //////Method to fetch user details from the firebase /////////////
        showUsersDetails();

        ////// Method to request permission for external storage and helps user to browse images from mobile ////////
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Please Select File"), 101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                ///// to ask for permission again if user denied the permission ////////
                                permissionToken.continuePermissionRequest();

                            }
                        }).check();
                imageSelected = true;
            }

        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageSelected) {
                    uploadToFirebase();
                } else {
                    Toast.makeText(EditProfileActivity.this, " Please select Image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadToFirebase() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading...");
        pd.show();
        final StorageReference reference = FirebaseStorage.getInstance().getReference().child("UsersImg").child(mUser.getUid().substring(0, 8) + "." + getFileExtension(filePath));
        StorageTask uploadTask = reference.putFile(filePath);
        uploadTask.continueWithTask(new Continuation() {
            @Override
            public Object then(@NonNull Task task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return reference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                Uri downloadUri = (Uri) task.getResult();
                imageUrl = downloadUri.toString();

                FirebaseDatabase.getInstance().getReference().child("UsersDatabase").child(mUser.getUid()).child("imageUrl").setValue(imageUrl);
                pd.dismiss();
                Toast.makeText(EditProfileActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String getFileExtension(Uri filePath) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(this.getContentResolver().getType(filePath));
    }

    /////////////Method to fetch user details from the firebase //////////////
    private void showUsersDetails() {
        FirebaseDatabase.getInstance().getReference().child("UsersDatabase").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDatabase user = snapshot.getValue(UserDatabase.class);

                if (user != null) {
                    linearLayout.setVisibility(View.VISIBLE);
                    cardView.setVisibility(View.GONE);
                    name.setText(user.getName());
                    email.setText(user.getEmail());
                    password.setText(user.getPassword());
                    mobile.setText(user.getPhoneNo().substring(3));
                    if (!user.getImageUrl().equals("default")) {
                        Picasso.get().load(user.getImageUrl()).placeholder(R.drawable.ic_account_circle_).into(profileImg);
                    } else {
                        profileImg.setImageResource(R.drawable.ic_account_circle_);
                    }


                } else {
                    linearLayout.setVisibility(View.GONE);
                    cardView.setVisibility(View.VISIBLE);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /////// Part of above method that helps user to browse images ///////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                profileImg.setImageBitmap(bitmap);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }


        }
    }
}