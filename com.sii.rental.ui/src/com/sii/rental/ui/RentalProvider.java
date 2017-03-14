package com.sii.rental.ui;

import java.util.Collection;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.RentalObject;

public class RentalProvider extends LabelProvider implements ITreeContentProvider, IColorProvider {

	class AgencyCategory {
		private final String name;
		private final RentalAgency agency;
		private final Supplier<Object[]> childrenSuplier;
		
		AgencyCategory(String name, RentalAgency agency, Supplier<Object[]> childrenSuplier) {
			this.name = name;
			this.agency = agency;
			this.childrenSuplier = childrenSuplier;
		}
		
		RentalAgency getRentalAgency() {
			return agency;
		}
		
		public Object[] getchildren() {
			return childrenSuplier.get();
		}

		@Override
		public String toString() {
			return name;
		}
		
	}
	
	@Override
	public String getText(Object element) {
		if (RentalAgency.class.isInstance(element)) {
			final RentalAgency agency = RentalAgency.class.cast(element);
			return agency.getName();
		} else if (Customer.class.isInstance(element)) {
			final Customer customer = Customer.class.cast(element);
			return customer.getDisplayName();
		} else if (AgencyCategory.class.isInstance(element)) {
			final AgencyCategory customer = AgencyCategory.class.cast(element);
			return customer.toString();
		} else if (RentalObject.class.isInstance(element)) {
			final RentalObject rentalObject = RentalObject.class.cast(element);
			return rentalObject.getName();
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
			AgencyCategory[] agencyCategories = new AgencyCategory[] {
				new AgencyCategory("Customers", agency, () -> agency.getCustomers().toArray()),
				new AgencyCategory("Locations", agency, () -> agency.getRentals().toArray()),
				new AgencyCategory("Object à louer", agency, () -> agency.getObjectsToRent().toArray())
			};
			return agencyCategories;
		} else if (AgencyCategory.class.isInstance(parentElement)) {
			return AgencyCategory.class.cast(parentElement).getchildren();
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
		} else if (AgencyCategory.class.isInstance(element)) {
			return true;
		}

		return false;
	}

	@Override
	public Color getForeground(Object element) {
		if (RentalAgency.class.isInstance(element)) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
		}
		
		return Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_FOREGROUND);
	}

	@Override
	public Color getBackground(Object element) {
		if (RentalAgency.class.isInstance(element)) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
		}
		
		return Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
	}

}
