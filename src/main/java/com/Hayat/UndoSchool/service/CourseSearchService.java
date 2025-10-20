package com.Hayat.UndoSchool.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.ElasticsearchClient;

import co.elastic.clients.json.JsonData;
import com.Hayat.UndoSchool.document.CourseDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseSearchService {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    private static final String INDEX = "courses";

    public List<CourseDocument> searchCourses(
            String keyword, String category, String type,
            Integer minAge, Integer maxAge,
            Double minPrice, Double maxPrice,
            String startDate, String sort, int page, int size
    ) throws IOException {

        List<Query> mustQueries = new ArrayList<>();
        List<Query> filterQueries = new ArrayList<>();

        // Full-text search
        if (keyword != null && !keyword.isEmpty()) {
            mustQueries.add(MultiMatchQuery.of(m -> m
                    .fields("title", "description","category","type")
                    .query(keyword)
                    .fuzziness("AUTO")
            )._toQuery());
        }

        // Category filter
        if (category != null && !category.isEmpty()) {
            filterQueries.add(Query.of(q -> q.term(t -> t
                    .field("category")
                    .value(FieldValue.of(category))
            )));
        }

        // Type filter
        if (type != null && !type.isEmpty()) {
            filterQueries.add(Query.of(q -> q.term(t -> t
                    .field("type")
                    .value(FieldValue.of(type))
            )));
        }

        // Age filterQueries
        if (minAge != null) {
            filterQueries.add(Query.of(q -> q.range(r -> r
                    .untyped(u -> u
                            .field("minAge")
                            .gte(JsonData.of(minAge))
                    )
            )));
        }
        if (maxAge != null) {
            filterQueries.add(Query.of(q -> q.range(r -> r
                    .untyped(u -> u
                            .field("maxAge")
                            .lte(JsonData.of(maxAge))
                    )
            )));
        }

        // Price filterQueries
        if (minPrice != null) {
            filterQueries.add(Query.of(q -> q.range(r -> r
                    .untyped(u -> u
                            .field("price")
                            .gte(JsonData.of(minPrice))
                    )
            )));
        }
        if (maxPrice != null) {
            filterQueries.add(Query.of(q -> q.range(r -> r
                    .untyped(u -> u
                            .field("price")
                            .lte(JsonData.of(maxPrice))
                    )
            )));
        }

        //  Date filter
        if (startDate != null && !startDate.isEmpty()) {
            filterQueries.add(Query.of(q -> q.range(r -> r
                    .untyped(u -> u
                            .field("nextSessionDate")
                            .gte(JsonData.of(startDate))
                    )
            )));
        }

        //  Build final query
        Query finalQuery = Query.of(q -> q.bool(b -> b
                .must(mustQueries)
                .filter(filterQueries)));

        // Sorting logic
        String sortField;
        SortOrder sortOrder;

        if ("priceAsc".equalsIgnoreCase(sort)) {
            sortField = "price";
            sortOrder = SortOrder.Asc;
        } else if ("priceDesc".equalsIgnoreCase(sort)) {
            sortField = "price";
            sortOrder = SortOrder.Desc;
        } else {
            sortField = "nextSessionDate";
            sortOrder = SortOrder.Asc;
        }

        // Execute search
        var response = elasticsearchClient.search(s -> s
                        .index(INDEX)
                        .query(finalQuery)
                        .sort(so -> so.field(f -> f.field(sortField).order(sortOrder)))
                        .from(page * size)
                        .size(size),
                CourseDocument.class
        );

        return response.hits().hits().stream()
                .map(hit -> hit.source())
                .toList();
    }
}

