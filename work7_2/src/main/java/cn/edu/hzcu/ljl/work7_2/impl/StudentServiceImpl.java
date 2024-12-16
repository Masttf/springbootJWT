package cn.edu.hzcu.ljl.work7_2.impl;

import cn.edu.hzcu.ljl.work7_2.entity.Student;
import cn.edu.hzcu.ljl.work7_2.repository.StudentRepository;
import cn.edu.hzcu.ljl.work7_2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private  RedisTemplate<String, Object> redisTemplate;
    private static final String GPA_RANK_KEY = "student:gpa_rank";

    @Override
    public List<Student> getStudentList() {
        return studentRepository.findAll();
    }

    @Override
    @Cacheable(value = "students", key = "#id")
    public Student findStudentById(long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student findStudentByName(String name) {
        return studentRepository.findByName(name);
    }
    @Override
    public Student add(Student student){
        studentRepository.save(student);
        return studentRepository.findById(student.getId());
    }
    @Override
    @Transactional
    @Cacheable(value = "students", key = "#student.id")
    public Student update(Student student){
        studentRepository.save(student);
        return studentRepository.findById(student.getId());
    }
    @Override
    @CacheEvict(value = "students", key = "#id")
    public void deleteById(long id){
        studentRepository.deleteById(id);
    }
    @Override
    @Transactional
    @CacheEvict(value = "students", key = "#id")
    public Student updateStudentGPA(long id, double gpa) {
        Student student = findStudentById(id);
        student.setGpa(gpa);
        studentRepository.save(student);
        // 保存完整的学生信息到Redis
        redisTemplate.delete("student:" + id);
        redisTemplate.opsForZSet().remove("gpa_rank", id);
        redisTemplate.opsForZSet().add("gpa_rank", id, gpa);

        return student;
    }

    public List<Student> getTopStudents(int topN) {
        Set<ZSetOperations.TypedTuple<Object>> ids = redisTemplate.opsForZSet().reverseRangeWithScores(GPA_RANK_KEY, 0, topN - 1);
        if (ids == null || ids.size() != topN) {
            List<Student> s = studentRepository.sortByGpa();
            redisTemplate.delete("gpa_rank");
            for (Student student : s) {
                redisTemplate.opsForZSet().add("gpa_rank", student.getId(), student.getGpa());
            }
        }
        ids = redisTemplate.opsForZSet().reverseRangeWithScores("gpa_rank", 0, topN - 1);
        return ids.stream().map(id -> studentRepository.findById((Integer) Objects.requireNonNull(id.getValue()))).collect(Collectors.toList());
    }
}
