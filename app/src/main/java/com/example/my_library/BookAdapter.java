// BookAdapter.java
package com.example.my_library;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> bookList;
    private Context context;

    // Constructor
    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bookName.setText(book.getName());
        holder.bookAuthor.setText(book.getAuthor());
        holder.bookPublication.setText(book.getPublication());
        holder.bookQuantity.setText(String.valueOf(book.getQuantity()));

        // Intent to navigate to `updateBook` activity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, updateBook.class);
            intent.putExtra("BOOK_ID", book.getId()); // Pass book ID for editing
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView bookName, bookAuthor, bookPublication, bookQuantity;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.txtbname);
            bookAuthor = itemView.findViewById(R.id.txtbauthor);
            bookPublication = itemView.findViewById(R.id.txtbpublication);
            bookQuantity = itemView.findViewById(R.id.txtquntity);
        }
    }
}
