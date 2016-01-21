package library.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Book {
	private Long id;
	private String title;
	private String authors;
	private String details;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public Book() {

	}

	public Book(String title, String authors) {
		this.title = title;
		this.authors = authors;
	}

	public Book(Long id, String title, String authors) {
		this.setId(id);
		this.title = title;
		this.authors = authors;
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
	
	public void setTextDetail(){
		this.details = createTextDetail();
	}

	private String createTextDetail(){
		StringBuilder detail = new StringBuilder();
		detail.append("This book ").append(title).append(" was written by ").append(authors).append(".");
		return detail.toString();	
	}
	
	public String getTitle() {
		return title;
	}

	public String getAuthors() {
		return authors;
	}
	
	public void setId(Long id) {
		propertyChangeSupport.firePropertyChange("id", this.id, this.id = id);
	}

	public void setTitle(String title) {
		propertyChangeSupport.firePropertyChange("title", this.title, this.title = title);
	}

	public void setAuthor(String authors) {
		propertyChangeSupport.firePropertyChange("authors", this.authors, this.authors = authors);
	}
	
	public String getDetails() {
		return details;
	}

	public Long getId() {
		return id;
	}

}
