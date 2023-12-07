package com.example.bookapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bookapp.adapter.MainAdapter;
import com.example.bookapp.data.Book;
import com.example.bookapp.data.BookDatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MainAdapter.ItemClickListener {
    private final static String LOG_TAG = BookDatabaseHelper.class.getSimpleName();
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private BookDatabaseHelper mDdHelper;
    private List<Book> booksList;
    private MainAdapter mMainAdapter;
    public static final String ARG_UPDATE = "UPDATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        booksList = new ArrayList<>();
        initializeViews();

        floatingActionButton.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ActionActivity.class));
        });

        mDdHelper = new BookDatabaseHelper(this);

        initRecyclerView();
        Log.d(LOG_TAG, "onCreate: is called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        booksList.clear();
        displayBooks();
        mMainAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        mMainAdapter = new MainAdapter(this, booksList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mMainAdapter);
    }

    private void displayBooks() {
        Cursor cursor = mDdHelper.getAllBooks();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data has been found!", Toast.LENGTH_LONG).show();
        } else {
            populateBooksList(cursor);
        }
    }

    private void populateBooksList(Cursor cursor) {
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String author = cursor.getString(2);
            int pageCount = cursor.getInt(3);
            String timestamp = cursor.getString(4);

            Book book = new Book(id, title, author, pageCount, timestamp);
            booksList.add(book);
        }
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.float_action_btn_add);
    }

    private void startActionActivity(int position) {
        Book selectedBook = booksList.get(position);

        Bundle bundle = new Bundle();
        bundle.putString(ARG_UPDATE, "UPDATE");
        bundle.putInt("id", selectedBook.getId());
        bundle.putString("title", selectedBook.getBookTitle());
        bundle.putString("author", selectedBook.getAuthorName());
        bundle.putString("pages", Integer.toString(selectedBook.getTotalPages()));

        Intent intent = new Intent(MainActivity.this, ActionActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        startActionActivity(position);
    }

}