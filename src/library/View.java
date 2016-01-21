package library;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import library.dialog.BookDialog;
import library.model.Book;
import library.model.ModelProvider;
import library.tableviewer.BookComparator;
import library.tableviewer.BookFilter;

public class View extends ViewPart {
	public View() {
	}

	public static final String ID = "library.view";

	private TableViewer viewer;
	private BookFilter filter;
	private BookComparator comparator;

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		GridLayout layout = new GridLayout(3, false);
		parent.setLayout(layout);
		Label searchLabel = new Label(parent, SWT.NONE);

		searchLabel.setText("Search: ");
		final Text searchText = new Text(parent, SWT.BORDER | SWT.SEARCH);
		searchText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));

		createViewer(parent);

		new Label(parent, SWT.NONE);
		Button btnAddBook = new Button(parent, SWT.PUSH);
		btnAddBook.setText("Add Book");
		btnAddBook.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BookDialog dialog = new BookDialog(getViewSite().getShell());
				dialog.open();
				if (dialog.getBook() != null) {
					ModelProvider.INSTANCE.addBook(dialog.getBook());
				}
			}
		});

		searchText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				filter.setSearchText(searchText.getText());
				viewer.refresh();
			}

		});

		filter = new BookFilter();
		viewer.addFilter(filter);

		comparator = new BookComparator();
		viewer.setComparator(comparator);

	}

	private void createViewer(Composite parent) {
		viewer = new TableViewer(parent, SWT.SINGLE| SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		createColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);
		
		ViewerSupport.bind(viewer, ModelProvider.INSTANCE.getBooks(), BeanProperties.values(new String[] { "title", "authors"}));
		
		MenuManager menuManager = new MenuManager();
		Menu menu = menuManager.createContextMenu(viewer.getTable());
		viewer.getTable().setMenu(menu);
		getSite().registerContextMenu(menuManager, viewer);
		getSite().setSelectionProvider(viewer);
	
	}

	public TableViewer getViewer() {
		return viewer;
	}

	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Title", "Author" };
		int[] bounds = { 150, 150 };

		TableViewerColumn titleCol = createTableViewerColumn(titles[0], bounds[0], 0);
		titleCol.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Book p = (Book) element;
				return p.getTitle();
			}
		});

		TableViewerColumn authorCol = createTableViewerColumn(titles[1], bounds[1], 1);
		authorCol.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Book p = (Book) element;
				return p.getAuthors();
			}
		});

	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		column.addSelectionListener(getSelectionAdapter(column, colNumber));

		return viewerColumn;
	}

	private SelectionAdapter getSelectionAdapter(final TableColumn column, final int index) {
		SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				comparator.setColumn(index);
				int dir = comparator.getDirection();
				viewer.getTable().setSortDirection(dir);
				viewer.getTable().setSortColumn(column);
				viewer.refresh();
			}
		};
		return selectionAdapter;
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}