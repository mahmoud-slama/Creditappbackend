package c.example.aibouauth.user;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(long id){
        super("There is no user with the id :"+id);
    }
}
