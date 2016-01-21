package library.model;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Shell;
import org.glassfish.jersey.client.ClientConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import library.util.ErrorStatus;

public class RestClient {

	private static final String GET = "http://localhost:9721/workshop/books-by-title/?titlePrefix=";
	private static final String URL = "http://localhost:9721/workshop/book";
	private static final String DELETE = "http://localhost:9721/workshop/delete-book";

	private ObjectMapper mapper = new ObjectMapper();
	private ClientConfig config = new ClientConfig();
	private Client client = ClientBuilder.newClient(config);

	public RestClient() {

	}

	public List<Book> getAllBooks() {
		List<Book> result = new ArrayList<Book>();
		try {
			WebTarget target = client.target(getBaseURI(GET));
			String response = target.request().accept(MediaType.APPLICATION_JSON).get(Response.class)
					.readEntity(String.class);

			Book[] books = mapper.readValue(response, Book[].class);
			for (Book book : books) {
				result.add(book);
			}

		} catch (Exception e) {
			MultiStatus status = ErrorStatus.createMultiStatus(e.getLocalizedMessage(), e);
			ErrorDialog.openError(new Shell(), "Error", "This is an error", status);
		}

		return result;
	}

	public void addBook(Book book) {
		try {
			WebTarget target = client.target(getBaseURI(URL));
			String jsonBook = mapper.writeValueAsString(book);
			@SuppressWarnings("unused")
			Response response = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.json(jsonBook),
					Response.class);

		} catch (Exception e) {
			MultiStatus status = ErrorStatus.createMultiStatus(e.getLocalizedMessage(), e);
			ErrorDialog.openError(new Shell(), "Error", "This is an error", status);
		}
	}

	public void editBook(Book book) {
		try {
			WebTarget target = client.target(getBaseURI(URL));
			String jsonBook = mapper.writeValueAsString(book);
			@SuppressWarnings("unused")
			Response response = target.request().accept(MediaType.APPLICATION_JSON).put(Entity.json(jsonBook),
					Response.class);

		} catch (JsonProcessingException e) {
			MultiStatus status = ErrorStatus.createMultiStatus(e.getLocalizedMessage(), e);
			ErrorDialog.openError(new Shell(), "Error", "This is an error", status);
		}
	}

	public void deleteBook(Book book) {
		try {
			WebTarget target = client.target(getBaseURI(DELETE));
			String jsonBook = mapper.writeValueAsString(book);
			@SuppressWarnings("unused")
			Response response = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.json(jsonBook),
					Response.class);

		} catch (Exception e) {
			MultiStatus status = ErrorStatus.createMultiStatus(e.getLocalizedMessage(), e);
			ErrorDialog.openError(new Shell(), "Error", "This is an error", status);
		}
	}

	private static URI getBaseURI(String URL) {
		return UriBuilder.fromUri(URL).build();
	}

}
