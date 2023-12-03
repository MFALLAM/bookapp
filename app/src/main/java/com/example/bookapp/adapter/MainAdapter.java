package com.example.bookapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookapp.R;
import com.example.bookapp.data.Book;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.viewHolderAdapter> {

    private Context mContext;
    private List<Book> mListBooks;

    public MainAdapter(Context context, List<Book> listBooks) {
        this.mContext = context;
        this.mListBooks = listBooks;
    }

    @NonNull
    @Override
    public viewHolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_view, parent, false);
        return new viewHolderAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderAdapter holder, int position) {
        Book book = mListBooks.get(position);

        holder.book_id_textView.setText(String.valueOf(book.getId()));
        holder.book_title_textview.setText(book.getBookTitle());
        holder.book_author_textview.setText(book.getAuthorName());
        holder.book_pages_textview.setText(String.valueOf(book.getTotalPages()));
    }

    @Override
    public int getItemCount() {
        return mListBooks.size();
    }

    public class viewHolderAdapter extends RecyclerView.ViewHolder {

        TextView book_id_textView;
        TextView book_title_textview;
        TextView book_author_textview;
        TextView book_pages_textview;

        public viewHolderAdapter(@NonNull View itemView) {
            super(itemView);
            book_id_textView = itemView.findViewById(R.id.book_id_textview);
            book_title_textview = itemView.findViewById(R.id.book_title_textview);
            book_author_textview = itemView.findViewById(R.id.book_author_textview);
            book_pages_textview = itemView.findViewById(R.id.book_pages_textview);
        }
    }
}
