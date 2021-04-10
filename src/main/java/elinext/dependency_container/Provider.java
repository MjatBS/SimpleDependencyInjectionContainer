package elinext.dependency_container;

public interface Provider<T> {
    T getInstance();
}
