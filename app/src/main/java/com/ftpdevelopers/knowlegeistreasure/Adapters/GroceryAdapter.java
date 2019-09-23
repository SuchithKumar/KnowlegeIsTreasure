package com.ftpdevelopers.knowlegeistreasure.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ftpdevelopers.knowlegeistreasure.R;
import com.ftpdevelopers.knowlegeistreasure.contracts.GroceryContract;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public GroceryAdapter(Context context, Cursor cursor){
        mContext=context;
        mCursor=cursor;
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder{

        public TextView itemName ;
        public TextView itemQuantity;

        public GroceryViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.groceryItem);
            itemQuantity = itemView.findViewById(R.id.groceryQuantity);


        }
    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.grocery_item,parent,false);
        return new GroceryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(GroceryContract.GroceryEntry.COLUMN_ITEM));
        int quantity = mCursor.getInt(mCursor.getColumnIndex(GroceryContract.GroceryEntry.COLUMN_QUANTITY));
        long id = mCursor.getLong(mCursor.getColumnIndex(GroceryContract.GroceryEntry.COLUMN_ID));

        holder.itemName.setText(name);
        holder.itemQuantity.setText(String.valueOf(quantity));
        holder.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();

    }

    public void swapCursor(Cursor newCursor){
        if(mCursor!=null){
            mCursor.close();
        }

        mCursor = newCursor;

        if(newCursor !=null){
            notifyDataSetChanged();
        }
    }

}
