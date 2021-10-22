package vdx.store;

/**
 * Enums for all store type
 */
public enum ConfigStoreType {

    LOCAL("local"),
    GIT("git"),
    AWS("aws"),
    GCP("gcp");

    private String name;
    private ConfigStoreType(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public static ConfigStoreType fromName(String name) {
        for (ConfigStoreType store : ConfigStoreType.values()) {
            if (store.name.equalsIgnoreCase(name)) {
                return store;
            }
        }
        return null;
    }
}
