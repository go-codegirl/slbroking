package com.telosws.broking.events;

import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventScope;
import com.telosws.broking.ui.ParentUI;

/**
 * @author Harish Kalepu
 */

public class BrokingEventBus {

    public static void post(final Object event,EventBus.UIEventBus uiEventBus) {
        ParentUI.getSafetyLeafEventBus(uiEventBus).publish(EventScope.SESSION,event);
    }
}
