package com.duanndz.apache.poi.services;

import com.duanndz.apache.poi.models.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By duan.nguyen at 5/17/20 9:23 AM
 */
public class DataServiceImpl implements DataService {

    @Override
    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Duan Nguyen", "duan.nguyen@gmail.com"));
        students.add(new Student(2, "Xuan Hang", "hang.xuan@gmail.com"));
        return students;
    }
}
