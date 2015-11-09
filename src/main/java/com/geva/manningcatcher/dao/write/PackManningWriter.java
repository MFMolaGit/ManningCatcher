package com.geva.manningcatcher.dao.write;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.geva.manningcatcher.beans.Book;
import com.geva.manningcatcher.beans.Pack;
import com.geva.manningcatcher.dao.MongoManningConnector;
import com.geva.manningcatcher.utils.Constants;
import com.geva.manningcatcher.utils.PackNodes;
import com.mongodb.client.MongoCollection;

public class PackManningWriter implements ManningWriter<Pack>, PackNodes {

	private ManningWriter<Book> bookManningWriter;
	
	private MongoCollection<Document> connection;
	
	public PackManningWriter() {
		bookManningWriter = new BookManningWriter();
		connection = MongoManningConnector.connect(Constants.MONGO_PACKS_COLLECTION);
	}
	
	@Override
	public String write(Pack pack) {
		List<ObjectId> bookIds = new ArrayList<ObjectId>();
			
			for(Book book:pack.getBooks()) {
				bookIds.add(new ObjectId(bookManningWriter.write(book)));
			}
		
		Document packDoc = new Document(BOOKS, new Document("$all", bookIds));
		Document result = connection.findOneAndUpdate(packDoc, new Document("$inc", new Document(TIMES, new Integer(1))));
		ObjectId id;
		
			if(result == null) {
				Document packToAdd = new Document(BOOKS, bookIds).append(TIMES, new Integer(1));
				connection.insertOne(packToAdd);
				id = packToAdd.getObjectId(PACKID);
			} else {
				id = result.getObjectId(PACKID);
			}
			
			pack.setId(id);
			
		return id.toHexString();
	}
	
}
