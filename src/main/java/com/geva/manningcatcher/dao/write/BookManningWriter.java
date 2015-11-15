package com.geva.manningcatcher.dao.write;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.geva.manningcatcher.beans.Book;
import com.geva.manningcatcher.utils.BookNodes;
import com.mongodb.client.MongoCollection;

@Component(value="bookWriter")
public class BookManningWriter implements ManningWriter<Book>, BookNodes {

	@Autowired
	@Qualifier(value="booksCollection")
	private MongoCollection<Document> booksCollection;
	
//	public BookManningWriter(MongoCollection<Document> collection) {
//		this.booksCollection = collection;
//	}
	
	@Override
	public String write(Book book) {
		Document titleDoc = new Document(TITLE, book.getTitle());
		Document result = booksCollection.findOneAndUpdate(titleDoc, new Document("$inc", new Document(TIMES, new Integer(1))));
		ObjectId id;
		
			if(result == null) {
				Document titleToAdd = new Document(TITLE, book.getTitle()).append(TIMES, new Integer(1));
				booksCollection.insertOne(titleToAdd);
				id = titleToAdd.getObjectId(BOOKID);
			} else {
				id = result.getObjectId(BOOKID);
			}
			
			book.setId(id);
			
		return id.toHexString();
	}
	
}
