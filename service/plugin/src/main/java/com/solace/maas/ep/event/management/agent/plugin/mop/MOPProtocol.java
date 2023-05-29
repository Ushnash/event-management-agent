package com.solace.maas.ep.event.management.agent.plugin.mop;

public enum MOPProtocol {
    scanData(2850),
    scanDataControl(2851),
    EMAHeartbeat(2852);

    private final int id;

    MOPProtocol(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
