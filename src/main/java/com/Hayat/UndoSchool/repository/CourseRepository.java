package com.Hayat.UndoSchool.repository;

import com.Hayat.UndoSchool.document.CourseDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CourseRepository extends ElasticsearchRepository<CourseDocument, String> {

}
