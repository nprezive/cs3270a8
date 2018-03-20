package com.example.nprezive.cs3270a8;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nprezive.cs3270a8.models.Assignment;

/**
 * Created by nprezive on 3/19/18.
 */

public class AssignmentListRecyclerAdapter
        extends RecyclerView.Adapter<AssignmentListRecyclerAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txvAssignmentName;
        public View mView;
        public Assignment mItem;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
