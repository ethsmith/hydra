package org.dragonetmc.hydra.level;

import com.google.gson.JsonElement;

// TODO Hook into Pterodactyl API for Server Instances
public class ServerLevel extends Level {

    private String address;
    private String url;

    public ServerLevel(String id) {
        super(id);
    }

    public ServerLevel(String id, Level source) {
        super(id, source);
    }

    @Override
    public void create() {

    }

    @Override
    public void destroy() {

    }

    public String getAddress() {
        return address;
    }

    public String getUrl() {
        return url;
    }

    public JsonElement getInfo(String key) {
        return null;
    }
}
