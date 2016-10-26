package com.telosws.broking.util;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.themes.ValoTheme;

public class Utilites {

	public static <T extends Object> T getObject(GenericResponse genericResponse, Class<T> type)
			throws JsonParseException, JsonMappingException, JsonProcessingException, IOException 
	{
		if (!(genericResponse == null || genericResponse.getResponseObject() == null)) {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(mapper.writeValueAsString(genericResponse.getResponseObject()), type);
		}
		return null;
	}

	public static <T extends Object> List<T> getListObject(GenericResponse genericResponse, Class<T> type)
			throws JsonParseException, JsonMappingException, JsonProcessingException, IOException 
	{
		if (!(genericResponse == null || genericResponse.getResponseObject() == null)) {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(mapper.writeValueAsString(genericResponse.getResponseObject()),
					mapper.getTypeFactory().constructCollectionType(List.class, type));
		}
		return null;
	}

	public static void setFormStyle(AbstractOrderedLayout form, AbstractField<?> field, boolean required) 
	{
		if(required)
		{
			field.addValidator(new NullValidator(field.getCaption()+" must be entered.", false));
			field.setValidationVisible(true);
			field.setImmediate(true);
		}
		if( field instanceof DateField )
		{
			((DateField)field).setDateFormat("dd/MM/yyyy");
			((DateField)field).setRangeEnd(new Date());
		}
		field.setRequired(required);
		field.addStyleName(ValoTheme.LABEL_H1);
		if(form != null)
			form.addComponent(field);
	}
	
	public static void setNonDOBFormStyle(AbstractOrderedLayout form, AbstractField<?> field, boolean required) 
	{
		if(required)
		{
			field.addValidator(new NullValidator(field.getCaption()+" must be entered.", false));
			field.setValidationVisible(true);
			field.setImmediate(true);
		}
		if( field instanceof DateField )
		{
			((DateField)field).setDateFormat("dd/MM/yyyy");
		}
		field.setRequired(required);
		field.addStyleName(ValoTheme.LABEL_H1);
		form.addComponent(field);
	}
}