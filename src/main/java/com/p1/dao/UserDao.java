package com.p1.dao;

import java.util.List;

import com.p1.models.*;

public interface UserDao {
    User getUserByUsername(String username);

    List<ReimbType> getReimbTypes();


}
