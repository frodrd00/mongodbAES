package aes;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;


public class ConnectionMongo {
	
	private MongoClient mongoClient;
	private MongoClientURI uri;
	private MongoDatabase database;
	

	public void ConnectMongo(){
		try{
			//conexi�n a mongodb formato URI
			//mongodb://user1@host1/?authSource=$external&authMechanism=PLAIN
			String db = "test";
			String host = "";
			int port = ;//default
			String user = "";
			String password = "";

			//autenticacion externa
			uri = new MongoClientURI("mongodb://"+user+":"+password+"@"+host+":"+port+"/?authSource=$external&authMechanism=PLAIN");
			
			//conexion con el servidor de mongodb
			mongoClient = new MongoClient(uri);
			
			//se guarda la base de datos para utilizarla despues a la hora de insertar documentos
			database = mongoClient.getDatabase(db);
			
			System.out.println("La conexion con " + database.getName() +" fue establecida.");
	    } catch (MongoException e) {
	    	System.out.println("Fallo conexion con mongodb "+ database.getName() +"!"+e);
	    }
	}

	public void disConnect(){
		try {
			mongoClient.close();
			System.out.println("Se ha cerrado la conexion con mongodb "+ database.getName());
		} catch (MongoException e) {
			System.out.println("Fallo desconexion con mongodb "+ database.getName() +"!"+e);
		}
	
	}
	
	
	public MongoClient getMongoClient(){
		
		return mongoClient;
	}
	
	public MongoDatabase getDatabase() {
		return database;
	}
	
}

