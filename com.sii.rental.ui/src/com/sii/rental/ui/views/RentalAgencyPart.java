package com.sii.rental.ui.views;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import com.opcoach.training.rental.RentalAgency;
import com.sii.rental.ui.RentalAddOn;
import com.sii.rental.ui.RentalProvider;

public class RentalAgencyPart {

	@PostConstruct
	public void createUi(Composite parent,
			IEclipseContext context,
			@Named(RentalAddOn.RENTAL_AGENCIES) Collection<RentalAgency> rentalAgencies) {
		TreeViewer treeViewer = new TreeViewer(parent);

		RentalProvider provider = ContextInjectionFactory.make(RentalProvider.class, context);

		treeViewer.setContentProvider(provider);

		treeViewer.setLabelProvider(provider);

		treeViewer.setInput(rentalAgencies);
		
		treeViewer.expandAll();
	}

}
