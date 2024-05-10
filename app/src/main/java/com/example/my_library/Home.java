// Home.java
package com.example.my_library;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;



public class Home extends AppCompatActivity {

    private Button btnAddBook;
    private Button btnViewBook;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize buttons
        btnAddBook = findViewById(R.id.btnaddbook);
        btnViewBook = findViewById(R.id.btnviewbook);
        btnUpdate = findViewById(R.id.btnUpdate);

        // Set click listener for "Add Book" button
        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to addBook Activity
                Intent intent = new Intent(Home.this, addBook.class);
                startActivity(intent);
            }
        });

        // Set click listener for "View Book" button
        btnViewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ViewBooksActivity
                Intent intent = new Intent(Home.this, viewBook.class);
                startActivity(intent);
            }
        });
        btnUpdate.setOnClickListener(v -> {
            // Navigate to updateBook Activity
            // Note: If "updateBook" requires a specific book, you might need to pass an ID via intent
            Intent intent = new Intent(Home.this, updateBook.class);
            intent.putExtra("BOOK_NAME", -1); // Replace -1 with a specific book ID if needed
            startActivity(intent);
        });


    }
}


