package com.telosws.broking.views.policyadmin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus;

import com.telosws.broking.util.GetProperties;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;

/**
 * 
 * @author Harish Kalepu
 *
 */

@UIScope
@SpringView(name = SearchPoliciesView.VIEW_NAME)
@SuppressWarnings("serial")
public class SearchPoliciesView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "Search Policies";
	
	@Autowired
    EventBus.UIEventBus uiEventBus;

    @Autowired
    private GetProperties getProperties;
    
	public SearchPoliciesView() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
    void init() {
		uiEventBus.subscribe(this);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
