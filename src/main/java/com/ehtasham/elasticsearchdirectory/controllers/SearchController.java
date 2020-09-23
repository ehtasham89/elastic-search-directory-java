package com.ehtasham.elasticsearchdirectory.controllers;


//import javax.validation.Valid;

//import com.ehtasham.todoapp.models.Todo;
//import com.ehtasham.todoapp.repositories.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import java.util.List;

@RestController
@RequestMapping("/api/todo/")
@CrossOrigin("*")
public class SearchController {

    @Autowired
    //TodoRepository todoRepository;

    @GetMapping("/")
    public String listAll() {
        
        return "Spring is running well";
    }
   
}
