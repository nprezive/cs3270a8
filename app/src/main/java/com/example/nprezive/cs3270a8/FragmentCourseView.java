package com.example.nprezive.cs3270a8;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.nprezive.cs3270a8.db.AppDatabase;
import com.example.nprezive.cs3270a8.db.Course;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCourseView extends DialogFragment {

    private OnFragmentCourseViewEditListener mCallback;
    private int cid;
    private String id, name, courseCode, startAt, endAt;

    public FragmentCourseView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_course_view, container, false);

        //Toolbar stuff
        Toolbar toolbar = (Toolbar) root.findViewById(R.id.tbFragCourseView);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        }
        setHasOptionsMenu(true);

        //populate fields with stuff from the bundle
        Bundle bundle = this.getArguments();
        cid = bundle.getInt("cid");
        id = bundle.getString("id");
        name = bundle.getString("name");
        courseCode = bundle.getString("courseCode");
        startAt = bundle.getString("startAt");
        endAt = bundle.getString("endAt");

        ((TextView) root.findViewById(R.id.txvID)).setText(id);
        ((TextView) root.findViewById(R.id.txvName)).setText(name);
        ((TextView) root.findViewById(R.id.txvCourseCode)).setText(courseCode);
        ((TextView) root.findViewById(R.id.txvStartAt)).setText(startAt);
        ((TextView) root.findViewById(R.id.txvEndAt)).setText(endAt);

        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnFragmentCourseViewEditListener) activity;
        }
        catch (ClassCastException ex) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentCourseViewEditListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        getActivity().getMenuInflater().inflate(R.menu.menu_fragment_course_view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            dismiss();
            return true;
        }
        else if (id == R.id.action_edit) {

            Course course = new Course(this.id, name, courseCode, startAt, endAt);
            course.setCid(cid);
            mCallback.OnFragmentCourseViewEdit(course);

            dismiss();
            return true;
        }
        else if (id == R.id.action_delete) {

            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.delete_course)
                    .setMessage(R.string.confirm_delete_course_message)
//                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    AppDatabase.getInstance(getContext())
                                            .courseDAO()
                                            .deleteByID(cid);
                                }
                            }).start();

                            dismiss();
                        }})
                    .setNegativeButton(android.R.string.no, null).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public interface OnFragmentCourseViewEditListener {
        void OnFragmentCourseViewEdit(Course course);
    }
}
