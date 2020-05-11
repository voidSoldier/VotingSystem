package ru.votingsystems.restraurantvotingsystem.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.repository.UserRepository;
import ru.votingsystems.restraurantvotingsystem.to.UserTo;
import ru.votingsystems.restraurantvotingsystem.util.UserUtil;
import ru.votingsystems.restraurantvotingsystem.util.exception.NotFoundException;
import ru.votingsystems.restraurantvotingsystem.web.AuthorizedUser;

import javax.validation.Valid;
import java.util.List;

import static ru.votingsystems.restraurantvotingsystem.util.UserUtil.prepareToSave;

@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {


    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }
//public UserService(UserRepository repository) {
//    this.repository = repository;
//
//}

    @Cacheable("users")
    public List<User> getAll() {
        return repository.findAll(SORT_NAME_EMAIL);
    }

    public User get(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("User doesn't exist."));
    }

    public User getWithVotes(int id) {
        return repository.getWithVotes(id);
    }

//    @Autowired
//    private VoteRepository votoRepo;
//    public List<Vote> getActivity(int id) {
//        return votoRepo.findVotesByUserId(id);
//    }

    public User getByEmail(String email) {
        return repository.findUserByEmail(email);
    }

//    @CacheEvict(value = "users", allEntries = true)
    public UserTo getActivity(int userId) {
        return new UserTo(getWithVotes(userId));
//        return getWithVotes(userId);
    }

    @CacheEvict(value = "users", allEntries = true)
    public boolean delete(int id) {
        int result = repository.deleteUserById(id);
        if (result != 0) return true;
        else throw new NotFoundException("User doesn't exist.");
    }

//    @CacheEvict(value = "users", allEntries = true)
//    @Transactional
//    public void update(int id, User user) {
//        repository.save(user);
//    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        prepareAndSave(user);
//        repository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void update(UserTo userTo) {
        User user = get(userTo.getId());
        User updated = UserUtil.updateFromTo(user, userTo);
        repository.save(updated);
    }


    @CacheEvict(value = "users", allEntries = true)
    public User create(@Valid User user) {
        return repository.save(user);
    }



    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void enable(int id, boolean enabled) {
        User user = get(id);
        user.setEnabled(enabled);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findUserByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }


    private User prepareAndSave(User user) {
        return repository.save(prepareToSave(user, passwordEncoder));
    }
}
