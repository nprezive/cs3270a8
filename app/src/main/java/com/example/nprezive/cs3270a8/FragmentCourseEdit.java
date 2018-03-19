package com.example.nprezive.cs3270a8;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.example.nprezive.cs3270a8.db.AppDatabase;
import com.example.nprezive.cs3270a8.db.Course;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCourseEdit extends DialogFragment {

    public FragmentCourseEdit() {
        // Required empty public constructor
    }

    private View root;
    TextInputEditText txvID, txvName, txvCourseCode, txvStartAt, txvEndAt;
    private int cid;
    private String id, name, courseCode, startAt, endAt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        boolean isUpdate = false;

        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_course_edit, container, false);

        //Toolbar stuff
        Toolbar toolbar = (Toolbar) root.findViewById(R.id.tbFragCourseEdit);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeButtonEnabled(true);
//            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
//        }

        txvID = (TextInputEditText) root.findViewById(R.id.edtID);
        txvName = (TextInputEditText) root.findViewById(R.id.edtName);
        txvCourseCode = (TextInputEditText) root.findViewById(R.id.edtCourseCode);
        txvStartAt = (TextInputEditText) root.findViewById(R.id.edtStartAt);
        txvEndAt = (TextInputEditText) root.findViewById(R.id.edtEndAt);

        final Bundle bundle = this.getArguments();
        if (bundle != null) {
            isUpdate = true;

            id = bundle.getString("id");
            name = bundle.getString("name");
            courseCode = bundle.getString("courseCode");
            startAt = bundle.getString("startAt");
            endAt = bundle.getString("endAt");

            txvID.setText(id);
            txvName.setText(name);
            txvCourseCode.setText(courseCode);
            txvStartAt.setText(startAt);
            txvEndAt.setText(endAt);
        }

        FloatingActionButton fabSave = root.findViewById(R.id.fabSave);
        final boolean finalIsUpdate = isUpdate;
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check that edt fields are not null
                if (txvID.getText().toString().equals("")
                        || txvName.getText().toString().equals("")
                        || txvCourseCode.getText().toString().equals("")
                        || txvStartAt.getText().toString().equals("")
                        || txvEndAt.getText().toString().equals("")) {
                    Toast.makeText(getContext(),
                            getString(R.string.fields_cannot_be_blank),
                            Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                //update version
                if (finalIsUpdate) {
//                    Log.d("test", "hit the update version?");

                    id = txvID.getText().toString();
                    name = txvName.getText().toString();
                    courseCode = txvCourseCode.getText().toString();
                    startAt = txvStartAt.getText().toString();
                    endAt = txvEndAt.getText().toString();

                    final Course course = new Course(id, name, courseCode, startAt, endAt);
                    course.setCid(cid);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            //I don't understand why this update doesn't work. I'm going to need
                            //professorial help :(

                            AppDatabase.getInstance(getContext())
                                    .courseDAO()
//                                    .updateByID(cid, id, name, courseCode, startAt, endAt);
                                    .update(course);

//                            Log.d("test", "name value: " + name);
                        }
                    }).start();

                    dismiss();
                    return;
                }

                //Insert version
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase.getInstance(getContext())
                                .courseDAO()
                                .insertAll(new Course(
                                        txvID.getText().toString(),
                                        txvName.getText().toString(),
                                        txvCourseCode.getText().toString(),
                                        txvStartAt.getText().toString(),
                                        txvEndAt.getText().toString()));
                    }
                }).start();

                dismiss();
            }
        });

        return root;
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

        getActivity().getMenuInflater().inflate(R.menu.menu_fragment_course_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // handle close button click here
            dismiss();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
