package com.example.javainstagramclone.view;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.Manifest;
import android.widget.Toast;

import com.example.javainstagramclone.databinding.ActivityUploadBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

public class UploadActivity extends AppCompatActivity {

    private FirebaseStorage storage;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private StorageReference storageReference;
    Uri imageData;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> permissionLauncher;

    private ActivityUploadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        registerLauncher();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storageReference = storage.getReference();
    }

    public void uploadButtonClicked(View view){

        UUID uuid = UUID.randomUUID();
        String imageName = "images/" + uuid + ".jpg";
        if (imageData != null){
            storageReference.child(imageName).putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    StorageReference newReference = storageReference.getStorage().getReference(imageName);
                    newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String downloadUrl = uri.toString();
                            String comment = binding.commentText.getText().toString();

                            FirebaseUser user = auth.getCurrentUser();
                            String email = user.getEmail();

                            HashMap<String,Object> postData = new HashMap<>();
                            postData.put("useremail",email);
                            postData.put("downloadurl",downloadUrl);
                            postData.put("comment",comment);
                            postData.put("date", FieldValue.serverTimestamp());

                            firestore.collection("Posts").add(postData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Intent intent = new Intent(UploadActivity.this,FeedActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UploadActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    public void selectImage(View view) {
        String permission;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            permission = Manifest.permission.READ_MEDIA_IMAGES;
        } else {
            permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        }

        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            // İzin zaten varsa direkt galeriye git
            openGallery();
        } else {
            // Kullanıcı daha önce reddetti mi kontrol edelim
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                Snackbar.make(view, "Galeriye erişim izni gerekli!", Snackbar.LENGTH_INDEFINITE)
                        .setAction("İzin Ver", v -> permissionLauncher.launch(permission))
                        .show();
            } else {
                // İlk defa istiyoruz veya kalıcı olarak reddedildi
                permissionLauncher.launch(permission);
            }
        }
    }

    // Galeri açma fonksiyonu
    private void openGallery() {
        Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultLauncher.launch(intentToGallery);
    }


    public void registerLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent intentFromResult = result.getData();
                if (intentFromResult != null) {
                    imageData = intentFromResult.getData();
                    binding.imageView.setImageURI(imageData);
                }
            }
        });

        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if (result) {
                // Kullanıcı izin verdiyse galeriyi aç
                Toast.makeText(UploadActivity.this, "İzin verildi! Galeri açılıyor...", Toast.LENGTH_SHORT).show();
                openGallery();
            } else {
                Toast.makeText(UploadActivity.this, "İzin reddedildi! Ayarlardan açabilirsiniz.", Toast.LENGTH_LONG).show();
            }
        });
    }

}