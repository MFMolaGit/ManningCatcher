package com.geva.manningcatcher.beans;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.bson.types.ObjectId;

public class Pack {

	private ObjectId id;
	
	private List<String> books;
	
	private Integer times;

	public Pack() {
		books = new ArrayList<String>();
	}
	
	/**
	 * @return the books
	 */
	public List<String> getBooks() {
		return books;
	}

	/**
	 * @param books the books to set
	 */
	@XmlElementWrapper(name = "books")
	@XmlElement(name = "book")	
	public void setBooks(List<String> books) {
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

	@XmlElement
	public void setId(ObjectId id) {
		this.id = id;
	}

	public void addBook(String title) {
		books.add(title);
	}
	
	public void addTime() {
		times++;
	}
	
}
