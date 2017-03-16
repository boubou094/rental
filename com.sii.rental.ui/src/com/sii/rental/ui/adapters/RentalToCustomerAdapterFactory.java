package com.sii.rental.ui.adapters;

import java.util.Optional;

import org.eclipse.core.runtime.IAdapterFactory;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.Rental;

public class RentalToCustomerAdapterFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		return Optional.of(adaptableObject)
		.filter(Rental.class::isInstance)
		.map(Rental.class::cast)
		.map(Rental::getCustomer)
		.filter(adapterType::isInstance)
		.map(adapterType::cast)
		.orElse(null);
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class[] {Customer.class};
	}

}
