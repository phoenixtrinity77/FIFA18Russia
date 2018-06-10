package com.trinity.phoenix.fifa_18russia;

public class PlayerList {
    String url;
    String playername;

    public PlayerList() {
    }

    public PlayerList(String url, String playername) {
        this.url = url;
        this.playername = playername;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPlayername() {
        return playername;
    }

    public void setPlayername(String playername) {
        this.playername = playername;
    }
}
