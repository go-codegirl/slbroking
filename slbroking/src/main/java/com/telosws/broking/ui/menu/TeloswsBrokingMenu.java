package com.telosws.broking.ui.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.vaadin.spring.events.EventBus;

import com.telosws.broking.util.GetProperties;
import com.telosws.orm.tables.pojos.User;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 * 
 * @author Harish Kalepu
 *
 */

public class TeloswsBrokingMenu extends CustomComponent {
	
    @Autowired
    protected EventBus.UIEventBus.ApplicationEventBus uiEventBus;
    
    @Autowired
    private GetProperties getProperties;
    
    private RestTemplate restTemplate;
	
	public void rebuildLogo(User user){
    	CssLayout menuItem = (CssLayout)this.getCompositionRoot();
    	/**
    	 * Code knows that the Component[1] from INSIDE ROOT Component is MenuBar and first MenuItem Text must be LoggedIn user name
    	 */
    	MenuBar menuBar = null;
    	/*if(user.getRole().equals("HR"))
    		menuBar = (MenuBar)menuItem.getComponent(3); 
    	else */
    		menuBar = (MenuBar)menuItem.getComponent(2);
    	
    	List<MenuItem> menuItems = menuBar.getItems();
    	if( !(menuItems == null || menuItems.isEmpty()) )
    	{
    		menuItems.get(0).setText(user.getFirstName()+" "+user.getLastName());
    	}    	
    	HorizontalLayout hLayout = (HorizontalLayout)menuItem.getComponent(1);  
	    if(hLayout.getComponentCount() > 0) {
	    	/*GenericResponse companyResponse;
	    	Company company;
			try {
				restTemplate=new RestTemplate();
				companyResponse = restTemplate.getForObject(getProperties.getBaseUrl()+"/getCompanyById?id="+user.getCompanyId().toString(), GenericResponse.class);
				company = Utilites.getObject(companyResponse, Company.class);
				if(company != null && company.getCompanyLogo() != null && company.getCompanyLogo().length > 0) {
					StreamSource ss = new StreamSource() {
						private static final long serialVersionUID = 1L;
						byte[] bytes = company.getCompanyLogo();
			     		InputStream is = new ByteArrayInputStream(bytes);
			     		@Override
			             public InputStream getStream() {
			                 return is;
			             }
					};
			     	StreamResource resource = new StreamResource(ss, company.getName());
			     	if(resource != null && company.getCompanyLogo().length > 0) {
						Image image  = new Image(null,resource);
						image.setWidth("201px");
						image.setHeight("70px");
						Component component = hLayout.getComponent(0);
						hLayout.removeComponent(component);
						hLayout.addComponent(image);
						hLayout.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
						hLayout.addStyleName("valo-menu-logo");
					}
				}
			} catch (RestClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	    }
    }
	
	@Override
	protected void setCompositionRoot(Component compositionRoot) {
		// TODO Auto-generated method stub
		super.setCompositionRoot(compositionRoot);
	}

}
