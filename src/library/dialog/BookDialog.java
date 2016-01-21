package library.dialog;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import library.model.Book;

public class BookDialog extends TitleAreaDialog {
	private Text title;
	private Text author;

	private Long id;
	private String titleStr = "";
	private String authorStr = "";

	private Book book;

	public BookDialog(Shell parentShell) {
		super(parentShell);
	}

	public void setInitData(Long id, String title, String authors){
		this.id = id;
		this.titleStr = title;
		this.authorStr = authors;
	}
	
	public Book getBook() {
		return book;
	}

	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		setTitle("Add a new Book");
		setMessage("Please enter the data of the new book", IMessageProvider.INFORMATION);
		return contents;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

		createTitle(container);
		createAuthor(container);

		return area;
	}

	private void createTitle(Composite container) {
		Label lbtFirstName = new Label(container, SWT.NONE);
		lbtFirstName.setText("Title");

		GridData dataTitle = new GridData();
		dataTitle.grabExcessHorizontalSpace = true;
		dataTitle.horizontalAlignment = GridData.FILL;

		title = new Text(container, SWT.BORDER);
		title.setLayoutData(dataTitle);
		title.setText(titleStr);
	}

	private void createAuthor(Composite container) {
		Label lbtLastName = new Label(container, SWT.NONE);
		lbtLastName.setText("Author");

		GridData dataAuthor = new GridData();
		dataAuthor.grabExcessHorizontalSpace = true;
		dataAuthor.horizontalAlignment = GridData.FILL;

		author = new Text(container, SWT.BORDER);
		author.setLayoutData(dataAuthor);
		author.setText(authorStr);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	private void saveInput() {
		book = new Book(id, title.getText(), author.getText());
		setTitleStr(title.getText());
		setAuthorStr(author.getText());
	}

	@Override
	protected void okPressed() {
		if (title.getText().length() != 0 && author.getText().length() != 0) {
			saveInput();
			super.okPressed();
		} else {
			setErrorMessage("Please enter all data");
		}
	}

	public String getTitleStr() {
		return titleStr;
	}

	public void setTitleStr(String titleStr) {
		this.titleStr = titleStr;
	}

	public String getAuthorStr() {
		return authorStr;
	}

	public void setAuthorStr(String authorStr) {
		this.authorStr = authorStr;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
