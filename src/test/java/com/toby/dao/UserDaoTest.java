package com.toby.dao;

import com.toby.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.*;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {
    @Autowired
    ApplicationContext context;
    UserDao userDao;
    @BeforeEach
    void setUp(){
        userDao = context.getBean("awsUserDao",UserDao.class);
        userDao.deleteAll();
    }


    @Test
    void addAndGet(){
        //Given
        User user = new User("3","김희정","1234");
        //When
        userDao.add(user);
        User selectedUser = userDao.findById(user.getId());
        //Then
        assertThat(selectedUser.getName()).isEqualTo("김희정");
    }

    @Test
    void deleteAll(){
        //Given
        User user = new User("0","김희정","1234");
        userDao.add(user);
        //When
        userDao.deleteAll();
        //Then
        assertThat(userDao.getCount()).isEqualTo(0);
    }

    @Test
    void getCount(){
        //Given
        User user1 = new User("1","김희정","1234");
        User user2 = new User("2","희정김","4321");
        User user3 = new User("3","정희김","asdf");

        //When
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);
        //Then
        assertThat(userDao.getCount()).isEqualTo(3);
    }

    @Test
    void findByIdException(){
        //Given
        userDao.deleteAll();
        //When
        assertThat(userDao.getCount()).isEqualTo(0);
        //Then
        Assertions.assertThrows(EmptyResultDataAccessException.class,()->{
            userDao.findById("100");
        });
    }

    @Test
    void findAll(){
        //Given
        User user1 = new User("1","김희정","1234");
        User user2 = new User("2","희정김","4321");
        User user3 = new User("3","정희김","asdf");

        //When
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);
        List<User> userList = userDao.findAll();

        //Then
        assertThat(userList.size()).isEqualTo(3);
    }

}