package com.example.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
    private final static String LOG_TAG = BookDatabaseHelper.class.getSimpleName();
    private static boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        findViewById();
        mDdHelper = new BookDatabaseHelper(ActionActivity.this);

        // getting the bundle back
        Bundle bundle = getIntent().getExtras();


        if (bundle != null && bundle.containsKey("id")) {
            setData(bundle);
            flag = true;
        }

        mAddButton.setOnClickListener(view -> {
            if (flag) {
                if (bundle != null) {
                    modifyBook(bundle);
                } else {
                    // TODO: Handle the case where the bundle is null
                }
            } else {
                saveBook();
            }
        });

    }

    private void setData(Bundle bundle) {
        mBookTitleEdittext.setText(bundle.getString("title"));
        mBookAuthorEdittext.setText(bundle.getString("author"));
        mPagesNumberEdittext.setText(bundle.getString("pages"));
    }

    private void modifyBook(Bundle bundle) {
        int id = bundle.getInt("id");
        String title = mBookTitleEdittext.getText().toString().trim();
        String author = mBookAuthorEdittext.getText().toString().trim();
        int pages = Integer.parseInt(mPagesNumberEdittext.getText().toString().trim());

        long status = 0;
        status = mDdHelper.updateBook(id, title, author, pages);
        Log.d(LOG_TAG, "__modifyBookID: " + status + " _bookID_" + id);
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