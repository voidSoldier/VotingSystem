package ru.votingsystems.restraurantvotingsystem.service;

import org.springframework.stereotype.Service;
import ru.votingsystems.restraurantvotingsystem.model.User;

import java.util.List;

@Service
public class UserService {


    public List<User> getAll() {return null;}

    public User get(int id){return null;}

    public User getByEmail(String email){return null;}

    public void delete(int id){}

    public void update(int id, User user){}

    public void create(User user){}

}
