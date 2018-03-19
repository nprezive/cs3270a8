package com.example.nprezive.cs3270a8.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by nprezive on 3/3/18.
 */

@Entity
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int cid;

    private String id;

    private String name;

    @ColumnInfo(name = "course_code")
    private String courseCode;

    @ColumnInfo(name = "start_at")
    private String startAt;

    @ColumnInfo(name = "end_at")
    private String endAt;

    public Course(String id, String name, String courseCode, String startAt, String endAt) {
        this.id = id;
        this.name = name;
        this.courseCode = courseCode;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
