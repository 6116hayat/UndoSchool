package com.Hayat.UndoSchool.service;

import com.Hayat.UndoSchool.document.CourseDocument;
import com.Hayat.UndoSchool.repository.CourseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class CourseIndexService implements CommandLineRunner {

    private final CourseRepository courseRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CourseIndexService(CourseRepository courseRepository){
        this.courseRepository=courseRepository;
    }

    @Override
    public void run(String... args) throws Exception{
        InputStream inputStream = getClass().getResourceAsStream("/sample-courses2.json");
        List<CourseDocument> courses = objectMapper.readValue(inputStream, new TypeReference<>(){});
        courseRepository.saveAll(courses);
        System.out.println("✅ Indexed " +  courses.size() + "courses into ElasticSearch!");

    }
}
