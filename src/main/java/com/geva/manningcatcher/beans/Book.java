package com.geva.manningcatcher.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.bson.types.ObjectId;

import com.geva.manningcatcher.business.adapters.ObjectIdAdapter;

@XmlRootElement
public class Book {

	private ObjectId id;
	
	private String title;
	
	private Integer times;

	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	@XmlJavaTypeAdapter(ObjectIdAdapter.class)
	@XmlElement
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	@XmlElement
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the times
	 */
	public Integer getTimes() {
		return times;
	}

	/**
	 * @param times the times to set
	 */
	@XmlElement
	public void setTimes(Integer times) {
		this.times = times;
	}
	
}
