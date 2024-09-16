package com.example.todosimple.repositories;
import org.springframework.transaction.annotation.Transactional;
import com.example.todosimple.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {


@Transactional(readOnly = true)
User findByUsername(String username);

}
