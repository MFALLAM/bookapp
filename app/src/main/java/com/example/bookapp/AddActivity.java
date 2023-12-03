package com.example.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bookapp.data.BookDatabaseHelper;

public class AddActivity extends AppCompatActivity {

    private EditText mBookTitleEdittext;
    private EditText mBookAuthorEdittext;
    private EditText mPagesNumberEdittext;
    private Button mAddButton;
    private BookDatabaseHelper mDdHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        findViewById();

        mAddButton.setOnClickListener(view -> {
            mDdHelper = new BookDatabaseHelper(AddActivity.this);
            mDdHelper.addBook(mBookTitleEdittext.getText().toString().trim(),
                    mBookAuthorEdittext.getText().toString().trim(),
                    Integer.parseInt(mPagesNumberEdittext.getText().toString().trim()));
        });
    }

    private void findViewById() {
        mBookTitleEdittext = findViewById(R.id.book_title_edittext);
        mBookAuthorEdittext = findViewById(R.id.book_author_edittext);
        mPagesNumberEdittext = findViewById(R.id.pages_number_edittext);
        mAddButton = findViewById(R.id.add_button);
    }
}