package vdx.manager;

/**
 * Simple POJO class to store different store or env config and pass on the file to Store to make connection/download file
 * Its not used but kept as scope of enhancement.
 * Hardcoding can be removed from individual store and values can be configured in env.properties file
 */
public class Config {

    private String url;
    private String username;
    private String pass;
    private String deploymentLoc;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDeploymentLoc() {
        return deploymentLoc;
    }

    public void setDeploymentLoc(String deploymentLoc) {
        this.deploymentLoc = deploymentLoc;
    }

    @Override
    public String toString() {
        return "Config{" +
                "url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", pass='" + pass + '\'' +
                ", deploymentLoc='" + deploymentLoc + '\'' +
                '}';
    }
}
