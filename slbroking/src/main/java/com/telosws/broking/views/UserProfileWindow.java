package com.telosws.broking.views;
import com.telosws.broking.events.BrokingEvents;
import com.telosws.broking.events.BrokingEventBus;
import com.telosws.broking.util.Encryptor;
import com.telosws.broking.util.GenericResponse;
import com.telosws.orm.tables.pojos.User;
import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.vaadin.spring.events.EventBus;
import com.telosws.broking.util.Util;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rakeshkata on 3/24/16.
 */


@SuppressWarnings("serial")
public class UserProfileWindow extends Window {
    private static String BaseUrl = "";
    private EventBus.UIEventBus uiEventBus = null;
    final Logger logger = LoggerFactory.getLogger(UserProfileWindow.class);
    public static final String ID = "profilepreferenceswindow";
    private String UserOrginialEmail;
    private final BeanFieldGroup<User> fieldGroup;
    /*
     * Fields for editing the User object are defined here as class members.
     * They are later bound to a FieldGroup by calling
     * fieldGroup.bindMemberFields(this). The Fields' values don't need to be
     * explicitly set, calling fieldGroup.setItemDataSource(user) synchronizes
     * the fields with the user object.
     */
    @PropertyId("firstName")
    private TextField firstNameField;
    @PropertyId("middleName")
    private TextField middleName;
    @PropertyId("lastName")
    private TextField lastNameField;
    @PropertyId("primaryEmail")
    private TextField emailField;
    @PropertyId("primaryPhone")
    private TextField phoneField;

    @PropertyId("currentPassword")
    private PasswordField currentPassword;
    @PropertyId("newpassword")
    private PasswordField newPassword;
    @PropertyId("confirmPassword")
    private PasswordField confirmPassword;
    
    final String REQUIRED = " is required.";
    private ComboBox countryDropDown;
    private static final String[] countryCodes = new String[] { "IND", "US", "UK","UAE"};
    private User user;

    private UserProfileWindow(final User user,EventBus.UIEventBus uiScopeEventBus) {
        addStyleName("profile-window");
        setId(ID);
        Responsive.makeResponsive(this);

        setModal(true);
        setCloseShortcut(KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(false);
        setHeight(380, Unit.PIXELS);
        
        this.user = user;
        this.uiEventBus = uiScopeEventBus;

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true, false, false, false));
        setContent(content);

        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        content.addComponent(detailsWrapper);
        content.setExpandRatio(detailsWrapper, 1f);

        detailsWrapper.addComponent(buildProfileTab());
        detailsWrapper.addComponent(buildPasswordTab());


        content.addComponent(buildFooter());

        fieldGroup = new BeanFieldGroup<>(User.class);
        fieldGroup.bindMemberFields(this);
        fieldGroup.setItemDataSource(user);
        emailField.setReadOnly(true);
    }

    private Component buildPasswordTab() {
        VerticalLayout root = new VerticalLayout();
        root.setCaption("Reset Password");
        root.setIcon(FontAwesome.COGS);
        root.setSpacing(true);
        root.setMargin(true);
        root.setSizeFull();

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(details);
        root.setExpandRatio(details, 1);

        currentPassword = new PasswordField("Current Password");
        currentPassword.setRequired(Boolean.TRUE.booleanValue());
        details.addComponent(currentPassword);
        currentPassword.setWidth("100%");
        currentPassword.setNullRepresentation("");
        currentPassword.setId("currentPassword");
        currentPassword.addValidator(new PasswordValidator());

        newPassword = new PasswordField("New Password");
        newPassword.setRequired(Boolean.TRUE.booleanValue());
        details.addComponent(newPassword);
        newPassword.setWidth("100%");
        newPassword.setNullRepresentation("");
        newPassword.setId("newPassword");
        newPassword.addValidator(new PasswordValidator());

        confirmPassword = new PasswordField("Confirm Password");
        confirmPassword.setId("confirmPassword");
        confirmPassword.setRequired(Boolean.TRUE.booleanValue());
        confirmPassword.addValidator((Validator) value -> {
            if(newPassword.getValue().length() > 0 && !newPassword.getValue().equals(value))
            {
                throw new Validator.InvalidValueException("The new password and confirmation password do not match.");
            }
        });
        details.addComponent(confirmPassword);
        confirmPassword.setWidth("100%");
        confirmPassword.setNullRepresentation("");
        
        currentPassword.addFocusListener(focusListener);
        currentPassword.addBlurListener(blurListener);
        newPassword.addFocusListener(focusListener);
        newPassword.addBlurListener(blurListener);
        confirmPassword.addFocusListener(focusListener);
        confirmPassword.addBlurListener(blurListener);

        return root;
    }

    private Component buildProfileTab() {
        HorizontalLayout root = new HorizontalLayout();
        root.setCaption("Profile");
        root.setIcon(FontAwesome.USER);
        root.setSpacing(true);
        root.setMargin(true);
        root.setSizeFull();


        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(details);
        root.setExpandRatio(details, 1);

        UserOrginialEmail = user.getPrimaryEmail();
        firstNameField = new TextField("First Name");
        firstNameField.setId("firstNameField");
        firstNameField.addValidator(new StringLengthValidator("The name must be 1-20 letters (was {0})",1, 20, false));
        firstNameField.addValidator((Validator) value -> {
            if(value == null || StringUtils.isBlank(value.toString()) || !value.toString().matches( "[a-zA-z]+([ '-][a-zA-Z]+)*" ))
            {
                throw new Validator.InvalidValueException("Please enter a valid First Name.");
            }
        });
        firstNameField.setRequired(Boolean.TRUE.booleanValue());
        firstNameField.setWidth("100%");
        firstNameField.setNullRepresentation("");
        details.addComponent(firstNameField);
        
        middleName = new TextField("Middle Name");
        middleName.setInputPrompt("Middle Name");
        middleName.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				if(value != null){
					if(!value.equals("") && value != null && (StringUtils.isBlank(value.toString()) || !value.toString().matches( "[a-zA-z]+([ '-][a-zA-Z]+)*" ))) {
						throw new InvalidValueException("Please enter a valid Middle Name.");	
					}
				}
			}
		});
        middleName.setWidth("100%");
        middleName.setNullRepresentation("");
        middleName.setVisible(true);
        details.addComponent(middleName);

        lastNameField = new TextField("Last Name");
        lastNameField.setId("lastNameField");
        lastNameField.addValidator(new StringLengthValidator("The name must be 1-20 letters (was {0})",1, 20, false));
        lastNameField.addValidator((Validator) value -> {
            if(value == null || StringUtils.isBlank(value.toString()) || !value.toString().matches( "[a-zA-z]+([ '-][a-zA-Z]+)*" ))
            {
                throw new Validator.InvalidValueException("Please enter a valid Sur Name.");
            }
        });
        lastNameField.setRequired(Boolean.TRUE.booleanValue());
        lastNameField.setWidth("100%");
        lastNameField.setNullRepresentation("");
        details.addComponent(lastNameField);

        Label section = new Label("Contact Info");
        section.addStyleName(ValoTheme.LABEL_H4);
        section.addStyleName(ValoTheme.LABEL_COLORED);
        details.addComponent(section);

        emailField = new TextField("Email");
        emailField.setReadOnly(true);
        emailField.setId("emailField");
        emailField.addStyleName("readOnly");
        emailField.setDescription("Read Only.");
        emailField.setWidth("100%");
        emailField.setRequired(Boolean.TRUE.booleanValue());
        emailField.setNullRepresentation("");
        emailField.setValidationVisible(true);
        emailField.addValidator((Validator) value -> {
            Pattern pattern;
            Matcher matcher;
            if(value instanceof String)
            {
                String email= (String)value;
                String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

                pattern = Pattern.compile(emailPattern);
                matcher = pattern.matcher(email);


                if (!matcher.matches()) {
                    throw new Validator.InvalidValueException("Not a Valid Email");
                }
            }


        });
        details.addComponent(emailField);

        Label phoneNumberLabel = new Label("Mobile <span style='color:#FF7A85;font-weight:bold;'>*</span>",ContentMode.HTML);
        phoneNumberLabel.addStyleName("phoneNumberLabelStyle");
        
        
        phoneField = new TextField();
        phoneField.setInputPrompt("Mobile number");
        phoneField.setId("phoneField");
        phoneField.setWidth("100%");
        phoneField.setNullRepresentation("");
        phoneField.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        
        
        phoneField.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				if(value != null){
					String phoneNumber = value.toString();
					if(phoneNumber.startsWith("+91"))
						phoneNumber = phoneNumber.substring(phoneNumber.indexOf("1")+1,phoneNumber.length());
					if(phoneNumber.startsWith("+1"))
						phoneNumber = phoneNumber.substring(phoneNumber.indexOf("1")+1,phoneNumber.length());
					if(phoneNumber.startsWith("+44"))
						phoneNumber = phoneNumber.substring(phoneNumber.indexOf("4")+2,phoneNumber.length());
					if(phoneNumber.startsWith("+971"))
						phoneNumber = phoneNumber.substring(phoneNumber.indexOf("1")+1,phoneNumber.length());
					value = phoneNumber;
				}
					
				if(!(value == null || StringUtils.isBlank( value.toString() ) ) && !value.toString().matches("\\+?\\d{10}"))
				{
					throw new InvalidValueException("Please enter a valid Phone number.");
				}
			}
		});
        
        countryDropDown = new ComboBox();
        countryDropDown.setInputPrompt("Country");
        countryDropDown.setWidth(114,Unit.PIXELS);
        for (int i = 0; i < countryCodes.length; i++) {
        	countryDropDown.addItem(countryCodes[i]);
        }

        countryDropDown.setNullSelectionAllowed(false);
        countryDropDown.setImmediate(true);
        countryDropDown.addStyleName(ValoTheme.COMBOBOX_BORDERLESS);
        countryDropDown.setStyleName("regcountryDropDownStyle");
        countryDropDown.setStyleName("regPhoneFieldStyle");
        countryDropDown.addListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = -5188369735622627751L;

            public void valueChange(ValueChangeEvent event) {
            	String value = (String) countryDropDown.getValue();
                if (value != null) {
                	if(value.equals("IND"))
                		phoneField.setValue("+91");
                	else if(value.equals("US"))
                		phoneField.setValue("+1");
                	else if(value.equals("UK"))
                		phoneField.setValue("+44");
                	else if(value.equals("UAE"))
                		phoneField.setValue("+971");
                }
            }
        });
        User user = user=(User) VaadinSession.getCurrent().getAttribute("user");
        if(user.getPrimaryPhone() != null) {
	        if(user.getPrimaryPhone().startsWith("+91"))
	        	countryDropDown.setValue(countryCodes[0]);
	        if(user.getPrimaryPhone().startsWith("+1"))
	        	countryDropDown.setValue(countryCodes[1]);
	        if(user.getPrimaryPhone().startsWith("+44"))
	        	countryDropDown.setValue(countryCodes[2]);
	        if(user.getPrimaryPhone().startsWith("+971"))
	        	countryDropDown.setValue(countryCodes[3]);
        }
        HorizontalLayout phoneLayout = new HorizontalLayout();
        phoneLayout.addComponent(phoneNumberLabel);
        phoneLayout.addComponent(countryDropDown);
        phoneLayout.addComponent(phoneField);
        phoneLayout.addStyleName("regPhoneLayoutStyle");
        details.addComponent(phoneLayout);

        
        firstNameField.addFocusListener(focusListener);
        firstNameField.addBlurListener(blurListener);
        lastNameField.addFocusListener(focusListener);
        lastNameField.addBlurListener(blurListener);
        emailField.addFocusListener(focusListener);
        emailField.addBlurListener(blurListener);
        phoneField.addFocusListener(focusListener);
        phoneField.addBlurListener(blurListener);

        return root;
    }
    
    FocusListener focusListener = new FocusListener() { 
    	@Override
    	public void focus(FocusEvent event) {
    		if(event.getComponent().getId().equals("firstNameField"))
    			Page.getCurrent().getJavaScript().execute("document.getElementById('"+event.getComponent().getId()+"').style.color = '#474747';");
    		else if(event.getComponent().getId().equals("lastNameField"))
    			Page.getCurrent().getJavaScript().execute("document.getElementById('"+event.getComponent().getId()+"').style.color = '#474747';");
    		else if(event.getComponent().getId().equals("currentPassword"))
    			Page.getCurrent().getJavaScript().execute("document.getElementById('"+event.getComponent().getId()+"').style.color = '#474747';");
    		else if(event.getComponent().getId().equals("newPassword"))
    			Page.getCurrent().getJavaScript().execute("document.getElementById('"+event.getComponent().getId()+"').style.color = '#474747';");
    		else if(event.getComponent().getId().equals("confirmPassword"))
    			Page.getCurrent().getJavaScript().execute("document.getElementById('"+event.getComponent().getId()+"').style.color = '#474747';");
    		else if(event.getComponent().getId().equals("phoneField"))
    			Page.getCurrent().getJavaScript().execute("document.getElementById('"+event.getComponent().getId()+"').style.color = '#474747';");
    		else if(event.getComponent().getId().equals("emailField"))
    			Page.getCurrent().getJavaScript().execute("document.getElementById('"+event.getComponent().getId()+"').style.color = '#474747';");
    	}
    };
    
    BlurListener blurListener = new BlurListener(){
    	@Override
    	public void blur(BlurEvent event) {
    		if(event.getComponent().getId().equals("firstNameField") && (firstNameField.getValue()==null || firstNameField.getValue().equals("")))
    			Page.getCurrent().getJavaScript().execute("document.getElementById('"+event.getComponent().getId()+"').style.color = '#FF7A85';");
    		else if(event.getComponent().getId().equals("lastNameField") && (lastNameField.getValue()==null || lastNameField.getValue().equals("")))
    			Page.getCurrent().getJavaScript().execute("document.getElementById('"+event.getComponent().getId()+"').style.color = '#FF7A85';");
    		else if(event.getComponent().getId().equals("currentPassword") && (currentPassword.getValue()==null || currentPassword.getValue().equals("")))
    			Page.getCurrent().getJavaScript().execute("document.getElementById('"+event.getComponent().getId()+"').style.color = 'red';");
    		else if(event.getComponent().getId().equals("newPassword") && (newPassword.getValue()==null || newPassword.getValue().equals("")))
    			Page.getCurrent().getJavaScript().execute("document.getElementById('"+event.getComponent().getId()+"').style.color = 'red';");
    		else if(event.getComponent().getId().equals("confirmPassword") && (confirmPassword.getValue()==null || confirmPassword.getValue().equals("")))
    			Page.getCurrent().getJavaScript().execute("document.getElementById('"+event.getComponent().getId()+"').style.color = 'red';");
    		else if(event.getComponent().getId().equals("emailField") && (emailField.getValue()==null || emailField.getValue().equals("")))
    			Page.getCurrent().getJavaScript().execute("document.getElementById('"+event.getComponent().getId()+"').style.color = '#FF7A85';");
    		else if(event.getComponent().getId().equals("phoneField") && phoneField.getInputPrompt().contains(REQUIRED) && phoneField.getValue()==null )
    			Page.getCurrent().getJavaScript().execute("document.getElementById('"+event.getComponent().getId()+"').style.color = 'red';");
    	}
    };

    private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button cancel = new Button("Cancel");
        cancel.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        cancel.addClickListener((ClickListener) event -> close());
        footer.addComponent(cancel);
        footer.setComponentAlignment(cancel, Alignment.TOP_LEFT);

        Button submit = new Button("Submit");
        submit.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        submit.addClickListener((ClickListener) event -> {
            try {
            	ArrayList<Component> validationList= new ArrayList<>();
            	
            	if (!firstNameField.isValid())
                {
            		firstNameField.setInputPrompt(firstNameField.getCaption()+REQUIRED);
                	Page.getCurrent().getJavaScript().execute("document.getElementById('firstNameField').style.color = '#FF7A85';");
                    validationList.add(firstNameField);
                }
                if(!lastNameField.isValid())
                {
                	lastNameField.setInputPrompt(lastNameField.getCaption()+REQUIRED);
                	Page.getCurrent().getJavaScript().execute("document.getElementById('lastNameField').style.color = '#FF7A85';");
                    validationList.add(lastNameField);
                }
                if(!newPassword.getValue().equals("")) {
                	if (!currentPassword.isValid()) {
                		Page.getCurrent().getJavaScript().execute("document.getElementById('currentPassword').placeholder = '"+currentPassword.getCaption()+REQUIRED+"';");
                		Page.getCurrent().getJavaScript().execute("document.getElementById('currentPassword').style.color = 'red';");
                		validationList.add(currentPassword);
                	}
                	if(!confirmPassword.isValid())
                    {
                    	Page.getCurrent().getJavaScript().execute("document.getElementById('confirmPassword').placeholder = '"+confirmPassword.getCaption()+REQUIRED+"';");
                    	Page.getCurrent().getJavaScript().execute("document.getElementById('confirmPassword').style.color = 'red';");
                    	validationList.add(confirmPassword);
                    }
                }
                
                if(!currentPassword.getValue().equals("")) {
                	if(!confirmPassword.isValid())
                    {
                    	Page.getCurrent().getJavaScript().execute("document.getElementById('confirmPassword').placeholder = '"+confirmPassword.getCaption()+REQUIRED+"';");
                    	Page.getCurrent().getJavaScript().execute("document.getElementById('confirmPassword').style.color = 'red';");
                    	validationList.add(confirmPassword);
                    }
                	if(!newPassword.isValid())
                    {
                    	Page.getCurrent().getJavaScript().execute("document.getElementById('newPassword').placeholder = '"+newPassword.getCaption()+REQUIRED+"';");
                    	Page.getCurrent().getJavaScript().execute("document.getElementById('newPassword').style.color = 'red';");
                    	validationList.add(newPassword);
                    }
                }
                
                if(!confirmPassword.getValue().equals("")) {
                	if(!newPassword.isValid())
                    {
                    	Page.getCurrent().getJavaScript().execute("document.getElementById('newPassword').placeholder = '"+newPassword.getCaption()+REQUIRED+"';");
                    	Page.getCurrent().getJavaScript().execute("document.getElementById('newPassword').style.color = 'red';");
                    	validationList.add(newPassword);
                    }
                	if (!currentPassword.isValid()) {
                		Page.getCurrent().getJavaScript().execute("document.getElementById('currentPassword').placeholder = '"+currentPassword.getCaption()+REQUIRED+"';");
                		Page.getCurrent().getJavaScript().execute("document.getElementById('currentPassword').style.color = 'red';");
                		validationList.add(currentPassword);
                	}
                }
                
                if(!newPassword.getValue().equals("") && !confirmPassword.getValue().equals("")) {
                	if (!currentPassword.isValid()) {
                		Page.getCurrent().getJavaScript().execute("document.getElementById('currentPassword').placeholder = '"+currentPassword.getCaption()+REQUIRED+"';");
                		Page.getCurrent().getJavaScript().execute("document.getElementById('currentPassword').style.color = 'red';");
                		validationList.add(currentPassword);
                	}
                }
                if(!currentPassword.getValue().equals("") && !newPassword.getValue().equals("")) {
                	if(!confirmPassword.isValid())
                    {
                    	Page.getCurrent().getJavaScript().execute("document.getElementById('confirmPassword').placeholder = '"+confirmPassword.getCaption()+REQUIRED+"';");
                    	Page.getCurrent().getJavaScript().execute("document.getElementById('confirmPassword').style.color = 'red';");
                    	validationList.add(confirmPassword);
                    }
                }
                if(!currentPassword.getValue().equals("") && !confirmPassword.getValue().equals("")) {
                	if(!newPassword.isValid())
                    {
                    	Page.getCurrent().getJavaScript().execute("document.getElementById('newPassword').placeholder = '"+newPassword.getCaption()+REQUIRED+"';");
                    	Page.getCurrent().getJavaScript().execute("document.getElementById('newPassword').style.color = 'red';");
                    	validationList.add(newPassword);
                    }
                }
				if (currentPassword.getValue() != null && newPassword.getValue() != null) {
					if (!currentPassword.getValue().equals("") && !newPassword.getValue().equals("")) {
						if (currentPassword.getValue().equals(newPassword.getValue())) {
							validationList.add(newPassword);
							validationList.add(currentPassword);
							Util.errorNotification("Current Password and New Password should not be same.");
						}
					}
				}
                if (!emailField.isValid())
                {
                	emailField.setInputPrompt(emailField.getCaption()+REQUIRED);
                	Page.getCurrent().getJavaScript().execute("document.getElementById('emailField').style.color = '#FF7A85';");
                    validationList.add(emailField);
                }
                if (phoneField.getValue()==null || phoneField.getValue().equals(""))
                {
                	phoneField.setInputPrompt("Phone "+REQUIRED);
                	Page.getCurrent().getJavaScript().execute("document.getElementById('phoneField').style.color = 'red';");
                    validationList.add(phoneField);
                }
                if (phoneField.getValue() != null && !phoneField.getValue().equals("") && countryDropDown.getValue()==null)
                {
                    validationList.add(countryDropDown);
                    Util.errorNotification("Country code is required.");
                }
                if (!phoneField.isValid())
                {
                    validationList.add(phoneField);
                }
                if(phoneField != null) {
                	phoneField.addValidator(new Validator() {
            			@Override
            			public void validate(Object value) throws InvalidValueException {
		                    if(phoneField.getValue().equals("+91") || phoneField.getValue().equals("+1") || phoneField.getValue().equals("+44") || phoneField.getValue().equals("+971")) {
		        				throw new InvalidValueException("Please enter a valid Phone number.");
		                    }
            			}
                	});
                }
                if(validationList.isEmpty()) {

	                fieldGroup.commit();
	                if(!newPassword.getValue().equals("") && !currentPassword.getValue().equals("") && !confirmPassword.getValue().equals("")) {
	                    if (!currentPassword.getValue().equals(Encryptor.decrypt(null, null, user.getPassword()))) {
	                        Util.errorNotification("Current Password entered is not correct. Please enter it again");
	                        return;
	                    }
	                }
	                if(!newPassword.getValue().equals("") && !currentPassword.getValue().equals("") && !confirmPassword.getValue().equals(""))
	                user.setPassword(Encryptor.encrypt(null, null, newPassword.getValue()));
	                user.setFirstName(firstNameField.getValue());
	                user.setMiddleName(middleName.getValue());
	                user.setLastName(lastNameField.getValue());
	                if(!emailField.getValue().equals(UserOrginialEmail))
	                {
	                    GenericResponse userResponse = userByEmail(emailField.getValue());
	                    if(userResponse.getStatus().contains("success")){
	                        Util.errorNotification("Email address entered already exists in the system");
	                        return;
	                    }
	                }
	                user.setPrimaryEmail(emailField.getValue());
	                user.setPrimaryPhone(phoneField.getValue());
	                GenericResponse response = updateUser(user);
	                if (response.getStatus().contains("success")) {
	
	                    Util.successNotification("Profile updated successfully");
	                    BrokingEventBus.post(new BrokingEvents.UserUpdateEvent(user),uiEventBus);
	                } else {
	                    Util.errorNotification("Error while updating user profile.");
	                    return;
	                }
	                close();
                }
            } catch (Exception e) {
            	logger.error("Exception occured while updating profile.");
                //Util.errorNotification("Something went wrong, please contact customer service.");
                return;
            }
        });
        submit.focus();
        footer.addComponent(submit);
        footer.setComponentAlignment(submit, Alignment.TOP_RIGHT);
        return footer;
    }

    public static void open(String baseUrl, final User newUser, EventBus.UIEventBus uiScopeEventBus) {
        BaseUrl = baseUrl;
        //uiEventBus = uiScopeEventBus;
        //user = newUser;
        Window w = new UserProfileWindow(newUser,uiScopeEventBus);
        UI.getCurrent().addWindow(w);
        w.focus();
    }

    private GenericResponse updateUser(User user) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        GenericResponse result = restTemplate.postForObject(BaseUrl + "/updateUser", user, GenericResponse.class);
        return result;
    }

    private GenericResponse userByEmail(String emailAddress) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        GenericResponse result = restTemplate.getForObject(BaseUrl + "/userByEmail?emailAddress=" + emailAddress, GenericResponse.class);
        return result;
    }

    // Validator for validating the passwords
    private static final class PasswordValidator extends
            AbstractValidator<String> {

        public PasswordValidator() {
            super("Password must be 6 characters long including one uppercase letter, one lower case letter, special characters(!@#$%^&*()_+=?~-) and numbers.");
        }

        @Override
        protected boolean isValidValue(String value) {
            //
            // Password must be at least 6 characters long and contain at least
            // one number
            //
            if (value != null
                    && (value.length() < 6 || !value.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=?~-])(?=\\S+$).{6,20}$"))) {
                return false;
            }
            return true;
        }

        @Override
        public Class<String> getType() {
            return String.class;
        }
    }
}
