package elinext.dependency_container.classes_for_testing;

import elinext.dependency_container.Inject;

public class TooManyConstructions implements EventService{
    private EventDao eventDao;

    @Inject
    public TooManyConstructions(EventDao eventDao){
        this.eventDao = eventDao;
    }

    @Inject
    public TooManyConstructions(UserDao userDao){

    }

    @Override
    public EventDao getEventDao() {
        return eventDao;
    }
}
