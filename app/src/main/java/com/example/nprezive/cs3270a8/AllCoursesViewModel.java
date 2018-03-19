package com.example.nprezive.cs3270a8;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.nprezive.cs3270a8.db.AppDatabase;
import com.example.nprezive.cs3270a8.db.Course;

import java.util.List;

/**
 * Created by nprezive on 3/3/18.
 */

public class AllCoursesViewModel extends ViewModel {
    private LiveData<List<Course>> courseList;

    public LiveData<List<Course>> getAllCourses(Context context) {
        if (courseList == null)
            courseList = AppDatabase.getInstance(context).courseDAO().getAll();

        return courseList;
    }

    public Course getCourseByID(int cid) {
        for (Course c: courseList.getValue()) {
            if (c.getCid() == cid)
                return c;
        }

        return null;
    }
}
