package com.telosws.broking.views.policyadmin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.annotation.PostConstruct;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.vaadin.spring.events.EventBus;

import com.telosws.broking.beans.UploadRenewalsBean;
import com.telosws.broking.util.Util;
import com.telosws.orm.tables.pojos.PolicyDocuments;
import com.telosws.orm.tables.pojos.PolicyType;
import com.telosws.orm.tables.records.PolicyTypeRecord;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

/**
 * 
 * @author Harish Kalepu
 *
 */

@UIScope
@SpringView(name = UploadRenewalsView.VIEW_NAME)
@SuppressWarnings("serial")
public class UploadRenewalsView extends VerticalLayout implements View {
	final Logger logger = LoggerFactory.getLogger(UploadRenewalsView.class);
	public static final String VIEW_NAME = "Upload Renewals";
	private static final String ValoThemeUI = null;
	private VerticalLayout uploadLayout;
	private HorizontalLayout uploadLabelLayout;

	private RestTemplate restTemplate;
	@Autowired
	EventBus.UIEventBus uiEventBus;
	private HorizontalLayout documentLabelLayout;
	private String fileName;
	private PolicyDocuments policyDocuments;
	private Object policyApplication;
	private Label comments;
	private File file;
	private VerticalLayout documentLayout;
	private FormLayout personalForm;
	private HorizontalLayout uploadHDocument;
	private AbstractOrderedLayout layout;
	private ProgressBar progressbar;
	float progress = 0;
	private Image printIcon;
	private Image shareIcon;
	private GridLayout uploadGridLayout;
	private HorizontalLayout buttonHlayout;
	private Object cellVectorHolder;
	List<UploadRenewalsBean> UploadRenewalsDocumentsList;
	BeanItemContainer<UploadRenewalsBean> UploadRenewalsBeanItemContainer;

	private Grid grid;
	private UploadRenewalsBean uploadrenewalsbeanlist;

	public UploadRenewalsView() {
		setSizeFull();
		setSpacing(true);
	}

	@SuppressWarnings("deprecation")
	@PostConstruct
	void init() {

		uiEventBus.subscribe(this);
		restTemplate = new RestTemplate();

		// VerticalLayout
		uploadLayout = new VerticalLayout();

		// HorizontalLayout
		uploadLabelLayout = new HorizontalLayout();
		uploadLabelLayout.setSizeFull();
		uploadLabelLayout.addStyleName("uploadHeaderStyle");

		Label uploadDocLabelEmpty = new Label();
		uploadDocLabelEmpty.addStyleName("uploadDocLabel");
		uploadLabelLayout.addComponent(uploadDocLabelEmpty);

		Label uploadDocLabel = new Label("Upload Renewals");
		uploadDocLabel.addStyleName("uploadDocLabel");
		uploadLabelLayout.addComponent(uploadDocLabel);

		final Button notificationButton = new Button();
		notificationButton.addStyleName("notificationbuttonStyleName");
		uploadLabelLayout.addComponent(notificationButton);

		uploadLabelLayout.setComponentAlignment(uploadDocLabelEmpty, Alignment.MIDDLE_LEFT);
		uploadLabelLayout.setComponentAlignment(uploadDocLabel, Alignment.MIDDLE_CENTER);
		uploadLabelLayout.setComponentAlignment(notificationButton, Alignment.MIDDLE_RIGHT);

		uploadLayout.addComponent(uploadLabelLayout);

		Label documentHDocLabel = new Label("Select A Renewal Document");
		documentHDocLabel.addStyleName("documentHDocLabel");
		uploadLayout.addComponent(documentHDocLabel);

		this.addProgressBar();
		uploadLayout.addComponent(addGridComponent(UploadRenewalsDocumentsList));
		addComponent(uploadLayout);

	}

	@SuppressWarnings("unused")
	private Grid addGridComponent(List<UploadRenewalsBean> UploadRenewalsDocumentsList) {
		grid = new Grid();
		grid.setSelectionMode(SelectionMode.MULTI);
		grid.addStyleName("sl-grid");
		grid.setWidth(100, Unit.PERCENTAGE);
		
		UploadRenewalsBeanItemContainer = new BeanItemContainer<>(UploadRenewalsBean.class);
		UploadRenewalsBeanItemContainer.removeAllItems();
		if(UploadRenewalsDocumentsList == null)
			UploadRenewalsDocumentsList = new ArrayList<UploadRenewalsBean>();
		UploadRenewalsBeanItemContainer.addAll(UploadRenewalsDocumentsList);
		grid.setImmediate(true);
		grid.setContainerDataSource(UploadRenewalsBeanItemContainer);
		grid.markAsDirty();
		grid.setVisible(true);
		grid.addStyleName("sl-grid");
		grid.getColumn("SNO").setHeaderCaption(" S.NO");
		grid.getColumn("name").setHeaderCaption("Name");
		grid.getColumn("department").setHeaderCaption("Department");
		grid.getColumn("renewalPolicyNumber").setHeaderCaption("Renewal Policy Number");
		grid.getColumn("amount").setHeaderCaption("Renewal Premium");
		grid.getColumn("status").setHeaderCaption("Status");
		grid.setHeight(String.valueOf((UploadRenewalsDocumentsList.size() + 1) * 38));
		grid.getColumn("insuredPhoneno").setHidden(true);
		grid.getColumn("insurerCompany").setHidden(true);
		grid.getColumn("officeCode").setHidden(true);
		grid.getColumn("renewalDate").setHidden(true);
		grid.getColumn("renewalCompany").setHidden(true);
		grid.getColumn("renewalOfficeCode").setHidden(true);
		grid.getColumn("renewalSerialNumber").setHidden(true);
		grid.getColumn("policyNo").setHidden(true);
		grid.getColumn("agentName").setHidden(true);
		grid.setColumnOrder("SNO", "name", "department", "renewalPolicyNumber", "amount", "status");
		
		grid.addSelectionListener((SelectionEvent.SelectionListener) event -> {

		});
		return grid;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	private void addProgressBar() {
		uploadGridLayout = new GridLayout(3, 1);
		uploadGridLayout.setSizeFull();

		Thread update = new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
						getUI().access(new Runnable() {
							@Override
							public void run() {
								progressbar.setValue(progress);

								if (progress > 1) {
									progress = 0;
								} else {
									progress += 0.2 * Math.random();
								}
							}
						});
					} catch (InterruptedException e) {
						break;
					}
				}
			};
		};
		Upload uploadDocument = new Upload("", (Upload.Receiver) (filename, mimeType) -> {
			try {
				if (!filename.equals("") && filename != null) {
					this.fileName = filename;
					String fileExtension = filename.split("\\.")[1];
					file = java.io.File.createTempFile("temp", "." + fileExtension);
					return new FileOutputStream(file);
				} else {
					Util.errorNotification("Please choose file.");
				}
			} catch (IOException e) {
				logger.error("IOException occured while reading file", e);
				return null;
			}
			return null;
		});
		uploadDocument.addListener((Upload.FinishedListener) finishedEvent -> {
			UploadRenewalsDocumentsList = new ArrayList<UploadRenewalsBean>();
			Vector cellVectorHolder = new Vector();

			try {
				if (file != null) {
					getUI().setPollInterval(1000);
					update.start();

					FileInputStream inputStream = new FileInputStream(file);

					Workbook workbook = new XSSFWorkbook(inputStream);
					Sheet firstSheet = workbook.getSheetAt(0);
					Iterator<Row> iterator = firstSheet.iterator();

					while (iterator.hasNext()) {
						Row nextRow = iterator.next();
						if (nextRow.getRowNum() != 0) {
							UploadRenewalsBean uploadrenewalsbean = new UploadRenewalsBean();
							if(getCellValue(nextRow.getCell(0))!=null){
								uploadrenewalsbean.setSNO(Integer.valueOf(getCellValue(nextRow.getCell(0)).toString()));
								if(getCellValue(nextRow.getCell(3)) !=null)
									uploadrenewalsbean.setDepartment(getCellValue(nextRow.getCell(3)).toString());
								if(getCellValue(nextRow.getCell(12)) !=null)
									uploadrenewalsbean.setRenewalPolicyNumber(Integer.valueOf(getCellValue(nextRow.getCell(12)).toString()));
								if(getCellValue(nextRow.getCell(11)) !=null)
									uploadrenewalsbean.setAmount(Double.valueOf(getCellValue(nextRow.getCell(11)).toString()));
								//uploadrenewalsbeanlist.setStatus((String) getCellValue(cellIterator));
								UploadRenewalsDocumentsList.add(uploadrenewalsbean);
							}
						}
					}
					
					Grid newgrid = addGridComponent(UploadRenewalsDocumentsList);
					Grid rGrid = (Grid)uploadLayout.getComponent(3);
					uploadLayout.removeComponent(rGrid);
					uploadLayout.addComponent(newgrid);
//					for(int i=0;i<=50000;i++){
//						update.interrupt();
//					}
					inputStream.close();

					
				}
			}

			catch (Exception e) {

				e.printStackTrace();
			}

		});
		uploadDocument.setImmediate(true);
		uploadDocument.setButtonCaption("Browse");
		uploadDocument.addStyleName("BrowseButtonStyleName");
		uploadGridLayout.addComponent(uploadDocument);

		progressbar = new ProgressBar();
		progressbar.setCaption("Renewal Update In Progress:");
		progressbar.setWidth("300px");
		progressbar.addStyleName(ValoTheme.PROGRESSBAR_POINT);
		uploadGridLayout.addComponent(progressbar);

		buttonHlayout = new HorizontalLayout();
		printIcon = new Image(null, new ThemeResource("img/Print_icon.png"));
		printIcon.addStyleName("printbuttonStyleName");
		buttonHlayout.addComponent(printIcon);

		shareIcon = new Image(null, new ThemeResource("img/Send_Icon.png"));
		shareIcon.addStyleName("sharebuttonStyleName");
		buttonHlayout.addComponent(shareIcon);
		buttonHlayout.setComponentAlignment(shareIcon, Alignment.MIDDLE_CENTER);
		buttonHlayout.setComponentAlignment(printIcon, Alignment.MIDDLE_RIGHT);

		uploadGridLayout.addComponent(buttonHlayout);

		uploadLayout.addComponent(uploadGridLayout);

	}

	private Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();

		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();

		case Cell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();
		}

		return null;
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
