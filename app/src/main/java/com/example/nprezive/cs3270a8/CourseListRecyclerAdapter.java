package com.example.nprezive.cs3270a8;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nprezive.cs3270a8.db.Course;
import com.example.nprezive.cs3270a8.FragmentCourseList.OnFragmentCourseListInteractionListener;

import java.util.List;


/**
 * Created by nprezive on 3/3/18.
 */

public class CourseListRecyclerAdapter
        extends RecyclerView.Adapter<CourseListRecyclerAdapter.ViewHolder> {

    private final List<Course> mValues;
    private final OnFragmentCourseListInteractionListener mListener;

    public CourseListRecyclerAdapter(List<Course> items,
                                     OnFragmentCourseListInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Course course = mValues.get(position);
        if (course != null) {
            holder.mItem = course;
            holder.txvCourseName.setText(course.getName());
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onFragmentCourseListInteraction(holder.mItem);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void addItems(List<Course> items) {
        mValues.clear();
        mValues.addAll(items);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txvCourseName;
        public View mView;
        public Course mItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            txvCourseName = (TextView) itemView.findViewById(R.id.courseName);
        }
    }
}
