package ru.votingsystems.restraurantvotingsystem.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.votingsystems.restraurantvotingsystem.util.ValidationUtil;
import ru.votingsystems.restraurantvotingsystem.util.exception.FieldNotUniqueException;
import ru.votingsystems.restraurantvotingsystem.util.exception.IllegalRequestDataException;
import ru.votingsystems.restraurantvotingsystem.util.exception.NotFoundException;
import ru.votingsystems.restraurantvotingsystem.util.exception.VotingTimeoutNotExpiredException;

import javax.servlet.http.HttpServletRequest;


@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class CustomExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);

    //  http://stackoverflow.com/a/22358422/548473
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    public void handleError(HttpServletRequest req, NotFoundException e) {
       logExceptionInfo(req, e, false);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler({DataIntegrityViolationException.class, FieldNotUniqueException.class, VotingTimeoutNotExpiredException.class})
    public void conflict(HttpServletRequest req, Exception e) {
        logExceptionInfo(req, e, true);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public void illegalRequestDataError(HttpServletRequest req, Exception e) {
        logExceptionInfo(req, e, false);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void handleError(HttpServletRequest req, Exception e) {
        logExceptionInfo(req, e, true);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public void bindValidationError(HttpServletRequest req, Exception e) {
         logExceptionInfo(req, e, false);
    }

//    @ExceptionHandler(Exception.class)
//    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
//        log.error("Exception at request " + req.getRequestURL(), e);
//        Throwable rootCause = ValidationUtil.getRootCause(e);
//
//        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//        ModelAndView mav = new ModelAndView("exception",
//                Map.of("exception", rootCause, "message", rootCause.toString(), "status", httpStatus));
//        mav.setStatus(httpStatus);
//
//        // Interceptor is not invoked, put userTo
//        AuthorizedUser authorizedUser = SecurityUtil.safeGet();
//        if (authorizedUser != null) {
//            mav.addObject("userTo", authorizedUser.getUserTo());
//        }
//        return mav;
//    }


    //    https://stackoverflow.com/questions/538870/should-private-helper-methods-be-static-if-they-can-be-static
    private static void logExceptionInfo(HttpServletRequest req, Exception e, boolean logException) {
        String msg = String.format("exception at request  %s: %s",
                req.getRequestURL(), ValidationUtil.getRootCause(e));

        if (logException) {
            log.error(msg);
        } else {
            log.warn(msg);
        }
    }


}