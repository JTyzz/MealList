package com.earthdefensesystem.meallist.activities;

import android.content.Intent;
import android.os.Bundle;

import com.earthdefensesystem.meallist.R;
import com.earthdefensesystem.meallist.adapters.DishListAdapter;
import com.earthdefensesystem.meallist.data.DatabaseHandler;
import com.earthdefensesystem.meallist.models.Dish;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText dishName;
    private EditText dishIngredients;
    private EditText ingredientItems;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        FloatingActionButton ing_fab = findViewById(R.id.fab_ingredients);

        ing_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createQueryPopup();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopup();
            }
        });

        db = new DatabaseHandler(this);
        RecyclerView recyclerView = findViewById(R.id.rv_dishlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Dish> dishList;
        List<Dish> listItems = new ArrayList<>();

        dishList = db.getAllDishes();

        for (Dish d : dishList) {
            Dish dish = new Dish();
            dish.setName(d.getName());
            dish.setIngredients(d.getIngredients());
            dish.setId(d.getId());

            listItems.add(dish);
        }

        DishListAdapter recyclerViewAdapter = new DishListAdapter(this, listItems);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private void createPopup() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);
        dishName = view.findViewById(R.id.dish_name);
        dishIngredients = view.findViewById(R.id.dish_ingredients);

        Button saveBtn = view.findViewById(R.id.save_button);

        dialogBuilder.setView(view);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dishName.getText().toString().isEmpty()) {
                    addToDB(v);
                }
            }
        });

    }

    private void createQueryPopup(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.ingredient_popup, null);


        Button queryBtn = view.findViewById(R.id.query_button);
        ingredientItems = view.findViewById(R.id.ingredient_items);

        dialogBuilder.setView(view);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    private void addToDB(View v) {
        Dish dish = new Dish();

        dish.setName(dishName.getText().toString());
        dish.setIngredients(dishIngredients.getText().toString());

        db.addDish(dish);

        Snackbar.make(v, "Item Saved", Snackbar.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, MainActivity.class));
    }
}
