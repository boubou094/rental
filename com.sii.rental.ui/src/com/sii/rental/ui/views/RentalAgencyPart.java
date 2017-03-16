package com.sii.rental.ui.views;

import static com.sii.rental.ui.RentalUIConstants.PREF_CUSTOMER_COLOR;
import static com.sii.rental.ui.RentalUIConstants.PREF_RENTAL_COLOR;
import static com.sii.rental.ui.RentalUIConstants.PREF_RENTAL_OBJECT_COLOR;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.di.AboutToHide;
import org.eclipse.e4.ui.di.AboutToShow;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import com.opcoach.training.rental.RentalAgency;
import com.sii.rental.ui.RentalAddOn;
import com.sii.rental.ui.RentalProvider;

@SuppressWarnings("restriction")
public class RentalAgencyPart {

	private TreeViewer treeViewer;
	private RentalProvider provider;
	
	@Inject
	private ESelectionService selectionService;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	@Inject
	public RentalAgencyPart(Composite parent,
			IEclipseContext context,
			@Named(RentalAddOn.RENTAL_AGENCIES) Collection<RentalAgency> rentalAgencies,
			EMenuService menuService) {
		treeViewer = new TreeViewer(parent);

		provider = ContextInjectionFactory.make(RentalProvider.class, context);

		treeViewer.setContentProvider(provider);

		treeViewer.setLabelProvider(provider);

		treeViewer.setInput(rentalAgencies);
		
		treeViewer.addSelectionChangedListener((event) -> {
			ISelection selection = event.getSelection();
			if (IStructuredSelection.class.isInstance(selection)) {
				IStructuredSelection structuredSelection = IStructuredSelection.class.cast(selection);
				selectionService.setSelection(structuredSelection.size() == 1 ? structuredSelection.getFirstElement(): structuredSelection.toArray());
			}
		});
		
		treeViewer.expandAll();

		menuService.registerContextMenu(treeViewer.getControl(), "com.sii.rental.eap.popupmenu.message");
	}
	
	@AboutToShow
	public void aboutToShow(List<MMenuElement> items, EModelService modelService) {
		/*String bc = "bundleclass://com.sii.rental.ui.handlers/com.sii.rental.ui.handlers.MessageHandler";
		String bundle = "platfrom:/plugin/com.sii.rental.eap";
		
		MHandledMenuItem handlerItem = modelService.createModelElement(MHandledMenuItem.class);
		handlerItem.setLabel("Message");*/
	}
	
	@AboutToHide
	public void aboutToHide(List<MMenuElement> items) {
		
	}
	
	@Inject
	public void updateValues(@Preference(value=PREF_CUSTOMER_COLOR) String customerColor,
			@Preference(value=PREF_RENTAL_COLOR) String rentalColor,
			@Preference(value=PREF_RENTAL_OBJECT_COLOR) String rentalObjectColor) {
		if (null != treeViewer && !this.treeViewer.getControl().isDisposed())
		
		treeViewer.refresh();
	}

}
