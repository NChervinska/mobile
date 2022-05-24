package com.chervinska.nure.mobile.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chervinska.nure.mobile.R;
import com.chervinska.nure.mobile.listeners.AnimalListener;
import com.chervinska.nure.mobile.models.Activity;
import com.chervinska.nure.mobile.models.Animal;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {
    private List<Activity> activities;

    public ActivityAdapter(List<Activity> activities) {
        this.activities = activities;

    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActivityViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.activity_item,
                        parent,
                        false
                )
        );

    }

    @Override
    public void onBindViewHolder(@NonNull ActivityAdapter.ActivityViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.setActivity(activities.get(position));
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    static class ActivityViewHolder extends RecyclerView.ViewHolder {

        TextView activeHour, sleepHour;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            activeHour = itemView.findViewById(R.id.activityActiveHour);
            sleepHour = itemView.findViewById(R.id.activitySleepHour);
        }

        void setActivity(Activity activity) {
            activeHour.setText(itemView.getResources().getString(R.string.activeHour) + activity.getActiveHour());
            sleepHour.setText(itemView.getResources().getString(R.string.sleepHour) + activity.getSleepHour());
        }
    }

}
