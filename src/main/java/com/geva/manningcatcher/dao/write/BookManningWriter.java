package com.geva.manningcatcher.dao.write;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.geva.manningcatcher.beans.Book;
import com.geva.manningcatcher.dao.MongoManningConnector;
import com.geva.manningcatcher.utils.BookNodes;
import com.geva.manningcatcher.utils.Constants;
import com.mongodb.client.MongoCollection;

public class BookManningWriter implements ManningWriter<Book>, BookNodes {

	private MongoCollection<Document> connection;
	
	public BookManningWriter() {
		connection = MongoManningConnector.connect(Constants.MONGO_BOOKS_COLLECTION);
	}
	
	@Override
	public String write(Book book) {
		Document titleDoc = new Document(TITLE, book.getTitle());
		Document result = connection.findOneAndUpdate(titleDoc, new Document("$inc", new Document(TIMES, new Integer(1))));
		ObjectId id;
		
			if(result == null) {
				Document titleToAdd = new Document(TITLE, book.getTitle()).append(TIMES, new Integer(1));
				connection.insertOne(titleToAdd);
				id = titleToAdd.getObjectId(BOOKID);
			} else {
				id = result.getObjectId(BOOKID);
			}
			
			book.setId(id);
			
		return id.toHexString();
	}
	
}
