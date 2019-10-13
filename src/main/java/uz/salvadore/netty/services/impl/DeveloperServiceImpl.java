package uz.salvadore.netty.services.impl;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import uz.salvadore.netty.models.Developer;
import uz.salvadore.netty.services.DeveloperService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DeveloperServiceImpl implements DeveloperService {

    private final MongoClient client;
    private final MongoDatabase db;
    private final PojoCodecProvider codecProvider;
    private final CodecRegistry pojoCodecRegistry;

    public DeveloperServiceImpl() {
        this.client = new MongoClient("localhost", 27017);
        this.db = client.getDatabase("koa_db");
        codecProvider = PojoCodecProvider.builder()
                .automatic(true)
                .build();
        pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(codecProvider));
    }

    @Override
    public List<Developer> findAll() {
        List<Developer> developers = new ArrayList<>();
        MongoCollection<Developer> collection = db.withCodecRegistry(pojoCodecRegistry).getCollection("developers", Developer.class);
        Iterator<Developer> devs = collection.find().iterator();
        while (devs.hasNext()) {
            developers.add(devs.next());
        }
        return developers;
    }
}
