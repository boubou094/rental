
package com.sii.rental.ui.views;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.Rental;
import com.opcoach.training.rental.RentalAgency;

public class RentalPart {

	private Label rentedObjectLabel, customerNameLabel;
	private Group rentalLocationDates;
	private Label rentalDateToLabel;
	private Label rentalDateToEdit;
	private Label rentalDateFromEdit;
	private String partId;

	public void setRental(Rental rental) {
		if (java.util.Optional.ofNullable(rental).isPresent()) {
			rentedObjectLabel.setText(rental.getRentedObject().getName());
			customerNameLabel.setText(
					"Loué à : " + rental.getCustomer().getLastName() + " " + rental.getCustomer().getFirstName());
			rentalDateFromEdit.setText(rental.getStartDate().toString());
			rentalDateToEdit.setText(rental.getEndDate().toString());
		} else {
			rentedObjectLabel.setText("");
			customerNameLabel.setText("Loué à : ");
			rentalDateFromEdit.setText("");
			rentalDateToEdit.setText("");
		}
	}

	private void setCustomer(Set<Customer> customers) {
		customerNameLabel.setText("Loué à : "
				+ customers.stream().map(Customer::getDisplayName).reduce((n1, n2) -> n1 + "; " + n2).orElse(""));
	}

	@SuppressWarnings("unused")
	@Inject
	private ESelectionService selectionService;

	@Inject
	public RentalPart(MPart part, Composite parent, RentalAgency rentalAgency) {
		partId = part.getElementId();
		parent.setLayout(GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(false).create());

		Group infoGroup = new Group(parent, SWT.NONE);
		infoGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		infoGroup.setText("Information");
		infoGroup.setLayout(GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).create());

		rentedObjectLabel = new Label(infoGroup, SWT.BORDER);
		GridData gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = SWT.FILL;
		rentedObjectLabel.setLayoutData(gd);
		// rentedObjectLabel.setText("Perceuse Electrique");

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
		// customerNameLabel.setText("Loué à : John Wayne");

		setRental(rentalAgency.getRentals().get(0));
	}

	@Focus
	public void onFocus() {
		rentedObjectLabel.setFocus();
	}

	@Inject
	@Optional
	public void receiveSelection(@Named(IServiceConstants.ACTIVE_SELECTION) Rental rental,
			@Named(IServiceConstants.ACTIVE_PART) MPart part) {
		if (java.util.Optional.ofNullable(part)
			.filter((p) -> 0 != partId.compareTo(part.getElementId()))
			.isPresent()) {
			this.setRental(rental);
		}
	}
	
	@Inject
	@Optional
	public void receiveSelection(@Named(IServiceConstants.ACTIVE_SELECTION) Customer customer,
			@Named(IServiceConstants.ACTIVE_PART) MPart part) {
		if (java.util.Optional.ofNullable(part)
			.filter((p) -> 0 != partId.compareTo(part.getElementId()))
			.isPresent()) {
			this.setCustomer(java.util.Optional.ofNullable(customer)
					.map((c) -> Stream.of(c))
					.orElse(Stream.empty())
					.collect(Collectors.toSet()));
		}
	}

	@Inject
	@Optional
	public void selectCustomers(@Named(IServiceConstants.ACTIVE_SELECTION) Object[] objects) {
		java.util.Optional.ofNullable(objects).ifPresent((customers) -> this.setCustomer(Arrays.stream(customers)
				.filter(Customer.class::isInstance).map(Customer.class::cast).collect(Collectors.toSet())));
	}

}