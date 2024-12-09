package ec.com.technoloqie.document.loader.api.config;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDbConfig extends AbstractMongoClientConfiguration {
	
	@Value("${spring.data.mongodb.database}")
    private String appDatabase;

    @Value("${spring.data.mongodb.uri}")
    private String dbConnection;
    
    @Override
    protected String getDatabaseName() {
        // Application default database name
        return appDatabase;
    }
    
    @Bean
    @Override
    public MongoClient mongoClient() {
    	 CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
    	            MongoClientSettings.getDefaultCodecRegistry(),
    	            CodecRegistries.fromProviders(
    	                PojoCodecProvider.builder().automatic(true).build()
    	            )
    	        );
    	 MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
    	            .applyConnectionString(new ConnectionString(dbConnection))
    	            .codecRegistry(pojoCodecRegistry)
    	            .build();
    	// MongoDB connection string
       /* ConnectionString connectionString = new ConnectionString(dbConnection);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();*/
        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }
	
    
    @Bean
    public MongoDatabase mongoDatabase(MongoClient mongoClient) {
        return mongoClient.getDatabase(appDatabase); 
    }

}
