package vdx.store;


import vdx.store.impl.GitHubStore;
import vdx.store.impl.LocalStore;

/**
 * Factory class to return different store object dynamically
 * To enhance and add store, need to instantiate corresponding store object here
 */
public class ConfigStoreFactory {

    private static ConfigStoreFactory factory = new ConfigStoreFactory();

    public static ConfigStoreFactory getInstance(){
        return factory;
    }

    public IConfigStore getConfigStore(ConfigStoreType type){
        IConfigStore store = null;
        switch (type){
            case LOCAL:
                store = new LocalStore();
                break;
            case GCP:
                break;
            case GIT:
                store = new GitHubStore();
                break;
            case AWS:
                break;
            default:
                break;
        }
        return store;
    }
}
