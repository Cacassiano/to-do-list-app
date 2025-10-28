package dev.cassiano.to_do_api.exceptions.customs;

public class NotFoundException extends Exception{
    private final String message;
    public NotFoundException(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return this.message;
    }
}
