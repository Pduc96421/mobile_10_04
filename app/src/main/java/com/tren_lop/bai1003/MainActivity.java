package com.tren_lop.bai1003;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tren_lop.bai1003.activities.LoginActivity;
import com.tren_lop.bai1003.fragments.AdminFragment;
import com.tren_lop.bai1003.fragments.HomeFragment;
import com.tren_lop.bai1003.fragments.ProfileFragment;
import com.tren_lop.bai1003.fragments.TicketsFragment;
import com.tren_lop.bai1003.models.User;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userRole = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        db = FirebaseFirestore.getInstance();
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        // Hide Admin tab by default, check role later
        bottomNavigationView.getMenu().findItem(R.id.nav_admin).setVisible(false);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                replaceFragment(new HomeFragment(), "Home");
                return true;
            } else if (id == R.id.nav_tickets) {
                replaceFragment(new TicketsFragment(), "My Tickets");
                return true;
            } else if (id == R.id.nav_admin) {
                replaceFragment(new AdminFragment(), "Admin Panel");
                return true;
            } else if (id == R.id.nav_profile) {
                replaceFragment(new ProfileFragment(), "Profile");
                return true;
            }
            return false;
        });

        // Load initial fragment
        replaceFragment(new HomeFragment(), "Home");
        
        checkUserRole();
        requestNotificationPermission();
    }

    private void requestNotificationPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }
    }

    private void checkUserRole() {
        String userId = mAuth.getCurrentUser().getUid();
        db.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    User user = documentSnapshot.toObject(User.class);
                    if (user != null && "admin".equalsIgnoreCase(user.getRole())) {
                        userRole = "admin";
                        bottomNavigationView.getMenu().findItem(R.id.nav_admin).setVisible(true);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error checking permissions.", Toast.LENGTH_SHORT).show();
                });
    }

    private void replaceFragment(Fragment fragment, String title) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}