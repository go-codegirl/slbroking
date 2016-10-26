package com.telosws.broking.views;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.vaadin.spring.events.EventBus;

import com.telosws.broking.constants.SLBrokingConstants;
import com.telosws.broking.events.BrokingEventBus;
import com.telosws.broking.events.BrokingEvents;
import com.telosws.broking.ui.menu.PolicyAdminMenu;
import com.telosws.broking.ui.util.PolicyAdminNavigationViewList;
import com.telosws.broking.util.GenericResponse;
import com.telosws.broking.util.GetProperties;
import com.telosws.broking.views.policyadmin.NewPolicyView;
import com.telosws.orm.tables.pojos.Onlineusers;
import com.telosws.orm.tables.pojos.User;
import com.vaadin.event.LayoutEvents;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
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
 * 
 * @author Harish Kalepu
 *
 */
@UIScope
@SpringView(name = BrokingHomeView.VIEW_NAME)
@SuppressWarnings("serial")
public class BrokingHomeView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "Home";
	
	final Logger logger = LoggerFactory.getLogger(BrokingHomeView.class);

	@Autowired
	EventBus.UIEventBus uiEventBus;

	@Autowired
	private GetProperties getProperties;
	
	@Autowired
	private SpringViewProvider viewProvider;
	
	@Autowired
	private PolicyAdminMenu brokingMenu;

	private VerticalLayout homeViewLayout;
	private HorizontalLayout topHorizontalLayout;
	private GridLayout bodyGridLayout;
	private HorizontalLayout footerLayout;
	private MenuBar logoutMenuBar;
	private MenuBar.MenuItem logoutMenuBarItem;
	private User user;
	public BrokingHomeView() {
		setSizeFull();
		addStyleName("welcomePageImage");
		setSpacing(true);
	}

	@PostConstruct
	void init() {
		uiEventBus.subscribe(this);
		//Home Page Layout
		homeViewLayout = new VerticalLayout();
		homeViewLayout.setSizeFull();
		Responsive.makeResponsive(homeViewLayout);
		
		//header layout
		topHorizontalLayout = new HorizontalLayout();
		topHorizontalLayout.setSizeFull();
		topHorizontalLayout.setSpacing(true);
		topHorizontalLayout.setWidth(100, Unit.PERCENTAGE);
		Responsive.makeResponsive(topHorizontalLayout);
		//Column one
		logoutMenuBar = new MenuBar();
		logoutMenuBar.addStyleName("homeuser-menu");
		logoutMenuBarItem = logoutMenuBar.addItem("", null,null);
		logoutMenuBarItem.setStyleName("menuItemStyleName");
		logoutMenuBarItem.addItem("Sign Out", (MenuBar.Command) selectedItem -> {
	            try {
	            	user = (User)UI.getCurrent().getSession().getAttribute("user");
	            	Onlineusers onlineusers = new Onlineusers();
	            	onlineusers.setPrimaryEmail(user.getPrimaryEmail());
	            	RestTemplate restTemplate = new RestTemplate();
	            	 restTemplate.postForObject(getProperties.getBaseUrl() + "/deleteOnlineUser",onlineusers,GenericResponse.class);
	            	getUI().getSession().close();
					getUI().getPage().setLocation(getProperties.getBaseUrl()+"/" );
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        });
		logoutMenuBar.addListener(new Listener() {
			
			@Override
			public void componentEvent(Event event) {
				// TODO Auto-generated method stub
				
			}
		});
		Image slImage = new Image(null, new ThemeResource("img/Hambergure.png"));
		slImage.addStyleName("homeHambergureStyleName");
		topHorizontalLayout.addComponent(logoutMenuBar);
		topHorizontalLayout.setComponentAlignment(logoutMenuBar, Alignment.MIDDLE_LEFT);
		topHorizontalLayout.setExpandRatio(logoutMenuBar,1);
		slImage.addClickListener(new ClickListener() {
			
			@Override
			public void click(ClickEvent event) {
				logoutMenuBar.addStyleName("homeuserone-menu");
			}
		});
		//Column Two
		Label label = new Label("BROKING CENTER");
		label.setStyleName(ValoTheme.LABEL_H2);
		label.addStyleName("homeLabelStyleName");
		topHorizontalLayout.addComponent(label);
		topHorizontalLayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
		topHorizontalLayout.setExpandRatio(label, 1);
		//Column Three
		Image logo = new Image(null, new ThemeResource("img/logo.png"));
		logo.addStyleName("homeLogoStyleName");
		topHorizontalLayout.addComponent(logo);
		topHorizontalLayout.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
		topHorizontalLayout.addStyleName("homePageGirdLayout");
		topHorizontalLayout.setExpandRatio(logo, 1);
		
		//body layout
		bodyGridLayout = new GridLayout(3,2);
		bodyGridLayout.setSizeFull();
		bodyGridLayout.setSpacing(true);
		bodyGridLayout.setMargin(new MarginInfo(false, true, true, false));
		bodyGridLayout.setWidth(85, Unit.PERCENTAGE);
		bodyGridLayout.setHeight(300,Unit.PIXELS);
		bodyGridLayout.addStyleName("bodyGridLayoutStyleName");
		
		//Policy Administration
		HorizontalLayout policyAdmin = new HorizontalLayout();
		policyAdmin.setHeight(111,Unit.PIXELS);
		policyAdmin.setWidth(375,Unit.PIXELS);
		
		Image policyAdminIcon = new Image(null, new ThemeResource("img/PolicyAdministration.png"));
		policyAdminIcon.setWidth(107,Unit.PIXELS);
		
		VerticalLayout policyAdminVLayout = new VerticalLayout();
		policyAdminVLayout.setSizeFull();
		policyAdminVLayout.setSpacing(true);
		policyAdminVLayout.setWidth(100,Unit.PERCENTAGE);
		
		policyAdminVLayout.addStyleName("policyAdminVLayoutStyleName");
		
		Label policyAdminlabel = new Label("Policy Administration");
		policyAdminlabel.setStyleName(ValoTheme.LABEL_H3);
		policyAdminlabel.addStyleName("policyAdminlabelStyleName");
		
		Label policyAdminCaptionlabel = new Label("Client Policy Information");
		policyAdminCaptionlabel.addStyleName("policyAdminCaptionlabelStyleName");
		
		policyAdminVLayout.addComponent(policyAdminlabel);
		policyAdminVLayout.setComponentAlignment(policyAdminlabel, Alignment.TOP_CENTER);
		policyAdminVLayout.addComponent(policyAdminCaptionlabel);
		policyAdminVLayout.setComponentAlignment(policyAdminCaptionlabel, Alignment.MIDDLE_CENTER);
		
		policyAdmin.addComponent(policyAdminIcon);
		policyAdmin.setExpandRatio(policyAdminIcon, 0.8f);
		policyAdmin.addComponent(policyAdminVLayout);
		policyAdmin.setExpandRatio(policyAdminVLayout, 2);
		policyAdmin.addStyleName("homeGridRightBottomStyleName");
		bodyGridLayout.addComponent(policyAdmin);
		
		policyAdmin.addLayoutClickListener((LayoutEvents.LayoutClickListener) event -> {
			try {
				GridLayout mainLayout = mainGridLayout();
				Panel mainContent = mainPanel();
				user = (User)UI.getCurrent().getSession().getAttribute("user");
				mainContent.setContent(buildScreen(user,SLBrokingConstants.POLICY_ADMIN));
				BrokingEventBus.post(new BrokingEvents.UserUpdateEvent(user),uiEventBus);
				updateMenu(user,SLBrokingConstants.POLICY_ADMIN, mainLayout);
				mainLayout.addComponent(mainContent);
				mainLayout.setColumnExpandRatio(1,1);
			    (UI.getCurrent()).setContent(mainLayout);
			} catch (Exception e) {
				logger.error("Exception occured when retriving domains", e);
			}
		});
		
		//Finance Management
		HorizontalLayout financeMgmt = new HorizontalLayout();
		financeMgmt.setHeight(111,Unit.PIXELS);
		financeMgmt.setWidth(375,Unit.PIXELS);
		
		Image financeMgmtIcon = new Image(null, new ThemeResource("img/FinanceManagement.png"));
		financeMgmtIcon.setWidth(107,Unit.PIXELS);
		
		VerticalLayout financeMgmtVLayout = new VerticalLayout();
		financeMgmtVLayout.setSizeFull();
		financeMgmtVLayout.setSpacing(true);
		financeMgmtVLayout.setWidth(100,Unit.PERCENTAGE);
		
		financeMgmtVLayout.addStyleName("policyAdminVLayoutStyleName");
		
		Label financeMgmtlabel = new Label("Finance Management");
		financeMgmtlabel.setStyleName(ValoTheme.LABEL_H3);
		financeMgmtlabel.addStyleName("policyAdminlabelStyleName");
		
		Label financeMgmtCaptionlabel = new Label("Reporting, Accounting & Debt");
		financeMgmtCaptionlabel.addStyleName("policyAdminCaptionlabelStyleName");
		
		financeMgmtVLayout.addComponent(financeMgmtlabel);
		financeMgmtVLayout.setComponentAlignment(financeMgmtlabel, Alignment.TOP_CENTER);
		financeMgmtVLayout.addComponent(financeMgmtCaptionlabel);
		financeMgmtVLayout.setComponentAlignment(financeMgmtCaptionlabel, Alignment.MIDDLE_CENTER);
		
		financeMgmt.addComponent(financeMgmtIcon);
		financeMgmt.setExpandRatio(financeMgmtIcon, 0.8f);
		financeMgmt.addComponent(financeMgmtVLayout);
		financeMgmt.setExpandRatio(financeMgmtVLayout, 2);
		financeMgmt.addStyleName("homeGridRightBottomStyleName");
		bodyGridLayout.addComponent(financeMgmt);
		
		//Process Management
		HorizontalLayout processMgmt = new HorizontalLayout();
		processMgmt.setHeight(111,Unit.PIXELS);
		processMgmt.setWidth(375,Unit.PIXELS);
		
		Image processMgmtIcon = new Image(null, new ThemeResource("img/ProcessManagement.png"));
		processMgmtIcon.setWidth(107,Unit.PIXELS);
		
		VerticalLayout processMgmtVLayout = new VerticalLayout();
		processMgmtVLayout.setSizeFull();
		processMgmtVLayout.setSpacing(true);
		processMgmtVLayout.setWidth(100,Unit.PERCENTAGE);
		
		processMgmtVLayout.addStyleName("policyAdminVLayoutStyleName");
		
		Label processMgmtlabel = new Label("Process Management");
		processMgmtlabel.setStyleName(ValoTheme.LABEL_H3);
		processMgmtlabel.addStyleName("policyAdminlabelStyleName");
		
		Label processMgmtCaptionlabel = new Label("Documents");
		processMgmtCaptionlabel.addStyleName("policyAdminCaptionlabelStyleName");
		
		processMgmtVLayout.addComponent(processMgmtlabel);
		processMgmtVLayout.setComponentAlignment(processMgmtlabel, Alignment.TOP_CENTER);
		processMgmtVLayout.addComponent(processMgmtCaptionlabel);
		processMgmtVLayout.setComponentAlignment(processMgmtCaptionlabel, Alignment.MIDDLE_CENTER);
		
		processMgmt.addComponent(processMgmtIcon);
		processMgmt.setExpandRatio(processMgmtIcon, 0.8f);
		processMgmt.addComponent(processMgmtVLayout);
		processMgmt.setExpandRatio(processMgmtVLayout, 2);
		processMgmt.addStyleName("homeGridRightBottomStyleName");
		bodyGridLayout.addComponent(processMgmt);
		
		//Customisation
		HorizontalLayout customisation = new HorizontalLayout();
		customisation.setHeight(111,Unit.PIXELS);
		customisation.setWidth(375,Unit.PIXELS);
		
		Image customisationIcon = new Image(null, new ThemeResource("img/Customisation.png"));
		customisationIcon.setWidth(107,Unit.PIXELS);
		
		VerticalLayout customisationVLayout = new VerticalLayout();
		customisationVLayout.setSizeFull();
		customisationVLayout.setSpacing(true);
		customisationVLayout.setWidth(100,Unit.PERCENTAGE);
		
		customisationVLayout.addStyleName("policyAdminVLayoutStyleName");
		
		Label customisationlabel = new Label("Customisation");
		customisationlabel.setStyleName(ValoTheme.LABEL_H3);
		customisationlabel.addStyleName("policyAdminlabelStyleName");
		
		Label customisationCaptionlabel = new Label("Support Managers,Agents,etc");
		customisationCaptionlabel.addStyleName("policyAdminCaptionlabelStyleName");
		
		customisationVLayout.addComponent(customisationlabel);
		customisationVLayout.setComponentAlignment(customisationlabel, Alignment.TOP_CENTER);
		customisationVLayout.addComponent(customisationCaptionlabel);
		customisationVLayout.setComponentAlignment(customisationCaptionlabel, Alignment.MIDDLE_CENTER);
		
		customisation.addComponent(customisationIcon);
		customisation.setExpandRatio(customisationIcon, 0.8f);
		customisation.addComponent(customisationVLayout);
		customisation.setExpandRatio(customisationVLayout, 2);
		customisation.addStyleName("homeGridRightStyleName");
		bodyGridLayout.addComponent(customisation);
		
		//Communication
		HorizontalLayout communication = new HorizontalLayout();
		communication.setHeight(111,Unit.PIXELS);
		communication.setWidth(375,Unit.PIXELS);
		
		Image communicationIcon = new Image(null, new ThemeResource("img/Communication.png"));
		communicationIcon.setWidth(107,Unit.PIXELS);
		
		VerticalLayout communicationVLayout = new VerticalLayout();
		communicationVLayout.setSizeFull();
		communicationVLayout.setSpacing(true);
		communicationVLayout.setWidth(100,Unit.PERCENTAGE);
		
		communicationVLayout.addStyleName("policyAdminVLayoutStyleName");
		
		Label communicationlabel = new Label("Communication");
		communicationlabel.setStyleName(ValoTheme.LABEL_H3);
		communicationlabel.addStyleName("policyAdminlabelStyleName");
		
		Label communicationCaptionlabel = new Label("SMS & Email Communication");
		communicationCaptionlabel.addStyleName("policyAdminCaptionlabelStyleName");
		
		communicationVLayout.addComponent(communicationlabel);
		communicationVLayout.setComponentAlignment(communicationlabel, Alignment.TOP_CENTER);
		communicationVLayout.addComponent(communicationCaptionlabel);
		communicationVLayout.setComponentAlignment(communicationCaptionlabel, Alignment.MIDDLE_CENTER);
		
		communication.addComponent(communicationIcon);
		communication.setExpandRatio(communicationIcon, 0.8f);
		communication.addComponent(communicationVLayout);
		communication.setExpandRatio(communicationVLayout, 2);
		communication.addStyleName("homeGridRightStyleName");
		bodyGridLayout.addComponent(communication);
		
		HorizontalLayout salesMgmt = new HorizontalLayout();
		salesMgmt.setHeight(111,Unit.PIXELS);
		salesMgmt.setWidth(375,Unit.PIXELS);
		
		Image salesMgmtIcon = new Image(null, new ThemeResource("img/SalesManagement.png"));
		salesMgmtIcon.setWidth(107,Unit.PIXELS);
		
		VerticalLayout salesMgmtVLayout = new VerticalLayout();
		salesMgmtVLayout.setSizeFull();
		salesMgmtVLayout.setSpacing(true);
		salesMgmtVLayout.setWidth(100,Unit.PERCENTAGE);
		
		salesMgmtVLayout.addStyleName("policyAdminVLayoutStyleName");
		
		Label salesMgmtlabel = new Label("Sales Management");
		salesMgmtlabel.setStyleName(ValoTheme.LABEL_H3);
		salesMgmtlabel.addStyleName("policyAdminlabelStyleName");
		
		Label salesMgmtCaptionlabel = new Label("Both new and policy informations");
		salesMgmtCaptionlabel.addStyleName("salesMgmtCaptionlabelStyleName");
		
		Label readMore = new Label("Read More", ContentMode.HTML);
		readMore.addStyleName("readMoreStyleName");
		
		salesMgmtVLayout.addComponent(salesMgmtlabel);
		salesMgmtVLayout.setComponentAlignment(salesMgmtlabel, Alignment.TOP_CENTER);
		salesMgmtVLayout.addComponent(salesMgmtCaptionlabel);
		salesMgmtVLayout.setComponentAlignment(salesMgmtCaptionlabel, Alignment.MIDDLE_CENTER);
		salesMgmtVLayout.addComponent(readMore);
		salesMgmtVLayout.setComponentAlignment(readMore, Alignment.BOTTOM_RIGHT);
		
		salesMgmt.addComponent(salesMgmtIcon);
		salesMgmt.setExpandRatio(salesMgmtIcon, 0.8f);
		salesMgmt.addComponent(salesMgmtVLayout);
		salesMgmt.setExpandRatio(salesMgmtVLayout, 2);
		salesMgmt.addStyleName("homeGridRightStyleName");
		bodyGridLayout.addComponent(salesMgmt);
		
		//Footer powered by image
		footerLayout = new HorizontalLayout();
		footerLayout.setSizeUndefined();
		footerLayout.setWidth(100.0f, Unit.PERCENTAGE);
		//footerLayout.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		Image footerImage = new Image(null, new ThemeResource("img/Powered by.png"));
		footerLayout.addComponent(footerImage);
		footerLayout.setComponentAlignment(footerImage, Alignment.TOP_RIGHT);
		footerLayout.addStyleName("homeFooterLayoutStyleName");
		
		homeViewLayout.addComponent(topHorizontalLayout);
		homeViewLayout.addComponent(bodyGridLayout);
		homeViewLayout.setComponentAlignment(bodyGridLayout, Alignment.MIDDLE_CENTER);
		homeViewLayout.addComponent(footerLayout);
		addComponent(homeViewLayout);
	}
	
	public VerticalLayout buildScreen(User user,String ViewName) {
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeFull();
		final Panel viewContainer = new Panel();
		viewContainer.setSizeFull();
		Navigator navigator = new Navigator(this.getUI(), viewContainer);
		navigator.addProvider(viewProvider);
		if (ViewName.equals(SLBrokingConstants.POLICY_ADMIN)) {
			navigator.addViewChangeListener(new ViewChangeListener() {

				@Override
				public boolean beforeViewChange(final ViewChangeEvent event) {
					// Since there's no conditions in switching between the
					// views
					// we can always return true.
					return true;
				}

				@Override
				public void afterViewChange(final ViewChangeEvent event) {
					PolicyAdminNavigationViewList view = PolicyAdminNavigationViewList.getByViewName(event.getViewName());
					BrokingEventBus.post(new BrokingEvents.PostViewChangeEvent(view),uiEventBus);

				}
			});
			navigator.navigateTo(NewPolicyView.VIEW_NAME);
		} 
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSizeUndefined();
		footer.setWidth(100.0f, Unit.PERCENTAGE);
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		Image slImage = new Image(null, new ThemeResource("img/Powered by.png"));
		footer.addComponent(slImage);
		footer.setComponentAlignment(slImage, Alignment.TOP_RIGHT);


		verticalLayout.addComponent(viewContainer);
		verticalLayout.addComponent(footer);
		verticalLayout.setExpandRatio(viewContainer, 1);
		return verticalLayout;
	}

	public GridLayout mainGridLayout() {
		GridLayout mainLayout = new GridLayout(2,1);
		mainLayout.setMargin(false);
		mainLayout.setSpacing(false);
		mainLayout.setSizeFull();
		return mainLayout;
	}
	public Panel mainPanel(){
		Panel mainContent = new Panel();
		mainContent.setSizeFull();
		mainContent.addStyleName(ValoTheme.PANEL_BORDERLESS);
		return mainContent;
	}
	
	public void updateMenu(User user,String viewName, GridLayout mainLayout) {
		if (viewName.equals(SLBrokingConstants.POLICY_ADMIN)) {
			brokingMenu.rebuildLogo(user);
			mainLayout.addComponent(brokingMenu);
		}
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
