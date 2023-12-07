package com.example.bookapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class BookDatabaseHelper extends SQLiteOpenHelper {

    private final Context context;
    public static final String DATABASE_NAME = "book_library.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "library";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_PAGES = "pages";
    public static final String COLUMN_TIMESTAMP = "publish_date";

    private final static String LOG_TAG = BookDatabaseHelper.class.getSimpleName();

    public static final String SQL_CREATE_BOOK_LIBRARY_TABLE =
                    "CREATE TABLE "  + TABLE_NAME                            + " (" +
                    COLUMN_ID        + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_TITLE     + " TEXT,"                              +
                    COLUMN_AUTHOR    + " TEXT,"                              +
                    COLUMN_PAGES     + " INTEGER,"                           +
                    COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"  +
                    ");";


    public BookDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_BOOK_LIBRARY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void deleteBook(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{id});
        db.close();
    }

    public long addBook(String title, String authorName, int pages) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_AUTHOR, authorName);
        contentValues.put(COLUMN_PAGES, pages);

        long status = db.insert(TABLE_NAME, null, contentValues);
        Log.d(LOG_TAG, "OPERATION _ID " + status);
        return status;
    }

    public long updateBook(int id, String title, String authorName, int pages) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_AUTHOR, authorName);
        contentValues.put(COLUMN_PAGES, pages);

        String[] args = new String[]{String.valueOf(id)};
        long status = db.update(TABLE_NAME, contentValues, "_id =?", args);
        Log.d(LOG_TAG, "__updateBookId: " + status);
        return status;
    }

    public Cursor getAllBooks() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }
}
