package com.example.todosimple.controllers;

import com.example.todosimple.models.Task;
import com.example.todosimple.services.TaskService;
import com.example.todosimple.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id){
        Task obj = this.taskService.findByid(id);
        return ResponseEntity.ok().body(obj);
    }
    @PostMapping
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody Task obj){
        this.taskService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Task obj, @PathVariable Long id){
        obj.setId(id);
        this.taskService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/user")
    public ResponseEntity<List<Task>> findAllByUser(){
        List<Task> list = this.taskService.findAllByUser();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping({"AllTasks"})
    public ResponseEntity<List<Task>> findAll(){
        List<Task> list = this.taskService.ReturnAllTasks();
        return ResponseEntity.ok().body(list);
    }
}
