package vdx.store;

import vdx.manager.Config;
import vdx.store.exception.ConfigStoreException;

import java.util.List;
import java.util.Map;

public interface IConfigStore {

    void connectToStore(Config config) throws ConfigStoreException;

    Map<String, Map<String, String>> retrieveFile(List<String> application, String env) throws ConfigStoreException;

    String retrieveValue(String app, String key, String env) throws ConfigStoreException;
}
