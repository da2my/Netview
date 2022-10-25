package com.dmj.Netview.configuracion;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@Configuration
public class ConfiguracionFB {

	@Bean
	public Firestore inicializarFB() {
		FileInputStream serviceAccount;

		try {

			serviceAccount = new FileInputStream("netview-3cece-firebase-adminsdk-abiwq-0cbd343d40.json");

			FirebaseOptions options = FirebaseOptions.builder()

					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://netview-3cece.firebaseio.com").build();

			FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);

			return FirestoreClient.getFirestore(firebaseApp);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
