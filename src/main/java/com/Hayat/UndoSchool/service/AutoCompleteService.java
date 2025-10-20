package com.Hayat.UndoSchool.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.Hayat.UndoSchool.document.CourseDocument;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutoCompleteService {

    private final ElasticsearchClient client;
    private static final String INDEX = "courses";

    public AutoCompleteService(ElasticsearchClient client){
        this.client = client;
    }

    public List<String> getSuggestions(String prefix) throws IOException{
        SearchResponse<CourseDocument> response = client.search(s -> s
                .index(INDEX)
                .suggest(sg -> sg
                        .suggesters("course-suggest", sugg -> sugg
                                .prefix(prefix)
                                .completion(c -> c
                                        .field("suggest")
                                        .skipDuplicates(true)
                                        .size(10)
                                )
                        )
                ),
                CourseDocument.class);

        return response.suggest().get("course-suggest").stream()
                .flatMap(s -> s.completion().options().stream())
                .map(opt -> opt.text())
                .distinct()
                .collect(Collectors.toList());
    }
}
