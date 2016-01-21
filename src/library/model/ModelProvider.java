package library.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;

public enum ModelProvider {
	INSTANCE;

	private List<Book> books = new ArrayList<Book>();
	private RestClient restClient = new RestClient();
	private WritableList writableList;
	
	
	private ModelProvider() {
		books = restClient.getAllBooks();
		setBookDetails();
		writableList = new WritableList(books, Book.class);
	}
	
	private void setBookDetails(){
		for(Book b : books){
			b.setTextDetail();
		}
	}

	public WritableList getBooks() {
		return writableList;
	}

	public void addBook(Book book) {
		restClient.addBook(book);
		loadData();
	}
	
	public void deleteBook(Book book) {
		restClient.deleteBook(book);
		loadData();
	}
	
	public void editBook(Book book) {
		restClient.editBook(book);
		loadData();
	}

	private void loadData(){
		books = restClient.getAllBooks();
		setBookDetails();
		writableList.clear();
		writableList.addAll(books);
	}
	
	public WritableList getWritableList() {
		return writableList;
	}

	public void setWritableList(WritableList writableList) {
		this.writableList = writableList;
	}
	
}
