package com.tren_lop.bai1003.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tren_lop.bai1003.R;
import com.tren_lop.bai1003.models.Movie;

import java.util.UUID;

public class AddMovieActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private TextInputEditText etTitle, etDesc, etDuration, etReleaseDate;
    private ImageView ivPoster;
    private Button btnSelectImage, btnSave;
    private Uri imageUri;

    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference("movie_posters");

        etTitle = findViewById(R.id.etMovieTitle);
        etDesc = findViewById(R.id.etMovieDesc);
        etDuration = findViewById(R.id.etMovieDuration);
        etReleaseDate = findViewById(R.id.etMovieReleaseDate);
        ivPoster = findViewById(R.id.ivMoviePoster);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnSave = findViewById(R.id.btnSaveMovie);

        btnSelectImage.setOnClickListener(v -> openFileChooser());
        btnSave.setOnClickListener(v -> saveMovie());
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ivPoster.setImageURI(imageUri);
        }
    }

    private void saveMovie() {
        String title = etTitle.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();
        String durationStr = etDuration.getText().toString().trim();
        String releaseDate = etReleaseDate.getText().toString().trim();

        if (title.isEmpty() || desc.isEmpty() || durationStr.isEmpty() || releaseDate.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Please fill all fields and select an image.", Toast.LENGTH_SHORT).show();
            return;
        }

        int duration = Integer.parseInt(durationStr);
        uploadImageAndSaveMovie(title, desc, duration, releaseDate);
    }

    private void uploadImageAndSaveMovie(String title, String desc, int duration, String releaseDate) {
        String fileName = UUID.randomUUID().toString();
        StorageReference fileRef = storageRef.child(fileName);

        fileRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();
                    String movieId = db.collection("movies").document().getId();
                    
                    Movie movie = new Movie(movieId, title, desc, imageUrl, "", duration, releaseDate);
                    
                    db.collection("movies").document(movieId).set(movie)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(AddMovieActivity.this, "Movie added successfully!", Toast.LENGTH_SHORT).show();
                                finish();
                            })
                            .addOnFailureListener(e -> Toast.makeText(AddMovieActivity.this, "Failed to save movie data.", Toast.LENGTH_SHORT).show());
                }))
                .addOnFailureListener(e -> Toast.makeText(AddMovieActivity.this, "Failed to upload image.", Toast.LENGTH_SHORT).show());
    }
}
