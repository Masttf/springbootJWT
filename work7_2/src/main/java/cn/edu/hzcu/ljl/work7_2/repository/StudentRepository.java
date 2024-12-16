package cn.edu.hzcu.ljl.work7_2.repository;

import cn.edu.hzcu.ljl.work7_2.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    Student findById(long id);
    Student findByName(String name);
    @Query(value = "select * from student order by gpa desc", nativeQuery = true)
    List<Student> sortByGpa();
}
