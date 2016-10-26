package com.telosws.broking.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telosws.broking.events.BrokingEventBus;
import com.telosws.broking.events.BrokingEvents;
import com.telosws.broking.ui.menu.PolicyAdminMenu;
import com.telosws.broking.ui.util.BrokingHomeNavigationViewList;
import com.telosws.broking.util.Encryptor;
import com.telosws.broking.util.GenericResponse;
import com.telosws.broking.util.GetProperties;
import com.telosws.broking.util.Utilites;
import com.telosws.orm.tables.pojos.Onlineusers;
import com.telosws.orm.tables.pojos.User;
import com.vaadin.event.LayoutEvents;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * 
 * @author Harish Kalepu
 *
 */

@UIScope
@SpringComponent
@SuppressWarnings("serial")
public class LoginView extends VerticalLayout {

	final Logger logger = LoggerFactory.getLogger(LoginView.class);
	
	static User user;

	private String USERNAME_COOKIE = "SL_USER";
	
	private String PASSWORD_COOKIE = "SL_PWD";
	
	Boolean showLogin;

	List<String> onlineUsers;
	
	@Autowired
	private SpringViewProvider viewProvider;
	
	@Autowired
	EventBus.UIEventBus uiEventBus;
	
	@Autowired
	private PolicyAdminMenu brokingMenu;

	@Autowired
	private GetProperties getProperties;

	private RestTemplate restTemplate = new RestTemplate();

	private String errorMessage = null;
	Label errorLabel;
	HorizontalLayout errorMessageLayout;
	private TextField username=null;
	private PasswordField password=null;
	private VerticalLayout loginPanel = null; 

	public LoginView() {
		setSizeFull();
	}

	@PostConstruct
	private void init() {

		//If user session exists , we use that session and  redirect user to home page instead of login
		if (VaadinSession.getCurrent().getAttribute("user") == null) {

			Component loginForm = buildLoginForm();
			addComponent(loginForm);
			setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
			setStyleName("backgroundimage");
			setSizeFull();
		}

	}

	private Component buildLoginForm() {
		loginPanel = new VerticalLayout();
		loginPanel.setSizeUndefined();
		loginPanel.setSpacing(true);
		Responsive.makeResponsive(loginPanel);
		loginPanel.addStyleName("login-panel");

		errorLabel = new Label(errorMessage);
		errorLabel.addStyleName("errormessage");
		errorLabel.setVisible(false);

		loginPanel.addComponent(buildLabels());

		CheckBox rememberMe = new CheckBox("Remember me", false);
		loginPanel.addComponent(buildFields(rememberMe));

		errorMessageLayout = new HorizontalLayout();
		errorMessageLayout.setSizeFull();
		errorMessageLayout.setSpacing(true);
		errorMessageLayout.addComponent(errorLabel);
		errorMessageLayout.setVisible(false);
		loginPanel.addComponent(errorMessageLayout);

		HorizontalLayout optionsLayout = new HorizontalLayout();
		optionsLayout.setSizeFull();
		optionsLayout.setSpacing(true);
		loginPanel.addComponent(optionsLayout);

		optionsLayout.addComponent(rememberMe);
		optionsLayout.setComponentAlignment(rememberMe, Alignment.MIDDLE_LEFT);

		Label registerHere = new Label("REGISTER");
		registerHere.addStyleName(ValoTheme.LABEL_H4);
		registerHere.addStyleName(ValoTheme.LABEL_COLORED);
		registerHere.addStyleName("foobar");
		registerHere.addStyleName("foobar-label");

		Label forgotPassword = new Label("Forgot Password?");
		forgotPassword.addStyleName(ValoTheme.LABEL_H4);
		forgotPassword.addStyleName(ValoTheme.LABEL_COLORED);
		forgotPassword.addStyleName("foobar");

		HorizontalLayout myHorizontalLayout = new HorizontalLayout();
		myHorizontalLayout.setMargin(new MarginInfo(false, false, false, true));
		myHorizontalLayout.addComponent(registerHere);
		myHorizontalLayout.addLayoutClickListener((LayoutEvents.LayoutClickListener) event -> {
			try {
				//UI.getCurrent().getSession().setAttribute("onlineUsers", onlineUsers);
				//RegistrationWindow.open(getProperties.getBaseUrl());
			} catch (Exception e) {
				logger.error("Exception occured when retriving domains", e);
			}
		});

		HorizontalLayout forgotPasswordLayout = new HorizontalLayout();
		forgotPasswordLayout.setMargin(new MarginInfo(false, false, false, false));
		forgotPasswordLayout.addComponent(forgotPassword);
		forgotPasswordLayout.addLayoutClickListener((LayoutEvents.LayoutClickListener) event -> {
			try {
				//ForgotPasswordWindow.open(getProperties.getBaseUrl());
			} catch (Exception e) {
				logger.error("Exception occured when geting forgot password", e);
			}
		});

		optionsLayout.addComponents(myHorizontalLayout);
		optionsLayout.addComponents(forgotPasswordLayout);
		return loginPanel;
	}

	private Cookie getCookieByName(String name) {
		// Fetch all cookies from the request
		Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();

		// Iterate to find cookie by its name
		for (Cookie cookie : cookies) {
			if (name.equals(cookie.getName())) {
				return cookie;
			}
		}

		return null;
	}

	private Component buildFields(CheckBox rememberMe) {
		HorizontalLayout fields = new HorizontalLayout();
		fields.setSpacing(true);
		fields.addStyleName("fields");

		username = new TextField("Username");
		username.setInputPrompt("Email address");
		username.setIcon(FontAwesome.USER);
		username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		username.addStyleName("login-text");

		final Cookie usernameCookie;
		if (getCookieByName(USERNAME_COOKIE) == null) {
			usernameCookie = new Cookie(USERNAME_COOKIE,StringUtils.EMPTY);
			usernameCookie.setMaxAge(2592000);
			usernameCookie.setPath("/");
			VaadinService.getCurrentResponse().addCookie(usernameCookie);
		} else {
			usernameCookie = getCookieByName(USERNAME_COOKIE);
			username.setValue(Encryptor.decrypt(null, null,usernameCookie.getValue()));
		}

		password = new PasswordField("Password");
		password.setIcon(FontAwesome.LOCK);
		password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		password.addStyleName("login-text");

		final Cookie passwordCookie;
		if (getCookieByName(PASSWORD_COOKIE) == null) {
     		passwordCookie = new Cookie(PASSWORD_COOKIE, StringUtils.EMPTY);
			passwordCookie.setMaxAge(2592000);
			passwordCookie.setPath("/");
			VaadinService.getCurrentResponse().addCookie(passwordCookie);
		} else {
			passwordCookie = getCookieByName(PASSWORD_COOKIE);
			password.setValue(Encryptor.decrypt(null, null,passwordCookie.getValue()));
		}

		// Set remember me check box
		if (!(StringUtils.isBlank(usernameCookie.getValue()) || StringUtils.isBlank(passwordCookie.getValue()))) {
			rememberMe.setValue(Boolean.TRUE);
		}

		final Button signin = new Button("Sign In", (e) -> {
			try {
				boolean validSubmit = true;
				if (username.getValue() == null || username.getValue().trim().length() == 0) {
					username.setRequired(true);
					username.setRequiredError(username.getCaption() + " is required for login.");
					validSubmit = false;
				}
				if (password.getValue() == null || password.getValue().trim().length() == 0) {
					password.setRequired(true);
					password.setRequiredError(password.getCaption() + " is required for login.");
					validSubmit = false;
				}
				onlineUsers = getOnlineUsers();
				if(onlineUsers != null) {
					for(String useremail : onlineUsers){
						if(useremail.equals(username.getValue())){
							validSubmit = false;
							errorMessage = "Your loggedin in another system please signout.";
							setErrorMessage();
							return;
						}
						
					}
				}
				if (validSubmit) {
					if (rememberMe.getValue()) {

						usernameCookie.setValue(Encryptor.encrypt(null, null, username.getValue()));
						usernameCookie.setMaxAge(2592000);
						usernameCookie.setPath("/");
						passwordCookie.setValue(Encryptor.encrypt(null, null, password.getValue()));
						passwordCookie.setMaxAge(2592000);
						passwordCookie.setPath("/");
						VaadinService.getCurrentResponse().addCookie(usernameCookie);
						VaadinService.getCurrentResponse().addCookie(passwordCookie);

					}
					else
					{
						usernameCookie.setMaxAge(0);
						usernameCookie.setPath("/");
						VaadinService.getCurrentResponse().addCookie(usernameCookie);
						passwordCookie.setMaxAge(0);
						passwordCookie.setPath("/");
						VaadinService.getCurrentResponse().addCookie(passwordCookie);
					}
					authenticate(username.getValue(), password.getValue());
				}
			} catch (Exception e1) {
				logger.debug(e1.getMessage());
				errorMessage = "Invalid username or password";
				setErrorMessage();

			}
		});

		signin.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		signin.setClickShortcut(KeyCode.ENTER);
		signin.focus();

		fields.addComponents(username, password, signin);
		fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

		return fields;
	}

	private void authenticate(String username, String password) throws Exception {

		VerticalLayout mainLayout = mainVerticalLayout();
		Panel mainContent = mainPanel();

		GenericResponse genericResponse = authenticateUser(username, password);
		if ("success".equals(genericResponse.getStatus())
				&& "Authentication Successful".equals(genericResponse.getMsg())) {
			User newUser = Utilites.getObject(genericResponse, User.class);
			user = newUser;
			VaadinSession.getCurrent().setAttribute("user", newUser);
			mainContent.setContent(buildScreen(user));
			//BrokingEventBus.post(new BrokingEvents.UserUpdateEvent(user),uiEventBus);
		}
		/*else if ("success".equals(genericResponse.getStatus()) && "Application Completed".equals(genericResponse.getMsg())) {*/
			/*User newUser = Utilites.getObject(genericResponse, User.class);
			user = newUser;
			VaadinSession.getCurrent().setAttribute("user", newUser);
			//UI.getCurrent().getSession().setAttribute("user", newUser);
			EmployeeListResponse response = getEmployeeDetails();
			if (response.getEmployeeList() != null) {
				Employee entity = response.getEmployeeList().get(0);
				Employee createEmployeeRequest = new Employee();
				createEmployeeRequest.setUserId(entity.getUserId());
				createEmployeeRequest.setDateOfBirth(entity.getDateOfBirth());
			  //createEmployeeRequest.setEmail(entity.getEmail());
				createEmployeeRequest.setDateOfHire(entity.getDateOfHire());
				createEmployeeRequest.setEmployeeBand(entity.getEmployeeBand());
				createEmployeeRequest.setEmployeeCode(entity.getEmployeeCode());
				createEmployeeRequest.setId(entity.getId());
				createEmployeeRequest.setFullName(entity.getFullName());
				createEmployeeRequest.setGender(entity.getGender());
				createEmployeeRequest.setMaritalStatus(entity.getMaritalStatus());
				createEmployeeRequest.setMarriageDate(entity.getMarriageDate());
				createEmployeeRequest.setMobileNumber(entity.getMobileNumber());
				UI.getCurrent().getSession().setAttribute("employee", createEmployeeRequest);
				int employeeId = response.getEmployeeList().get(0).getId();
				DependentsListResponse dependentsListResponse = getDependentDetails(employeeId);
				if (dependentsListResponse.getDependsList() != null) {
					List<Dependents> dependents = new ArrayList<Dependents>();
					dependents.addAll(dependentsListResponse.getDependsList().stream().collect(Collectors.toList()));
					UI.getCurrent().getSession().setAttribute("dependents", dependents);
					SafetyLeafEventBus.post(new EventsList.UserUpdateEvent(user),uiEventBus);
					mainContent.setContent(buildScreen(user));
				}
			}*/
		else {
			errorMessage = genericResponse.getMsg();
			setErrorMessage();
			return;
		}

		//updateMenu(user, mainLayout);
		mainLayout.addComponent(mainContent);
		//mainLayout.setColumnExpandRatio(1,1);
		(UI.getCurrent()).setContent(mainLayout);
	}

	private GenericResponse authenticateUser(String username, String password) throws Exception {
		User user = new User();
		user.setPrimaryEmail(username);
		user.setPassword(password);
		GenericResponse result = restTemplate.postForObject(getProperties.getBaseUrl() + "/authenticate", user,
				GenericResponse.class);
		return result;
	}

	private Component buildLabels() {
		CssLayout labels = new CssLayout();
		labels.addStyleName("labels");

		Label welcome = new Label("Welcome");
		welcome.setSizeUndefined();
		welcome.addStyleName(ValoTheme.LABEL_H4);
		welcome.addStyleName(ValoTheme.LABEL_COLORED);
		labels.addComponent(welcome);

		Label title = new Label("TelosBroking Portal");
		title.setSizeUndefined();
		title.addStyleName(ValoTheme.LABEL_H3);
		title.addStyleName(ValoTheme.LABEL_LIGHT);
		/*Resource res = new ThemeResource("img/logo.png");

		Image image = new Image(null, res);
		image.setSizeUndefined();
		image.setHeight("60px");*/
		labels.addComponent(title);
		return labels;
	}

	public VerticalLayout buildScreen(User user) {
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeFull();
		final Panel viewContainer = new Panel();
		viewContainer.setSizeFull();
		Navigator navigator = new Navigator(this.getUI(), viewContainer);
		navigator.addProvider(viewProvider);
		/*if (UserRoles.HUMANRESOURCES.getUserRole().equals(user.getRole())) {
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
					HRViewList view = HRViewList.getByView(event.getViewName());
					SafetyLeafEventBus.post(new EventsList.ViewChangeEvent(view),uiEventBus);

				}
			});
			navigator.navigateTo(DashboardView.VIEW_NAME);
		} else {*/
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
					BrokingHomeNavigationViewList view = BrokingHomeNavigationViewList.getByViewName(event.getViewName());
					BrokingEventBus.post(new BrokingEvents.ViewChangeEvent(view),uiEventBus);

				}
			});
			/*if (LookupUtil.UserStatus.SUBMITTED.getUserStatus().equalsIgnoreCase(user.getStatus()) ||
					LookupUtil.UserStatus.APPROVED.getUserStatus().equalsIgnoreCase(user.getStatus())){
				employeeMenu.downloadEcardButton.setVisible(true);
				navigator.navigateTo(ReviewView.VIEW_NAME);
				SafetyLeafEventBus.post(new EventsList.PostReviewViewChangeEvent(NavigationViewList.REVIEW),uiEventBus);
			} else {
				employeeMenu.downloadEcardButton.setVisible(false);
				navigator.navigateTo(DefaultView.VIEW_NAME);
			}*/
			navigator.navigateTo(BrokingHomeView.VIEW_NAME);
		//}

		HorizontalLayout footer = new HorizontalLayout();
		footer.setSizeUndefined();
		footer.setWidth(100.0f, Unit.PERCENTAGE);
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		Image slImage = new Image(null, new ThemeResource("img/Powered by.png"));
		footer.addComponent(slImage);
		footer.setComponentAlignment(slImage, Alignment.TOP_RIGHT);


		verticalLayout.addComponent(viewContainer);
		//verticalLayout.addComponent(footer);
		verticalLayout.setExpandRatio(viewContainer, 1);
		return verticalLayout;
	}

	private void setErrorMessage() {
		errorMessageLayout.setVisible(true);
		errorLabel.setValue(errorMessage);
		errorLabel.setVisible(true);
	}

	public List<String> getOnlineUsers() {
		onlineUsers = new ArrayList<String>();
		RestTemplate restTemplate = new RestTemplate();
		try{
			List<Onlineusers> usersList = new ArrayList<>();
			ObjectMapper mapper = new ObjectMapper();
			List<Map> map = (List<Map>) restTemplate.getForObject(getProperties.getBaseUrl() + "/getAllOnlineUsers",List.class);
			Onlineusers emailLookup = new Onlineusers();
			for (Map lookMap : map) {
				emailLookup = mapper.readValue(mapper.writeValueAsString(lookMap), Onlineusers.class);
				usersList.add(emailLookup);
				onlineUsers.add(emailLookup.getPrimaryEmail());
			}
			return onlineUsers;
		}catch(Exception ex){
			logger.error("Error while getting online users", ex);
		}
		return null;
	}

	public VerticalLayout mainVerticalLayout()
	{

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		//mainLayout.setHeight(100,Unit.PERCENTAGE);
		Responsive.makeResponsive(mainLayout);
		return mainLayout;
	}

	public Panel mainPanel()
	{
		Panel mainContent = new Panel();
		//mainContent.setHeight(100,Unit.PERCENTAGE);
		mainContent.setSizeFull();
		mainContent.addStyleName(ValoTheme.PANEL_BORDERLESS);
		return mainContent;
	}

	public void updateMenu(User user, GridLayout mainLayout)
	{
		/*if (UserRoles.HUMANRESOURCES.getUserRole().equals(user.getRole())) {
			hrMenu.rebuildLogo(user);
			mainLayout.addComponent(hrMenu);
		}
		else {*/
			brokingMenu.rebuildLogo(user);
			mainLayout.addComponent(brokingMenu);
		//}
	}
    public void clearFields() {
    	if(getCookieByName(PASSWORD_COOKIE) == null && getCookieByName(USERNAME_COOKIE) == null) {
	    	errorMessageLayout.setVisible(false);
			username.clear();
			password.clear();
    	}else{
    		errorMessageLayout.setVisible(false);
    	}
    }
}
