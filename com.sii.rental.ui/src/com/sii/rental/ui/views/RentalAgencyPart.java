package com.sii.rental.ui.views;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import com.opcoach.training.rental.RentalAgency;
import com.sii.rental.ui.RentalAddOn;
import com.sii.rental.ui.RentalProvider;

public class RentalAgencyPart {

	@PostConstruct
	public void createUi(Composite parent, @Named(RentalAddOn.RENTAL_AGENCIES) Collection<RentalAgency> rentalAgencies) {
		TreeViewer treeViewer = new TreeViewer(parent);

		RentalProvider provider = new RentalProvider();

		treeViewer.setContentProvider(provider);

		treeViewer.setLabelProvider(provider);

		treeViewer.setInput(rentalAgencies);
		
		treeViewer.expandAll();
	}

}
