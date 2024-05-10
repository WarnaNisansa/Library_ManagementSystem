package com.example.my_library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    // Database Name and Version
    private static final String DB_NAME = "LibraryDB";
    private static final int DB_VERSION = 1;

    // User Table and Column Names
    private static final String USER_TABLE = "user";
    private static final String USER_ID = "id";
    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";
    private static final String USER_CONFIRM_PASSWORD = "confirm_password";

    // Book Table and Column Names
    private static final String BOOK_TABLE = "newbook";
    public static final String BOOK_ID = "bid";
    public static final String BOOK_NAME = "bname";
    public static final String BOOK_AUTHOR = "bauthor";
    public static final String BOOK_PUBLICATION = "bpublication";
    public static final String BOOK_QUANTITY = "bquantity";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL query to create the user table
        String createUserTableQuery = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + " ("
                + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_NAME + " TEXT, "
                + USER_EMAIL + " TEXT UNIQUE, "
                + USER_PASSWORD + " TEXT, "
                + USER_CONFIRM_PASSWORD + " TEXT)";
        db.execSQL(createUserTableQuery);

        // SQL query to create the book table
        String createBookTableQuery = "CREATE TABLE IF NOT EXISTS " + BOOK_TABLE + " ("
                + BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BOOK_NAME + " TEXT, "
                + BOOK_AUTHOR + " TEXT, "
                + BOOK_PUBLICATION + " TEXT, "
                + BOOK_QUANTITY + " INTEGER)";
        db.execSQL(createBookTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle schema changes here if the database version changes
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BOOK_TABLE);
        onCreate(db);
    }

    // Method to register a new user
    public void registerUser(String name, String email, String password, String confirmPassword) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, name);
        values.put(USER_EMAIL, email);
        values.put(USER_PASSWORD, password);
        values.put(USER_CONFIRM_PASSWORD, confirmPassword);

        long newRowId = db.insert(USER_TABLE, null, values);
        if (newRowId != -1) {
            Log.d("DBHelper", "User registered successfully");
        } else {
            Log.e("DBHelper", "Error registering user");
        }
        db.close();
    }

    // Method to authenticate a user
    public boolean loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean isLoggedIn = false;

        // Query to find the user with the given email and password
        String selection = USER_EMAIL + "=? AND " + USER_PASSWORD + "=?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(USER_TABLE, null, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            isLoggedIn = true; // User is found and authenticated
            cursor.close();
        }

        return isLoggedIn;
    }

    // Method to insert new book details
    public void insertNewBook(String book_name, String book_author, String book_publication, int book_quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOK_NAME, book_name);
        values.put(BOOK_AUTHOR, book_author);
        values.put(BOOK_PUBLICATION, book_publication);
        values.put(BOOK_QUANTITY, book_quantity);

        // Insert the book data into the database
        long newRowId = db.insert(BOOK_TABLE, null, values);
        if (newRowId != -1) {
            Log.d("DBHelper", "New book added successfully");
        } else {
            Log.e("DBHelper", "Error adding new book");
        }

        db.close(); // Close the database after use
    }

    // Method to fetch all books from the database
    public Cursor getAllBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(BOOK_TABLE, null, null, null, null, null, null);
    }

    // Method to fetch a book by its name
    public Cursor getBookByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = BOOK_NAME + " = ?";
        String[] selectionArgs = {name};
        return db.query(BOOK_TABLE, null, selection, selectionArgs, null, null, null);
    }

    // Method to fetch a book by its ID
    public Cursor getBookById(int bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = BOOK_ID + " = ?";
        String[] selectionArgs = {String.valueOf(bookId)};
        return db.query(BOOK_TABLE, null, selection, selectionArgs, null, null, null);
    }

    // Method to update an existing book
    public boolean updateBook(int bookId, String name, String author, String publication, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOK_NAME, name);
        values.put(BOOK_AUTHOR, author);
        values.put(BOOK_PUBLICATION, publication);
        values.put(BOOK_QUANTITY, quantity);

        int rowsAffected = db.update(BOOK_TABLE, values, BOOK_ID + " = ?", new String[]{String.valueOf(bookId)});
        return rowsAffected > 0;
    }

    // Method to delete a book by ID
    public boolean deleteBook(int bookId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(BOOK_TABLE, BOOK_ID + " = ?", new String[]{String.valueOf(bookId)});
        return rowsDeleted > 0;
    }

    // Method to fetch all books from the database
    public Cursor getAllBook() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(BOOK_TABLE, null, null, null, null, null, null);
    }

}
