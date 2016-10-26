package com.telosws.broking.ui.menu;

import java.io.File;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;

import com.telosws.broking.constants.SLBrokingConstants;
import com.telosws.broking.events.BrokingEventBus;
import com.telosws.broking.events.BrokingEvents;
import com.telosws.broking.ui.util.BrokingHomeNavigationViewList;
import com.telosws.broking.ui.util.PolicyAdminNavigationViewList;
import com.telosws.broking.util.GenericResponse;
import com.telosws.broking.util.GetProperties;
import com.telosws.broking.views.BrokingHomeView;
import com.telosws.broking.views.LoginView;
import com.telosws.broking.views.UserProfileWindow;
import com.telosws.broking.views.policyadmin.NewPolicyView;
import com.telosws.orm.tables.pojos.Onlineusers;
import com.telosws.orm.tables.pojos.User;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;



/**
 * @author Harish Kalepu
 */
@SpringComponent
@UIScope
@SuppressWarnings("serial")
public class PolicyAdminMenu extends TeloswsBrokingMenu {

    @Autowired
    EventBus.UIEventBus uiEventBus;

    @Autowired
    private GetProperties getProperties;
    
    @Autowired
	private SpringViewProvider viewProvider;
    
    final Logger logger = LoggerFactory.getLogger(LoginView.class);

    public static final String ID = "dashboard-menu";
    private static final String STYLE_VISIBLE = "valo-menu-visible";
    private static final String STYLE_SELECTED = "selected";
    private MenuBar.MenuItem settingsItem;
    private User user;
    public Object ui;
    private CssLayout menuItemsLayout;
    public Button downloadEcardButton;
    private String userName="";
    static Image logoIcon;

    public PolicyAdminMenu() {
        setPrimaryStyleName("valo-menu");
        setId(ID);
        setSizeUndefined();

    }

    @PostConstruct
    public void inti()
    {

        uiEventBus.subscribe(this);
        setCompositionRoot(buildContent());
    }

    public Component buildContent() {
        final CssLayout menuContent = new CssLayout();
        menuContent.addStyleName("sidebar");
        menuContent.addStyleName(ValoTheme.MENU_PART);
        menuContent.addStyleName("no-vertical-drag-hints");
        menuContent.addStyleName("no-horizontal-drag-hints");
        menuContent.setWidth(null);
        menuContent.setHeight("100%");

        menuContent.addComponent(buildTitle());
        menuContent.addComponent(buildLogo());
        menuContent.addComponent(buildUserMenu());
        menuContent.addComponent(buildModuleTitle());
        menuContent.addComponent(buildToggleButton());
        menuContent.addComponent(buildMenuItems());
       // menuContent.addComponent(buildEcardItem());

        return menuContent;
    }

    private Component buildTitle() {
        Label logo = new Label("<strong>TelosBrooking <b>Portal</b></strong>",
                ContentMode.HTML);
        logo.setSizeUndefined();
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName("valo-menu-title");
        return logoWrapper;
    }
    
    public Component buildLogo() {
    	try{
    		Resource resource = null;
    		File logoFile = null;
    		String destPath = VaadinServlet.getCurrent().getServletContext().getRealPath("/VAADIN/themes/dashboard/img");
    		if(UI.getCurrent() != null && UI.getCurrent().getSession() != null && VaadinSession.getCurrent().getAttribute("user") != null) {
    			user=(User)VaadinSession.getCurrent().getAttribute("user");
    			logoFile = new File(destPath+"/Logo"+user.getCompanyId()+".png");
    			if(logoFile.exists() && !logoFile.isDirectory()) {
        			resource = new ThemeResource("img/Logo"+user.getCompanyId()+".png");
        		} else {
        			resource = new ThemeResource("img/TempLogo.png");
        		}
    		} else {
    			resource = new ThemeResource("img/TempLogo.png");
    		}
	        logoIcon = new Image(null,resource);
	        logoIcon.setWidth(null);
	        logoIcon.setHeight(null);
	        HorizontalLayout logoIconWrapper = new HorizontalLayout(logoIcon);
	        logoIconWrapper.setComponentAlignment(logoIcon, Alignment.MIDDLE_CENTER);
	        logoIconWrapper.addStyleName("valo-menu-logo");
	        return logoIconWrapper;
    	} catch(Exception e){
    		e.printStackTrace();
    	}
        return null;
    }

    private Component buildUserMenu() {
        final MenuBar settings = new MenuBar();
        settings.addStyleName("user-menu");
        settingsItem = settings.addItem("", null, null);
        settingsItem.addItem("Edit Profile", (MenuBar.Command) selectedItem -> {
            try {
                UserProfileWindow.open(getProperties.getBaseUrl(),user,uiEventBus);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsItem.addSeparator();
        settingsItem.addItem("Sign Out", (MenuBar.Command) selectedItem -> {
            try {
            	user = (User)UI.getCurrent().getSession().getAttribute("user");
            	Onlineusers onlineusers = new Onlineusers();
            	onlineusers.setPrimaryEmail(user.getPrimaryEmail());
            	RestTemplate restTemplate = new RestTemplate();
            	restTemplate.postForObject(getProperties.getBaseUrl() + "/deleteOnlineUser",onlineusers,GenericResponse.class);
            	getUI().getSession().close();
            	getUI().getPage().setLocation(getProperties.getBaseUrl()+"/");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        return settings;
    }
    
    private Component buildModuleTitle() {
        Label logo = new Label("<strong>Policy Adminstration</strong>",
                ContentMode.HTML);
        logo.setSizeUndefined();
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName("moduleTitleStyleName");
        return logoWrapper;
    }

    private Component buildToggleButton() {
        Button valoMenuToggleButton = new Button("Menu", (Button.ClickListener) event -> {
            if (getCompositionRoot().getStyleName().contains(STYLE_VISIBLE)) {
                getCompositionRoot().removeStyleName(STYLE_VISIBLE);
            } else {
                getCompositionRoot().addStyleName(STYLE_VISIBLE);
            }
        });
        valoMenuToggleButton.setIcon(FontAwesome.LIST);
        valoMenuToggleButton.addStyleName("valo-menu-toggle");
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
        return valoMenuToggleButton;
    }

    private Component buildMenuItems() {
        menuItemsLayout = new CssLayout();
        menuItemsLayout.addStyleName("valo-menuitems");
        for (final PolicyAdminNavigationViewList view : PolicyAdminNavigationViewList.values()) {
            Component menuItemComponent = new ValoMenuItemButton(view);
            menuItemsLayout.addComponent(menuItemComponent);
        }
        /*downloadEcardButton.addClickListener((Button.ClickListener) event -> {
            try {
                Ecard.buildPdf(getProperties.getBaseUrl());
            } catch (Exception e) {
                Util.errorNotification("Your e-card is not available with TPA to download.");
            }
        });*/
        return menuItemsLayout;

    }

    public final class ValoMenuItemButton extends Button {

        public ValoMenuItemButton(PolicyAdminNavigationViewList view) {
            setId(view.getViewName());
           /* if(view.getViewName().equals(NavigationViewList.RAISEREQUEST.getViewName())
                    || view.getViewName().equals(NavigationViewList.CLAIMS.getViewName()))
                setEnabled(true);
            else
                setEnabled(false);*/
            setPrimaryStyleName("valo-menu-item");
            setIcon(view.getIcon());
            setCaption(view.getViewName().substring(0, 1).toUpperCase()
                    + view.getViewName().substring(1));
            addClickListener((ClickListener) event -> {
            	if(view.getViewName().equals("Home")){
            		user = (User)UI.getCurrent().getSession().getAttribute("user");
            		if(user != null){
            			GridLayout layout = (GridLayout) UI.getCurrent().getContent();
            			Panel panel = (Panel)layout.getComponent(1, 0);
            			VerticalLayout verticalLayout = (VerticalLayout)panel.getContent();
            			HorizontalLayout hlLayout = (HorizontalLayout) verticalLayout.getComponent(1);
            			verticalLayout.removeComponent(hlLayout);
            			panel.setContent(verticalLayout);
            			layout.removeComponent(0, 0);
            			(UI.getCurrent()).setContent(layout);
            			UI.getCurrent().getNavigator().navigateTo(view.getViewName());
            		}
            	} else {
            		UI.getCurrent().getNavigator().navigateTo(view.getViewName());
            	}

            });

        }
    }
    
    @EventBusListenerMethod
    public void updateUserName(BrokingEvents.UserUpdateEvent event) {
        user = (event.getUser() == null) ? getCurrentUser() : event.getUser();
        if (user != null)
            settingsItem.setText(user.getFirstName() + " " + user.getLastName());

    }


    @EventBusListenerMethod()
    public void myListener(BrokingEvents.PostViewChangeEvent payload) {
        // remove style for all menu items
        for (int i = 0; i < PolicyAdminNavigationViewList.values().length; i++) {
            menuItemsLayout.getComponent(i).removeStyleName(STYLE_SELECTED);
        }
        //hide menu items if application is not submitted
        if(!payload.getView().getViewName().isEmpty() && payload.getView().getViewName().equals(PolicyAdminNavigationViewList.HOME.getViewName()))
        {
            /*for (int i = 0; i < NavigationViewList.values().length; i++) {
                if(NavigationViewList.values()[i].getViewName().equals(NavigationViewList.RAISEREQUEST.getViewName()) ||
                        NavigationViewList.values()[i].getViewName().equals(NavigationViewList.CLAIMS.getViewName()))
                    menuItemsLayout.getComponent(i).setVisible(false);
            }*/
        }

        // add style to selected menu item
        for (int i = 0; i < PolicyAdminNavigationViewList.values().length; i++) {

            if(!payload.getView().getViewName().isEmpty() && payload.getView().getViewName().equals(PolicyAdminNavigationViewList.values()[i].getViewName()))
            {
                menuItemsLayout.getComponent(i).setStyleName(STYLE_SELECTED);
                menuItemsLayout.getComponent(i).setEnabled(true);
                break;
            }
        }

    }

    @EventBusListenerMethod
    public void updateMenuItems(BrokingEvents.PostReviewViewChangeEvent payload) {

        //show menu items once application is submitted
        /*if(!payload.getView().getViewName().isEmpty() && payload.getView().getViewName().equals(NavigationViewList.REVIEW.getViewName()))
        {
            downloadEcardButton.setVisible(true);
            for (int i = 0; i < NavigationViewList.values().length; i++) {
                if(NavigationViewList.values()[i].getViewName().equals(NavigationViewList.RAISEREQUEST.getViewName()) ||
                        NavigationViewList.values()[i].getViewName().equals(NavigationViewList.CLAIMS.getViewName()) ||
                        NavigationViewList.values()[i].getViewName().equals(NavigationViewList.VAULT.getViewName())) {
                    menuItemsLayout.getComponent(i).setVisible(true);
                    menuItemsLayout.getComponent(i).setEnabled(true);
                }
            }


            for (int i = 0; i < NavigationViewList.values().length; i++) {
                if(NavigationViewList.values()[i].getViewName().equals(NavigationViewList.HOME.getViewName()) ||
                        NavigationViewList.values()[i].getViewName().equals(NavigationViewList.EMPLOYEE.getViewName()) ||
                                NavigationViewList.values()[i].getViewName().equals(NavigationViewList.DEPENDENT.getViewName()))
                    menuItemsLayout.getComponent(i).setVisible(false);
            }
        }
*/
    }

    private User getCurrentUser() {
        return (User) VaadinSession.getCurrent().getAttribute("user");
    }


}
