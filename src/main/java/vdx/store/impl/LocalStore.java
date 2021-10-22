package vdx.store.impl;

import vdx.manager.Config;
import vdx.store.IConfigStore;
import vdx.store.exception.ConfigStoreException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Local store implementation details
 */
public class LocalStore implements IConfigStore {
    private String prefixDir = "src/main/resources/local/";
    private Path path = null;
    private static Map<String, Long> timeStore = new HashMap<>();

    @Override
    public void connectToStore(Config config) throws ConfigStoreException {
        path = Paths.get(prefixDir);
        try {
            boolean isOpen = path.getFileSystem().isOpen();
            if(isOpen) {
                System.out.println("Successfully connected to Local store");
            }
        } catch (Exception e) {
            throw new ConfigStoreException("Exception while connecting to store");
        }
    }

    @Override
    public Map<String, Map<String, String>> retrieveFile(List<String> application, String env) throws ConfigStoreException {
        final Map<String, Map<String, String>> configValues = new HashMap<>();
        if(null != path) {
            application.stream().forEach(app -> {
                try {
                    configValues.put(app, retrieve(app, env));
                } catch (ConfigStoreException e) {
                    e.printStackTrace();
                }
            });
        }
        return configValues;
    }

    private Map<String, String> retrieve(String app, String env) throws ConfigStoreException {
        final Map<String, String> configValues = new HashMap<>();
            try {
                String newDir = prefixDir.concat(app).concat("/").concat(env).concat("/demo-config.properties");
                Path newPath = Paths.get(newDir);
                FileTime lastModifiedTime = Files.getLastModifiedTime(newPath);
                long modifiedTime = lastModifiedTime.toMillis();
                Long prevTime = timeStore.getOrDefault(app,0L).longValue();
                if(modifiedTime > prevTime){
                    System.out.println("File is modified");
                    timeStore.put(app, modifiedTime);
                    configValues.putAll(Files.readAllLines(newPath).stream().map( line -> line.split("="))
                            .collect(Collectors.toMap(line -> line[0].trim(), line ->line[1].trim())));
                }else {
                    System.out.println("No File is modified since last poll..skipping..");
                }
            } catch (IOException e) {
                throw new ConfigStoreException("Exception while retrieving file from store");
            }
        return configValues;
    }

    public String retrieveValue(String app, String key, String env) throws ConfigStoreException {
        final Map<String, String> configValues = new HashMap<>();
        try {
            String newDir = prefixDir.concat(app).concat("/").concat(env).concat("/demo-config.properties");
            Path newPath = Paths.get(newDir);
            configValues.putAll(Files.readAllLines(newPath).stream().map( line -> line.split("="))
                        .collect(Collectors.toMap(line -> line[0].trim(), line ->line[1].trim())));

        } catch (IOException e) {
            throw new ConfigStoreException("Exception while retrieving file from store");
        }
        return configValues.get(key);
    }
}
