package com.geva.manningcatcher.beans;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.geva.manningcatcher.business.DateAdapter;

@XmlRootElement
public class Offer {
	
	private String offercode;
	
	private Pack pack;
	
	private Date readDate;
	
	public Offer() {
		pack = new Pack();
	}
	
	public String getOffercode() {
		return offercode;
	}

	@XmlElement
	public void setOffercode(String offercode) {
		this.offercode = offercode;
	}
	
	public Date getReadDate() {
		return readDate;
	}
	
	@XmlJavaTypeAdapter(DateAdapter.class)
	@XmlElement
	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}
	
	public void addBook(String title) {
		pack.addBook(title);
	}
	
	public List<String> getBooks() {
		return pack.getBooks();
	}
	
	public Pack getPack() {
		return pack;
	}
	
	@XmlElement
	public void setPack(Pack pack) {
		this.pack = pack;
	}
	
	public String toString() {
		
		StringBuilder results = new StringBuilder("FECHA: ")
			.append(DateAdapter.getDateFormat().format(readDate))
			.append('\n')
			.append("CODIGO:")
			.append('\t')
			.append(offercode)
			.append('\n')
			.append("LIBROS:");
			
		for(String book:pack.getBooks()) {
			results.append('\t')
					.append(book)
					.append('\n');
		}
		
		return results.toString();
	}
}
