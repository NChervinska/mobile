package com.chervinska.nure.mobile.adapters;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chervinska.nure.mobile.R;
import com.chervinska.nure.mobile.listeners.AnimalListener;
import com.chervinska.nure.mobile.models.Animal;

import java.util.ArrayList;
import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {
    private List<Animal> animals;
    private AnimalListener animalListener;

    public AnimalAdapter(List<Animal> animals, AnimalListener animalsListener) {
        this.animals = animals;
        this.animalListener = animalsListener;

    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AnimalViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.animal_item,
                        parent,
                        false
                )
        );

    }

    @Override
    public void onBindViewHolder(@NonNull AnimalAdapter.AnimalViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.setAnimal(animals.get(position));
        holder.tableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animalListener.onAnimalClicked(animals.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return animals.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    static class AnimalViewHolder extends RecyclerView.ViewHolder {

        TextView nameView, typeView, ageView, employeeView;
        TableRow tableLayout;

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            tableLayout = itemView.findViewById(R.id.table);
            nameView = itemView.findViewById(R.id.animalName);
            typeView = itemView.findViewById(R.id.animalType);
            ageView = itemView.findViewById(R.id.animalAge);
        }

        void setAnimal(Animal animal) {
            nameView.setText(animal.getName());
            typeView.setText(animal.getType());
            ageView.setText("" + animal.getAge());
        }
    }

}
