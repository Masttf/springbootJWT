package cn.edu.hzcu.ljl.work7_2.service;

import cn.edu.hzcu.ljl.work7_2.entity.Student;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Set;

public interface StudentService {
    public List<Student> getStudentList();
    public Student findStudentById(long id);
    public Student updateStudentGPA(long id, double gpa);

    Student findStudentByName(String name);

    public Student add(Student student);
    public void deleteById(long id);
    public Student update(Student student);
    public List<Student> getTopStudents(int topN);
}
