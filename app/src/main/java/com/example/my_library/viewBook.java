package com.example.my_library;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class viewBook extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<CardItem> cardItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_viewbook);

        recyclerView = findViewById(R.id.recyclerViewviewbook);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));



        cardItemList = new ArrayList<>();
        adapter = new MyAdapter(this, cardItemList);
        recyclerView.setAdapter(adapter);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    } @Override
    protected void onResume() {
        super.onResume();
        refreshAdapters();
    }

    private void refreshAdapters() {
        cardItemList.clear();

        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getAllBook();

        if (cursor != null && cursor.moveToFirst()) {
            int bookNameIndex = cursor.getColumnIndex(DBHelper.BOOK_NAME);
            int bookAuthorIndex  = cursor.getColumnIndex(DBHelper.BOOK_AUTHOR);
            int bookPublisherIndex  = cursor.getColumnIndex(DBHelper.BOOK_PUBLICATION);
            int bookQuantityIndex  = cursor.getColumnIndex(DBHelper.BOOK_QUANTITY);



            do {
                String name = cursor.getString(bookNameIndex);
                String author = cursor.getString(bookAuthorIndex);
                String publisher = cursor.getString(bookPublisherIndex);
                String quantity = cursor.getString(bookQuantityIndex);

                cardItemList.add(new CardItem(name, author, publisher, quantity));
            } while (cursor.moveToNext());
            cursor.close();
        }
        dbHelper.close();

        adapter.notifyDataSetChanged();
    }

    private static class CardItem {
        String name;
        String author;
        String publisher;
        String quantity;



        CardItem(String name, String author, String publisher, String quantity ) {
            this.name = name;
            this.author = author;
            this.publisher = publisher;
            this.quantity = quantity;

        }
    }

    private static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private final List<CardItem> cardItems;
        private final Context context;

        MyAdapter(Context context, List<CardItem> cardItems) {
            this.context = context;
            this.cardItems = cardItems;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            CardItem item = cardItems.get(position);

            holder.textViewName.setText(item.name);
            holder.textViewAuthor.setText(item.author);
            holder.textViewPublisher.setText(item.publisher);
            holder.textViewQuantity.setText(item.quantity);



            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, updateBook.class);
                intent.putExtra("bookName", item.name);
                context.startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return cardItems.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            final TextView textViewName;
            final TextView textViewAuthor;
            final TextView textViewPublisher;
            final TextView textViewQuantity;


            ViewHolder(View itemView) {
                super(itemView);
                textViewName = itemView.findViewById(R.id.txtbname);
                textViewAuthor = itemView.findViewById(R.id.txtbauthor);
                textViewPublisher = itemView.findViewById(R.id.txtbpublication);
                textViewQuantity = itemView.findViewById(R.id.txtquntity);

            }
        }
    }
}