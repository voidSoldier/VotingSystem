package ru.votingsystems.restraurantvotingsystem.util.exception;

public class VotingTimeoutNotExpiredException extends RuntimeException {
    public VotingTimeoutNotExpiredException(String msg) { super(msg); }
}
