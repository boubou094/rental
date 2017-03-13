
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

public class RentalPart {

	private Label rentedObjectLabel, customerNameLabel;

	public void setRental(Rental rental) {
		rentedObjectLabel.setText(rental.getRentedObject().getName());
		customerNameLabel.setText("Loué à : " + rental.getCustomer().getLastName() + " " + rental.getCustomer().getFirstName());
	}
	
	@PostConstruct
	public void createUi(Composite parent) {
		parent.setLayout(GridLayoutFactory
				.fillDefaults()
				.numColumns(1)
				.equalWidth(false)
				.create());
		
		Group infoGroup = new Group(parent, SWT.NONE);
		infoGroup.setText("Information");
		infoGroup.setLayout(GridLayoutFactory
				.fillDefaults()
				.numColumns(2)
				.equalWidth(false)
				.create());
		
		rentedObjectLabel = new Label(infoGroup, SWT.BORDER);
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = SWT.FILL;
		rentedObjectLabel.setLayoutData(gd);
		//rentedObjectLabel.setText("Perceuse Electrique");
		
		customerNameLabel = new Label(infoGroup, SWT.BORDER);
		GridData gdName = new GridData();
		gdName.horizontalSpan = 2;
		gdName.horizontalAlignment = SWT.FILL;
		customerNameLabel.setLayoutData(gdName);
		//customerNameLabel.setText("Loué à : John Wayne");
		
		setRental(RentalCoreActivator.getAgency().getRentals().get(0));
	}

	@Focus
	public void onFocus() {
		rentedObjectLabel.setFocus();
	}

}