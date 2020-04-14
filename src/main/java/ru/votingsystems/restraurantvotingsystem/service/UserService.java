package ru.votingsystems.restraurantvotingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.repository.DataJpaUserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private DataJpaUserRepository repository;

    public List<User> getAll() {return repository.getAll();}

    public User get(int id){return repository.get(id);}

    public User getByEmail(String email){return repository.getByEmail(email);}

    public void delete(int id){repository.delete(id);}

    public void update(int id, User user){repository.update(id, user);}

    public void create(User user){repository.create(user);}

}
