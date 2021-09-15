package Library;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoClientProvider {
    public static MongoClient getMongoProvider(){
        return new MongoClient();
    }

    public static com.mongodb.client.MongoClient getClient(){
        //1. uzregistruoti provaideriui codec
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
        //2. uzregistruojam provideri registeryje
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        // 3. sukurti Mongo setingus ir ideti registry
        MongoClientSettings clientSettings = MongoClientSettings.builder().codecRegistry(codecRegistry).build();
        return MongoClients.create(clientSettings);
    }
}
