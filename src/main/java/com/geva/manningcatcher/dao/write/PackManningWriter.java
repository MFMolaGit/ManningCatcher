package com.geva.manningcatcher.dao.write;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.geva.manningcatcher.beans.Book;
import com.geva.manningcatcher.beans.Pack;
import com.geva.manningcatcher.utils.PackNodes;
import com.mongodb.client.MongoCollection;

@Component(value="packWriter")
public class PackManningWriter implements ManningWriter<Pack>, PackNodes {

	@Autowired
	@Qualifier(value="bookWriter")
	private ManningWriter<Book> bookWriter;
	
	@Autowired
	@Qualifier(value="packsCollection")
	private MongoCollection<Document> packsCollection;
	
//	@Autowired
//	public PackManningWriter(MongoCollection<Document> collection) {
//		this.packsCollection = collection;
//	}
	
	@Override
	public String write(Pack pack) {
		List<ObjectId> bookIds = new ArrayList<ObjectId>();
			
			for(Book book:pack.getBooks()) {
				bookIds.add(new ObjectId(bookWriter.write(book)));
			}
		
		Document packDoc = new Document(BOOKS, new Document("$all", bookIds));
		Document result = packsCollection.findOneAndUpdate(packDoc, new Document("$inc", new Document(TIMES, new Integer(1))));
		ObjectId id;
		
			if(result == null) {
				Document packToAdd = new Document(BOOKS, bookIds).append(TIMES, new Integer(1));
				packsCollection.insertOne(packToAdd);
				id = packToAdd.getObjectId(PACKID);
			} else {
				id = result.getObjectId(PACKID);
			}
			
			pack.setId(id);
			
		return id.toHexString();
	}
	
}
