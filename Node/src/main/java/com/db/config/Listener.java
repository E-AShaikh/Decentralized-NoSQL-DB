package com.db.config;

public abstract class Listener implements Runnable {
    protected boolean isRunning = true;
    @Override
    public void run() {
        listen();
    }

    protected abstract void listen();

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
