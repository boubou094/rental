package com.sii.rental.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;

import com.opcoach.e4.preferences.ScopedPreferenceStore;
import com.sii.rental.ui.RentalUIConstants;
import org.eclipse.swt.graphics.RGB;

public class RentalPreferenceInitializer extends AbstractPreferenceInitializer implements RentalUIConstants {

	public RentalPreferenceInitializer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeDefaultPreferences() {
		// TODO Auto-generated method stub
		IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "com.sii.rental.ui");
		
		store.setDefault(PREF_CUSTOMER_COLOR, StringConverter.asString(new RGB(255, 0, 0)));
		store.setDefault(PREF_RENTAL_COLOR, StringConverter.asString(new RGB(0, 255, 0)));
		store.setDefault(PREF_RENTAL_OBJECT_COLOR, StringConverter.asString(new RGB(0, 0, 255)));
	}

}
