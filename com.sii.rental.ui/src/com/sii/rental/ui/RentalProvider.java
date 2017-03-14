package com.sii.rental.ui;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.RentalAgency;

public class RentalProvider extends LabelProvider implements ITreeContentProvider {

	@Override
	public String getText(Object element) {
		if (RentalAgency.class.isInstance(element)) {
			final RentalAgency agency = RentalAgency.class.cast(element);
			return agency.getName();
		} else if (Customer.class.isInstance(element)) {
			final Customer customer = Customer.class.cast(element);
			return customer.getDisplayName();
		}

		return super.getText(element);
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (Collection.class.isInstance(inputElement)) {
			return Collection.class.cast(inputElement).toArray();
		}

		return new Object[0];
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (RentalAgency.class.isInstance(parentElement)) {
			final RentalAgency agency = RentalAgency.class.cast(parentElement);
			return agency.getCustomers().toArray();
		}

		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		if (EObject.class.isInstance(element)) {
			final EObject eObject = EObject.class.cast(element);
			return eObject.eContainer();
		} else if (Customer.class.isInstance(element)) {
			final Customer customer = Customer.class.cast(element);
			return customer.getParentAgency();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (RentalAgency.class.isInstance(element)) {
			final RentalAgency agency = RentalAgency.class.cast(element);
			return !agency.getCustomers().isEmpty();
		}

		return false;
	}

}
