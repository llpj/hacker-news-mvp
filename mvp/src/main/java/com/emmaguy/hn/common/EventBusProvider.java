package com.emmaguy.hn.common;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class EventBusProvider {
    private static final Bus mNetworkBus = new Bus(ThreadEnforcer.ANY);

    private EventBusProvider() {
    }

    public static Bus getNetworkBusInstance() {
        return mNetworkBus;
    }
}
