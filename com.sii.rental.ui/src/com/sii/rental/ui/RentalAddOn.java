package com.sii.rental.ui;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;

import com.opcoach.training.rental.RentalAgency;
import com.sii.rental.ecore.RentalCoreActivator;

public class RentalAddOn {

	@PostConstruct
	public void initContext(IEclipseContext context) {
		context.set(RentalAgency.class, RentalCoreActivator.getAgency());
	}
	
}

