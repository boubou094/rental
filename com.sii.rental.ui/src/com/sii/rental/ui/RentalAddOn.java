package com.sii.rental.ui;

import static com.sii.rental.ui.RentalUIConstants.IMG_AGENCY;
import static com.sii.rental.ui.RentalUIConstants.IMG_CUSTOMER;
import static com.sii.rental.ui.RentalUIConstants.IMG_RENTAL;
import static com.sii.rental.ui.RentalUIConstants.IMG_RENTAL_OBJECT;
import static com.sii.rental.ui.RentalUIConstants.RENTAL_UI_IMG_REGISTRY;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.opcoach.training.rental.RentalAgency;
import com.sii.rental.ecore.RentalCoreActivator;

public class RentalAddOn {

	public static final String RENTAL_AGENCIES = "com.opcoach.training.rental.RentalAgency (Collection)"; 
	
	@PostConstruct
	public void initContext(IEclipseContext context) {
		context.set(RentalAgency.class, RentalCoreActivator.getAgency());
		context.set(RENTAL_AGENCIES, Stream.of(RentalCoreActivator.getAgency(), RentalCoreActivator.getAgency()).collect(Collectors.toList()));
		context.set(RENTAL_UI_IMG_REGISTRY, getLocalImageRegistry());
	}
	
	ImageRegistry getLocalImageRegistry() {
		Bundle b = FrameworkUtil.getBundle(this.getClass());
		
		ImageRegistry registry = new ImageRegistry();
		
		registry.put(IMG_CUSTOMER, ImageDescriptor.createFromURL(b.getEntry(IMG_CUSTOMER)));
		registry.put(IMG_RENTAL, ImageDescriptor.createFromURL(b.getEntry(IMG_RENTAL)));
		registry.put(IMG_RENTAL_OBJECT, ImageDescriptor.createFromURL(b.getEntry(IMG_RENTAL_OBJECT)));
		registry.put(IMG_AGENCY, ImageDescriptor.createFromURL(b.getEntry(IMG_AGENCY)));
		
		return registry;
	}
	
}

