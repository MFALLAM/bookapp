package com.example.bookapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookapp.R;
import com.example.bookapp.data.Book;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.viewHolder> {

    private Context mContext;
    private List<Book> mListBooks;
    private ItemClickListener mListenOnItemClick;

    public MainAdapter(Context context, List<Book> listBooks, ItemClickListener listenOnItemClick) {
        this.mContext = context;
        this.mListBooks = listBooks;
        this.mListenOnItemClick = listenOnItemClick;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_view, parent, false);
        return new viewHolder(view, mListenOnItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Book book = mListBooks.get(position);

        //holder.book_id_textView.setText(String.valueOf(book.getId()));
        holder.book_title_textview.setText(book.getBookTitle());
        holder.book_author_textview.setText(book.getAuthorName());
        holder.book_pages_textview.setText(book.getTotalPages()+" pages");
        holder.book_publish_date_textview.setText(getDateCurrentTimeZone(book.getTimestamp()));

        Log.d("MainAdapter", "onBindViewHolder: SQLite Date->" + book.getTimestamp());

    }

    @Override
    public int getItemCount() {
        return mListBooks.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView book_id_textView;
        TextView book_title_textview;
        TextView book_author_textview;
        TextView book_pages_textview;
        TextView book_publish_date_textview;

        ItemClickListener mListenOnItemClick;

        public viewHolder(@NonNull View itemView, ItemClickListener listenOnItemClick) {
            super(itemView);

            //book_id_textView = itemView.findViewById(R.id.book_id_textview);
            book_title_textview = itemView.findViewById(R.id.book_title_textview);
            book_author_textview = itemView.findViewById(R.id.book_author_textview);
            book_pages_textview = itemView.findViewById(R.id.book_pages_textview);
            book_publish_date_textview = itemView.findViewById(R.id.book_publish_date_textview);

            this.mListenOnItemClick = listenOnItemClick;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.mListenOnItemClick.onItemClick(getAdapterPosition());
        }
    }

    public void setFilteredBooks(List<Book> books) {
        this.mListBooks = books;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    private static String getDateCurrentTimeZone(String timestamp) {
        if (timestamp == null || timestamp.isEmpty()) {
            return "Invalid Date";
        }

        try {
            // Assuming timestamp is in the format "yyyy-MM-dd HH:mm:ss"
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateObject = inputFormat.parse(timestamp);

            // Assuming you want the output in the format "MM/dd/yyyy"
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMM-dd");
            return outputFormat.format(dateObject);
        } catch (ParseException e) {
            // Handle the case where the timestamp string cannot be parsed to a date
            e.printStackTrace();
            return "Invalid Date";
        }
    }
}
