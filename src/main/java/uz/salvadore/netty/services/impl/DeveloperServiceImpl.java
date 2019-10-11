package uz.salvadore.netty.services.impl;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import uz.salvadore.netty.models.Developer;
import uz.salvadore.netty.services.DeveloperService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DeveloperServiceImpl implements DeveloperService {

    private final MongoClient client;
    private final MongoDatabase db;
    private final MongoCollection<Developer> collection;

    public DeveloperServiceImpl() {
        this.client = new MongoClient("localhost", 27017);
        this.db = client.getDatabase("koa_db");
        this.collection = db.getCollection("developers", Developer.class);
    }

    @Override
    public List<Developer> findAll() {
        Iterator<Developer> cursor = collection.find().iterator();
        final List<Developer> developers = new ArrayList<>();
        while (cursor.hasNext()) {
            developers.add(cursor.next());
        }
        return developers;
    }
}
