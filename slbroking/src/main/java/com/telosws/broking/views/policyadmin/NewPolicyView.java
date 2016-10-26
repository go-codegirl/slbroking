package com.telosws.broking.views.policyadmin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telosws.broking.beans.PersonalDetailsBean;
import com.telosws.broking.beans.PolicyApplication;
import com.telosws.broking.constants.SLBrokingConstants;
import com.telosws.broking.events.BrokingEvents;
import com.telosws.broking.util.GenericResponse;
import com.telosws.broking.util.GetProperties;
import com.telosws.broking.util.Util;
import com.telosws.broking.util.Utilites;
import com.telosws.orm.tables.pojos.Address;
import com.telosws.orm.tables.pojos.AmountDetails;
import com.telosws.orm.tables.pojos.InsuranceComapnyDetails;
import com.telosws.orm.tables.pojos.PersonalDetails;
import com.telosws.orm.tables.pojos.PolicyDetails;
import com.telosws.orm.tables.pojos.PolicyDocuments;
import com.telosws.orm.tables.pojos.PolicyType;
import com.telosws.orm.tables.pojos.User;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.event.MouseEvents;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;
import com.zybnet.autocomplete.server.AutocompleteField;
import com.zybnet.autocomplete.server.AutocompleteQueryListener;
import com.zybnet.autocomplete.server.AutocompleteSuggestionPickedListener;

/**
 * @author Harish Kalepu
 */
@UIScope
@SpringView(name = NewPolicyView.VIEW_NAME)
@SuppressWarnings("serial")
public class NewPolicyView extends VerticalLayout implements View {
	final Logger logger = LoggerFactory.getLogger(NewPolicyView.class);
    public static final String VIEW_NAME = "New Policy";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY="AIzaSyC76c3VMDgGdz3tb0ZGje05Z3Q22yR0Aag";
    public static final Object ADD_PROPERTY_NAME = "Add";
    private static final String COUNTRY_CODE = "&components=country:IN";
    final String REQUIRED = " is required.";
    
    @Autowired
    EventBus.UIEventBus uiEventBus;

    @Autowired
    private GetProperties getProperties;
    
    @Autowired
    private UploadDocumentWindow uploadDocumentWindow;
    
    private RestTemplate restTemplate;
    
    private VerticalLayout newPolicyVLayout;
    private User user;
    private TabSheet newPolicyTabSheet;
    private VerticalLayout personalDetailsTab;
    private VerticalLayout insuranceCompanyDetailsTab;
    private VerticalLayout policyDetailsTab;
    private VerticalLayout amountDetailsTab;
    private VerticalLayout uploadDocumentsTab;
    private OptionGroup industryRadioButton;
    private static String DATE_FORMAT = "dd/MM/yyyy";
    private FormLayout personalForm;
    private FormLayout addressForm;
    private HorizontalLayout personalDeatilsHLayput;
    private Label nameofTheIndividualLabel;
    private TextField individualName;
    private Label nameofTheCompanyLabel;
    private TextField companyName;
    private Label dateOfBirthLabel;
    private PopupDateField dateOfBirthField;
    private HorizontalLayout phoneLabelLayout;
    private Label phoneLabel;
    private Label sPhoneLabel;
    private HorizontalLayout phoneFieldLayout;
    private TextField phoneNumber;
    private TextField scondaryNumber;
    private HorizontalLayout emailLabelLayout;
    private Label emailLabel;
    private Label sEmailLabel;
    private HorizontalLayout emailFieldLayout;
    private TextField email;
    private TextField sEmail;
    private Label genderLabel;
    private OptionGroup genderRadioButton;
    private Label addressLabel;
    private AutocompleteField<String> addressField;
    private Label streetAddLabel;
    private TextField streetAddressField;
    private Label address2Label;
    private TextField address2Field;
    private HorizontalLayout cityLabelLayout;
    private Label cityLabel;
    private Label stateLabel;
    private HorizontalLayout cityStateFieldLayout;
    private TextField city;
    private TextField state;
    private HorizontalLayout zipCountryLabelLayout;
    private Label zipLabel;
    private Label countryLabel;
    private HorizontalLayout zipCountryFieldLayout;
    private TextField zip;
    private TextField country;
    private Image printIcon;
    private Image shareIcon;
    private PolicyApplication policyApplication=null;
    private ComboBox policyTypeBox;
    private TextField policyNumberField;
    private ComboBox agentNamesBox;
    private TextField officeCodeField;
    private TextField referenceField;
    private TextField endorseNumField;
    private PopupDateField startsOnField;
    private PopupDateField endsOnField;
    private ComboBox insCmpNameBox;
    private TextArea branchAddressArea;
    private ComboBox insTypeBox;
    private TextField productNameField;
    private TextArea policyDetailsArea;
    private TextField grossPremiumField;
    private TextField terrorismField;
    private TextField serviceTaxField;
    private TextField serviceTaxAmountField;
    private TextField netPremiumField;
    private TextField commissionRatePerField;
    private TextField commissionRateAmaountField;
    private PopupDateField collectionDateField;
    private String fileName;
    private File file;
    private PolicyDocuments policyDocuments;
    private Grid grid;
    private Panel policyDocumentsPanel;
    private Panel claimsDocumentsPanel;
    private Panel ecardsDocumentsPanel;
    private Panel mandateDocumentsPanel;
    private Panel brochureDocumentsPanel;
    private Panel otherDocumentsPanel;
    private TextField memberName;
    private PopupDateField memberDOB;
    private ComboBox policyTypeFieldBox;
	private OptionGroup diseases;
	private TextField coverage;
	private ComboBox policyTremBox;
	private TextField adultName;
	private PopupDateField adultDOB;
	private TextField kidName;
	private PopupDateField kidDOB;
	private TextField ageOfEldest;
	private VerticalLayout adultsVlayoutOriginal;
	private VerticalLayout childVlayoutOriginal;

    public NewPolicyView() {
        setSizeFull();
        addStyleName("homepageimage");
        setSpacing(true);
        restTemplate = new RestTemplate();
        policyApplication= new PolicyApplication();
    }

    @PostConstruct
    void init() {

        uiEventBus.subscribe(this);
        newPolicyVLayout = new VerticalLayout();
        newPolicyVLayout.setSpacing(true);
        newPolicyVLayout.addStyleName("newPolicyVLayOut");
        user = (User) VaadinSession.getCurrent().getAttribute("user");
        
        newPolicyTabSheet = new TabSheet();
        newPolicyTabSheet.setSizeFull();
        newPolicyTabSheet.addStyleName("newPolicyTabSheetStyleName");
        
        this.buildPersonalDetailsTab();
        this.buildInsuranceCompDetailsTab();
        this.buildPolicyDetailsTab();
        this.buildAmountDetailsTab();
        this.buildUploadDocumentsTab();
        
        newPolicyVLayout.addComponent(newPolicyTabSheet);
        newPolicyVLayout.setExpandRatio(newPolicyTabSheet, 1f);
        Responsive.makeResponsive(newPolicyVLayout);
        addComponent(newPolicyVLayout);
        //setComponentAlignment(newPolicyVLayout, Alignment.TOP_CENTER);
    }
    
    private void buildPersonalDetailsTab() {
    	personalDetailsTab = new VerticalLayout();
    	personalDetailsTab.addStyleName("personalDetailsTabStyleName");
    	personalDetailsTab.setSpacing(true);
    	
    	personalDeatilsHLayput = new HorizontalLayout();
    	personalDeatilsHLayput.setSizeFull();
    	personalDeatilsHLayput.setSpacing(true);
    	personalDeatilsHLayput.addStyleName("personalDeatilsHLayputStyleName");
    	
    	personalForm = new FormLayout();
    	personalForm.setMargin(true);
    	personalForm.setSizeFull();
    	personalForm.addStyleName("personalFormStyleName");
    	
    	Label industryLabel = new Label("Industry :");
    	industryLabel.addStyleName("industryLabelStyleName");
    	personalForm.addComponent(industryLabel);
    	
		industryRadioButton = new OptionGroup();
		industryRadioButton.addItems("Individual", "Co-oporate");
		industryRadioButton.setStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
		// genderCheckBox.setNullSelectionAllowed(true);
		industryRadioButton.select("Individual");
		industryRadioButton.setHtmlContentAllowed(true);
		industryRadioButton.setImmediate(true);
		//industryRadioButton.setRequired(true);
		personalForm.addComponent(industryRadioButton);
		
		industryRadioButton.addValueChangeListener(e -> industryChangeEvent(e)
		);
		
		nameofTheIndividualLabel = new Label("Name of the Individual :");
		nameofTheIndividualLabel.addStyleName("industryLabelStyleName");
		personalForm.addComponent(nameofTheIndividualLabel);
    	
		individualName = new TextField();
		individualName.setInputPrompt("Name of the Individual");
		individualName.addStyleName("individualNameStyleName");
		Utilites.setFormStyle(null, individualName, Boolean.TRUE.booleanValue());
		personalForm.addComponent(individualName);
		
		nameofTheCompanyLabel = new Label("Name of the Company :");
		nameofTheCompanyLabel.addStyleName("industryLabelStyleName");
		personalForm.addComponent(nameofTheCompanyLabel);
    	
		companyName = new TextField();
		companyName.setInputPrompt("Name of the Company");
		companyName.addStyleName("individualNameStyleName");
		Utilites.setFormStyle(null, companyName, Boolean.TRUE.booleanValue());
		personalForm.addComponent(companyName);
		
		dateOfBirthLabel = new Label("Date Of Birth :");
		dateOfBirthLabel.addStyleName("industryLabelStyleName");
		personalForm.addComponent(dateOfBirthLabel);
		
		dateOfBirthField = new PopupDateField();
		dateOfBirthField.addStyleName("dateOfBirthFieldStyleName");
		dateOfBirthField.setInputPrompt(DATE_FORMAT.toUpperCase());
		personalForm.addComponent(dateOfBirthField);
		
		phoneLabelLayout = new HorizontalLayout();
		phoneLabelLayout.setSpacing(true);
		phoneLabel = new Label("Phone Number :");
		sPhoneLabel = new Label("Secondary Number :");
		phoneLabel.addStyleName("phoneLabelStyleName");
		sPhoneLabel.addStyleName("sPhoneLabelStyleName");
		phoneLabelLayout.addComponent(phoneLabel);
		phoneLabelLayout.addComponent(sPhoneLabel);
		personalForm.addComponent(phoneLabelLayout);
		
		phoneFieldLayout = new HorizontalLayout();
		phoneFieldLayout.setSpacing(true);
		phoneNumber = new TextField();
		phoneNumber.setInputPrompt("Phone Number");
		scondaryNumber = new TextField();
		scondaryNumber.setInputPrompt("Secondary Number");
		phoneNumber.addStyleName("phoneNumberStyleName");
		this.setRequired(phoneNumber);
		scondaryNumber.addStyleName("scondaryNumberStyleName");
		phoneFieldLayout.addComponent(phoneNumber);
		phoneFieldLayout.addComponent(scondaryNumber);
		personalForm.addComponent(phoneFieldLayout);
		
		emailLabelLayout = new HorizontalLayout();
		emailLabelLayout.setSpacing(true);
		emailLabel = new Label("Email :");
		sEmailLabel = new Label("Secondary Email :");
		emailLabel.addStyleName("emailLabelStyleName");
		sEmailLabel.addStyleName("sEmailLabelStyleName");
		emailLabelLayout.addComponent(emailLabel);
		emailLabelLayout.addComponent(sEmailLabel);
		personalForm.addComponent(emailLabelLayout);
		
		emailFieldLayout = new HorizontalLayout();
		emailFieldLayout.setSpacing(true);
		email = new TextField();
		email.setInputPrompt("Email");
		sEmail = new TextField();
		sEmail.setInputPrompt("Secondary Email");
		this.setRequired(email);
		email.addStyleName("emailStyleName");
		sEmail.addStyleName("sEmailStyleName");
		emailFieldLayout.addComponent(email);
		emailFieldLayout.addComponent(sEmail);
		personalForm.addComponent(emailFieldLayout);
		
		personalDeatilsHLayput.addComponent(personalForm);
		
		//Address Form
		addressForm = new FormLayout();
    	addressForm.setMargin(true);
    	addressForm.setSizeFull();
    	addressForm.addStyleName("addressFormStyleName");
    	
    	genderLabel = new Label("Gender :");
    	genderLabel.addStyleName("industryLabelStyleName");
    	addressForm.addComponent(genderLabel);
    	
    	genderRadioButton = new OptionGroup();
    	genderRadioButton.addItems("Male", "Female");
    	genderRadioButton.setStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
		// genderCheckBox.setNullSelectionAllowed(true);
    	genderRadioButton.select("Male");
    	genderRadioButton.setHtmlContentAllowed(true);
    	genderRadioButton.setImmediate(true);
		//industryRadioButton.setRequired(true);
    	addressForm.addComponent(genderRadioButton);
    	
    	addressLabel = new Label("Address :");
    	addressLabel.addStyleName("industryLabelStyleName");
    	addressForm.addComponent(addressLabel);
    	
    	addressField = new AutocompleteField<String>();
    	addressField.setCaption("Address");
    	addressField.addStyleName("addressFieldStyleName");
    	this.setRequired(addressField);
    	addressField.setId("addressFieldId");
    	addressForm.addComponent(addressField);
		
    	streetAddLabel = new Label("Street Address :");
    	streetAddLabel.addStyleName("industryLabelStyleName");
    	addressForm.addComponent(streetAddLabel);
    	
    	streetAddressField = new TextField();
    	streetAddressField.setInputPrompt("Street Address");
    	streetAddressField.addStyleName("addressFieldStyleName");
    	this.setRequired(streetAddressField);
    	addressForm.addComponent(streetAddressField);
    	
    	address2Label = new Label("Address Line 2:");
    	address2Label.addStyleName("industryLabelStyleName");
    	addressForm.addComponent(address2Label);
    	
    	address2Field = new TextField();
    	address2Field.setInputPrompt("Address Line 2");
    	address2Field.addStyleName("addressFieldStyleName");
    	addressForm.addComponent(address2Field);
    	
    	cityLabelLayout = new HorizontalLayout();
    	cityLabelLayout.setSpacing(true);
		cityLabel = new Label("City :");
		stateLabel = new Label("State/ Province/ Region :");
		cityLabel.addStyleName("cityLabelStyleName");
		stateLabel.addStyleName("stateLabelStyleName");
		cityLabelLayout.addComponent(cityLabel);
		cityLabelLayout.addComponent(stateLabel);
		addressForm.addComponent(cityLabelLayout);
		
		cityStateFieldLayout = new HorizontalLayout();
		cityStateFieldLayout.setSpacing(true);
		city = new TextField();
		city.setInputPrompt("City");
		state = new TextField();
		state.setInputPrompt("State");
		city.addStyleName("cityStyleName");
		state.addStyleName("stateStyleName");
		this.setRequired(city);
		this.setRequired(state);
		cityStateFieldLayout.addComponent(city);
		cityStateFieldLayout.addComponent(state);
		addressForm.addComponent(cityStateFieldLayout);
		
		zipCountryLabelLayout = new HorizontalLayout();
		zipCountryLabelLayout.setSpacing(true);
		zipLabel = new Label("Zip/ Postal Code :");
		countryLabel = new Label("Country :");
		zipLabel.addStyleName("zipLabelStyleName");
		countryLabel.addStyleName("countryLabelStyleName");
		zipCountryLabelLayout.addComponent(zipLabel);
		zipCountryLabelLayout.addComponent(countryLabel);
		addressForm.addComponent(zipCountryLabelLayout);
		
		zipCountryFieldLayout = new HorizontalLayout();
		zipCountryFieldLayout.setSpacing(true);
		zip = new TextField();
		zip.setInputPrompt("Zip/ Postal Code");
		country = new TextField();
		country.setInputPrompt("Country");
		zip.addStyleName("zipStyleName");
		country.addStyleName("countryStyleName");
		this.setRequired(country);
		this.setRequired(zip);
		zipCountryFieldLayout.addComponent(zip);
		zipCountryFieldLayout.addComponent(country);
		addressForm.addComponent(zipCountryFieldLayout);
		
		Button saveButton = new Button("Save");
		saveButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		saveButton.addStyleName("saveButtonStyleName");
    	addressForm.addComponent(saveButton);
    	
		personalDeatilsHLayput.addComponent(addressForm);
		
		//personalDeatilsHLayput.setExpandRatio(personalForm, 1);
		//personalDeatilsHLayput.setExpandRatio(addressForm, 2);
		
		HorizontalLayout buttonsHLayout = new HorizontalLayout();
		buttonsHLayout.addStyleName("buttonsHLayoutStyleName");
		
		Button newPolicyButton = new Button("New Policy");
		newPolicyButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		buttonsHLayout.addComponent(newPolicyButton);
		
		printIcon = new Image(null, new ThemeResource("img/Print_icon.png"));
    	printIcon.addStyleName("printIconPersoanlStyleName");
    	buttonsHLayout.addComponent(printIcon);
    	
    	shareIcon = new Image(null, new ThemeResource("img/Send_Icon.png"));
    	shareIcon.addStyleName("shareIconPersonalStyleName");
    	buttonsHLayout.addComponent(shareIcon);
    	
    	personalDetailsTab.addComponent(buttonsHLayout);
		personalDetailsTab.addComponent(personalDeatilsHLayput);
    	newPolicyTabSheet.addTab(personalDetailsTab,"Personal Details");
    	
    	addressField.setQueryListener(new AutocompleteQueryListener<String>() {
			
			@Override
			public void handleUserQuery(AutocompleteField<String> autocompleteField, String query) {
				if(autocompleteField.getId()==addressField.getId()){
					if(query != null){
						String input = query;
						String urlString = PLACES_API_BASE+TYPE_AUTOCOMPLETE+OUT_JSON+"?key="+API_KEY+COUNTRY_CODE+"&input="+input;
						HttpURLConnection conn = null;
						ArrayList<String> resultList = null;
						StringBuilder jsonResults = new StringBuilder();
						try{
							URL url = new URL(urlString.toString());
							conn = (HttpURLConnection) url.openConnection();
							InputStreamReader in = new InputStreamReader(conn.getInputStream());
	
							// Load the results into a StringBuilder
							int read;
							char[] buff = new char[1024];
							while ((read = in.read(buff)) != -1) {
								jsonResults.append(buff, 0, read);
							}
						}catch(Exception ex){
							ex.printStackTrace();
						}
						try {
							// Create a JSON object hierarchy from the results
							JSONObject jsonObj = new JSONObject(jsonResults.toString());
							JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

							// Extract the Place descriptions from the results
							resultList = new ArrayList<String>(predsJsonArray.length());
							for (int i = 0; i < predsJsonArray.length(); i++) {
								System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
								System.out.println("============================================================");
								resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
							}
							for(String value : resultList){
								addressField.addSuggestion(value, value);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				}
			}
		});
    	addressField.setSuggestionPickedListener(new AutocompleteSuggestionPickedListener<String>() {
			
			@Override
			public void onSuggestionPicked(String suggestion) {
				String[] addressFileds = suggestion.split(",");
				int length = addressFileds.length-3;
				city.setValue(addressFileds[addressFileds.length-3]);
				state.setValue(addressFileds[addressFileds.length-2]);
				country.setValue(addressFileds[addressFileds.length-1]);
				if(length != 0 && length>1) {
					addressField.clear();
					addressField.setValue(null);
					streetAddressField.clear();
					for(int i=0;i<length;i++){
						if(i<2) {
							String value = addressFileds[i];
							if(addressField.getValue() != null){
								value = addressField.getValue()+","+value;
								addressField.setValue(value);
							} else
								addressField.setValue(value);
						} else {
							String value = addressFileds[i];
							if(streetAddressField.getValue() != null && !streetAddressField.getValue().equals("")) {
								value = streetAddressField.getValue()+","+value;
								streetAddressField.setValue(value);
							} else
								streetAddressField.setValue(value);
						}
					}
				} else if(length != 0 && length==1){
					addressField.clear();
					streetAddressField.clear();
					addressField.setValue(addressFileds[0]);
				}
				System.out.println(addressFileds);
			}
		});
    	
    	saveButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(event.getComponent()==saveButton) {
					try {
						ArrayList<Component> validationList= new ArrayList<>();
						
						if (individualName.isVisible() &&(individualName.getValue() == null || individualName.getValue().equals(""))) {
							individualName.setRequiredError(nameofTheIndividualLabel.getValue() + REQUIRED);
							validationList.add(individualName);
						} 
						if((companyName.getValue() == null || companyName.getValue().equals("")) && industryRadioButton.getValue().toString().equals("Co-oporate")) {
							companyName.setRequiredError(nameofTheCompanyLabel.getValue() + REQUIRED);
							validationList.add(companyName);
						} 
						if(phoneNumber.getValue() == null || phoneNumber.getValue().equals("")) {
							phoneNumber.setRequiredError(phoneLabel.getValue() + REQUIRED);
							validationList.add(phoneNumber);
						} 
						if(email.getValue() == null || email.getValue().equals("")) {
							email.setRequiredError(emailLabel.getValue() + REQUIRED);
							validationList.add(email);
						} 
						if(addressField.getValue() == null || addressField.getValue().equals("")) {
							addressField.setRequiredError(addressLabel.getValue() + REQUIRED);
							validationList.add(addressField);
						} 
						if(streetAddressField.getValue() == null || streetAddressField.getValue().equals("")) {
							streetAddressField.setRequiredError(streetAddLabel.getValue() + REQUIRED);
							validationList.add(streetAddressField);
						} 
						if(city.getValue() == null || city.getValue().equals("")) {
							city.setRequiredError(cityLabel.getValue() + REQUIRED);
							validationList.add(city);
						} 
						if(state.getValue() == null || state.getValue().equals("")) {
							state.setRequiredError(stateLabel.getValue() + REQUIRED);
							validationList.add(state);
						} 
						if(zip.getValue() == null || zip.getValue().equals("")) {
							zip.setRequiredError(zipLabel.getValue() + REQUIRED);
							validationList.add(zip);
						} 
						if(country.getValue() == null || country.getValue().equals("")) {
							country.setRequiredError(countryLabel.getValue() + REQUIRED);
							validationList.add(country);
						}
						if(validationList.isEmpty()) {
							PersonalDetailsBean personalDetailsBean = new PersonalDetailsBean();
							Address address = prepareAddressObject();
							PersonalDetails personalDetails = preparePersonalDetailsObject();
							personalDetailsBean.setAddress(address);
							personalDetailsBean.setPersonalDetails(personalDetails);
							GenericResponse response = restTemplate.postForObject(getProperties.getBaseUrl()+"/savePersonalDetails", personalDetailsBean, GenericResponse.class);
							if(response.getStatus().equals("Success")){
								personalDetailsBean = Utilites.getObject(response, PersonalDetailsBean.class);
								policyApplication.setAddress(personalDetailsBean.getAddress());
								policyApplication.setPersonalDetails(personalDetailsBean.getPersonalDetails());
								Util.successNotification("Personal Details Saved Successfully.");
							} else {
								Util.errorNotification("Something went wrong please try again..!!!");
							}
						}
					} catch(Exception ex) {
						ex.printStackTrace();
						Util.errorNotification("Something went wrong please try again..!!!");
					}
				}
			}
		});
    	newPolicyButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(event.getComponent()==newPolicyButton){
					clearFields();
				}
			}
		});
    }
    
    private void buildInsuranceCompDetailsTab() {
    	insuranceCompanyDetailsTab = new VerticalLayout();
    	insuranceCompanyDetailsTab.addStyleName("insuranceCompanyDetailsTabStyleName");
    	insuranceCompanyDetailsTab.setSizeFull();
    	
    	HorizontalLayout insuranceCompanyDetailsHLayout = new HorizontalLayout();
    	insuranceCompanyDetailsHLayout.addStyleName("insuranceCompanyDetailsHLayoutStyleName");
    	insuranceCompanyDetailsHLayout.setSizeFull();
    	insuranceCompanyDetailsHLayout.setSpacing(true);
    	
    	FormLayout agentForm = new FormLayout();
    	agentForm.setMargin(true);
    	agentForm.setSizeFull();
    	agentForm.addStyleName("agentFormStyleName");
    	
    	Label policyTypeLabel = new Label("Policy Type :");
    	policyTypeLabel.addStyleName("industryLabelStyleName");
    	agentForm.addComponent(policyTypeLabel);
    	
    	policyTypeBox = new ComboBox();
    	policyTypeBox.setInputPrompt("Select");
    	policyTypeBox.setNullSelectionAllowed(true);
    	policyTypeBox.setImmediate(true);
    	policyTypeBox.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
    	policyTypeBox.setStyleName("policyTypeBoxStyleName");
    	agentForm.addComponent(policyTypeBox);
    	this.setRequired(policyTypeBox);
    	
    	policyTypeBox.addItem("New Sales");
    	policyTypeBox.addItem("Renewal");
    	
    	Label policyNumberLabel = new Label("Policy Number :");
    	policyNumberLabel.addStyleName("industryLabelStyleName");
    	agentForm.addComponent(policyNumberLabel);
    	
    	policyNumberField = new TextField();
    	this.setRequired(policyNumberField);
    	policyNumberField.setInputPrompt("Policy Number");
    	policyNumberField.addStyleName("policyNumberFieldStyleName");
    	agentForm.addComponent(policyNumberField);
    	
    	Label agentNameLabel = new Label("Agent Name :");
    	agentNameLabel.addStyleName("industryLabelStyleName");
    	agentForm.addComponent(agentNameLabel);
    	
    	agentNamesBox = new ComboBox();
    	agentNamesBox.setInputPrompt("Select");
    	agentNamesBox.setNullSelectionAllowed(true);
    	agentNamesBox.setImmediate(true);
    	agentNamesBox.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
    	agentNamesBox.setStyleName("agentNamesBoxStyleName");
    	this.setRequired(agentNamesBox);
    	agentForm.addComponent(agentNamesBox);
    	
    	Label officeCodeLabel = new Label("Office Code :");
    	officeCodeLabel.addStyleName("industryLabelStyleName");
    	agentForm.addComponent(officeCodeLabel);
    	
    	officeCodeField = new TextField();
    	this.setRequired(officeCodeField);
    	officeCodeField.setInputPrompt("Office Code");
    	officeCodeField.addStyleName("officeCodeFieldStyleName");
    	agentForm.addComponent(officeCodeField);
    	
    	Label referenceLabel = new Label("Reference :");
    	referenceLabel.addStyleName("industryLabelStyleName");
    	agentForm.addComponent(referenceLabel);
    	
    	referenceField = new TextField();
    	referenceField.setInputPrompt("Reference");
    	referenceField.addStyleName("referenceFieldStyleName");
    	agentForm.addComponent(referenceField);
    	
    	Label endorseNoLabel = new Label("Endorse No :");
    	endorseNoLabel.addStyleName("industryLabelStyleName");
    	agentForm.addComponent(endorseNoLabel);
    	
    	endorseNumField = new TextField();
    	endorseNumField.setInputPrompt("Endorse No");
    	endorseNumField.addStyleName("endorseNumFieldStyleName");
    	agentForm.addComponent(endorseNumField);
    	
    	insuranceCompanyDetailsHLayout.addComponent(agentForm);
    	
    	//Ins company Address Form
    	FormLayout insAddressForm = new FormLayout();
    	insAddressForm.setMargin(true);
    	insAddressForm.setSizeFull();
    	insAddressForm.addStyleName("insAddressFormStyleName");
    	
    	Label startsOnLabel = new Label("Policy Start Date :");
    	startsOnLabel.addStyleName("industryLabelStyleName");
    	insAddressForm.addComponent(startsOnLabel);
    	
    	startsOnField = new PopupDateField();
    	this.setRequired(startsOnField);
    	startsOnField.addStyleName("startsOnFieldStyleName");
    	startsOnField.setInputPrompt(DATE_FORMAT.toUpperCase());
    	insAddressForm.addComponent(startsOnField);
    	
    	Label endsOnLabel = new Label("Policy End Date :");
    	endsOnLabel.addStyleName("industryLabelStyleName");
    	insAddressForm.addComponent(endsOnLabel);
		
    	endsOnField = new PopupDateField();
    	this.setRequired(endsOnField);
    	endsOnField.addStyleName("endsOnFieldStyleName");
    	endsOnField.setInputPrompt(DATE_FORMAT.toUpperCase());
    	insAddressForm.addComponent(endsOnField);
    	
    	Label insuranceCmpNameLabel = new Label("Insurance Company Name :");
    	insuranceCmpNameLabel.addStyleName("industryLabelStyleName");
    	insAddressForm.addComponent(insuranceCmpNameLabel);
    	
    	insCmpNameBox = new ComboBox();
    	insCmpNameBox.setInputPrompt("Select");
    	insCmpNameBox.setNullSelectionAllowed(true);
    	insCmpNameBox.setImmediate(true);
    	insCmpNameBox.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
    	insCmpNameBox.setStyleName("insCmpNameBoxStyleName");
    	insAddressForm.addComponent(insCmpNameBox);
    	this.setRequired(insCmpNameBox);
    	
    	Label insuranceCmpBranchAddlabel = new Label("Insurance Branch Address :");
    	insuranceCmpBranchAddlabel.addStyleName("industryLabelStyleName");
    	insAddressForm.addComponent(insuranceCmpBranchAddlabel);
    	
    	branchAddressArea = new TextArea();
    	this.setRequired(branchAddressArea);
    	branchAddressArea.addStyleName("branchAddressAreaStyleName");
    	branchAddressArea.setHeight(158,Unit.PIXELS);
    	branchAddressArea.setWidth(291,Unit.PIXELS);
    	insAddressForm.addComponent(branchAddressArea);
    	
    	Button saveButton = new Button("Save");
    	saveButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
    	saveButton.addStyleName("genSaveButtonStyleName");
    	insAddressForm.addComponent(saveButton);
    	
    	insuranceCompanyDetailsHLayout.addComponent(insAddressForm);
    	
    	insuranceCompanyDetailsTab.addComponent(insuranceCompanyDetailsHLayout);
    	newPolicyTabSheet.addTab(insuranceCompanyDetailsTab,"General Details");
    	
    	saveButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(event.getComponent()==saveButton) {
					ArrayList<Component> validationList= new ArrayList<>();
					try{
						if(policyTypeBox.getValue() == null || policyTypeBox.getValue().equals("")) {
							policyTypeBox.setRequiredError("Policy Type " + REQUIRED);
							validationList.add(policyTypeBox);
						}
						if(policyNumberField.getValue()==null || policyNumberField.getValue().equals("")) {
							policyNumberField.setRequiredError(policyNumberField.getCaption() + REQUIRED);
							validationList.add(policyNumberField);
						}
						if(agentNamesBox.getValue()==null || agentNamesBox.getValue().equals("")) {
							agentNamesBox.setRequiredError("Agent Name " + REQUIRED);
							validationList.add(agentNamesBox);
						}
						if(officeCodeField.getValue()==null || officeCodeField.getValue().equals("")) {
							officeCodeField.setRequiredError(officeCodeField.getCaption() + REQUIRED);
							validationList.add(officeCodeField);
						}
						if(referenceField.getValue()==null || referenceField.getValue().equals("")) {
							referenceField.setRequiredError(referenceField.getCaption() + REQUIRED);
							validationList.add(referenceField);
						}
						if(endorseNumField.getValue()==null || endorseNumField.getValue().equals("")) {
							endorseNumField.setRequiredError(endorseNumField.getCaption() + REQUIRED);
							validationList.add(endorseNumField);
						}
						if(startsOnField.getValue()==null || startsOnField.getValue().equals("")) {
							startsOnField.setRequiredError("Start Date " + REQUIRED);
							validationList.add(startsOnField);
						}
						if(endsOnField.getValue()==null || endsOnField.getValue().equals("")) {
							endsOnField.setRequiredError("End Date " + REQUIRED);
							validationList.add(endsOnField);
						}
						if(insCmpNameBox.getValue()==null || insCmpNameBox.getValue().equals("")) {
							insCmpNameBox.setRequiredError("Insurance Company " + REQUIRED);
							validationList.add(insCmpNameBox);
						}
						if(branchAddressArea.getValue()==null || branchAddressArea.getValue().equals("")) {
							branchAddressArea.setRequiredError("Branch Address " + REQUIRED);
							validationList.add(branchAddressArea);
						}
						if(validationList.isEmpty()) {
							InsuranceComapnyDetails insuranceComapnyDetails = new InsuranceComapnyDetails();
							insuranceComapnyDetails.setPolicyType(policyTypeBox.getValue()!=null?policyTypeBox.getValue().toString():"");
							insuranceComapnyDetails.setPolicyNumber(policyNumberField.getValue()!=null?policyNumberField.getValue():"");
							insuranceComapnyDetails.setAgent(agentNamesBox.getValue()!=null?agentNamesBox.getValue().toString():"Harish");
							insuranceComapnyDetails.setOfficeCode(officeCodeField.getValue()!=null?officeCodeField.getValue():"");
							insuranceComapnyDetails.setReference(referenceField.getValue()!=null?referenceField.getValue():"");
							insuranceComapnyDetails.setEndorseNumber(endorseNumField.getValue()!=null?endorseNumField.getValue():"");
							insuranceComapnyDetails.setPolicyStartDate(new java.sql.Date(startsOnField.getValue().getTime()));
							insuranceComapnyDetails.setPolicyEndDate(new java.sql.Date(endsOnField.getValue().getTime()));
							insuranceComapnyDetails.setInsuranceCompanyName(insCmpNameBox.getValue()!=null?insCmpNameBox.getValue().toString():"");
							insuranceComapnyDetails.setInsuranceBranchAddress(branchAddressArea.getValue()!=null?branchAddressArea.getValue():"");
							insuranceComapnyDetails.setPersonalDetailsId(policyApplication.getPersonalDetails().getPersonalId());
							GenericResponse response = restTemplate.postForObject(getProperties.getBaseUrl()+"/saveInsuranceDetails", insuranceComapnyDetails, GenericResponse.class);
							if(response.getStatus().equals("Success")){
								insuranceComapnyDetails = Utilites.getObject(response, InsuranceComapnyDetails.class);
								policyApplication.setInsuranceComapnyDetails(insuranceComapnyDetails);
								Util.successNotification("Insurance Details Saved Successfully.");
							} else {
								Util.errorNotification("Something went wrong please try again..!!!");
							}
						}
					}catch(Exception ex){
						ex.printStackTrace();
						Util.errorNotification("Something went wrong please try again..!!!");
					}
				}
			}
    	});
    }
    
    private void buildPolicyDetailsTab(){
    	policyDetailsTab = new VerticalLayout();
    	policyDetailsTab.addStyleName("policyDetailsTabStyleName");
    	policyDetailsTab.setSizeFull();
    	
    	HorizontalLayout policyDetailsHLayout = new HorizontalLayout();
    	policyDetailsHLayout.addStyleName("policyDetailsHLayoutStyleName");
    	policyDetailsHLayout.setSizeFull();
    	policyDetailsHLayout.setSpacing(true);
    	
    	FormLayout policyDetailsLeftForm = new FormLayout();
    	policyDetailsLeftForm.setMargin(true);
    	policyDetailsLeftForm.setSizeFull();
    	policyDetailsLeftForm.addStyleName("policyDetailsLeftFormStyleName");
    	
    	GenericResponse response = null;
    	try {
    		response = restTemplate.getForObject(getProperties.getBaseUrl()+"/getPolicyTypes", GenericResponse.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	List<PolicyType> policyTypes = new ArrayList<PolicyType>();
		try {
			List<Map> policyTypeMap = (List<Map>)Utilites.getObject(response, List.class);;
			ObjectMapper mapper = new ObjectMapper ();
			for(Map map:policyTypeMap) {
				PolicyType polType = mapper.readValue ( mapper.writeValueAsString ( map ), PolicyType.class );
				policyTypes.add(polType);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	insTypeBox = new ComboBox("Type of Insurance :");
    	insTypeBox.setInputPrompt("Select");
    	insTypeBox.setNullSelectionAllowed(true);
    	insTypeBox.setImmediate(true);
    	insTypeBox.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
    	insTypeBox.setStyleName("insTypeBoxStyleName");
    	policyDetailsLeftForm.addComponent(insTypeBox);
    	this.setRequired(insTypeBox);
    	
    	for(PolicyType object : policyTypes){
    		insTypeBox.addItem(object.getPolicyTypeName());
    	}
		
    	productNameField = new TextField("Product Name :");
    	this.setRequired(productNameField);
    	productNameField.setInputPrompt("Product Name");
    	productNameField.addStyleName("productNameFieldStyleName");
    	policyDetailsLeftForm.addComponent(productNameField);
    	
    	policyDetailsHLayout.addComponent(policyDetailsLeftForm);
    	
    	FormLayout policyDetailsRightForm = new FormLayout();
    	policyDetailsRightForm.setMargin(true);
    	policyDetailsRightForm.setSizeFull();
    	policyDetailsRightForm.addStyleName("policyDetailsRightFormStyleName");
    	
    	policyDetailsArea = new TextArea("Policy Details :");
    	policyDetailsArea.addStyleName("policyDetailsAreaStyleName");
    	policyDetailsArea.setHeight(158,Unit.PIXELS);
    	policyDetailsArea.setWidth(291,Unit.PIXELS);
    	policyDetailsRightForm.addComponent(policyDetailsArea);
    	
    	policyDetailsHLayout.addComponent(policyDetailsRightForm);
    	
    	policyDetailsTab.addComponent(policyDetailsHLayout);
    	
    	
    	HorizontalLayout footerHLayout = new HorizontalLayout();
    	footerHLayout.setSizeFull();
    	footerHLayout.setSpacing(true);
    	footerHLayout.addStyleName("footerHLayout");
    	Button saveButton = new Button("Save");
    	saveButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
    	saveButton.addStyleName("polSaveButtonStyleName");
    	footerHLayout.addComponent(saveButton);
    	footerHLayout.setComponentAlignment(saveButton, Alignment.TOP_RIGHT);
    	policyDetailsTab.addComponent(footerHLayout);
    	
    	newPolicyTabSheet.addTab(policyDetailsTab,"Policy Details");
    	
    	//Policy Type Fileds
    	policyTypeFieldBox = new ComboBox("Policy Type");
    	policyTypeFieldBox.setInputPrompt("Select");
    	policyTypeFieldBox.setNullSelectionAllowed(true);
    	policyTypeFieldBox.setImmediate(true);
    	policyTypeFieldBox.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
    	policyTypeFieldBox.setStyleName("policyTypeBoxStyleName");
		policyDetailsLeftForm.addComponent(policyTypeFieldBox);
		this.setRequired(policyTypeFieldBox);
		policyTypeFieldBox.setVisible(false);
		
		policyTypeFieldBox.addItem("Individual");
		policyTypeFieldBox.addItem("Family Floater");
		policyTypeFieldBox.addItem("Group Policy");
		
		memberName = new TextField("Insured Name");
		memberName.addStyleName("memberName");
		memberName.setInputPrompt("Insured Name");
		this.setRequired(memberName);
		policyDetailsLeftForm.addComponent(memberName);
		
		memberName.setVisible(false);
		
		memberDOB = new PopupDateField("Insured DOB");
		memberDOB.addStyleName("memberDOB");
		memberDOB.setInputPrompt(DATE_FORMAT.toUpperCase());
		this.setRequired(memberDOB);
		policyDetailsLeftForm.addComponent(memberDOB);
		memberDOB.setVisible(false);
		
		diseases = new OptionGroup("Pre- Existing Diseases:");
		diseases.addItems("Yes", "No");
		diseases.setStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
		diseases.select("Male");
		diseases.setHtmlContentAllowed(true);
		diseases.setImmediate(true);
		diseases.setValue("No");
		policyDetailsRightForm.addComponent(diseases);
		diseases.setVisible(false);
		
		coverage = new TextField("Coverage");
		coverage.addStyleName("coverage");
		coverage.setInputPrompt("Coverage");
		this.setRequired(coverage);
		policyDetailsRightForm.addComponent(coverage);
		coverage.setVisible(false);
		
		policyTremBox = new ComboBox("Policy Term");
		this.setRequired(policyTremBox);
		policyTremBox.setInputPrompt("Select");
		policyTremBox.setNullSelectionAllowed(true);
		policyTremBox.setImmediate(true);
		policyTremBox.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
		policyTremBox.setStyleName("policyTypeBoxStyleName");
		policyDetailsRightForm.addComponent(policyTremBox);
		policyTremBox.setVisible(false);
		
		adultsVlayoutOriginal = this.prepareAdultsLayout();
    	policyDetailsLeftForm.addComponent(adultsVlayoutOriginal);
    	adultsVlayoutOriginal.setVisible(false);
		
		childVlayoutOriginal = this.prepareChildLayout();
		policyDetailsLeftForm.addComponent(childVlayoutOriginal);
		childVlayoutOriginal.setVisible(false);
		
		ageOfEldest = new TextField("Age of Eldest");
		ageOfEldest.addStyleName("ageOfEldest");
		this.setRequired(ageOfEldest);
		ageOfEldest.setInputPrompt("Age of Eldest");
		policyDetailsLeftForm.addComponent(ageOfEldest);
		ageOfEldest.setVisible(false);
		
    	insTypeBox.addListener(new Property.ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				String value = event.getProperty().getValue() != null ? event.getProperty().getValue().toString():"";
				if(value.equals(SLBrokingConstants.HEALTH)) {
					policyTypeFieldBox.setVisible(true);
					policyTypeFieldBox.addListener(new Property.ValueChangeListener() {
						
						@Override
						public void valueChange(ValueChangeEvent event) {
							String value = event.getProperty().getValue() != null ? event.getProperty().getValue().toString():"";
							if(value.equals("Individual")){
								memberName.setVisible(true);
								memberDOB.setVisible(true);
								diseases.setVisible(true);
								coverage.setVisible(true);
								policyTremBox.setVisible(true);
								ageOfEldest.setVisible(false);
								adultsVlayoutOriginal.setVisible(false);
								childVlayoutOriginal.setVisible(false);
							} else if(value.equals("Family Floater")) {
								memberName.setVisible(false);
								memberDOB.setVisible(false);
								diseases.setVisible(true);
								coverage.setVisible(true);
								policyTremBox.setVisible(true);
								ageOfEldest.setVisible(true);
								adultsVlayoutOriginal.setVisible(true);
								childVlayoutOriginal.setVisible(true);
							} else if(value.equals("Group Policy")) {
								memberName.setVisible(false);
								memberDOB.setVisible(false);
								diseases.setVisible(false);
								coverage.setVisible(true);
								policyTremBox.setVisible(true);
								ageOfEldest.setVisible(false);
								adultsVlayoutOriginal.setVisible(false);
								childVlayoutOriginal.setVisible(false);
							} else {
								diseases.setVisible(true);
								memberName.setVisible(false);
								memberDOB.setVisible(false);
								ageOfEldest.setVisible(false);
								adultsVlayoutOriginal.setVisible(false);
								childVlayoutOriginal.setVisible(false);
							}
						}
					});
					diseases.setVisible(true);
					coverage.setVisible(true);
					policyTremBox.setVisible(true);
				} else {
					policyTypeFieldBox.setVisible(false);
					diseases.setVisible(false);
					coverage.setVisible(false);
					policyTremBox.setVisible(false);
					memberName.setVisible(false);
					memberDOB.setVisible(false);
					ageOfEldest.setVisible(false);
					adultsVlayoutOriginal.setVisible(false);
					childVlayoutOriginal.setVisible(false);
				}
			}
		});
    	
    	
    	saveButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(event.getComponent()==saveButton) {
					ArrayList<Component> validationList= new ArrayList<>();
					try{
						if(insTypeBox.getValue() == null || insTypeBox.getValue().equals("")) {
							insTypeBox.setRequiredError("Type of Insurance " + REQUIRED);
							validationList.add(insTypeBox);
						}
						if(productNameField.getValue() == null || productNameField.getValue().equals("")) {
							productNameField.setRequiredError("Product Name " + REQUIRED);
							validationList.add(productNameField);
						}
						if(policyTypeFieldBox.isVisible() && (policyTypeFieldBox.getValue() == null || policyTypeFieldBox.getValue().equals(""))) {
							policyTypeFieldBox.setRequiredError("Policy Type " + REQUIRED);
							validationList.add(policyTypeFieldBox);
						}
						if(memberName.isVisible() && (memberName.getValue() == null || memberName.getValue().equals(""))) {
							memberName.setRequiredError("Insured Name " + REQUIRED);
							validationList.add(memberName);
						}
						if(memberDOB.isVisible() && (memberDOB.getValue() == null || memberDOB.getValue().equals(""))) {
							memberDOB.setRequiredError("Insured DOB " + REQUIRED);
							validationList.add(memberDOB);
						}
						if(coverage.isVisible() && (coverage.getValue() == null || coverage.getValue().equals(""))) {
							coverage.setRequiredError("Coverage " + REQUIRED);
							validationList.add(coverage);
						}
						if(policyTremBox.isVisible() && (policyTremBox.getValue() == null || policyTremBox.getValue().equals(""))) {
							policyTremBox.setRequiredError("Policy Term " + REQUIRED);
							validationList.add(policyTremBox);
						}
						if(validationList.isEmpty()) {
							PolicyDetails policyDetails = new PolicyDetails();
							policyDetails.setInsuranceType(insTypeBox.getValue()!=null?insTypeBox.getValue().toString():"");
							policyDetails.setProductName(productNameField.getValue()!=null?productNameField.getValue():"");
							policyDetails.setPolicyDetails(policyDetailsArea.getValue()!=null?policyDetailsArea.getValue():"");
							policyDetails.setPersonDetailsId(policyApplication.getPersonalDetails().getPersonalId());
							policyDetails.setPNumber(policyApplication.getInsuranceComapnyDetails().getPolicyNumber());
							for(PolicyType object : policyTypes){
					    		if(insTypeBox.getValue().equals(object.getPolicyTypeName())){
					    			policyDetails.setPolicyTypeId(object.getPolicyTypeId());
					    			break;
					    		}
					    	}
							GenericResponse response = restTemplate.postForObject(getProperties.getBaseUrl()+"/savePolicyDetails", policyDetails, GenericResponse.class);
							if(response.getStatus().equals("Success")){
								policyDetails = Utilites.getObject(response, PolicyDetails.class);
								policyApplication.setPolicyDetails(policyDetails);
								Util.successNotification("Policy Details Saved Successfully.");
							} else {
								Util.errorNotification("Something went wrong please try again..!!!");
							}
						}
					}catch(Exception ex){
						ex.printStackTrace();
						Util.errorNotification("Something went wrong please try again..!!!");
					}
				}
			}
    	});
    }
    
    private void buildAmountDetailsTab() {
    	amountDetailsTab = new VerticalLayout();
    	amountDetailsTab.addStyleName("amountDetailsTabStyleName");
    	amountDetailsTab.setSizeFull();
    	
    	HorizontalLayout amountDetailsHLayout = new HorizontalLayout();
    	amountDetailsHLayout.addStyleName("amountDetailsHLayoutStyleName");
    	amountDetailsHLayout.setSizeFull();
    	amountDetailsHLayout.setSpacing(true);
    	
    	FormLayout amountDetailsLeftForm = new FormLayout();
    	amountDetailsLeftForm.setMargin(true);
    	amountDetailsLeftForm.setSizeFull();
    	amountDetailsLeftForm.addStyleName("amountDetailsLeftFormStyleName");
    	
    	Label grossPremiumLabel = new Label("Gross Premium :");
    	grossPremiumLabel.addStyleName("industryLabelStyleName");
    	amountDetailsLeftForm.addComponent(grossPremiumLabel);
    	
    	grossPremiumField = new TextField();
    	grossPremiumField.setInputPrompt("Rs:");
    	this.setRequired(grossPremiumField);
    	grossPremiumField.addStyleName("grossPremiumFieldStyleName");
    	amountDetailsLeftForm.addComponent(grossPremiumField);
    	
    	Label terrorismLabel = new Label("Terrorism Premium Amount :");
    	terrorismLabel.addStyleName("industryLabelStyleName");
    	amountDetailsLeftForm.addComponent(terrorismLabel);
    	
    	terrorismField = new TextField();
    	terrorismField.setInputPrompt("Rs:");
    	terrorismField.addStyleName("serviceTaxFieldStyleName");
    	amountDetailsLeftForm.addComponent(terrorismField);
    	
    	Label serviceTaxLabel = new Label("Service Tax(%) :");
    	serviceTaxLabel.addStyleName("industryLabelStyleName");
    	amountDetailsLeftForm.addComponent(serviceTaxLabel);
    	
    	serviceTaxField = new TextField();
    	this.setRequired(serviceTaxField);
    	serviceTaxField.setInputPrompt("0.00%");
    	serviceTaxField.addStyleName("serviceTaxFieldStyleName");
    	amountDetailsLeftForm.addComponent(serviceTaxField);
    	
    	Label serviceTaxAmountLabel = new Label("Service Tax Amount :");
    	serviceTaxAmountLabel.addStyleName("industryLabelStyleName");
    	amountDetailsLeftForm.addComponent(serviceTaxAmountLabel);
    	
    	serviceTaxAmountField = new TextField();
    	this.setRequired(serviceTaxAmountField);
    	serviceTaxAmountField.setInputPrompt("Rs:");
    	serviceTaxAmountField.addStyleName("serviceTaxAmountFieldStyleName");
    	amountDetailsLeftForm.addComponent(serviceTaxAmountField);
    	
    	Label netPremiumLabel = new Label("Net Premium :");
    	netPremiumLabel.addStyleName("industryLabelStyleName");
    	amountDetailsLeftForm.addComponent(netPremiumLabel);
    	
    	netPremiumField = new TextField();
    	this.setRequired(netPremiumField);
    	netPremiumField.setInputPrompt("Rs:");
    	netPremiumField.addStyleName("netPremiumFieldStyleName");
    	amountDetailsLeftForm.addComponent(netPremiumField);
    	
    	amountDetailsHLayout.addComponent(amountDetailsLeftForm);
    	
    	FormLayout amountDetailsRightForm = new FormLayout();
    	amountDetailsRightForm.setMargin(true);
    	amountDetailsRightForm.setSizeFull();
    	amountDetailsRightForm.addStyleName("amountDetailsRightFormStyleName");
    	
    	Label commissionRatePerLabel = new Label("Commission Rate(%) :");
    	commissionRatePerLabel.addStyleName("industryLabelStyleName");
    	amountDetailsRightForm.addComponent(commissionRatePerLabel);
    	
    	commissionRatePerField = new TextField();
    	this.setRequired(commissionRatePerField);
    	commissionRatePerField.setInputPrompt("0.00%");
    	commissionRatePerField.addStyleName("commissionRatePerFieldStyleName");
    	amountDetailsRightForm.addComponent(commissionRatePerField);
    	
    	Label commissionRateAmountLabel = new Label("Commission Rate Amount :");
    	commissionRateAmountLabel.addStyleName("industryLabelStyleName");
    	amountDetailsRightForm.addComponent(commissionRateAmountLabel);
    	
    	commissionRateAmaountField = new TextField();
    	this.setRequired(commissionRateAmaountField);
    	commissionRateAmaountField.setInputPrompt("Rs:");
    	commissionRateAmaountField.addStyleName("commissionRateAmaountFieldStyleName");
    	amountDetailsRightForm.addComponent(commissionRateAmaountField);
    	
    	Label collectionDateLabel = new Label("Collection Date :");
    	collectionDateLabel.addStyleName("industryLabelStyleName");
    	amountDetailsRightForm.addComponent(collectionDateLabel);
    	
    	collectionDateField = new PopupDateField();
    	this.setRequired(collectionDateField);
    	collectionDateField.addStyleName("collectionDateFieldStyleName");
    	collectionDateField.setInputPrompt(DATE_FORMAT.toUpperCase());
    	amountDetailsRightForm.addComponent(collectionDateField);
    	
    	Button saveButton = new Button("Save");
    	saveButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
    	saveButton.addStyleName("amountsaveButtonStyleName");
    	amountDetailsRightForm.addComponent(saveButton);
    	
    	amountDetailsHLayout.addComponent(amountDetailsRightForm);
    	
    	amountDetailsTab.addComponent(amountDetailsHLayout);
    	newPolicyTabSheet.addTab(amountDetailsTab,"Amount Details");
    	
    	saveButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(event.getComponent()==saveButton) {
					ArrayList<Component> validationList= new ArrayList<>();
					try{
						if(grossPremiumField.getValue() == null || grossPremiumField.getValue().equals("")) {
							grossPremiumField.setRequiredError("Gross Premium " + REQUIRED);
							validationList.add(grossPremiumField);
						}
						if(serviceTaxField.getValue() == null || serviceTaxField.getValue().equals("")) {
							serviceTaxField.setRequiredError("Service Tax " + REQUIRED);
							validationList.add(serviceTaxField);
						}
						if(serviceTaxAmountField.getValue() == null || serviceTaxAmountField.getValue().equals("")) {
							serviceTaxAmountField.setRequiredError("Service Tax Amount " + REQUIRED);
							validationList.add(serviceTaxAmountField);
						}
						if(netPremiumField.getValue() == null || netPremiumField.getValue().equals("")) {
							netPremiumField.setRequiredError("Net Premium " + REQUIRED);
							validationList.add(netPremiumField);
						}
						if(commissionRatePerField.getValue() == null || commissionRatePerField.getValue().equals("")) {
							commissionRatePerField.setRequiredError("Commission Rate " + REQUIRED);
							validationList.add(commissionRatePerField);
						}
						if(commissionRateAmaountField.getValue() == null || commissionRateAmaountField.getValue().equals("")) {
							commissionRateAmaountField.setRequiredError("Commission Rate Amount " + REQUIRED);
							validationList.add(commissionRateAmaountField);
						}
						if(collectionDateField.getValue() == null || collectionDateField.getValue().equals("")) {
							collectionDateField.setRequiredError("Collection Date " + REQUIRED);
							validationList.add(collectionDateField);
						}
						if(validationList.isEmpty()) {
							AmountDetails amountDetails = new AmountDetails();
							amountDetails.setGrossPremiumAmount(grossPremiumField.getValue()!=null?Double.valueOf(grossPremiumField.getValue()):0.00);
							amountDetails.setTerrorismPremiumAmount(terrorismField.getValue()!=null?Double.valueOf(terrorismField.getValue()):0.00);
							amountDetails.setServiceTax(serviceTaxField.getValue()!=null?Double.valueOf(serviceTaxField.getValue()):0.00);
							amountDetails.setServiceTaxAmount(serviceTaxAmountField.getValue()!=null?Double.valueOf(serviceTaxAmountField.getValue()):0.00);
							amountDetails.setNetPremiumAmount(netPremiumField.getValue()!=null?Double.valueOf(netPremiumField.getValue()):0.00);
							amountDetails.setComissionRate(commissionRatePerField.getValue()!=null?Double.valueOf(commissionRatePerField.getValue()):0.00);
							amountDetails.setComissionRateAmount(commissionRateAmaountField.getValue()!=null?Double.valueOf(commissionRateAmaountField.getValue()):0.00);
							amountDetails.setCollectionDate(new java.sql.Date(collectionDateField.getValue().getTime()));
							amountDetails.setPersonalDetailId(policyApplication.getPersonalDetails().getPersonalId());
							amountDetails.setPolicyNumber(policyApplication.getInsuranceComapnyDetails().getPolicyNumber());
							GenericResponse response = restTemplate.postForObject(getProperties.getBaseUrl()+"/saveAmountDetails", amountDetails, GenericResponse.class);
							if(response.getStatus().equals("Success")){
								amountDetails = Utilites.getObject(response, AmountDetails.class);
								policyApplication.setAmountDetails(amountDetails);
								Util.successNotification("Amount Details Saved Successfully.");
							} else {
								Util.errorNotification("Something went wrong please try again..!!!");
							}
						}
					}catch(Exception ex){
						ex.printStackTrace();
						Util.errorNotification("Something went wrong please try again..!!!");
					}
				}
			}
    	});
    }
    
    private void buildUploadDocumentsTab() {
    	uploadDocumentsTab = new VerticalLayout();
    	uploadDocumentsTab.addStyleName("uploadDocumentsTab");
    	uploadDocumentsTab.setSizeFull();
    	
    	GridLayout headerGridLyout = new GridLayout(3,1);
    	headerGridLyout.addStyleName("headerGridLyout");
    	headerGridLyout.setSizeFull();
    	
    	HorizontalLayout documentsTopHLayout = new HorizontalLayout();
    	documentsTopHLayout.setStyleName("documentsTopHLayoutStyleName");
    	
    	Image filterIcon = new Image(null, new ThemeResource("img/Filtericon.png"));
    	filterIcon.addStyleName("filterIconStyleName");
    	documentsTopHLayout.addComponent(filterIcon);
    	documentsTopHLayout.setComponentAlignment(filterIcon, Alignment.TOP_LEFT);
    	
    	ComboBox filterBox = new ComboBox();
    	filterBox.setInputPrompt("Select");
    	filterBox.setImmediate(true);
    	filterBox.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
    	filterBox.addStyleName(ValoTheme.COMBOBOX_BORDERLESS);
    	filterBox.setStyleName("filterBoxStyleName");
    	documentsTopHLayout.addComponent(filterBox);
    	headerGridLyout.addComponent(documentsTopHLayout);
    	
    	List<String> docTypes = new ArrayList<String>();
    	docTypes.add("All");
    	docTypes.add("Policy Document");
    	docTypes.add("Claims Document");
    	docTypes.add("E-Cards Document");
    	docTypes.add("Mandate");
    	docTypes.add("Policy Brochure");
    	docTypes.add("Other");
    	
    	for(String value : docTypes) {
    		filterBox.addItem(value);
    	}
    	filterBox.setValue("All");
    	
    	MenuBar uploadDocumentMenuBar = new MenuBar();
    	uploadDocumentMenuBar.addStyleName("uploadDocumentMenuBarStyleName");
    	headerGridLyout.addComponent(uploadDocumentMenuBar);
    	
    	MenuItem uploadDocumentsItem = uploadDocumentMenuBar.addItem("Upload Documents", null,null);
    	uploadDocumentsItem.addItem("Policy Document", (MenuBar.Command) selectedItem -> {
            try {
            	uploadDocumentWindow.setContent(buildUploadDocWindow(SLBrokingConstants.POLICY_DOCUMENTS));
				UI.getCurrent().addWindow(uploadDocumentWindow);
				uploadDocumentWindow.focus();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    	uploadDocumentsItem.addItem("Claims Document", (MenuBar.Command) selectedItem -> {
            try {
				uploadDocumentWindow.setContent(buildUploadDocWindow(SLBrokingConstants.CLAIMS_DOCUMENTS));
				UI.getCurrent().addWindow(uploadDocumentWindow);
				uploadDocumentWindow.focus();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    	uploadDocumentsItem.addItem("E-Cards Document", (MenuBar.Command) selectedItem -> {
            try {
            	uploadDocumentWindow.setContent(buildUploadDocWindow(SLBrokingConstants.ECARDS_DOCUMENTS));
				UI.getCurrent().addWindow(uploadDocumentWindow);
				uploadDocumentWindow.focus();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    	uploadDocumentsItem.addItem("Mandate Document", (MenuBar.Command) selectedItem -> {
            try {
            	uploadDocumentWindow.setContent(buildUploadDocWindow(SLBrokingConstants.MANDATE_DOCUMENTS));
				UI.getCurrent().addWindow(uploadDocumentWindow);
				uploadDocumentWindow.focus();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    	uploadDocumentsItem.addItem("Policy Brochure", (MenuBar.Command) selectedItem -> {
            try {
            	uploadDocumentWindow.setContent(buildUploadDocWindow(SLBrokingConstants.POLICY_BROCHURE));
				UI.getCurrent().addWindow(uploadDocumentWindow);
				uploadDocumentWindow.focus();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    	uploadDocumentsItem.addItem("Others", (MenuBar.Command) selectedItem -> {
            try {
            	uploadDocumentWindow.setContent(buildUploadDocWindow(SLBrokingConstants.OTHER_DOCUMENTS));
				UI.getCurrent().addWindow(uploadDocumentWindow);
				uploadDocumentWindow.focus();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    	
    	HorizontalLayout layoutButtons = new HorizontalLayout();
    	layoutButtons.addStyleName("layoutButtons");
    	
    	printIcon = new Image(null, new ThemeResource("img/Print_icon.png"));
    	printIcon.addStyleName("printIconStyleName");
    	layoutButtons.addComponent(printIcon);
    	
    	shareIcon = new Image(null, new ThemeResource("img/Send_Icon.png"));
    	shareIcon.addStyleName("shareIconStyleName");
    	layoutButtons.addComponent(shareIcon);
    	
    	Button sendNowButton = new Button("Send Now");
    	sendNowButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
    	sendNowButton.addStyleName("sendNowButtonStyleName");
    	layoutButtons.addComponent(sendNowButton);
    	headerGridLyout.addComponent(layoutButtons);
    	
    	HorizontalLayout selectAllHLayout = new HorizontalLayout();
    	selectAllHLayout.setStyleName("selectAllHLayoutStyleName");
    	selectAllHLayout.setSizeFull();
    	selectAllHLayout.setHeight(30,Unit.PIXELS);
    	
    	CheckBox selectAll = new CheckBox("Select All", false);
    	selectAllHLayout.addComponent(selectAll);
    	selectAll.addStyleName("selectAllStyleName");
    	
    	policyDocumentsPanel = new Panel();
    	policyDocumentsPanel.addStyleName("policyDocumentsPanel");
    	
    	VerticalLayout policyDocumentsVLayout = new VerticalLayout();
    	policyDocumentsVLayout.addStyleName("policyDocumentsVLayout");
    	
    	HorizontalLayout policyDocumentsHLayout = new HorizontalLayout();
    	policyDocumentsHLayout.addStyleName("policyDocumentsHLayout");
    	policyDocumentsHLayout.setSizeFull();
    	
    	CheckBox polDocBox = new CheckBox("",false);
    	polDocBox.addStyleName("polDocBox");
    	Label polDocLabel = new Label("Policy Documents");
    	polDocLabel.addStyleName("polDocLabel");
    	
    	policyDocumentsHLayout.addComponent(polDocBox);
    	policyDocumentsHLayout.addComponent(polDocLabel);
    	
    	grid = generateGrid(SLBrokingConstants.POLICY_DOCUMENTS);
    	
    	policyDocumentsVLayout.addComponent(policyDocumentsHLayout);
    	policyDocumentsVLayout.addComponent(grid);
    	policyDocumentsVLayout.setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
    	
    	claimsDocumentsPanel = new Panel();
    	claimsDocumentsPanel.addStyleName("policyDocumentsPanel");
    	
    	VerticalLayout claimsocumentsVLayout = new VerticalLayout();
    	claimsocumentsVLayout.addStyleName("claimsocumentsVLayout");
    	
    	HorizontalLayout claimsDocumentsHLayout = new HorizontalLayout();
    	claimsDocumentsHLayout.addStyleName("policyDocumentsHLayout");
    	claimsDocumentsHLayout.setSizeFull();
    	
    	CheckBox claimsDocBox = new CheckBox("",false);
    	claimsDocBox.addStyleName("polDocBox");
    	Label claimsDocLabel = new Label("Claims Documents");
    	claimsDocLabel.addStyleName("polDocLabel");
    	
    	claimsDocumentsHLayout.addComponent(claimsDocBox);
    	claimsDocumentsHLayout.addComponent(claimsDocLabel);
    	
    	Grid claimsGrid = generateGrid(SLBrokingConstants.CLAIMS_DOCUMENTS);
    	
    	claimsocumentsVLayout.addComponent(claimsDocumentsHLayout);
    	claimsocumentsVLayout.addComponent(claimsGrid);
    	claimsocumentsVLayout.setComponentAlignment(claimsGrid, Alignment.MIDDLE_CENTER);
    	
    	ecardsDocumentsPanel = new Panel();
    	ecardsDocumentsPanel.addStyleName("policyDocumentsPanel");
    	
    	VerticalLayout ecardsDocumentsVLayout = new VerticalLayout();
    	ecardsDocumentsVLayout.addStyleName("policyDocumentsVLayout");
    	
    	HorizontalLayout ecardsDocumentsHLayout = new HorizontalLayout();
    	ecardsDocumentsHLayout.addStyleName("policyDocumentsHLayout");
    	ecardsDocumentsHLayout.setSizeFull();
    	
    	CheckBox ecardsDocBox = new CheckBox("",false);
    	ecardsDocBox.addStyleName("polDocBox");
    	Label ecardsDocLabel = new Label("E-Cards Documents");
    	ecardsDocLabel.addStyleName("polDocLabel");
    	
    	ecardsDocumentsHLayout.addComponent(ecardsDocBox);
    	ecardsDocumentsHLayout.addComponent(ecardsDocLabel);
    	
    	Grid ecardsGrid = generateGrid(SLBrokingConstants.ECARDS_DOCUMENTS);
    	
    	ecardsDocumentsVLayout.addComponent(ecardsDocumentsHLayout);
    	ecardsDocumentsVLayout.addComponent(ecardsGrid);
    	ecardsDocumentsVLayout.setComponentAlignment(ecardsGrid, Alignment.MIDDLE_CENTER);
    	
    	mandateDocumentsPanel = new Panel();
    	mandateDocumentsPanel.addStyleName("policyDocumentsPanel");
    	
    	VerticalLayout mandateDocumentsVLayout = new VerticalLayout();
    	mandateDocumentsVLayout.addStyleName("policyDocumentsVLayout");
    	
    	HorizontalLayout mandateDocumentsHLayout = new HorizontalLayout();
    	mandateDocumentsHLayout.addStyleName("policyDocumentsHLayout");
    	mandateDocumentsHLayout.setSizeFull();
    	
    	CheckBox mandateDocBox = new CheckBox("",false);
    	mandateDocBox.addStyleName("polDocBox");
    	Label mandateDocLabel = new Label("Mandate Documents");
    	mandateDocLabel.addStyleName("polDocLabel");
    	
    	mandateDocumentsHLayout.addComponent(mandateDocBox);
    	mandateDocumentsHLayout.addComponent(mandateDocLabel);
    	
    	Grid mandategrid = generateGrid(SLBrokingConstants.MANDATE_DOCUMENTS);
    	
    	mandateDocumentsVLayout.addComponent(mandateDocumentsHLayout);
    	mandateDocumentsVLayout.addComponent(mandategrid);
    	mandateDocumentsVLayout.setComponentAlignment(mandategrid, Alignment.MIDDLE_CENTER);
    	
    	
    	brochureDocumentsPanel = new Panel();
    	brochureDocumentsPanel.addStyleName("policyDocumentsPanel");
    	
    	VerticalLayout brochureDocumentsVLayout = new VerticalLayout();
    	brochureDocumentsVLayout.addStyleName("policyDocumentsVLayout");
    	
    	HorizontalLayout brochureDocumentsHLayout = new HorizontalLayout();
    	brochureDocumentsHLayout.addStyleName("policyDocumentsHLayout");
    	brochureDocumentsHLayout.setSizeFull();
    	
    	CheckBox brochureDocBox = new CheckBox("",false);
    	brochureDocBox.addStyleName("polDocBox");
    	Label brochureDocLabel = new Label("Policy Brochure");
    	brochureDocLabel.addStyleName("polDocLabel");
    	
    	brochureDocumentsHLayout.addComponent(brochureDocBox);
    	brochureDocumentsHLayout.addComponent(brochureDocLabel);
    	
    	Grid brochuregrid = generateGrid(SLBrokingConstants.POLICY_BROCHURE);
    	
    	brochureDocumentsVLayout.addComponent(brochureDocumentsHLayout);
    	brochureDocumentsVLayout.addComponent(brochuregrid);
    	brochureDocumentsVLayout.setComponentAlignment(brochuregrid, Alignment.MIDDLE_CENTER);
    	
    	
    	otherDocumentsPanel = new Panel();
    	otherDocumentsPanel.addStyleName("policyDocumentsPanel");
    	
    	VerticalLayout otherDocumentsVLayout = new VerticalLayout();
    	otherDocumentsVLayout.addStyleName("policyDocumentsVLayout");
    	
    	HorizontalLayout otherDocumentsHLayout = new HorizontalLayout();
    	otherDocumentsHLayout.addStyleName("policyDocumentsHLayout");
    	otherDocumentsHLayout.setSizeFull();
    	
    	CheckBox otherDocBox = new CheckBox("",false);
    	otherDocBox.addStyleName("polDocBox");
    	Label otherDocLabel = new Label("Other Documents");
    	otherDocLabel.addStyleName("polDocLabel");
    	
    	otherDocumentsHLayout.addComponent(otherDocBox);
    	otherDocumentsHLayout.addComponent(otherDocLabel);
    	
    	Grid othergrid = generateGrid(SLBrokingConstants.OTHER_DOCUMENTS);
    	
    	otherDocumentsVLayout.addComponent(otherDocumentsHLayout);
    	otherDocumentsVLayout.addComponent(othergrid);
    	otherDocumentsVLayout.setComponentAlignment(othergrid, Alignment.MIDDLE_CENTER);
    	
    	policyDocumentsPanel.setContent(policyDocumentsVLayout);
    	claimsDocumentsPanel.setContent(claimsocumentsVLayout);
    	ecardsDocumentsPanel.setContent(ecardsDocumentsVLayout);
    	mandateDocumentsPanel.setContent(mandateDocumentsVLayout);
    	brochureDocumentsPanel.setContent(brochureDocumentsVLayout);
    	otherDocumentsPanel.setContent(otherDocumentsVLayout);
    	
    	uploadDocumentsTab.addComponent(headerGridLyout);
    	uploadDocumentsTab.setComponentAlignment(headerGridLyout, Alignment.MIDDLE_CENTER);
    	uploadDocumentsTab.addComponent(selectAllHLayout);
    	uploadDocumentsTab.addComponent(policyDocumentsPanel);
    	uploadDocumentsTab.addComponent(claimsDocumentsPanel);
    	uploadDocumentsTab.addComponent(ecardsDocumentsPanel);
    	uploadDocumentsTab.addComponent(mandateDocumentsPanel);
    	uploadDocumentsTab.addComponent(brochureDocumentsPanel);
    	uploadDocumentsTab.addComponent(otherDocumentsPanel);
    	
    	newPolicyTabSheet.addTab(uploadDocumentsTab,"Upload Documents");
    	
    	filterBox.addListener(new Property.ValueChangeListener() {
    		private static final long serialVersionUID = 1L;
			public void valueChange(ValueChangeEvent event) {
				String value = event.getProperty().getValue().toString();
            	if(value.equals("Policy Document")) {
            	} else if(value.equals("Claims Document")) {
            	} else if(value.equals("E-Cards Document")) {
            	} else if(value.equals("Mandate")) {
            	} else if(value.equals("Policy Brochure")) {
            	} else if(value.equals("Other")) {
            	} else if(value.equals("All")) {
            	}
			}
		});
    	
    	selectAll.addListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = -6857112166321059475L;
            public void valueChange(ValueChangeEvent event) {
            	Boolean value = (Boolean)event.getProperty().getValue();
            	if(value) {
            	} else {
            	}
            }
        });
    }
    
	private void industryChangeEvent(Property.ValueChangeEvent e) {

		if (String.valueOf(e.getProperty().getValue()).equals("Individual")) {
			nameofTheIndividualLabel.setVisible(true);
			individualName.setVisible(true);
			dateOfBirthLabel.setVisible(true);
			dateOfBirthField.setVisible(true);
			genderLabel.setVisible(true);
			genderRadioButton.setVisible(true);	
			companyName.setRequired(false);
			companyName.setValidationVisible(false);
		} else {
			nameofTheIndividualLabel.setVisible(false);
			individualName.setVisible(false);
			dateOfBirthLabel.setVisible(false);
			dateOfBirthField.setVisible(false);
			genderLabel.setVisible(false);
			genderRadioButton.setVisible(false);
			companyName.setRequired(true);
			companyName.setValidationVisible(true);
		}
	}
	
	public Address prepareAddressObject(){
		Address address = new Address();
		if(addressField.getValue() != null) {
			address.setAddress(addressField.getValue() != null ? addressField.getValue():"");
			address.setAddressLine_1(streetAddressField.getValue()!=null?streetAddressField.getValue():"");
			address.setAddressLine_2(address2Field.getValue()!=null?address2Field.getValue():"");
			address.setCity(city.getValue()!=null?city.getValue():"");
			address.setState(state.getValue() != null ? state.getValue():"");
			address.setZipPostalCode(zip.getValue() != null?zip.getValue():"");
			address.setCountry(country.getValue()!=null?country.getValue():"");
			return address;
		} else
			return null;
	}
	
	public PersonalDetails preparePersonalDetailsObject(){
		PersonalDetails personalDetails = new PersonalDetails();
		personalDetails.setIndustry(industryRadioButton.getValue()!=null?industryRadioButton.getValue().toString():"");
		if(industryRadioButton.getValue().toString().equals("Individual")){
			personalDetails.setNameOfIndividual(individualName.getValue()!=null?individualName.getValue():"");
			personalDetails.setGender(genderRadioButton.getValue().toString()!= null?genderRadioButton.getValue().toString():"");
			personalDetails.setDateOfBirth(new java.sql.Date(dateOfBirthField.getValue().getTime()));
		}
		personalDetails.setNameOfCompany(companyName.getValue()!=null?companyName.getValue():"");
		personalDetails.setPhoneNumber(phoneNumber.getValue()!=null?phoneNumber.getValue():"");
		personalDetails.setSecondaryNumber(scondaryNumber.getValue()!=null?scondaryNumber.getValue():"");
		personalDetails.setEmailAddress(email.getValue()!=null?email.getValue():"");
		personalDetails.setSecondrayEmailAddress(sEmail.getValue()!=null?sEmail.getValue():"");
		return personalDetails;
	}
	
	private void clearFields(){
	    individualName.clear();
	    companyName.clear();
	    dateOfBirthField.clear();
	    phoneNumber.clear();
	    scondaryNumber.clear();
	    email.clear();
	    sEmail.clear();
	    addressField.clear();
	    streetAddressField.clear();
	    address2Field.clear();
	    city.clear();
	    state.clear();
	    zip.clear();
	    country.clear();
	    policyApplication= new PolicyApplication();
	    policyTypeBox.clear();
	    policyNumberField.clear();
	    agentNamesBox.clear();
	    officeCodeField.clear();
	    referenceField.clear();
	    endorseNumField.clear();
	    startsOnField.clear();
	    endsOnField.clear();
	    insCmpNameBox.clear();
	    branchAddressArea.clear();
	    insTypeBox.clear();
	    productNameField.clear();
	    policyDetailsArea.clear();
	    grossPremiumField.clear();
	    terrorismField.clear();
	    serviceTaxField.clear();
	    serviceTaxAmountField.clear();
	    netPremiumField.clear();
	    commissionRatePerField.clear();
	    commissionRateAmaountField.clear();
	    collectionDateField.clear();
	}
	
	private Grid generateGrid(String docType){
		Grid grid = new Grid();
		grid.addStyleName("docsGridStyleNameScroll");
		grid.setSelectionMode(SelectionMode.MULTI);
        grid.setWidth(100,Unit.PERCENTAGE);
        grid.markAsDirty();
        BeanItemContainer<PolicyDocuments> policyDocumentsBeanItemContainer;
        policyDocumentsBeanItemContainer = new BeanItemContainer<>(PolicyDocuments.class);
        policyDocumentsBeanItemContainer.removeAllItems();
        Integer personId = policyApplication.getPersonalDetails()!= null?policyApplication.getPersonalDetails().getPersonalId():null;
        List<PolicyDocuments> policyDocumentsList = getPolicyDocuments(docType,personId);
        policyDocumentsBeanItemContainer.addAll(policyDocumentsList);
		grid.setImmediate(true);
		grid.setContainerDataSource(policyDocumentsBeanItemContainer);
		grid.markAsDirty();
		grid.setVisible(true);
		grid.addStyleName(ValoTheme.TABLE_COMPACT);
		grid.addStyleName("sl-grid-upload");

		grid.getColumn("documentName").setHeaderCaption("Document Name");
		grid.getColumn("comments").setHeaderCaption("Comment");
		grid.getColumn("uploadedDate").setHeaderCaption("Uploaded Date");
		grid.getColumn("status").setHeaderCaption("Status");
		grid.getColumn("policyNumber").setHeaderCaption("Action").setRenderer(new ButtonRenderer(event -> {
			
            PolicyDocuments policyDocuments = (PolicyDocuments) event.getItemId();
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		));
		grid.setHeight(String.valueOf((policyDocumentsList.size()+1) *38));
		grid.getColumn("documentId").setHidden(true);
		grid.getColumn("docTypeId").setHidden(true);
		grid.getColumn("personDetId").setHidden(true);
		grid.getColumn("document").setHidden(true);
		grid.setColumnOrder("documentName", "comments", "uploadedDate", "status","policyNumber");
		grid.setCellStyleGenerator((Grid.CellStyleGenerator) cellReference -> {
			 if (cellReference.getItem().getItemProperty("policyNumber").toString() != null) {
				 return "viewImage";
			 } else {
				 return null;
			 }
		});
		grid.addSelectionListener((SelectionEvent.SelectionListener) event -> {
			
		});
		return grid;
	}
	
	public List<PolicyDocuments> getPolicyDocuments(String docType,Integer personId){
		List<PolicyDocuments> policyDocumentsList = new ArrayList<PolicyDocuments>();
		try {
			if(policyApplication.getPersonalDetails() != null) {
				GenericResponse genericResponse = restTemplate.getForObject(
						getProperties.getBaseUrl() + "/fetchDocuments?docType="+docType.toString()+"&personId="+personId,GenericResponse.class);
				policyDocumentsList = Utilites.getListObject(genericResponse, PolicyDocuments.class);
			}
		} catch (HttpClientErrorException e) {
			logger.error("HttpClientErrorException occured where tried to retrive Vaults", e);
		} catch (Exception e) {
			logger.error("Exception occured where tried to retrive Vaults", e);
		}
		return policyDocumentsList;
	}

	@EventBusListenerMethod
    public void setUserName(BrokingEvents.UserUpdateEvent event) {
        user = (event.getUser() == null) ? user : event.getUser();
    }
	
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
    
    private Panel buildUploadDocWindow(String docType){
    	Panel panel = new Panel("Upload Documents");
    	VerticalLayout content = new VerticalLayout();
    	content.addStyleName("uploadDocVLStyleName");
        
    	FormLayout formLayout = new FormLayout();
        formLayout.addStyleName("formLayoutStyleName");
        formLayout.setMargin(true);
        formLayout.setSizeFull();
        
        TextField documentName = new TextField("Document Name :");
        documentName.addStyleName("documentNameStyleName");
        
        TextArea comments = new TextArea("Comments :");
        comments.addStyleName("docCommentsStyleName");
        
        Upload uploadDocument = new Upload("Upload "+docType+" :", (Upload.Receiver) (filename, mimeType) -> {
        	try {
    			if(!filename.equals("") && filename != null){
    				this.fileName = filename; 
    	            String fileExtension = filename.split("\\.")[1];
    	            file = java.io.File.createTempFile("temp", "."+fileExtension);
    	            return new FileOutputStream(file);
    			}else{
                    Util.errorNotification("Please choose file.");
    			}
            } catch (IOException e) {
            	logger.error("IOException occured while reading file", e);
            	return null;
            }
			return null;
        });
        uploadDocument.addListener((Upload.FinishedListener) finishedEvent -> {
            try {
            	if(file != null){
	            	FileInputStream fileToRead = new FileInputStream(file);
	            	//Blob blob=null;
	            	byte[] documentContent = IOUtils.toByteArray(fileToRead);
	            	documentName.setReadOnly(false);
	            	documentName.setValue(fileName);
	            	documentName.setReadOnly(true);
	            	policyDocuments = new PolicyDocuments();
	            	policyDocuments.setDocument(documentContent);
	            	policyDocuments.setDocumentName(documentName.getValue());
	            	Date date = new Date();
	            	policyDocuments.setUploadedDate(new Timestamp(date.getTime()));
	            	policyDocuments.setStatus("Reveived");
	            	policyDocuments.setComments(comments.getValue());
	            	policyDocuments.setPolicyNumber(policyApplication.getInsuranceComapnyDetails().getPolicyNumber());
	            	if(docType.equals(SLBrokingConstants.POLICY_DOCUMENTS))
	            		policyDocuments.setDocTypeId(1);
	            	if(docType.equals(SLBrokingConstants.CLAIMS_DOCUMENTS))
	            		policyDocuments.setDocTypeId(2);
	            	if(docType.equals(SLBrokingConstants.ECARDS_DOCUMENTS))
	            		policyDocuments.setDocTypeId(3);
	            	if(docType.equals(SLBrokingConstants.MANDATE_DOCUMENTS))
	            		policyDocuments.setDocTypeId(4);
	            	if(docType.equals(SLBrokingConstants.POLICY_BROCHURE))
	            		policyDocuments.setDocTypeId(5);
	            	if(docType.equals(SLBrokingConstants.OTHER_DOCUMENTS))
	            		policyDocuments.setDocTypeId(6);
	            	policyDocuments.setPersonDetId(policyApplication.getPersonalDetails().getPersonalId());
            	}
            } catch (Exception e) {
            	Util.errorNotification("Something went woring wile uploading \""+fileName+"\". please retry once.");
            	logger.error("Exception occured while inserting file into db",e);
                return;
            }
        });
        uploadDocument.setImmediate(true);
        uploadDocument.setButtonCaption("Select File");
        formLayout.addComponent(uploadDocument);
        
        formLayout.addComponent(documentName);
        
        formLayout.addComponent(comments);
        
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName("docFooterStyleName");
        footer.setSpacing(true);
        footer.setMargin(new MarginInfo(false,false,true,false));
        footer.setSizeFull();
        
        Button cancelButton = new Button("Cancel");
        cancelButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        footer.addComponent(cancelButton);
        footer.setComponentAlignment(cancelButton, Alignment.TOP_LEFT);

        Button saveButton = new Button("Save");
        saveButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        footer.addComponent(saveButton);
        footer.setComponentAlignment(saveButton, Alignment.TOP_RIGHT);
        
        cancelButton.addClickListener(clickEvent -> {
        	file = null;
        	fileName=null;
        	uploadDocumentWindow.close();
        });
        
        saveButton.addClickListener(clickEvent -> {
        	if(file == null)
        		Util.errorNotification("Please select file");
        	else {
        		try{
        			policyDocuments.setComments(comments.getValue());
	        		GenericResponse genericResponse = restTemplate.postForObject(getProperties.getBaseUrl()+"/uploadDocuments",policyDocuments, GenericResponse.class);
	        		Integer result = Utilites.getObject(genericResponse, Integer.class);
	        		if(result > 0) {
	        			Util.successNotification("Successfully uploaded.");
	        			uploadDocumentWindow.close();
	        			Grid newGird = generateGrid(docType);
	        			if(docType.equals(SLBrokingConstants.POLICY_DOCUMENTS)){
	        				VerticalLayout verticalLayout = (VerticalLayout)policyDocumentsPanel.getContent();
	        				Grid rGrid = (Grid)verticalLayout.getComponent(1);
	        				verticalLayout.removeComponent(rGrid);
	        				verticalLayout.addComponent(newGird);
	        			} else if(docType.equals(SLBrokingConstants.CLAIMS_DOCUMENTS)) {
	        				VerticalLayout verticalLayout = (VerticalLayout)claimsDocumentsPanel.getContent();
	        				Grid rGrid = (Grid)verticalLayout.getComponent(1);
	        				verticalLayout.removeComponent(rGrid);
	        				verticalLayout.addComponent(newGird);
	        			} else if(docType.equals(SLBrokingConstants.ECARDS_DOCUMENTS)) {
	        				VerticalLayout verticalLayout = (VerticalLayout)ecardsDocumentsPanel.getContent();
	        				Grid rGrid = (Grid)verticalLayout.getComponent(1);
	        				verticalLayout.removeComponent(rGrid);
	        				verticalLayout.addComponent(newGird);
	        			} else if(docType.equals(SLBrokingConstants.MANDATE_DOCUMENTS)) {
	        				VerticalLayout verticalLayout = (VerticalLayout)mandateDocumentsPanel.getContent();
	        				Grid rGrid = (Grid)verticalLayout.getComponent(1);
	        				verticalLayout.removeComponent(rGrid);
	        				verticalLayout.addComponent(newGird);
	        			} else if(docType.equals(SLBrokingConstants.POLICY_BROCHURE)) {
	        				VerticalLayout verticalLayout = (VerticalLayout)brochureDocumentsPanel.getContent();
	        				Grid rGrid = (Grid)verticalLayout.getComponent(1);
	        				verticalLayout.removeComponent(rGrid);
	        				verticalLayout.addComponent(newGird);
	        			} else if(docType.equals(SLBrokingConstants.OTHER_DOCUMENTS)) {
	        				VerticalLayout verticalLayout = (VerticalLayout)otherDocumentsPanel.getContent();
	        				Grid rGrid = (Grid)verticalLayout.getComponent(1);
	        				verticalLayout.removeComponent(rGrid);
	        				verticalLayout.addComponent(newGird);
	        			}
	        		} else
	        			Util.errorNotification("Something went wrong.");
        		} catch(Exception ex) {
        			ex.printStackTrace();
        			Util.errorNotification("Something went wrong.");
        		}
        	}
        	
        });
        
        content.addComponent(formLayout);
        content.addComponent(footer);
        panel.setContent(content);
        return panel;
    }
    
    public void setRequired(AbstractField<?> field){
    	field.addValidator(new NullValidator(field.getCaption()+" must be entered.", false));
    	field.setValidationVisible(true);
    	field.setImmediate(true);
    	field.setRequired(true);
    }
    
    public VerticalLayout prepareAdultsLayout(){
    	VerticalLayout adultsVlayout = new VerticalLayout();
		adultsVlayout.addStyleName("adultsVlayout");
		
		HorizontalLayout audltsLayout = this.prepareAdultFields();
    	adultsVlayout.addComponent(audltsLayout);
    	return adultsVlayout;
    }
    
    private HorizontalLayout prepareAdultFields(){
    	HorizontalLayout audltsLayout = new HorizontalLayout();
		audltsLayout.addStyleName("audltsLayout");
		
		Label adultNameLabel = new Label("Adult Name");
		adultNameLabel.addStyleName("adultNameLabel");
		Label adultDOCLabel = new Label("Adult DOB");
		adultDOCLabel.addStyleName("adultDOCLabel");
		
		adultName = new TextField();
		adultName.setInputPrompt("Adult Name");
		adultName.addStyleName("adultName");
		this.setRequired(adultName);
		audltsLayout.addComponent(adultNameLabel);
		audltsLayout.addComponent(adultName);
		
		
		
		adultDOB = new PopupDateField();
		adultDOB.addStyleName("adultDOB");
		adultDOB.setInputPrompt(DATE_FORMAT.toUpperCase());
		this.setRequired(adultDOB);
		audltsLayout.addComponent(adultDOCLabel);
		audltsLayout.addComponent(adultDOB);
		
		Image plusIcon = new Image(null, new ThemeResource("img/plus-square-left-o.png"));
		plusIcon.addStyleName("plusIcon");
		audltsLayout.addComponent(plusIcon);
		plusIcon.setWidth(16,Unit.PIXELS);
		plusIcon.addClickListener(new MouseEvents.ClickListener() {
	        @Override
	        public void click(MouseEvents.ClickEvent event) {
	        	HorizontalLayout laypout = prepareAdultFields(); 
	        	adultsVlayoutOriginal.addComponent(laypout);
	        }
	    });
    	
    	Image minusIcon = new Image(null, new ThemeResource("img/minus-square-left-o.png"));
    	minusIcon.addStyleName("minusIcon");
    	audltsLayout.addComponent(minusIcon);
    	minusIcon.setWidth(16,Unit.PIXELS);
    	return audltsLayout;
    }
    
    private VerticalLayout prepareChildLayout(){
    	VerticalLayout childVlayout = new VerticalLayout();
		childVlayout.addStyleName("childVlayout");
		
		HorizontalLayout kidsLayout = this.prepareChildFields();
    	childVlayout.addComponent(kidsLayout);
    	return childVlayout;
    }
    private HorizontalLayout prepareChildFields(){
    	HorizontalLayout kidsLayout = new HorizontalLayout();
		kidsLayout.addStyleName("audltsLayout");
		
		Label kidNameLabel = new Label("Child Name");
		kidNameLabel.addStyleName("adultNameLabel");
		Label kidDOCLabel = new Label("Child DOB");
		kidDOCLabel.addStyleName("adultDOCLabel");
		
		kidName = new TextField();
		kidName.setInputPrompt("Child Name");
		kidName.addStyleName("adultName");
		this.setRequired(kidName);
		kidsLayout.addComponent(kidNameLabel);
		kidsLayout.addComponent(kidName);
		
		
		
		kidDOB = new PopupDateField();
		kidDOB.addStyleName("adultDOB");
		kidDOB.setInputPrompt(DATE_FORMAT.toUpperCase());
		this.setRequired(kidDOB);
		kidsLayout.addComponent(kidDOCLabel);
		kidsLayout.addComponent(kidDOB);
		
		Image kidplusIcon = new Image(null, new ThemeResource("img/plus-square-left-o.png"));
		kidplusIcon.addStyleName("plusIcon");
		kidsLayout.addComponent(kidplusIcon);
		kidplusIcon.setWidth(16,Unit.PIXELS);
		kidplusIcon.addClickListener(new MouseEvents.ClickListener() {
	        @Override
	        public void click(MouseEvents.ClickEvent event) {
	        	HorizontalLayout laypout = prepareChildFields(); 
	        	childVlayoutOriginal.addComponent(laypout);
	        }
	    });
    	
    	Image kidminusIcon = new Image(null, new ThemeResource("img/minus-square-left-o.png"));
    	kidminusIcon.addStyleName("minusIcon");
    	kidsLayout.addComponent(kidminusIcon);
    	kidminusIcon.setWidth(16,Unit.PIXELS);
    	return kidsLayout;
    }
}