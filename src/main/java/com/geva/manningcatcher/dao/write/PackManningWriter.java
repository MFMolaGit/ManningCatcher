package com.geva.manningcatcher.dao.write;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.geva.manningcatcher.beans.Pack;
import com.geva.manningcatcher.dao.MongoManningConnector;
import com.geva.manningcatcher.utils.Constants;
import com.geva.manningcatcher.utils.PackNodes;
import com.mongodb.client.MongoCollection;

public class PackManningWriter implements ManningWriter<Pack>, PackNodes {

	private MongoCollection<Document> connection;
	
	public PackManningWriter() {
		connection = MongoManningConnector.connect(Constants.MONGO_PACKS_COLLECTION);
	}
	
	@Override
	public String write(Pack pack) {
		Document packDoc = new Document(BOOKS, new Document("$all", pack.getBooks()));
		Document result = connection.findOneAndUpdate(packDoc, new Document("$inc", new Document(TIMES, new Integer(1))));
		ObjectId id;
		
			if(result == null) {
				Document packToAdd = new Document(BOOKS, pack.getBooks()).append(TIMES, new Integer(1));
				connection.insertOne(packToAdd);
				id = packToAdd.getObjectId(PACKID);
			} else {
				id = result.getObjectId(PACKID);
			}
			
		return id.toHexString();
	}
	
}
