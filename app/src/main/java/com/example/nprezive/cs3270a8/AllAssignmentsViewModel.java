package com.example.nprezive.cs3270a8;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.nprezive.cs3270a8.models.Assignment;

import java.util.List;

/**
 * Created by nprezive on 3/19/18.
 */

public class AllAssignmentsViewModel extends ViewModel {
    private LiveData<List<Assignment>> assignmentList;

    public LiveData<List<Assignment>> getAllAssignments(Context context) {
        if (assignmentList == null)
            assignmentList =
    }
}
