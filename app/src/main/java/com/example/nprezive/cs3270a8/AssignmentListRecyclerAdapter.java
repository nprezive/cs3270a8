package com.example.nprezive.cs3270a8;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nprezive.cs3270a8.models.Assignment;

import java.util.List;

/**
 * Created by nprezive on 3/19/18.
 */

public class AssignmentListRecyclerAdapter
        extends RecyclerView.Adapter<AssignmentListRecyclerAdapter.ViewHolder> {

    private List<Assignment> mValues;

    public AssignmentListRecyclerAdapter(List<Assignment> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignment_recycler_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Assignment assignment = mValues.get(position);
        if (assignment != null) {
            holder.mItem = assignment;
            holder.txvAssignmentName.setText(assignment.getName());
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void addItems(List<Assignment> items) {
        mValues.clear();
        mValues.addAll(items);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txvAssignmentName;
        public View mView;
        public Assignment mItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            txvAssignmentName = (TextView) itemView.findViewById(R.id.assignmentName);
        }
    }
}
