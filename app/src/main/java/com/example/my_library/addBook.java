// addBook.java
package com.example.my_library;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class addBook extends AppCompatActivity {
    private EditText txtbname, txtbauthor, txtbpublication, txtquntity;
    private Button btnAddBook;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbook);

        // Initialize views
        txtbname = findViewById(R.id.txtbname);
        txtbauthor = findViewById(R.id.txtbauthor);
        txtbpublication = findViewById(R.id.txtbpublication);
        txtquntity = findViewById(R.id.txtquntity);
        btnAddBook = findViewById(R.id.btnlogin);

        // Initialize the database helper
        dbHelper = new DBHelper(this);

        // Set on-click listener for "Add Book" button
        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input values from the EditText fields
                String bookName = txtbname.getText().toString().trim();
                String author = txtbauthor.getText().toString().trim();
                String publication = txtbpublication.getText().toString().trim();
                int quantity;

                try {
                    quantity = Integer.parseInt(txtquntity.getText().toString().trim());
                } catch (NumberFormatException e) {
                    // Handle non-numeric input for quantity
                    Toast.makeText(addBook.this, "Invalid quantity", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check that all fields are filled
                if (bookName.isEmpty() || author.isEmpty() || publication.isEmpty()) {
                    Toast.makeText(addBook.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Insert the book details into the database
                dbHelper.insertNewBook(bookName, author, publication, quantity);
                Toast.makeText(addBook.this, "Book added successfully!", Toast.LENGTH_SHORT).show();

                // Optionally, clear the input fields
                txtbname.setText("");
                txtbauthor.setText("");
                txtbpublication.setText("");
                txtquntity.setText("");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database helper when the activity is destroyed
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}


