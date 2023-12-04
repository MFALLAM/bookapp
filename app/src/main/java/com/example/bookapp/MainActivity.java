package com.example.bookapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bookapp.adapter.MainAdapter;
import com.example.bookapp.data.Book;
import com.example.bookapp.data.BookDatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    BookDatabaseHelper mDdHelper;
    List<Book> booksList = new ArrayList<>();
    MainAdapter mMainAdapter;
    MainAdapter.ItemClickListener listenOnItemClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById();

        booksList = new ArrayList<>();

        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

        mDdHelper = new BookDatabaseHelper(this);

        mMainAdapter = new MainAdapter(this, booksList, listenOnItemClick);

        recyclerView.setAdapter(mMainAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        displayBooks();
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

            Book book = new Book(id, title, author, pageCount);
            booksList.add(book);
        }
    }

    private void findViewById() {
        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.float_action_btn_add);
    }
}