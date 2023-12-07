package com.example.bookapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookapp.data.BookDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;


public class ActionActivity extends AppCompatActivity {

    private EditText mBookTitleEdittext;
    private EditText mBookAuthorEdittext;
    private EditText mPagesNumberEdittext;
    private Button mAddButton;
    private BookDatabaseHelper mDdHelper;
    private final static String LOG_TAG = BookDatabaseHelper.class.getSimpleName();
    private static boolean flag = false;
    private MenuItem mItemMenu;
    Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        findViewById();
        mDdHelper = new BookDatabaseHelper(ActionActivity.this);

        // getting the bundle back
        mBundle = getIntent().getExtras();

        if (mBundle != null && mBundle.containsKey("id")) {
            setData(mBundle);
            flag = true;
        } else {
            flag = false;
        }

        mAddButton.setOnClickListener(view -> {
            if (flag) {
                if (mBundle != null) {
                    modifyBook(mBundle);
                    showSnackBar(true, "Note updated successfully");
                } else {
                    // TODO: Handle the case where the bundle is null
                    showSnackBar(false, "Error something went wrong");
                }
            } else {
                saveBook();
                showSnackBar(true, "Note created successfully");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        mItemMenu = menu.findItem(R.id.action_delete);

        if (flag) {
            mItemMenu.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_delete:
                // do something
                AlertDialog.Builder builder = new AlertDialog.Builder(ActionActivity.this);
                builder.setMessage("Are you sure you want to delete this book?");
                builder.setTitle("Delete book");
                builder.setPositiveButton("Confirm", (dialogInterface, i) -> {
                    String id = String.valueOf(mBundle.getInt("id"));
                    removeBook(id);
                    finish();
                }).setNegativeButton("Cancel", null).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void removeBook(String id) {
        mDdHelper.deleteBook(id);
    }

    private void showSnackBar(boolean status, String message) {
        String color = status ? "#1eb2a6" : "#D81B60";
        Snackbar snackbar = Snackbar.make(findViewById(R.id.action_screen), message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.parseColor(color));
        snackbar.setTextColor(Color.parseColor("#000000"));
        snackbar.show();
    }

    private void setData(Bundle bundle) {
        ActionActivity.this.setTitle(bundle.getString("title"));
        mBookTitleEdittext.setText(bundle.getString("title"));
        mBookAuthorEdittext.setText(bundle.getString("author"));
        mPagesNumberEdittext.setText(bundle.getString("pages"));
    }

    private void modifyBook(Bundle bundle) {
        int id = bundle.getInt("id");
        String title = mBookTitleEdittext.getText().toString().trim();
        String author = mBookAuthorEdittext.getText().toString().trim();
        int pages = Integer.parseInt(mPagesNumberEdittext.getText().toString().trim());

        long status;
        status = mDdHelper.updateBook(id, title, author, pages);
        Log.d(LOG_TAG, "__modifyBookID: " + status + " _bookID_" + id);
    }

    private void saveBook() {
        String title = mBookTitleEdittext.getText().toString().trim();
        String author = mBookAuthorEdittext.getText().toString().trim();
        int pages = Integer.parseInt(mPagesNumberEdittext.getText().toString().trim());

        long status;
        status = mDdHelper.addBook(title, author, pages);

        mBookTitleEdittext.getText().clear();
        mBookAuthorEdittext.getText().clear();
        mPagesNumberEdittext.getText().clear();
    }

    private void findViewById() {
        mBookTitleEdittext = findViewById(R.id.book_title_edittext);
        mBookAuthorEdittext = findViewById(R.id.book_author_edittext);
        mPagesNumberEdittext = findViewById(R.id.pages_number_edittext);
        mAddButton = findViewById(R.id.add_button);
    }
}