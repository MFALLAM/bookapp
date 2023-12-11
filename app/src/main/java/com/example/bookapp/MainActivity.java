package com.example.bookapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookapp.adapter.MainAdapter;
import com.example.bookapp.data.Book;
import com.example.bookapp.data.BookDatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MainAdapter.ItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private final static String LOG_TAG = BookDatabaseHelper.class.getSimpleName();
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private BookDatabaseHelper mDdHelper;
    private List<Book> booksList;
    private MainAdapter mMainAdapter;
    public static final String ARG_UPDATE = "UPDATE";
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        booksList = new ArrayList<>();
        initializeViews();

        // First Step: Set default values of the shared prefs
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);

        floatingActionButton.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ActionActivity.class));
        });

        mDdHelper = new BookDatabaseHelper(this);

        initRecyclerView();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String book) {
                filterBooks(book);
                return true;
            }
        });

        setupSharedPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void filterBooks(String filter) {
        List<Book> filteredBooks = new ArrayList<>();
        for (Book book : booksList) {
            if (book.getBookTitle().toLowerCase().contains(filter.toLowerCase()) || book.getAuthorName().toLowerCase().contains(filter.toLowerCase())) {
                filteredBooks.add(book);
            }
        }
        if (filteredBooks.isEmpty()) {
            Toast.makeText(this, "Can not find book with this title!", Toast.LENGTH_LONG).show();
        } else {
            mMainAdapter.setFilteredBooks(filteredBooks);
        }
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
        mSearchView = findViewById(R.id.searchView);
        mSearchView.clearFocus();
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

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String key) {
        // To get the shared preference of the project we need to call  SharedPreferences

        Log.d(LOG_TAG, "onSharedPreferenceChanged: " + sharedPreferences.getString(key, ""));
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PreferenceConstants.USER_SETTINGS_PREFERENCES, Context.MODE_PRIVATE);

        Log.d(LOG_TAG, "setupSharedPreferences: " + sharedPreferences.getString("sort_key", ""));

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

}