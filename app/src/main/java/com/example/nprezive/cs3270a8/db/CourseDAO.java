package com.example.nprezive.cs3270a8.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by nprezive on 3/3/18.
 */

@Dao
public interface CourseDAO {

    @Query("SELECT * FROM course")
    LiveData<List<Course>> getAll();

    @Query("SELECT * FROM course WHERE cid = :cid")
    LiveData<Course> getCourseByID(int cid);

    @Update
    void update(Course course);

    @Query("UPDATE course " +
            "SET id=:id, name=:name, course_code=:courseCode, start_at=:startAt, end_at=:endAt " +
            "WHERE cid=:cid")
    void updateByID(int cid, String id, String name, String courseCode, String startAt, String endAt);

    @Insert
    void insertAll(Course... courses);

    @Delete
    void delete(Course course);

    @Query("DELETE FROM course WHERE cid = :cid")
    void deleteByID(int cid);
}
