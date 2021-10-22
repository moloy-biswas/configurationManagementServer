package vdx.store.impl;


import org.apache.commons.io.IOUtils;
import org.springframework.web.client.RestTemplate;
import vdx.manager.Config;
import vdx.store.IConfigStore;
import vdx.store.exception.ConfigStoreException;

import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Git hub store implementation details
 */
public class GitHubStore implements IConfigStore {
    private String gitUrl = "https://api.github.com/repos/moloy-biswas/configurationServer/contents?ref=main";
    private String username = "user-name";
    private String password = "pass-word";
    private String file = "demo-config.properties";
    private List<Map> response = null;

    @Override
    public void connectToStore(Config config) {
        try{
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.getForObject(gitUrl, List.class, username,
                    password, "main");
            if(null != response){
                System.out.println("Successfully connected to git store..");
            }
        }catch (Exception e){
            System.out.println("Exception while connecting to git store.."+e.getMessage());
        }
    }

    @Override
    public Map<String, Map<String, String>> retrieveFile(List<String> application, String env){
        final Map<String, Map<String, String>> configValues = new HashMap<>();
        application.stream().forEach( app -> {
            try {
                configValues.put(app, retrieve(app, env));
            } catch (ConfigStoreException e) {
                e.printStackTrace();
            }
        });
        return configValues;
    }

    /**
     *
     * @param app
     * @param env
     * @return
     *
     * This method can be refactored and pass app/env values based on different branch in git repo and retrive values accordingly.
     * For simplicity and time constraint, keeping it for one hardcoded file name
     */
    private Map<String, String> retrieve(String app, String env) throws ConfigStoreException{
        final Map<String, String> configValues = new HashMap<>();
        if(null != response){
            Map fileMetaData = response.stream().filter(fileName -> file.equals(fileName.get("name"))).findFirst().get();
            String fileName = (String) fileMetaData.get("name");
            String downloadUrl = (String) fileMetaData.get("download_url");
            System.out.println("File " + fileName + " retrieved from github with download_url "+downloadUrl);
            String fileContent = null;
            try {
                fileContent = IOUtils.toString(new URI(downloadUrl), Charset.defaultCharset());
            } catch (IOException e) {
                throw new ConfigStoreException(e.getMessage());
            } catch (URISyntaxException e) {
                throw new ConfigStoreException(e.getMessage());
            }
            System.out.println("Content = \n" + fileContent + "\n");
            configValues.putAll(Arrays.stream(fileContent.split("\n")).map(line -> line.split("="))
                    .collect(Collectors.toMap(line -> line[0].trim(), line ->line[1].trim())));
        }
        return configValues;
    }

    @Override
    public String retrieveValue(String app, String key, String env) throws ConfigStoreException {
        return retrieve(app, env).get(key);
    }
}
