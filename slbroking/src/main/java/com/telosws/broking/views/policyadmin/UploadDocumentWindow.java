package com.telosws.broking.views.policyadmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Responsive;
import com.vaadin.server.Sizeable;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.Window;

@SpringComponent
@VaadinSessionScope
public class UploadDocumentWindow extends Window {
	final Logger logger = LoggerFactory.getLogger(UploadDocumentWindow.class);

	public UploadDocumentWindow() {
        // A layout structure used for composition
        this.addStyleName("upload-window");
        Responsive.makeResponsive(this);

        this.setModal(true);
        this.setCloseShortcut(ShortcutAction.KeyCode.ESCAPE, null);
        this.setResizable(false);
        this.setClosable(false);
        this.setWidth(40.0f, Sizeable.Unit.PERCENTAGE);
        this.center();
    }
}
