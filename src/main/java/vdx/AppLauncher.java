package vdx;


import vdx.helper.IProces;
import vdx.helper.ShutDownThread;
import vdx.manager.ConfigurationManager;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/*
    Main class to launch app and pass on Argument
 */
public class AppLauncher implements IProces {

    // Scheduler with fixed delay to poll file
    private ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);

    public static void main(String[] args) {
        List<String> store = null;
        String env = null;
        List<String> application = null;
        String poll = "";
        String key = "";

        if(args.length >= 4){
            store = getParsedValues(args[0]);
            application = getParsedValues(args[1]);
            env = args[2];
            poll = args[3];
            // key may be passed or not based on poll mode
            key = args.length>4 ? args[4] : null;
        }else{
            // Assuming for each run, we have to specify the env/app/store details with poll mode on/off
            System.out.println("Insufficient argument passed..exiting application...");
            System.exit(0);
        }
        AppLauncher launcher = new AppLauncher();
        Runtime.getRuntime().addShutdownHook(new ShutDownThread(launcher));
        launcher.start(store, env, application, poll, key);
    }

    private void start(List<String> store, String env, List<String> application, String poll, String key) {
        ConfigurationManager configManager = new ConfigurationManager(store, application, env, poll);
        configManager.setScheduler(scheduler);

        // Poll mode enabled
        if(Boolean.valueOf(poll)){
            configManager.loadConfig();
        }else if(key != null){
            // Just to retrieve key
            configManager.retrieveConfigValue(store.get(0), application.get(0), key);
        }
    }

    private static List<String> getParsedValues(String arg){
        return Arrays.asList(arg.split(","));
    }

    @Override
    public void shutdown() {
        if(null != scheduler){
            System.out.println("Poller shutdown started");
            scheduler.shutdown();
            System.out.println("Poller shutdown finished");
        }
    }
}
