package com.example.todosimple.services;



import com.example.todosimple.models.User;
import com.example.todosimple.models.enums.ProfileEnum;
import com.example.todosimple.repositories.TaskRepository;
import com.example.todosimple.repositories.UserRepository;
import com.example.todosimple.services.exceptions.DataBindingViolationException;
import com.example.todosimple.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public User findByid(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("User not found! Id: " + id + ", Tipo: " + User.class.getName()));


    }
    @Transactional
    public User create(User obj) {
        obj.setId(null);
        obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        obj.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        obj = this.userRepository.save(obj);
        this.taskRepository.saveAll(obj.getTasks());
        return obj;
    }
    @Transactional
    public User update(User obj){
        User newObj = findByid(obj.getId());
        newObj.setPassword(obj.getPassword());
        newObj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        return this.userRepository.save(newObj);
    }

    public void delete(Long id){
        findByid(id);
        try{
            this.userRepository.deleteById(id);
        }
        catch (Exception e){
            throw new DataBindingViolationException("Impossivel excluir, existem entidades relacionadas");
        }

    }
}
