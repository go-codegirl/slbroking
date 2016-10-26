package com.telosws.broking.ui.util;

import com.telosws.broking.views.BrokingHomeView;
import com.telosws.broking.views.policyadmin.NewPolicyView;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

/**
 * @author Harish Kalepu
 */
public enum BrokingHomeNavigationViewList {

    HOME(BrokingHomeView.VIEW_NAME, FontAwesome.HOME);

    private final String viewName;
    private final Resource icon;

    private BrokingHomeNavigationViewList(final String viewName,
                               final Resource icon) {
        this.viewName = viewName;
        this.icon = icon;
    }

    public String getViewName() {
        return viewName;
    }

    public Resource getIcon() {
        return icon;
    }

    public static BrokingHomeNavigationViewList getByViewName(final String viewName) {
        BrokingHomeNavigationViewList result = null;
        for (BrokingHomeNavigationViewList viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }
}