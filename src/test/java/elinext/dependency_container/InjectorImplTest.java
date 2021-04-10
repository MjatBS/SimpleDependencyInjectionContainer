package elinext.dependency_container;

import junit.framework.TestCase;
import org.junit.Test;
import elinext.dependency_container.classes_for_testing.*;

import static org.junit.Assert.*;

public class InjectorImplTest extends TestCase {

    @Test
    public void testCreatingSingleton(){
        Injector injector = new InjectorImpl();
        injector.bindSingleton(EventDao.class,EventDaoImpl.class);
        Provider<EventDao> provider1 = injector.getProvider(EventDao.class);
        Provider<EventDao> provider2 = injector.getProvider(EventDao.class);
        assertSame(provider1.getInstance(), provider2.getInstance());
    }

    @Test
    public void testCreatingPrototype(){
        Injector injector = new InjectorImpl();
        injector.bind(EventDao.class,EventDaoImpl.class);
        Provider<EventDao> provider1 = injector.getProvider(EventDao.class);
        Provider<EventDao> provider2 = injector.getProvider(EventDao.class);
        assertNotSame(provider1.getInstance(), provider2.getInstance());
    }

    @Test
    public void testExistingBinding(){
        Injector injector = new InjectorImpl();
        injector.bind(EventDao.class,EventDaoImpl.class);
        Provider<EventDao> daoProvider = injector.getProvider(EventDao.class);
        assertNotNull(daoProvider);
        assertNotNull(daoProvider.getInstance());
        assertSame(daoProvider.getInstance().getClass(), EventDaoImpl.class);
    }

    @Test
    public void testExistingBindingByInject(){
        Injector injector = new InjectorImpl();
        injector.bind(EventDao.class, EventDaoImpl.class);
        injector.bindSingleton(EventService.class, EventServiceImpl.class);
        EventService eventService = injector.getProvider(EventService.class).getInstance();
        assertNotNull(eventService.getEventDao());
    }

    @Test
    public void testThrowsTooManyConstructorsException(){
        Injector injector = new InjectorImpl();
        boolean wasException = false;
        try{
            injector.bind(EventDao.class, EventDaoImpl.class);
            injector.bindSingleton(EventService.class, TooManyConstructions.class);
            Provider<EventService> providerService = injector.getProvider(EventService.class);
            providerService.getInstance();
        }catch(TooManyConstructorsException e){
            wasException = true;
        }
        assertTrue(wasException);
    }

    @Test
    public void testThrowsConstructorNotFoundException(){
        Injector injector = new InjectorImpl();
        boolean wasException = false;
        try{
            injector.bindSingleton(EventService.class, DefaultConstructorNotFound.class);
            Provider<EventService> providerService = injector.getProvider(EventService.class);
            providerService.getInstance();
        }catch(ConstructorNotFoundException e){
            wasException = true;
        }
        assertTrue(wasException);
    }

    @Test
    public void testThrowsBindingNotFoundException(){
        Injector injector = new InjectorImpl();
        boolean wasException = false;
        try {
            injector.bindSingleton(EventService.class, EventServiceImpl.class);
            Provider<EventService> providerService = injector.getProvider(EventService.class);
            providerService.getInstance();
        }catch(BindingNotFoundException e){
            wasException = true;
        }
        System.out.println(wasException);
        assertTrue(wasException);
    }

    @Test
    public void testNotBinding(){
        Injector injector = new InjectorImpl();
        Provider<EventDao> providerDao = injector.getProvider(EventDao.class);
        assertNull(providerDao);
    }
}