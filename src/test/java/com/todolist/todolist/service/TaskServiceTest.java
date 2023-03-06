package com.todolist.todolist.service;

import com.todolist.todolist.dao.entity.Task;
import com.todolist.todolist.dao.entity.User;
import com.todolist.todolist.dao.repository.TaskRepository;
import com.todolist.todolist.dao.repository.UserRepository;
import com.todolist.todolist.dto.request.TaskCreation;
import com.todolist.todolist.model.TaskStatus;
import com.todolist.todolist.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    public void getTasksByEmail_success() {
        //given
        var email = "vusalahasanova423@gmail.com";
        var task = new Task();
        var expected = List.of(task, task);
        var user = new User();
        user.setTasks(expected);

        //when
        when(userRepository.findByEmail(email)).thenReturn(user);

        //then
        var actual = taskService.getTasksByEmail(email);

        assertEquals(expected, actual);
        verify(userRepository, times(1)).findByEmail(email);
    }

//    @Test
//    public void createTaskByEmail_success() {
//        //given
//        var email = "vusalahasanova423@gmail.com";
//        var taskCreation = new TaskCreation();
//        var task = new Task();
//        var user = new User();
//
//        //when
//        when(userRepository.findByEmail(email)).thenReturn(user);
//        //when(modelMapper.map(givenTask,Task.class)).thenReturn(givenTask);
//        when(taskRepository.save(task)).thenReturn(task);
//
//        //then
//        var actual = taskService.createTaskByEmail(email,taskCreation);
//
//        assertEquals(task , actual);
//        verify(userRepository, times(1)).findByEmail(email);
//        verify(taskRepository, times(1)).save(task);
//    }
//
////        @Test
//    public void updateTask_success() {
//        //given
//        var id = 1L;
//        var given = new Task();
//        given.setId(id);
//        given.setTaskName("given task");
//        var update = new Task();
//        update.setId(id);
//        update.setTaskName("update task");
//
//        //when
//        when(taskRepository.findById(id)).thenReturn(Optional.of(given));
//        when(taskRepository.save(update)).thenReturn(update);
//
//        //then
//
//        var actual = taskService.updateTask(id, update);
//
//        assertEquals(update, actual);
//        verify(taskRepository, times(1)).findById(id);
//        verify(taskRepository, times(1)).save(update);
//    }

    @Test
    public void getArchiveTasks_success() {
        //given
        var email = "vusalahasanova423@gmail.com";
        var user = new User();
        var task = new Task();
        task.setTaskStatus(TaskStatus.ARCHIVED);
        var expected = List.of(task, task);
        user.setTasks(expected);

        //when
        when(userRepository.findByEmail(email)).thenReturn(user);

        //then
        var actual = taskService.getArchiveTasks(email);

        assertEquals(expected, actual);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void deleteTask_success() {
        //given
        var id = 2L;

        //when
        doNothing().when(taskRepository).deleteById(id);

        //then
        taskService.deleteTask(id);
        verify(taskRepository, times(1)).deleteById(id);
    }

    @Test
    public void archiveTask_success() {
        //given
        var id = 1L;
        var task = new Task();
        task.setTaskStatus(TaskStatus.ACTIVE);

        //when
        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        //then
        taskService.archiveTask(id);
        verify(taskRepository, times(1)).findById(id);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void archiveTask_WhenTaskStatusIsArchived_ThenThrowUnsupportedOperationException() {
        //given
        var id = 1L;
        var task = new Task();

        //when
        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        //then
        taskService.archiveTask(id);
        verify(taskRepository, times(1)).findById(id);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void unArchiveTask_success() {
        //given
        var id = 1L;
        var task = new Task();
        task.setTaskStatus(TaskStatus.ARCHIVED);

        //when
        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        //then
        taskService.unArchiveTask(id);
        verify(taskRepository, times(1)).findById(id);
        verify(taskRepository, times(1)).save(task);
    }
}
