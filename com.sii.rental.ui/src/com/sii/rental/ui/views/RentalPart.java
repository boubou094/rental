
package com.sii.rental.ui.views;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.opcoach.training.rental.Rental;
import com.sii.rental.ecore.RentalCoreActivator;
import org.eclipse.swt.layout.GridLayout;

public class RentalPart {

	private Label rentedObjectLabel, customerNameLabel;
	private Group rentalLocationDates;
	private Label rentalDateToLabel;
	private Label rentalDateToEdit;
	private Label rentalDateFromEdit;

	public void setRental(Rental rental) {
		rentedObjectLabel.setText(rental.getRentedObject().getName());
		customerNameLabel.setText("Loué à : " + rental.getCustomer().getLastName() + " " + rental.getCustomer().getFirstName());
		rentalDateFromEdit.setText(rental.getStartDate().toString());
		rentalDateToEdit.setText(rental.getEndDate().toString());
	}
	
	@PostConstruct
	public void createUi(Composite parent) {
		parent.setLayout(GridLayoutFactory
				.fillDefaults()
				.numColumns(1)
				.equalWidth(false)
				.create());
		
		Group infoGroup = new Group(parent, SWT.NONE);
		infoGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		infoGroup.setText("Information");
		infoGroup.setLayout(GridLayoutFactory
				.fillDefaults()
				.numColumns(2)
				.equalWidth(false)
				.create());
		
		rentedObjectLabel = new Label(infoGroup, SWT.BORDER);
		GridData gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = SWT.FILL;
		rentedObjectLabel.setLayoutData(gd);
		//rentedObjectLabel.setText("Perceuse Electrique");
		
		customerNameLabel = new Label(infoGroup, SWT.BORDER);
		GridData gdName = new GridData();
		gdName.grabExcessHorizontalSpace = true;
		gdName.horizontalSpan = 2;
		gdName.horizontalAlignment = SWT.FILL;
		customerNameLabel.setLayoutData(gdName);
		
		rentalLocationDates = new Group(parent, SWT.NONE);
		rentalLocationDates.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		rentalLocationDates.setText("Dates de location");
		rentalLocationDates.setLayout(new GridLayout(2, false));
		
		Label rentalDateFromLabel = new Label(rentalLocationDates, SWT.NONE);
		rentalDateFromLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		rentalDateFromLabel.setText("Du :");
		
		rentalDateFromEdit = new Label(rentalLocationDates, SWT.NONE);
		rentalDateFromEdit.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		rentalDateFromEdit.setSize(55, 15);
		rentalDateFromEdit.setText("xx/xx/xxxx");
		
		rentalDateToLabel = new Label(rentalLocationDates, SWT.NONE);
		rentalDateToLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		rentalDateToLabel.setSize(19, 15);
		rentalDateToLabel.setText("Au :");
		
		rentalDateToEdit = new Label(rentalLocationDates, SWT.NONE);
		rentalDateToEdit.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		rentalDateToEdit.setText("xx/xx/xxxx");
		//customerNameLabel.setText("Loué à : John Wayne");
		
		setRental(RentalCoreActivator.getAgency().getRentals().get(0));
	}

	@Focus
	public void onFocus() {
		rentedObjectLabel.setFocus();
	}
}