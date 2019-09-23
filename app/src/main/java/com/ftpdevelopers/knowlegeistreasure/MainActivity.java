package com.ftpdevelopers.knowlegeistreasure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ftpdevelopers.knowlegeistreasure.Adapters.GroceryAdapter;
import com.ftpdevelopers.knowlegeistreasure.DBHelpers.GroceryDBHelper;
import com.ftpdevelopers.knowlegeistreasure.contracts.GroceryContract;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase sqLiteDatabase;
    private GroceryAdapter groceryAdapter;
    private EditText itemEditText ;
    private TextView itemQuantity ;
    private int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GroceryDBHelper dbHelper = new GroceryDBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        groceryAdapter = new GroceryAdapter(this,getAllItems());
        recyclerView.setAdapter(groceryAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT){

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long)viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

        itemEditText = findViewById(R.id.editTextName);
        itemQuantity = findViewById(R.id.textViewAmount);

        Button incrementButton = findViewById(R.id.buttonIncrease);
        Button decrementButton = findViewById(R.id.buttonDecrease);
        Button addItemButton = findViewById(R.id.buttonAdd);

        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementQuantity();
            }
        });

        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementQuantity();
            }
        });

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
    }

    public void incrementQuantity(){
        quantity++;
        itemQuantity.setText(String.valueOf(quantity));
    }

    public void decrementQuantity(){
        if(quantity>0) {
            quantity--;
            itemQuantity.setText(String.valueOf(quantity));
        }
    }

    public void addItem(){

        if(itemEditText.getText().toString().trim().length()>0 && quantity>0){
            String itemName = itemEditText.getText().toString();

            ContentValues cv = new ContentValues();
            cv.put(GroceryContract.GroceryEntry.COLUMN_ITEM,itemName);
            cv.put(GroceryContract.GroceryEntry.COLUMN_QUANTITY,quantity);

            sqLiteDatabase.insert(GroceryContract.GroceryEntry.TABLE_NAME,null,cv);
            groceryAdapter.swapCursor(getAllItems());

            itemEditText.getText().clear();
            quantity = 0;
            itemQuantity.setText("0");
        }

    }

    public void removeItem(long id){
        sqLiteDatabase.delete(GroceryContract.GroceryEntry.TABLE_NAME, GroceryContract.GroceryEntry.COLUMN_ID+" = "+id,null);
        groceryAdapter.swapCursor(getAllItems());
    }

    private Cursor getAllItems(){
        return sqLiteDatabase.query(GroceryContract.GroceryEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                GroceryContract.GroceryEntry.COLUMN_TIMESTAMP +" DESC;");
    }
}
