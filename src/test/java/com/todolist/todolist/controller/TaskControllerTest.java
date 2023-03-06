package com.todolist.todolist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todolist.todolist.dao.entity.Task;
import com.todolist.todolist.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WithMockUser
@WebMvcTest(controllers = TaskController.class)
public class TaskControllerTest {
    private static final String PATH = "/task";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

//    @Test
//    void getTasksByUserId_success() throws Exception {
//        //given
//        var id = 1L;
//        var task = new Task();
//        var response = List.of(task, task);
//
//        //when
//        when(taskService.getTasksByUserId(id)).thenReturn(response);
//
//        //then
//        mockMvc.perform(get(PATH + "/getTasks/userid/{userid}", id))
//                .andExpect(jsonPath("$.length()", is(2)))
//                .andExpect(status().isOk());
//
//    }
}
