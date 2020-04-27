package ru.votingsystems.restraurantvotingsystem.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.service.UserService;

import static ru.votingsystems.restraurantvotingsystem.web.SecurityUtil.authUserId;


@RestController
@RequestMapping(ProfileRestController.REST_URL)
public class ProfileRestController {

    @Autowired
    private UserService service;

    static final String REST_URL = "/rest/user/profile";

//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public User get() {
//        return service.get(authUserId());
//    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        service.delete(authUserId());
    }


//    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(value = HttpStatus.CREATED)
//    public ResponseEntity<User> register(@RequestBody User user) {
//        User created = service.create(user);
//        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path(REST_URL).build().toUri();
//        return ResponseEntity.created(uriOfNewResource).body(created);
//    }


    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user) {
        service.update(authUserId(), user);
    }

}