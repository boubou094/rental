package com.sii.rental.ui.views;

import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.opcoach.training.rental.Customer;

public class CustomerPart {
	private Composite parent;
	private Label title;
	private Set<Label> customerNameLabels;

	@Inject
	public CustomerPart(MPart part, Composite parent) {
		this.parent = parent;
		parent.setLayout(GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(false).create());

		Label title = new Label(parent, SWT.NONE);
		title.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		title.setSize(19, 15);
		title.setText("Pas de client sélectionné.");
	}

	public void setCustomers(Set<Customer> customers) {
		customerNameLabels.forEach(Label::dispose);
		
		customerNameLabels = customers.stream()
			.map(Customer::getDisplayName)
			.map((name) -> {
				Label label = new Label(this.parent, SWT.NONE);
				label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				label.setSize(19, 15);
				label.setText(name);
				return label;
			})
			.collect(Collectors.toSet());
	}
}
