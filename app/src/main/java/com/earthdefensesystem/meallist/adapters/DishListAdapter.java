package com.earthdefensesystem.meallist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.earthdefensesystem.meallist.R;
import com.earthdefensesystem.meallist.data.DatabaseHandler;
import com.earthdefensesystem.meallist.models.Dish;

import java.util.List;

public class DishListAdapter extends RecyclerView.Adapter<DishListAdapter.ViewHolder> {
    private Context context;
    private List<Dish> dataList;

    public DishListAdapter(Context context, List<Dish> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public DishListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull DishListAdapter.ViewHolder holder, int position) {
        Dish dish = dataList.get(position);

        holder.dishName.setText(dish.getName());
        holder.dishIngredients.setText(dish.getIngredients());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dishName;
        TextView dishIngredients;
        Button deleteBtn;
        int id;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            dishName = itemView.findViewById(R.id.name);
            dishIngredients = itemView.findViewById(R.id.ingredients);
            deleteBtn = itemView.findViewById(R.id.delete_btn);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(context);
                    int position = getAdapterPosition();
                    Dish dish = dataList.get(position);
                    db.deleteDish(dish.getId());
                    dataList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });

        }
    }


}
