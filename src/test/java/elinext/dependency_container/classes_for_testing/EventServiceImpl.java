package elinext.dependency_container.classes_for_testing;

import elinext.dependency_container.Inject;

public class EventServiceImpl implements EventService{
    private EventDao eventDao;

    @Inject
    public EventServiceImpl(EventDao eventDao){
        this.eventDao = eventDao;
    }

    @Override
    public EventDao getEventDao(){
        return eventDao;
    }
}
