package com.telosws.broking.ui.util;

import com.telosws.broking.views.BrokingHomeView;
import com.telosws.broking.views.policyadmin.NewPolicyView;
import com.telosws.broking.views.policyadmin.SearchPoliciesView;
import com.telosws.broking.views.policyadmin.UploadRenewalsView;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;

/**
 * @author Harish Kalepu
 */
public enum PolicyAdminNavigationViewList {
	
	HOME(BrokingHomeView.VIEW_NAME, FontAwesome.HOME),
    NEWPOLICY(NewPolicyView.VIEW_NAME, new ThemeResource("img/New_Policy.png")),
	SEARCHPOLICIES(SearchPoliciesView.VIEW_NAME, new ThemeResource("img/Search_Policy.png")),
	UPLOADRENEWALS(UploadRenewalsView.VIEW_NAME, new ThemeResource("img/Upload_Renewals.png"));

    private final String viewName;
    private final Resource icon;

    private PolicyAdminNavigationViewList(final String viewName,
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

    public static PolicyAdminNavigationViewList getByViewName(final String viewName) {
        PolicyAdminNavigationViewList result = null;
        for (PolicyAdminNavigationViewList viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }
}