package ru.votingsystems.restraurantvotingsystem.exception;

public class VotingTimeoutNotExpiredException extends RuntimeException {
    public VotingTimeoutNotExpiredException(String msg) { super(msg); }
}
