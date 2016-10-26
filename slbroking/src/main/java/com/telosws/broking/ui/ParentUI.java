package com.telosws.broking.ui;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.annotation.EnableVaadinEventBus;

import com.telosws.broking.events.BrokingEventBus;
import com.telosws.broking.events.BrokingEvents;
import com.telosws.broking.ui.menu.PolicyAdminMenu;
import com.telosws.broking.ui.menu.TeloswsBrokingMenu;
import com.telosws.broking.views.LoginView;
import com.telosws.orm.tables.pojos.User;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Harish Kalepu
 */
@SpringUI
@Theme("dashboard")
@Widgetset("AppWidgetset")
@Title("SafetyLeaf Broking Center")
@EnableVaadinEventBus
@VaadinSessionScope
@PreserveOnRefresh
@SuppressWarnings("serial")
public class ParentUI extends UI {

    private final Logger log = LoggerFactory.getLogger(ParentUI.class);

    @Autowired
    EventBus.UIEventBus uiEventBus;


    static EventBus.UIEventBus newuiEventBus;

    @Autowired
    LoginView loginView;
    
    @Autowired
	private PolicyAdminMenu brokingMenu;

	

    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	try {
	    	log.debug("************ ParentUI init() method starting");
	        setLocale(Locale.US);
	        uiEventBus.subscribe(this);
	        Responsive.makeResponsive(this);
	        addStyleName(ValoTheme.UI_WITH_MENU);
	        //setTheme("broking");
	        updateContent();
	        log.debug("************ ParentUI init() method ending");
    	} catch(Exception ex) {
    		log.error("***Error In init() method***",ex);
        }
    }

    /**
     * Updates the correct content for this UI based on the current user status.
     * If the user is logged in with appropriate privileges, main view is shown.
     * Otherwise login view is shown.
     */
    private void updateContent() {
    	try {
    		log.debug("************ ParentUI updateContent() method starting");
	        setContent(loginView);
	        //If user session exists , we use that session and  redirect user to home page instead of login
	        if (VaadinSession.getCurrent().getAttribute("user") != null) {
	            User user = (User) VaadinSession.getCurrent().getAttribute("user");
	            VerticalLayout mainLayout = loginView.mainVerticalLayout();
	            Panel mainContent = loginView.mainPanel();
	            mainContent.setContent(loginView.buildScreen(user));
	            mainLayout.addComponent(mainContent);
	            loginView.addComponent(mainLayout);
	            Responsive.makeResponsive(mainLayout);
	        }
	        log.debug("************ ParentUI updateContent() method ending");
    	} catch(Exception ex) {
    		log.error("***Error In updateContent() method***",ex);
    	}
    }

    public static EventBus getSafetyLeafEventBus(EventBus.UIEventBus uiEventBus) {
        newuiEventBus = uiEventBus;
        return newuiEventBus;
    }
    
    public void refreshLogo(VaadinRequest request){
    	try{
    		log.debug("************ ParentUI refreshLogo() method ending");
    		User user = user=(User) VaadinSession.getCurrent().getAttribute("user");
	    	TeloswsBrokingMenu teloswsMenu = new TeloswsBrokingMenu();
	    	if(user == null){
	    		loginView.clearFields();
	    	}
	    	/*if (user != null && UserRoles.HUMANRESOURCES.getUserRole().equals(user.getRole())) {
		    	teloswsMenu.setCompositionRoot(hrMenu.buildContent());
		    	hrMenu.rebuildLogo(user);
	    	} else if (user != null && UserRoles.EMPLOYEE.getUserRole().equals(user.getRole())) {
	    		teloswsMenu.setCompositionRoot(empMenu.buildContent());
	        	empMenu.rebuildLogo(user);
	    	}*/
	    	log.debug("************ ParentUI refreshLogo() method ending");
    	} catch(Exception ex) {
    		log.error("***Error In refreshLogo() method***",ex);
    	}
    	
    }
    
    @Override
    protected void refresh(VaadinRequest request) {
    	// TODO Auto-generated method stub
    	refreshLogo(request);
    	super.refresh(request);
    }
}