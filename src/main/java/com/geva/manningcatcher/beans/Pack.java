package com.geva.manningcatcher.beans;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.bson.types.ObjectId;

import com.geva.manningcatcher.business.ObjectIdAdapter;

@XmlRootElement
public class Pack {

	private ObjectId id;
	
	private List<Book> books;
	
	private Integer times;

	public Pack() {
		books = new ArrayList<Book>();
	}
	
	/**
	 * @return the books
	 */
	public List<Book> getBooks() {
		return books;
	}

	/**
	 * @param books the books to set
	 */
	@XmlElement(name = "books")	
	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public Integer getTimes() {
		return times;
	}

	@XmlElement
	public void setTimes(Integer times) {
		this.times = times;
	}
	
	public ObjectId getId() {
		return id;
	}

	@XmlJavaTypeAdapter(ObjectIdAdapter.class)
	@XmlElement
	public void setId(ObjectId id) {
		this.id = id;
	}

	public void addBook(Book book) {
		books.add(book);
	}
	
	public void addTime() {
		times++;
	}
	
}
