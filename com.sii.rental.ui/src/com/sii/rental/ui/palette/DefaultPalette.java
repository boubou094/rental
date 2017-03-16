package com.sii.rental.ui.palette;

import static com.sii.rental.ui.RentalUIConstants.PREF_CUSTOMER_COLOR;
import static com.sii.rental.ui.RentalUIConstants.PREF_RENTAL_COLOR;
import static com.sii.rental.ui.RentalUIConstants.PREF_RENTAL_OBJECT_COLOR;

import java.util.Optional;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import com.opcoach.e4.preferences.ScopedPreferenceStore;
import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.Rental;
import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.RentalObject;

public class DefaultPalette implements IColorProvider {

	private IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "com.sii.rental.ui");
	
	private Color getColor(String rgbKey) {
		ColorRegistry colorRegistry = JFaceResources.getColorRegistry();
		
		Optional<Color> col = Optional.ofNullable(colorRegistry.get(rgbKey));
		
		if (!col.isPresent()) {
			colorRegistry.put(rgbKey, StringConverter.asRGB(rgbKey));
			col = Optional.of(colorRegistry.get(rgbKey));
		}
		
		return col.get();
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
		} else if (Customer.class.isInstance(element)) {
			return this.getColor(this.store.getString(PREF_CUSTOMER_COLOR));
		} else if (Rental.class.isInstance(element)) {
			return this.getColor(this.store.getString(PREF_RENTAL_COLOR));
		} else if (RentalObject.class.isInstance(element)) {
			return this.getColor(this.store.getString(PREF_RENTAL_OBJECT_COLOR));
		}
		
		return Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
	}
}
