package com.Hayat.UndoSchool.service;

import com.Hayat.UndoSchool.document.CourseDocument;
import com.Hayat.UndoSchool.repository.CourseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.elasticsearch.core.suggest.Completion;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseIndexService implements CommandLineRunner {

    private final CourseRepository courseRepository;
    private final ObjectMapper objectMapper;


    public CourseIndexService(CourseRepository courseRepository){
        this.courseRepository=courseRepository;
        this.objectMapper= new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public void run(String... args) throws Exception{
        InputStream inputStream = getClass().getResourceAsStream("/sample-courses2.json");
        List<CourseDocument> courses = objectMapper.readValue(inputStream, new TypeReference<>(){});

        // Suggest field for autocomplete
        courses = courses.stream()
                        .peek(course -> course.setSuggest(new Completion(List.of(course.getTitle()))))
                        .collect(Collectors.toList());

        // Bulk Index
        courseRepository.saveAll(courses);
        System.out.println("✅ Indexed " +  courses.size() + " courses into ElasticSearch!");

    }
}
