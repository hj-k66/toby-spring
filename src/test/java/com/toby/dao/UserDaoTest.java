package com.toby.dao;

import com.toby.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.*;

import static org.assertj.core.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {
    @Autowired
    ApplicationContext context;

    @Test
    void addAndGet(){
        //Given
        User user = new User("3","김희정","1234");
        UserDao userDao = context.getBean("awsUserDao",UserDao.class);
        //When
        userDao.add(user);
        User selectedUser = userDao.findById(user.getId());
        //Then
        assertThat(selectedUser.getName()).isEqualTo("김희정");

    }

}