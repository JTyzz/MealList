package com.earthdefensesystem.meallist.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.earthdefensesystem.meallist.models.Dish;
import com.earthdefensesystem.meallist.ulilities.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private Context ctx;

    public DatabaseHandler(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSON);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DISH_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.KEY_DISH_NAME + " TEXT,"
                + Constants.KEY_DISH_INGREDIENTS + " TEXT,"
                + Constants.KEY_DATE + " LONG);";

        db.execSQL(CREATE_DISH_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }

    public void addDish(Dish dish) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_DISH_NAME, dish.getName());
        values.put(Constants.KEY_DISH_INGREDIENTS, dish.getIngredients());
        values.put(Constants.KEY_DATE, java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME, null, values);
    }

    public List<Dish> getAllDishes() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Dish> dishList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{
                        Constants.KEY_ID, Constants.KEY_DISH_NAME, Constants.KEY_DISH_INGREDIENTS, Constants.KEY_DATE},
                null, null, null, null,
                Constants.KEY_DATE + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Dish dish = new Dish();
                dish.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                dish.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_DISH_NAME)));
                dish.setIngredients(cursor.getString((cursor.getColumnIndex(Constants.KEY_DISH_INGREDIENTS))));

                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(
                        new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE))).getTime());

                dish.setDateItemAdded(formattedDate);
                dishList.add(dish);

            } while (cursor.moveToNext());
        }
        return dishList;
    }


    public void deleteDish(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();

    }

    public int getDishCount() {
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }
}
