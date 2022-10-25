package com.toby.dao;

import com.toby.domain.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserDaoTest {
    @Test
    void addAndGet(){
        //Given
        User user = new User("1","김희정","1234");
        UserDao userDao = new UserDao();
        //When
        userDao.add(user);
        User selectedUser = userDao.findById(user.getId());
        //Then
        assertThat(selectedUser.getName()).isEqualTo("김희정");

    }

}