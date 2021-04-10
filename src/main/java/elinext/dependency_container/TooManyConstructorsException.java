package elinext.dependency_container;

public class TooManyConstructorsException extends RuntimeException{
    TooManyConstructorsException(Class cl){
        super("Too many constructors with @Inject for " + cl);
    }
}
