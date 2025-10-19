//package com.Hayat.UndoSchool;
//
//import java.io.InputStream;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//    @Override
//    public void run(String... args) throws Exception {
//        InputStream inputStream = getClass().getResourceAsStream("/sample-courses2.json");
//        ObjectMapper mapper = new ObjectMapper();
//        List<Map<String, Object>> courses = mapper.readValue(inputStream, new TypeReference<>() {
//        });
//        System.out.println("Loaded " + courses.size() + " sample courses!");
//    }
//}
