package com.telosws.broking.util;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import org.apache.commons.lang.WordUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by karthikmarupeddi on 2/8/15.
 */

@Component
public class Util {
    final org.slf4j.Logger logger = LoggerFactory.getLogger(Util.class);

    @Autowired
    ServletContext servletContext;


    public static Double decimalVale(Double dValue)
    {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(dValue));
    }


    public static String formateData(Double value)
    {
        DecimalFormat noDecimal = new DecimalFormat("###.#");

        String str = Double.valueOf(value).toString();
        String format = str.substring(str.indexOf(".") + 1);

        if(format.length() ==1 && format.equals("0"))
        {
            return  noDecimal.format(value);
        }
        else
            return  Double.valueOf(value).toString();

    }

    public static String CleanText(String value)
    {
        return WordUtils.capitalize(value.replaceAll("\\d+", "").replaceAll("(.)([A-Z])", "$1 $2"));

    }

    public static Notification errorNotification(String message)
    {
        Notification error = new Notification(message);
        error.setDelayMsec(7000);
        error.setStyleName("bar error small");
        error.setPosition(Position.BOTTOM_CENTER);
        error.show(Page.getCurrent());
        return error;
    }

    public static Notification successNotification(String message)
    {
        Notification success = new Notification(message);
        success.setDelayMsec(7000);
        success.setStyleName("bar success small");
        success.setPosition(Position.BOTTOM_CENTER);
        success.show(Page.getCurrent());
        return success;
    }
}
