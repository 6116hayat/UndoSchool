package com.Hayat.UndoSchool.controller;

import com.Hayat.UndoSchool.document.CourseDocument;
import com.Hayat.UndoSchool.service.CourseSearchService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/search")
@CrossOrigin
public class CourseSearchController {

    private final CourseSearchService courseSearchService;

    public CourseSearchController(CourseSearchService courseSearchService){
        this.courseSearchService=courseSearchService;
    }

    @GetMapping
    public List<CourseDocument> searchCourses(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String startDate,
            @RequestParam(defaultValue = "upcoming") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) throws IOException {
        return courseSearchService.searchCourses(q, category, type, minAge, maxAge, minPrice, maxPrice, startDate, sort, page, size);
    }
}
