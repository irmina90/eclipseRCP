package library.tableviewer;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import library.model.Book;

public class BookFilter extends ViewerFilter {

	private String searchString;

	public void setSearchText(String s) {
		this.searchString = ".*" + s + ".*";
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (searchString == null || searchString.length() == 0) {
			return true;
		}
		Book p = (Book) element;
		if (p.getTitle().matches(searchString)) {
			return true;
		}
		if (p.getAuthors().matches(searchString)) {
			return true;
		}

		return false;
	}
}
