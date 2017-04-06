package aes;

import java.util.Collections;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Filters.*;

import aes.ConnectionMongo;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
    	
    	//Clave secreta para cifrar y descifrar
    	String secretKey = "ssshhhhhhhhhhh!!!!";
        
        String originalString = "125485";
        String encryptedString = AES.encrypt(originalString, secretKey) ;
       
         
        //conexion a mongodb
		ConnectionMongo conn = new ConnectionMongo();
    	conn.ConnectMongo();
    	MongoClient mongoC = conn.getMongoClient();
    	MongoCollection<Document> collection = conn.getDatabase().getCollection("test");
        
    	//insertamos un documento de prueba
    	Document result = new Document("nombre","Test").append("pwd", encryptedString);
    	collection.insertOne(result);
    	
    	//muestra el documento bson insertado
    	MongoCursor<Document> cursor = collection.find().iterator();
    	try {
    	    while (cursor.hasNext()) {
    	        System.out.println(cursor.next().toJson());
    	    }
    	} finally {
    	    cursor.close();
    	}

    	//busca el campo pwd 
    	FindIterable<Document> search = collection.find().projection(fields(include("pwd"),excludeId()));
		
    	  //password cifrado
		  for (Document d : search) {
			  
			  System.out.println(d.getString("pwd"));
		  
		  }
    	
		  
		  //password descrifrado
		  for (Document d : search) {
			  
			  System.out.println(AES.decrypt(d.getString("pwd"), secretKey));
		  
		  }
    	
    	//String decryptedString = AES.decrypt(encryptedString, secretKey) ;

    	
    	conn.disConnect();
        
    }
}
