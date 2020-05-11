package ru.votingsystems.restraurantvotingsystem.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.model.Vote;
import ru.votingsystems.restraurantvotingsystem.to.UserTo;
import ru.votingsystems.restraurantvotingsystem.util.UserUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.votingsystems.restraurantvotingsystem.web.SecurityUtil.authUserId;


@RestController
@RequestMapping(value = ProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestController extends AbstractUserController {

    static final String REST_URL = "/rest/profile";

//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public User get(@AuthenticationPrincipal AuthorizedUser authUser) {
//        return super.get(authUser.getId());
//    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable int id) {
        return super.get(id);
    }

//    @DeleteMapping
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@AuthenticationPrincipal AuthorizedUser authUser) {
//        super.delete(authUser.getId());
//    }

    @GetMapping(value = "/activity/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserTo getActivity(@PathVariable int id) {
        return service.getActivity(id);
    }


//    @GetMapping(value = "/activity/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<Vote> getActivity(@PathVariable int id) {
//        return service.getActivity(id);
//
//    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }


    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        User created = super.create(userTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

//    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void update(@RequestBody UserTo userTo, @AuthenticationPrincipal AuthorizedUser authUser) throws BindException {
//        checkAndValidateForUpdate(userTo, authUser.getId());
//        service.update(userTo);
//    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTo userTo) throws BindException {
        checkAndValidateForUpdate(userTo, authUserId());
        service.update(userTo);
    }

}