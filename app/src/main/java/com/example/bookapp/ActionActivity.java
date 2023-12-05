package com.example.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookapp.data.BookDatabaseHelper;

public class ActionActivity extends AppCompatActivity {

    private EditText mBookTitleEdittext;
    private EditText mBookAuthorEdittext;
    private EditText mPagesNumberEdittext;
    private Button mAddButton;
    private BookDatabaseHelper mDdHelper;
    public static final String ARG_ADD = "ADD";
    public static final String ARG_UPDATE = "UPDATE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        findViewById();
        mDdHelper = new BookDatabaseHelper(ActionActivity.this);

        mAddButton.setOnClickListener(view -> {
            saveBook();
        });

        // getting the bundle back
        Bundle bundle = getIntent().getExtras();

        if(bundle != null && bundle.containsKey(ARG_UPDATE)) {
            Toast.makeText(this, "Bundle found...", Toast.LENGTH_LONG).show();
            setData(bundle);
        } else {
            Toast.makeText(this, "Bundle is empty...", Toast.LENGTH_LONG).show();
        }
    }

    private void setData(Bundle bundle) {
        mBookTitleEdittext.setText(bundle.getString("title"));
        mBookAuthorEdittext.setText(bundle.getString("author"));
        mPagesNumberEdittext.setText(bundle.getString("pages"));
    }

    private void updateData(Bundle bundle) {

    }

    private void saveBook() {
        String title = mBookTitleEdittext.getText().toString().trim();
        String author = mBookAuthorEdittext.getText().toString().trim();
        int pages = Integer.parseInt(mPagesNumberEdittext.getText().toString().trim());

        long status = 0;
        status = mDdHelper.addBook(title,author,pages);
        if(status == -1) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Added new book", Toast.LENGTH_LONG).show();

            mBookTitleEdittext.getText().clear();
            mBookAuthorEdittext.getText().clear();
            mPagesNumberEdittext.getText().clear();
        }
    }

    private void findViewById() {
        mBookTitleEdittext = findViewById(R.id.book_title_edittext);
        mBookAuthorEdittext = findViewById(R.id.book_author_edittext);
        mPagesNumberEdittext = findViewById(R.id.pages_number_edittext);
        mAddButton = findViewById(R.id.add_button);
    }
}