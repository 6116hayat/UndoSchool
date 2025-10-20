package com.Hayat.UndoSchool.controller;

import com.Hayat.UndoSchool.service.AutoCompleteService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/search/suggest")
@CrossOrigin
public class AutoCompleteController {

    private final AutoCompleteService autoCompleteService;

    public AutoCompleteController(AutoCompleteService autoCompleteService){
        this.autoCompleteService = autoCompleteService;
    }

    @GetMapping
    public List<String> suggest(@RequestParam String q) throws IOException{
        return autoCompleteService.getSuggestions(q);
    }
}
