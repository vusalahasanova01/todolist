package com.todolist.todolist.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * TODO
 * I suggest that we use ModelMapper.
 * It is simple to use and does not lag behind in optimality
 */
@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

}
