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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MainAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    BookDatabaseHelper mDdHelper;
    List<Book> booksList = new ArrayList<>();
    MainAdapter mMainAdapter;
    MainAdapter.ItemClickListener listenOnItemClick;

    public static final String ARG_ADD = "ADD";
    public static final String ARG_UPDATE = "UPDATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        booksList = new ArrayList<>();
        findViewById();

        floatingActionButton.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ActionActivity.class));
        });

        mDdHelper = new BookDatabaseHelper(this);

        initRecyclerView();
        displayBooks();

    }

    @Override
    protected void onStart() {
        super.onStart();
        initRecyclerView();
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
            int timestamp = cursor.getInt(4);

            Book book = new Book(id, title, author, pageCount, timestamp);
            booksList.add(book);
        }
    }

    private void findViewById() {
        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.float_action_btn_add);
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();

        bundle.putString(ARG_UPDATE, "UPDATE");
        bundle.putInt("id", booksList.get(position).getId());
        bundle.putString("title", booksList.get(position).getBookTitle());
        bundle.putString("author", booksList.get(position).getAuthorName());
        bundle.putString("pages", Integer.toString(booksList.get(position).getTotalPages()));

        Intent intent = new Intent(MainActivity.this, ActionActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

}