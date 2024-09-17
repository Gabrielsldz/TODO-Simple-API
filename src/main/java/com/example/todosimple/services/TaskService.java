package com.example.todosimple.services;

import com.example.todosimple.models.Task;
import com.example.todosimple.models.User;
import com.example.todosimple.models.enums.ProfileEnum;
import com.example.todosimple.models.projection.TaskProjection;
import com.example.todosimple.repositories.TaskRepository;
import com.example.todosimple.security.UserSpringSecurity;
import com.example.todosimple.services.exceptions.AuthorizationException;
import com.example.todosimple.services.exceptions.DataBindingViolationException;
import com.example.todosimple.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findByid(Long id) {
        Task task = this.taskRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Task not found! Id: " + id + ", Tipo: " + Task.class.getName()));
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity) || !userSpringSecurity.hasRole(ProfileEnum.ADMIN) && !userHasTask(userSpringSecurity, task)) {
            throw new AuthorizationException("Acesso negado!");
        }
        return task;
    }

    public List<TaskProjection> findAllByUser() {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity)) {
            throw new AuthorizationException("Acesso negado!");
        }
        return this.taskRepository.findByUser_id(userSpringSecurity.getId());
    }

    @Transactional
    public Task create(Task obj) {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity)) {
            throw new AuthorizationException("Acesso negado!");
        }
        User user = this.userService.findById(userSpringSecurity.getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task update(Task obj) {
        Task newObj = findByid(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);

    }

    public void delete(Long id) {
        findByid(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Impossivel excluir, existem entidades relacionadas");
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<TaskProjection> ReturnAllTasks() {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity) || !userSpringSecurity.hasRole(ProfileEnum.ADMIN)) {
            throw new AuthorizationException("Acesso negado!");
        }
        return this.taskRepository.findAllTaskProjections();
    }

    private Boolean userHasTask(UserSpringSecurity userSpringSecurity, Task task) {
        return task.getUser().getId().equals(userSpringSecurity.getId());
    }




}
