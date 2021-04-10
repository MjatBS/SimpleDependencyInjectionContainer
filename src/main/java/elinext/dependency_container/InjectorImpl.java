package elinext.dependency_container;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.*;

public class InjectorImpl implements  Injector{
    Map<Class, Provider> beans;

    InjectorImpl(){
        beans = Collections.synchronizedMap(new HashMap<>());
    }

    @Override
    public <T> Provider<T> getProvider(Class<T> type) {
        return beans.getOrDefault(type, null);
    }

    @Override
    // prototype
    public <T> void bind(Class<T> intf, Class<? extends T> impl) {
        Provider<T> implProvider = new Provider<>() {
            @Override
            public T getInstance() {
                return injectDependencies(impl);
            }
        };
        beans.put(intf,implProvider);
    }
    @Override
    // singleton
    public <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) {
        Provider<T> implProvider = new Provider<>() {
            T instance = null;
            @Override
            public T getInstance() {
                if(instance == null){
                    instance = injectDependencies(impl);
                }
                return instance;
            }
        };
        beans.put(intf, implProvider);
    }

    private <T> T injectDependencies(Class<T> impl) {
        T result = null;
        try {
            boolean wasInject = false;
            for(Constructor<?> constructor : impl.getConstructors()){
                if(constructor.isAnnotationPresent(Inject.class)){
                    if(wasInject) throw new TooManyConstructorsException(impl);
                    Object[] objectParams = getConstructorParams(constructor);
                    result = (T) constructor.newInstance(objectParams);
                    wasInject = true;
                }
            }
            if(!wasInject){
                try{
                    result = impl.getConstructor().newInstance();
                } catch (NoSuchMethodException e) {
                    throw new ConstructorNotFoundException(impl);
                }
            }
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Object[] getConstructorParams(Constructor<?> constructor) {
        Class<?>[] paramsTypes = constructor.getParameterTypes();
        Object[] params = new Object[paramsTypes.length];
        for(int i = 0; i<paramsTypes.length; i++){
            Provider<?> paramProvider = getProvider(paramsTypes[i]);
            if(paramProvider != null){
                params[i] = paramProvider.getInstance();
            }else{
                throw new BindingNotFoundException(paramsTypes[i]);
            }
        }
        return params;
    }
}
