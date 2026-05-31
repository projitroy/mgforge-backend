package com.mgforge.MGForge.config;

import graphql.language.StringValue;
import graphql.scalars.ExtendedScalars;
import graphql.schema.Coercing;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
public class GraphqlConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .scalar(ExtendedScalars.DateTime)
                .scalar(ExtendedScalars.Json)
                .scalar(urlScalar());
    }

    @Bean
    public GraphQLScalarType urlScalar() {
        return GraphQLScalarType.newScalar()
                .name("URL")
                .description("A valid URL")
                .coercing(new Coercing<URL, String>() {

                    @Override
                    public String serialize(Object dataFetcherResult){
                        if (dataFetcherResult instanceof URL url) {
                            return url.toString();
                        }
                        throw new CoercingSerializeException("Expected a java.net.URL object.");
                    }

                    @Override
                    public URL parseValue(Object input){
                        if (input instanceof String s){
                            try{
                                return new URL(s);
                            } catch (MalformedURLException e){
                                throw new CoercingSerializeException("Invalid URL value:"+s);
                            }
                        }
                        throw new CoercingSerializeException("Expected a String for URL.");
                    }

                    @Override
                    public URL parseLiteral(Object input){
                        if(input instanceof StringValue stringValue){
                            try {
                                return new URL(stringValue.getValue());
                            } catch (MalformedURLException e){
                                throw new CoercingSerializeException("Invalid URL literal: "+ stringValue.getValue());
                            }
                        }
                        throw new CoercingSerializeException("Expected a String literal for URL.");
                    }

                })
                .build(); // Maps to java.net.URL
    }
}
