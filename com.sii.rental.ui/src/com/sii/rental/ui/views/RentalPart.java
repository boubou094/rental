
package com.sii.rental.ui.views;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.e4.ui.di.Focus;

public class RentalPart {

	private Label rentedObjectLabel, customerNameLabel;

	@PostConstruct
	public void createUi(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		
		Group infoGroup = new Group(parent, SWT.NONE);
		infoGroup.setText("Information");
		infoGroup.setLayout(new GridLayout(2, false));
		
		rentedObjectLabel = new Label(infoGroup, SWT.BORDER);
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = SWT.FILL;
		rentedObjectLabel.setLayoutData(gd);
		rentedObjectLabel.setText("Perceuse Electrique");
		
		customerNameLabel = new Label(infoGroup, SWT.BORDER);
		GridData gdName = new GridData();
		gdName.horizontalSpan = 2;
		gdName.horizontalAlignment = SWT.FILL;
		customerNameLabel.setLayoutData(gdName);
		customerNameLabel.setText("Loué à : John Wayne");
	}

	@Focus
	public void onFocus() {
		rentedObjectLabel.setFocus();
	}

}