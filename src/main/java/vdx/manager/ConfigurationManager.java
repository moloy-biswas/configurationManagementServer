package vdx.manager;

import vdx.store.ConfigStoreFactory;
import vdx.store.ConfigStoreType;
import vdx.store.IConfigStore;
import vdx.store.exception.ConfigStoreException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Main Class to delegate config load call from various store and handling polling
 */
public class ConfigurationManager {
    // Initial delay to start polling
    private static final int INITIAL_DELAY = 5;
    // Delay interval for polling
    private static final int POLL_DELAY = 1;
    private Map<String, Map<String, String>> configValues = null;
    private List<String> store = null;
    private String env = null;
    private List<String> application = null;
    private String mode = "";
    private ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);


    public ConfigurationManager(List<String> store, List<String> application, String env, String mode){
        this.store = store;
        this.env = env;
        this.application = application;
        this.mode = mode;
    }

    public void setScheduler(ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;
    }

    public void loadConfig(){
        if(Boolean.valueOf(mode)){
           scheduler.scheduleWithFixedDelay(() ->
            {
                store.stream().forEach( store -> {
                        if(ConfigStoreType.LOCAL.getName().equals(store)){
                            loadLocalConfig(store, application, env);
                        }else if(ConfigStoreType.GIT.getName().equals(store)){
                            //
                        }
                    });
            }, INITIAL_DELAY, POLL_DELAY, TimeUnit.SECONDS);

        }
    }

    public Map<String, Map<String, String>> loadLocalConfig(String loader, List<String> application, String env){
        IConfigStore configStore = ConfigStoreFactory.getInstance().getConfigStore(ConfigStoreType.fromName(loader));
        try {
            configStore.connectToStore(null);
            configValues = configStore.retrieveFile(application, env);
            System.out.println("Config Values loaded: "+configValues);
        } catch (ConfigStoreException e) {
            System.out.println("Exception while loading config for Local store"+e.getMessage());
        }
        return configValues;
    }

    public String retrieveConfigValue(String loader, String application, String key){
        String retrieveValue = null;
        IConfigStore configStore = ConfigStoreFactory.getInstance().getConfigStore(ConfigStoreType.fromName(loader));
        try {
            configStore.connectToStore(null);
            retrieveValue = configStore.retrieveValue(application, key, env);
        } catch (ConfigStoreException e) {
            System.out.println("Exception while retrieving config for application "
                    +application+" with key "+key+e.getMessage());
        }
        System.out.println("Configuration value for key "+key+" is "+retrieveValue);
        return retrieveValue;
    }
}
