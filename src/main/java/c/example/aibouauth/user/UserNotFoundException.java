package c.example.aibouauth.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(Integer id){
        super("There is no user with the id :"+id);
    }
}
