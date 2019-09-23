package com.ftpdevelopers.knowlegeistreasure.contracts;

import android.provider.BaseColumns;

public class GroceryContract {

    private GroceryContract(){

    }

    public static final class GroceryEntry implements BaseColumns{
        public static final String TABLE_NAME = "groceryList";
        public static final String COLUMN_ID= "id";
        public static final String COLUMN_ITEM = "item";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_TIMESTAMP = "timestamp";

    }
}
