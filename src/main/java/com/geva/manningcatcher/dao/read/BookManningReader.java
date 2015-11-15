package com.geva.manningcatcher.dao.read;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.geva.manningcatcher.beans.Book;
import com.geva.manningcatcher.beans.New;
import com.geva.manningcatcher.utils.BookNodes;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@Component(value="bookReader")
public class BookManningReader implements ManningReader<Book>, BookNodes {

	@Autowired
	@Qualifier(value="booksCollection")
	private MongoCollection<Document> booksCollection;
	
//	public BookManningReader(MongoCollection<Document> collection) {
//		this.booksCollection = collection;
//	}
	
	@Override
	public New<Book> read() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book read(String field, String value) {
		
		ObjectId oId = null;
		boolean isIdField = false;
		
		if(BookNodes.BOOKID.equals(field)) {
			isIdField = true;
			oId = new ObjectId(value);
		}
		
		List<Document> booksFound = booksCollection.find(new Document(field, isIdField?oId:value)).into(new ArrayList<Document>());
		Book book = new Book();
		
		if(!booksFound.isEmpty()) {
			Document dBook = booksFound.get(0); 
			book.setId(dBook.getObjectId(BOOKID));
			book.setTitle(dBook.getString(TITLE));
			book.setTimes(dBook.getInteger(TIMES));
		} else {
			if(isIdField) {
				book.setId(oId);
			} else {
				book.setTitle(value);
			}
			book.setTimes(1);
		}
			
		return book;
	}
	
	@Override
	public List<Book> readAll() {
		FindIterable<Document> packsFound = booksCollection.find();
		final List<Book> books = new ArrayList<Book>();
		
		packsFound.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document result) {
		    	Book book = new Book();
		    	
		    	book.setId(result.getObjectId(BOOKID));
		    	book.setTitle(result.getString(TITLE));
		    	book.setTimes(result.getInteger(TIMES));
		    	
		    	books.add(book);
		    }
		});
		
		return books;
	}

}
