package com.dmj.Netview.configuracion;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
		
		InputStream serviceAccount;

		try {
							
			serviceAccount = new URL("https://storage.googleapis.com/netview_test2/netviewtest2.json").openStream();

			FirebaseOptions options = FirebaseOptions.builder()

					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://netviewtest2.firebaseio.com").build();

			FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);

			return FirestoreClient.getFirestore(firebaseApp);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
