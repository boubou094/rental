package com.sii.rental.ui;

import static com.sii.rental.ui.RentalUIConstants.IMG_AGENCY;
import static com.sii.rental.ui.RentalUIConstants.IMG_CUSTOMER;
import static com.sii.rental.ui.RentalUIConstants.IMG_RENTAL;
import static com.sii.rental.ui.RentalUIConstants.IMG_RENTAL_OBJECT;
import static com.sii.rental.ui.RentalUIConstants.RENTAL_UI_IMG_REGISTRY;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.IColorProvider;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.RentalAgency;
import com.sii.rental.ecore.RentalCoreActivator;

public class RentalAddOn {

	public static final String RENTAL_AGENCIES = "com.opcoach.training.rental.RentalAgency (Collection)";
	public static final String PALETTE_MANAGER = "com.opcoach.training.rental.pallette.service (Map)";
	
	private final Map<String, Palette> paletteManager = new HashMap<>();
	
	@PostConstruct
	public void initContext(IEclipseContext context) {
		context.set(RentalAgency.class, RentalCoreActivator.getAgency());
		context.set(RENTAL_AGENCIES, Stream.of(RentalCoreActivator.getAgency(), RentalCoreActivator.getAgency()).collect(Collectors.toList()));
		context.set(RENTAL_UI_IMG_REGISTRY, getLocalImageRegistry());
		context.set(PALETTE_MANAGER, paletteManager);
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
	
	@Inject @Optional
	void reactOnRentalEvent(@UIEventTopic("rental/copy") Customer customer) {
		System.out.println(customer.getDisplayName());
	}
	
	@Inject
	public void getExtensionQuickAccess(IExtensionRegistry registry) {
		IConfigurationElement[] elements = registry.getConfigurationElementsFor("org.eclipse.e4.workbench.model");
		
		Arrays.stream(elements)
			.filter((e) -> e.getName().equals("fragment"))
			.map((e) -> e.getNamespaceIdentifier() + ":" + e.getAttribute("uri"))
			.forEach(System.out::println);
		
		Arrays.stream(elements)
		.filter((e) -> e.getName().equals("processor"))
		.map((e) -> e.getNamespaceIdentifier() + ":" + e.getAttribute("class"))
		.forEach(System.out::println);
					
	}
	
	@Inject
	public void initPaletteManager(IExtensionRegistry registry) {
		IConfigurationElement[] elements = registry.getConfigurationElementsFor("com.sii.rental.ui.palette");
		
		Arrays.stream(elements)
			.filter((e) -> e.getName().equals("palette"))
			.map((e) -> {
				String id = e.getAttribute("id");
				String name = e.getAttribute("name");
				
				Palette  palette = new Palette();
				palette.setId(id);
				palette.setName(name);
				try {
					palette.setColorProvider(IColorProvider.class.cast(e.createExecutableExtension("paletteClass")));
				} catch (CoreException e1) {
					return null;
				}
				
				return palette;
			})
			.filter(Objects::nonNull)
			.forEach((palette) -> paletteManager.put(palette.getId(), palette));
	}
	
}

