package elinext.dependency_container.classes_for_testing;

public class DefaultConstructorNotFound implements  EventService{
    private EventDao eventDao;

    public DefaultConstructorNotFound(Integer a){}

    @Override
    public EventDao getEventDao() {
        return eventDao;
    }
}
