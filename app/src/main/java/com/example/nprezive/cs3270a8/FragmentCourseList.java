package com.example.nprezive.cs3270a8;


import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nprezive.cs3270a8.db.Course;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCourseList extends Fragment {


    public FragmentCourseList() {
        // Required empty public constructor
    }


    private OnFragmentCourseListInteractionListener mCallback;
    private CourseListRecyclerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.courseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CourseListRecyclerAdapter(new ArrayList<Course>(), mCallback);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        ViewModelProviders.of(this)
                .get(AllCoursesViewModel.class)
                .getAllCourses(getContext())
                .observe(this, new Observer<List<Course>>() {
                    @Override
                    public void onChanged(@Nullable List<Course> courses) {
                        if (courses != null) {
                            adapter.addItems(courses);
                        }
                    }
                });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnFragmentCourseListInteractionListener) activity;
        }
        catch (ClassCastException ex) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentCourseListInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public interface OnFragmentCourseListInteractionListener {
        void onFragmentCourseListInteraction(Course course);
        void onFragmentCourseListLongClick(Course course);
    }
}
