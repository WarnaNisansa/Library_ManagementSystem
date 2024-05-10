
package com.example.my_library;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class updateBook extends AppCompatActivity {
    private EditText txtBookName, txtBookAuthor, txtBookPublication, txtQuantity;
    private Button btnUpdate, btnDelete;
    private DBHelper dbHelper;
    private int bookId = -1;

    private static final String TAG = "updateBook";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatebook);

        // Initialize views

        txtBookName = findViewById(R.id.txtbname);
        txtBookAuthor = findViewById(R.id.txtbauthor);
        txtBookPublication = findViewById(R.id.txtbpublication);
        txtQuantity = findViewById(R.id.txtquantity);
        btnUpdate = findViewById(R.id.btnupdate);
        btnDelete = findViewById(R.id.btndelete);

        // Initialize the database helper
        dbHelper = new DBHelper(this);

        // Retrieve book ID from the intent
        bookId = getIntent().getIntExtra("BOOK_ID", -1);
        Log.d(TAG, "Received book ID: " + bookId);

        if (bookId != -1) {
            loadBookDetails(bookId);
        } else {
            Log.e(TAG, "Invalid book ID");
        }

        // Set up Update button action
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBookDetails();
            }
        });

        // Set up Delete button action
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBook();
            }
        });
    }

    // Load book details into the input fields
    private void loadBookDetails(int bookId) {
        Cursor cursor = dbHelper.getBookById(bookId);
        if (cursor != null && cursor.moveToFirst()) {
            txtBookName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.BOOK_NAME)));
            txtBookAuthor.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.BOOK_AUTHOR)));
            txtBookPublication.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.BOOK_PUBLICATION)));
            txtQuantity.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.BOOK_QUANTITY))));
            cursor.close();
        } else {
            Log.e(TAG, "Failed to load book details for ID: " + bookId);
        }
    }

    // Update book details in the database
    private void updateBookDetails() {
        String name = txtBookName.getText().toString();
        String author = txtBookAuthor.getText().toString();
        String publication = txtBookPublication.getText().toString();
        int quantity;

        try {
            quantity = Integer.parseInt(txtQuantity.getText().toString());
        } catch (NumberFormatException e) {
            Log.e(TAG, "Invalid quantity: " + e.getMessage());
            Toast.makeText(this, "Invalid quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean updated = dbHelper.updateBook(bookId, name, author, publication, quantity);
        if (updated) {
            Log.d(TAG, "Book updated successfully");
            Toast.makeText(this, "Book updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "Failed to update book");
            Toast.makeText(this, "Failed to update book", Toast.LENGTH_SHORT).show();
        }
    }

    // Delete the book from the database
    private void deleteBook() {
        boolean deleted = dbHelper.deleteBook(bookId);
        if (deleted) {
            Log.d(TAG, "Book deleted successfully");
            Toast.makeText(this, "Book deleted successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity after deletion
        } else {
            Log.e(TAG, "Failed to delete book");
            Toast.makeText(this, "Failed to delete book", Toast.LENGTH_SHORT).show();
        }
    }
}
