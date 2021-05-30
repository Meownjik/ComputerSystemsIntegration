package com.wikia.meownjik.graphql;

import com.wikia.meownjik.jdbc.EcoNewsEntity;
import com.wikia.meownjik.redis.Cacher;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLError;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;

import java.util.List;
import java.util.Map;

import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

public class NewsClient {
    private final static String QUERY = "query { title, text }";

    private EcoNewsEntity newsEntity = Cacher.pop();

    private void setNewsEntity() {
        newsEntity = Cacher.pop();
    }

    private final DataFetcher<String> titlesDataFetcher = environment -> newsEntity.getTitle();

    private final DataFetcher<String> textsDataFetcher = environment -> newsEntity.getTitle();

    private final GraphQLObjectType newsType = newObject()
            .name("News")
            .field(newFieldDefinition()
                    .name("title")
                    .type(GraphQLString)
                    .dataFetcher(titlesDataFetcher))
            .field(newFieldDefinition()
                    .name("text")
                    .type(GraphQLString)
                    .dataFetcher(textsDataFetcher))
            .build();

    private final GraphQLSchema schema = GraphQLSchema.newSchema()
            .query(newsType)
            .build();

    private final GraphQL graphQL = GraphQL.newGraphQL(schema)
            .build();

    private final ExecutionInput executionInput = ExecutionInput.newExecutionInput().query(QUERY)
            .build();

    //---------------------------------------
    public EcoNewsEntity next() {
        try {
            ExecutionResult executionResult = graphQL.execute(executionInput);

            Map<String, String> data = executionResult.getData();
            List<GraphQLError> errors = executionResult.getErrors();
            if (errors.size() > 0) {
                System.out.println(errors);
            }
            return new EcoNewsEntity(data.get("title"), data.get("text"));
        }
        finally {
            setNewsEntity();
        }
    }
}
