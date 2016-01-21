package library.view;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.SWTResourceManager;

import library.model.Book;

public class DetailsBook extends ViewPart {

	public DetailsBook() {

	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		Label lblInformationAboutBook = new Label(parent, SWT.NONE);
		lblInformationAboutBook.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblInformationAboutBook.setText("Info");
		new Label(parent, SWT.NONE);

		Label lblTitle = new Label(parent, SWT.NONE);
		lblTitle.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblTitle.setText("Title:");

		Label lblTitleShow = new Label(parent, SWT.NONE);
		lblTitleShow.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		GridData gd_lblTitleShow = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblTitleShow.widthHint = 537;
		lblTitleShow.setLayoutData(gd_lblTitleShow);
		lblTitleShow.setText("title");

		Label lblAuthor = new Label(parent, SWT.NONE);
		lblAuthor.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblAuthor.setText("Author:");

		Label lblAuthorShow = new Label(parent, SWT.NONE);
		lblAuthorShow.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		GridData gd_lblAuthorShow = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblAuthorShow.widthHint = 535;
		lblAuthorShow.setLayoutData(gd_lblAuthorShow);
		lblAuthorShow.setText("author");

		Label lblDetails = new Label(parent, SWT.NONE);
		lblDetails.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblDetails.setText("Details:");

		Label lblDetailsShow = new Label(parent, SWT.NONE);
		GridData gd_lblDetailsShow = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblDetailsShow.widthHint = 534;
		lblDetailsShow.setLayoutData(gd_lblDetailsShow);
		lblDetailsShow.setText("details");

		getSite().getPage().addSelectionListener(new ISelectionListener() {
			@Override
			public void selectionChanged(IWorkbenchPart part, ISelection selection) {
				if (selection != null & selection instanceof IStructuredSelection) {
					IStructuredSelection strucSelection = (IStructuredSelection) selection;
					Object o = strucSelection.getFirstElement();
					if (o instanceof Book) {
						Book element = (Book) o;
						lblTitleShow.setText(element.getTitle());
						lblAuthorShow.setText(element.getAuthors());
						lblDetailsShow.setText(element.getDetails());
					}
				}
			}
		});

	}

	@Override
	public void setFocus() {

	}
}
