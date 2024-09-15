package com.example.todosimple.repositories;

import com.example.todosimple.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser_id(Long userId);


    //@Query(value = "SELECT t from Task t where t.user.id = :userId")
    //List<Task> findByUser_id(@Param("userId") Long userId);

    //@Query(nativeQuery=true, value = "SELECT * from task t where t.user_id = :id")
   // List<Task> findByUser_id(@Param("id") Long userId);

}
