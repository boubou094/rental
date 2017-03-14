package com.sii.rental.ui;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;

import com.opcoach.training.rental.RentalAgency;
import com.sii.rental.ecore.RentalCoreActivator;

public class RentalAddOn {

	public static final String RENTAL_AGENCIES = "com.opcoach.training.rental.RentalAgency (Collection)"; 
	
	@PostConstruct
	public void initContext(IEclipseContext context) {
		context.set(RentalAgency.class, RentalCoreActivator.getAgency());
		context.set(RENTAL_AGENCIES, Stream.of(RentalCoreActivator.getAgency()).collect(Collectors.toList()));
	}
	
}

