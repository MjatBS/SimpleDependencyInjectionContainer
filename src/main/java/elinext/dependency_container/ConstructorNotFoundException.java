package elinext.dependency_container;

public class ConstructorNotFoundException extends RuntimeException{
    ConstructorNotFoundException(Class cl){
        super("Default constructor not found for " + cl);
    }
}
