package com.example.nprezive.cs3270a8;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.nprezive.cs3270a8.db.Course;

public class MainActivity extends AppCompatActivity
        implements FragmentCourseList.OnFragmentCourseListInteractionListener,
                    FragmentCourseView.OnFragmentCourseViewEditListener {

    FragmentManager fm;
    FloatingActionButton fabAdd;
    FragmentCourseList fragmentCourseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tbMainActivity);
        setSupportActionBar(toolbar);
        fm = getSupportFragmentManager();

        //fabAdd onclicklistener
        fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .add(android.R.id.content,
                                new FragmentCourseEdit())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        fragmentCourseList = (FragmentCourseList) fm.findFragmentByTag("fragCourseList");
    }

    @Override
    public void onFragmentCourseListInteraction(Course course) {
//        if (fragmentCourseList == null) {
//            Log.d("test", "course list is null");
//            return;
//        }

        Bundle bundle = new Bundle();
        bundle.putInt("cid", course.getCid());
        bundle.putString("id", course.getId());
        bundle.putString("name", course.getName());
        bundle.putString("courseCode", course.getCourseCode());
        bundle.putString("startAt", course.getStartAt());
        bundle.putString("endAt", course.getEndAt());

        FragmentCourseView frag = new FragmentCourseView();
        frag.setArguments(bundle);

        fm.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(android.R.id.content, frag)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void OnFragmentCourseViewEdit(Course course) {
        Bundle bundle = new Bundle();
        bundle.putInt("cid", course.getCid());
        bundle.putString("id", course.getId());
        bundle.putString("name", course.getName());
        bundle.putString("courseCode", course.getCourseCode());
        bundle.putString("startAt", course.getStartAt());
        bundle.putString("endAt", course.getEndAt());

        FragmentCourseEdit frag = new FragmentCourseEdit();
        frag.setArguments(bundle);

        fm.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(android.R.id.content, frag)
                .addToBackStack(null)
                .commit();
    }
}
