package library.handlers;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import library.dialog.BookDialog;
import library.model.Book;
import library.model.ModelProvider;

public class EditBook extends AbstractHandler {
	
	@SuppressWarnings("unchecked")
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
		if (selection != null & selection instanceof IStructuredSelection) {
			IStructuredSelection strucSelection = (IStructuredSelection) selection;
			for (Iterator<Object> iterator = strucSelection.iterator(); iterator.hasNext();) {
				Book element = (Book) iterator.next();
				editBook(element);
			}
		}
		return null;
	}
	
	
	private void editBook(Book book){
		BookDialog dialog = new BookDialog(new Shell());
		dialog.setInitData(book.getId(), book.getTitle(), book.getAuthors());
		dialog.create();
		if (dialog.open() == Window.OK) {
			ModelProvider.INSTANCE.editBook(dialog.getBook());
		}
	}
}
