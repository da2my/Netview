package com.dmj.Netview.servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;
import com.dmj.Netview.modelo.Usuario;
import com.dmj.Netview.modelo.Video;
import com.dmj.Netview.repositorios.VideoRepositorio;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;


@Service
public class VideoServicio implements VideoRepositorio {

	
	// todos los videos (cartelera)
	public List<Video> findAll() {
		
		Firestore database = FirestoreClient.getFirestore();
		List <Video> allVideos = new ArrayList<>();

		try {
			
			ApiFuture<QuerySnapshot> future = database.collection("Videos").get();
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			
			for (QueryDocumentSnapshot document : documents) {
				allVideos.add(document.toObject(Video.class));
			}
			
			return allVideos;

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	//videos favoritos de un usuario
	public List<Video> favoritosDeUsuario(Usuario usuario) {
		
		Firestore database = FirestoreClient.getFirestore();
		List <Video> videosFavoritos = new ArrayList<>();
		ArrayList<HashMap<String, Object>> vfb = new ArrayList<>();

		try {
			
			ApiFuture<QuerySnapshot> future = database.collection("Usuarios").whereEqualTo("email", usuario.getEmail()).get();
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			
			for (QueryDocumentSnapshot document : documents) {
								
				vfb = (ArrayList<HashMap<String, Object>>) document.get("favoritos");

			}
						
			for(int i = 0; i<vfb.size(); i++) {
				Video v = new Video();
				Iterator it = vfb.get(i).keySet().iterator();
				while(it.hasNext()) {
					String key1 = (String) it.next();
					v.setUrlVideo(vfb.get(i).get(key1).toString());
					String key2 = (String) it.next();
					v.setTituloVideo(vfb.get(i).get(key2).toString());
					String key3 = (String) it.next();
					v.setCaratulaVideo(vfb.get(i).get(key3).toString());
				}
				
				videosFavoritos.add(v);
			
			}
		
			return videosFavoritos;

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	//buscar video por tituloVideo
	public Video findById(String tituloVideo) {
		
		Firestore database = FirestoreClient.getFirestore();

		try {
			
			ApiFuture<QuerySnapshot> future = database.collection("Videos").whereEqualTo("tituloVideo", tituloVideo).get();
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			DocumentSnapshot doc = documents.get(0);
			Video video = new Video();
			video = doc.toObject(Video.class);

			return video;

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}


}
