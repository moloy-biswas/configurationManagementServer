package vdx;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import vdx.manager.ConfigurationManager;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationManagerTest {
    private ConfigurationManager manager = null;
    private List<String> store = null;
    private String env = null;
    private List<String> application = null;
    private String poll = "";
    private String key = "";

    @Before
    public void setup(){
        store = new ArrayList<>();
        application = new ArrayList<>();
    }

    @Test
    public void testRetrieveConfigValue_Local() {
        store.add("local");
        env = "dev";
        application.add("app1");
        poll = "false";
        key = "key1";
        manager = new ConfigurationManager(store, application, env, poll);
        Assert.assertEquals("value1", manager.retrieveConfigValue(store.get(0), application.get(0), key));
    }

    @Test
    public void testRetrieveConfigValue_Local_Invalid() {
        store.add("local");
        env = "dev";
        application.add("app1");
        poll = "false";
        key = "key11111";
        manager = new ConfigurationManager(store, application, env, poll);
        Assert.assertNull(manager.retrieveConfigValue(store.get(0), application.get(0), key));
    }

    @Test
    public void testRetrieveConfigValue_Git() {
        store.add("git");
        env = "dev";
        application.add("app1");
        poll = "false";
        key = "key1";
        manager = new ConfigurationManager(store, application, env, poll);
        Assert.assertEquals("value2", manager.retrieveConfigValue(store.get(0), application.get(0), key));
    }

    @Test
    public void testRetrieveConfigValue_Git_Invalid() {
        store.add("git");
        env = "dev";
        application.add("app1");
        poll = "false";
        key = "key1111";
        manager = new ConfigurationManager(store, application, env, poll);
        Assert.assertNull(manager.retrieveConfigValue(store.get(0), application.get(0), key));
    }
}
