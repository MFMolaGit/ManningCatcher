package com.geva.manningcatcher.business;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.bson.types.ObjectId;

public class ObjectIdAdapter extends XmlAdapter<String, ObjectId> {
	
    @Override
    public String marshal(ObjectId v) throws Exception {
        return v.toHexString();
    }

    @Override
    public ObjectId unmarshal(String v) throws Exception {
        return new ObjectId(v);
    }
    
}
