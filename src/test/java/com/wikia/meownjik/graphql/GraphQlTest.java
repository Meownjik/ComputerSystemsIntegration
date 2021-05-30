package com.wikia.meownjik.graphql;

import com.wikia.meownjik.jdbc.EcoNewsDao;
import com.wikia.meownjik.jdbc.EcoNewsEntity;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLError;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

public class GraphQlTest {

    @Test
    public void graphQlTest() {
        DataFetcher<List<String>> titlesDataFetcher = environment -> {
            List<String> titles = new ArrayList<>();
            new EcoNewsDao().selectAll().forEach(n -> titles.add(n.getTitle()));
            return titles;
        };

        DataFetcher<List<String>> textsDataFetcher = environment -> {
            List<String> titles = new ArrayList<>();
            new EcoNewsDao().selectAll().forEach(n -> titles.add(n.getText()));
            return titles;
        };

        GraphQLObjectType newsType = newObject()
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


        GraphQLSchema schema = GraphQLSchema.newSchema()
                .query(newsType)
                .build();

        GraphQL graphQL = GraphQL.newGraphQL(schema)
                .build();

        ExecutionInput executionInput = ExecutionInput.newExecutionInput().query("query { title, text }")
                .build();

        ExecutionResult executionResult = graphQL.execute(executionInput);

        Object data = executionResult.getData();
        List<GraphQLError> errors = executionResult.getErrors();
        System.out.println(data);
        System.out.println(errors);
    }
}
