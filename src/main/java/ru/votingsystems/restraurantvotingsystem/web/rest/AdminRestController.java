package ru.votingsystems.restraurantvotingsystem.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.service.UserService;

@RestController
@RequestMapping(value = AdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/rest/admin/users";

    @Autowired
    private UserService userService;

    /*
     * FOR USERS
     */
//    @GetMapping
//    public List<User> getAll() {
//        log.info("getAll");
//
//        return userService.getAll();
//    }

//    @GetMapping("/{id}")
//    public User get(@PathVariable int id) {
//        log.info("get {}", id);
//
//        return userService.get(id);
//    }


    @GetMapping("/by")
    public User getByEmail(@RequestParam String email) {
        log.info("getByEmail {}", email);

        return userService.getByEmail(email);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);

        userService.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user, @PathVariable int id) {
        log.info("update {} with id={}", user, id);

        userService.update(id, user);
    }


//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<User> create(@RequestBody User user) {
//        log.info("create {}", user);
//
//        User created = userService.create(user);
//        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path(REST_URL + "/{id}")
//                .buildAndExpand(created.getId()).toUri();
//        return ResponseEntity.created(uriOfNewResource).body(created);
//    }

//    @PatchMapping("/{id}")
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public void enable(@PathVariable int id, @RequestParam boolean enabled) {
//        userService.enable(id, enabled);
//    }


}