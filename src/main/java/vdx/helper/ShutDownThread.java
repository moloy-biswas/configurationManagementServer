package vdx.helper;

/**
 * Abstract Implementation of Shutdown hook
 */
public class ShutDownThread extends Thread{
    private IProces process;

    public ShutDownThread(IProces process){
        this.process = process;
    }

    @Override
    public void run() {
        process.shutdown();
    }
}
