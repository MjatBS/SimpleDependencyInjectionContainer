package elinext.dependency_container;

public class BindingNotFoundException extends RuntimeException{
    BindingNotFoundException(Class cl){
        super("Binding not found for " + cl);
    }
}
