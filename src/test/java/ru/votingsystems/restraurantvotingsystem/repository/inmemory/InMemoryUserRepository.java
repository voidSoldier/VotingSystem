package ru.votingsystems.restraurantvotingsystem.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.votingsystems.restraurantvotingsystem.UTestData;
import ru.votingsystems.restraurantvotingsystem.model.User;

import java.util.Comparator;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.votingsystems.restraurantvotingsystem.UTestData.USER;
import static ru.votingsystems.restraurantvotingsystem.UTestData.ADMIN;


@Repository
public class InMemoryUserRepository extends InMemoryBaseRepository<User> {

    public void init() {
        map.clear();
        map.put(UTestData.USER_ID, USER);
        map.put(UTestData.ADMIN_ID, ADMIN);
    }

//    @Override
    public List<User> getAll() {
        return getCollection().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

//    @Override
    public User getByEmail(String email) {
        Objects.requireNonNull(email, "email must not be null");
        return getCollection().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
    }
}