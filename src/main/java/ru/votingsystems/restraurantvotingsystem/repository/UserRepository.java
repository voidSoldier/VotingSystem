package ru.votingsystems.restraurantvotingsystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.votingsystems.restraurantvotingsystem.model.User;

import java.util.List;

@Repository
public class UserRepository {


    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");


    private final CrudUserRepository repository;


    public UserRepository(CrudUserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAll() {
        return repository.findAll(SORT_NAME_EMAIL);
    }

    public User get(int id) {
        return repository.findById(id).orElse(null);
    }

    public User getByEmail(String email) {
        return repository.getByEmail(email);
    }

    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }

    public void update(int id, User user) {repository.update(id, user);}

    public User create(User user) {
        return repository.save(user);
    }
}
