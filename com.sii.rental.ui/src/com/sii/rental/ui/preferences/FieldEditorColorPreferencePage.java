package com.sii.rental.ui.preferences;

import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;

import com.sii.rental.ui.RentalUIConstants;

public class FieldEditorColorPreferencePage extends FieldEditorPreferencePage implements RentalUIConstants {

	public FieldEditorColorPreferencePage() {
		super(GRID);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void createFieldEditors() {
		addField(new ColorFieldEditor(PREF_CUSTOMER_COLOR, "Customers : ", getFieldEditorParent()));
		addField(new ColorFieldEditor(PREF_RENTAL_COLOR, "Rentals : ", getFieldEditorParent()));
		addField(new ColorFieldEditor(PREF_RENTAL_OBJECT_COLOR, "Rental Objects : ", getFieldEditorParent()));
	}

}
