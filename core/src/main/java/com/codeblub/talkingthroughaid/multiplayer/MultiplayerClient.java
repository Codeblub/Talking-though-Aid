package com.codeblub.talkingthroughaid.multiplayer;

import com.badlogic.gdx.Gdx;

public class MultiplayerClient {
    private final String playerName;
    private boolean connected;

    public MultiplayerClient(String playerName) {
        this.playerName = playerName;
    }

    public boolean connect(String host, int port) {
        Gdx.app.log("MultiplayerClient", "Connecting to " + host + ":" + port + " as " + playerName);
        connected = true;
        return connected;
    }

    public boolean joinMatch(String matchId) {
        if (!connected) {
            Gdx.app.log("MultiplayerClient", "Not connected, cannot join match");
            return false;
        }
        Gdx.app.log("MultiplayerClient", "Joined match " + matchId);
        return true;
    }

    public void disconnect() {
        Gdx.app.log("MultiplayerClient", "Disconnected");
        connected = false;
    }

    public boolean isConnected() {
        return connected;
    }
}
