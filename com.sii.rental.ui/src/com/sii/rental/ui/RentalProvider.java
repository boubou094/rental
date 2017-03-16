package com.sii.rental.ui;

import static com.sii.rental.ui.RentalUIConstants.IMG_AGENCY;
import static com.sii.rental.ui.RentalUIConstants.IMG_CUSTOMER;
import static com.sii.rental.ui.RentalUIConstants.IMG_RENTAL;
import static com.sii.rental.ui.RentalUIConstants.IMG_RENTAL_OBJECT;
import static com.sii.rental.ui.RentalUIConstants.PREF_CUSTOMER_COLOR;
import static com.sii.rental.ui.RentalUIConstants.PREF_PALETTE;
import static com.sii.rental.ui.RentalUIConstants.PREF_RENTAL_COLOR;
import static com.sii.rental.ui.RentalUIConstants.PREF_RENTAL_OBJECT_COLOR;
import static com.sii.rental.ui.RentalUIConstants.RENTAL_UI_IMG_REGISTRY;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.opcoach.e4.preferences.ScopedPreferenceStore;
import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.Rental;
import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.RentalObject;

public class RentalProvider extends LabelProvider implements ITreeContentProvider, IColorProvider {

	class AgencyCategory {
		private final String name;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((agency == null) ? 0 : agency.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			AgencyCategory other = (AgencyCategory) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (agency == null) {
				if (other.agency != null)
					return false;
			} else if (!agency.equals(other.agency))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

		private final RentalAgency agency;
		private final Supplier<Object[]> childrenSuplier;
		private final Image image;

		AgencyCategory(String name, RentalAgency agency, Supplier<Object[]> childrenSuplier, Image image) {
			this.name = name;
			this.agency = agency;
			this.childrenSuplier = childrenSuplier;
			this.image = image;
		}

		RentalAgency getRentalAgency() {
			return agency;
		}

		public Object[] getchildren() {
			return childrenSuplier.get();
		}

		public Image getImage() {
			return image;
		}

		@Override
		public String toString() {
			return name;
		}

		private RentalProvider getOuterType() {
			return RentalProvider.this;
		}

	}

	@Inject
	@Named(RENTAL_UI_IMG_REGISTRY)
	private ImageRegistry imageRegistry;

	private IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "com.sii.rental.ui");

	private Map<String, Palette> paletteManager;

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
					new AgencyCategory("Customers", agency, () -> agency.getCustomers().toArray(),
							imageRegistry.get(IMG_CUSTOMER)),
					new AgencyCategory("Locations", agency, () -> agency.getRentals().toArray(),
							imageRegistry.get(IMG_RENTAL)),
					new AgencyCategory("Object à louer", agency, () -> agency.getObjectsToRent().toArray(),
							imageRegistry.get(IMG_RENTAL_OBJECT)) };
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
		if (null != paletteManager) {
			Palette palette = paletteManager.get(this.store.getString(PREF_PALETTE));
			if (null != palette) {
				return palette.getColorProvider().getForeground(element);
			}
		}

		if (RentalAgency.class.isInstance(element)) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
		}

		return Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_FOREGROUND);
	}

	@Inject
	public void setPaletteManager(@Named(RentalAddOn.PALETTE_MANAGER) Object object) {
		if (Map.class.isInstance(object)) {
			@SuppressWarnings("unchecked")
			final Map<String, Palette> manager = (Map<String, Palette>) object;
			this.paletteManager = manager;
		}
	}

	@Override
	public Color getBackground(Object element) {
		if (null != paletteManager) {
			Palette palette = paletteManager.get(this.store.getString(PREF_PALETTE));
			if (null != palette) {
				return palette.getColorProvider().getBackground(element);
			}
		}

		if (RentalAgency.class.isInstance(element)) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
		} else if (Customer.class.isInstance(element)) {
			return this.getColor(this.store.getString(PREF_CUSTOMER_COLOR));
		} else if (Rental.class.isInstance(element)) {
			return this.getColor(this.store.getString(PREF_RENTAL_COLOR));
		} else if (RentalObject.class.isInstance(element)) {
			return this.getColor(this.store.getString(PREF_RENTAL_OBJECT_COLOR));
		}

		return Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
	}

	@Override
	public Image getImage(Object element) {
		if (RentalAgency.class.isInstance(element)) {
			return imageRegistry.get(IMG_AGENCY);
		} else if (Customer.class.isInstance(element)) {
			return imageRegistry.get(IMG_CUSTOMER);
		} else if (RentalObject.class.isInstance(element)) {
			return imageRegistry.get(IMG_RENTAL_OBJECT);
		} else if (Rental.class.isInstance(element)) {
			return imageRegistry.get(IMG_RENTAL);
		} else if (AgencyCategory.class.isInstance(element)) {
			return AgencyCategory.class.cast(element).getImage();
		}

		return super.getImage(element);
	}

	private Color getColor(String rgbKey) {
		ColorRegistry colorRegistry = JFaceResources.getColorRegistry();

		Optional<Color> col = Optional.ofNullable(colorRegistry.get(rgbKey));

		if (!col.isPresent()) {
			colorRegistry.put(rgbKey, StringConverter.asRGB(rgbKey));
			col = Optional.of(colorRegistry.get(rgbKey));
		}

		return col.get();
	}

}
