package com.dmj.Netview.servicios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dmj.Netview.modelo.Caratula;
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
	
	
	@Autowired
	private CaratulaServicio caratulaServicio;
	

	// todos los videos (cartelera)
	public List<Video> findAll() {

		Firestore database = FirestoreClient.getFirestore();
		List<Video> allVideos = new ArrayList<>();

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

	// videos favoritos de un usuario
	public List<Video> favoritosDeUsuario(Usuario usuario) {

		Firestore database = FirestoreClient.getFirestore();
		List<Video> videosFavoritos = new ArrayList<>();
		ArrayList<HashMap<String, Object>> vfb = new ArrayList<>();

		try {

			ApiFuture<QuerySnapshot> future = database.collection("Usuarios").whereEqualTo("email", usuario.getEmail()).get();
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();

			for (QueryDocumentSnapshot document : documents) {

				vfb = (ArrayList<HashMap<String, Object>>) document.get("favoritos");

			}

			for (int i = 0; i < vfb.size(); i++) {
				Video v = new Video();
				Iterator it = vfb.get(i).keySet().iterator();
				while (it.hasNext()) {
					String key1 = (String) it.next();
					v.setUrlVideo(vfb.get(i).get(key1).toString());
					String key2 = (String) it.next();
					v.setTituloVideo(vfb.get(i).get(key2).toString());
				}

				videosFavoritos.add(v);

			}
			
			//quitar repetidos y actualizar BBDD
			database.collection("Usuarios").document(usuario.getEmail()).update("favoritos", quitarRepetidos(videosFavoritos));

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

	// buscar video por tituloVideo
	public Video findById(String tituloVideo) {

		Firestore database = FirestoreClient.getFirestore();

		try {

			ApiFuture<QuerySnapshot> future = database.collection("Videos").whereEqualTo("tituloVideo", tituloVideo).get();
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			if (!documents.isEmpty()) {
				DocumentSnapshot doc = documents.get(0);
				Video video = new Video();
				video = doc.toObject(Video.class);
				return video;
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new Video();

	}
	
	//Buscar caratulas cartelera
	@Override
	public List<Caratula> buscarMisVideos(String tituloVideo) {
		
		List <Caratula> videosBuscados = new ArrayList<>();
		
		for(Caratula busq: caratulaServicio.findAll()) {
			
			if(tituloVideo.length()>busq.getTituloCaratula().length()) {
				continue;
			}

			if(tituloVideo.substring(0, tituloVideo.length()).equalsIgnoreCase((String) busq.getTituloCaratula().substring(0, tituloVideo.length()))) {
			videosBuscados.add(busq);
			}

		}

		return videosBuscados;

	}
	
	//Buscar caratulas TOP
	@Override
	public List<Caratula> buscarMisVideosTop(String tituloVideo) {
		
		List <Caratula> videosBuscados = new ArrayList<>();
		
		for(Caratula busq: caratulaServicio.findValoradas()) {
			
			if(tituloVideo.length()>busq.getTituloCaratula().length()) {
				continue;
			}

			if(tituloVideo.substring(0, tituloVideo.length()).equalsIgnoreCase((String) busq.getTituloCaratula().substring(0, tituloVideo.length()))) {
			videosBuscados.add(busq);
			}

		}

		return videosBuscados;

	}
	
	//SEGUIR VIENDO
	@Override
	public void seguirViendo(Video video, Usuario usuario) {
		
		Firestore database = FirestoreClient.getFirestore();
		
		Video[] tusReproducciones = new Video[1];
		
		tusReproducciones[0] = video;
		
		usuario.setSeguirViendo(Arrays.asList(tusReproducciones));
		
		database.collection("Usuarios").document(usuario.getEmail()).update("seguirViendo", usuario.getSeguirViendo());

	}
	
	//HISTORIAL DE VIDEOS
	@Override
	public List<Video> historialVideos(Usuario usuario) {
		
		Firestore database = FirestoreClient.getFirestore();
		List <Video> videosHistorial = new ArrayList<>();
		ArrayList<HashMap<String, Object>> vh = new ArrayList<>();

		try {
			
			ApiFuture<QuerySnapshot> future = database.collection("Usuarios").whereEqualTo("email", usuario.getEmail()).get();
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			if (!documents.isEmpty()) {
				for (QueryDocumentSnapshot document : documents) {
									
					vh = (ArrayList<HashMap<String, Object>>) document.get("historialVideos");
	
				}
							
				for(int i = 0; i<vh.size(); i++) {
					Video v = new Video();
					Iterator it = vh.get(i).keySet().iterator();
					while(it.hasNext()) {
						String key1 = (String) it.next();
						v.setUrlVideo(vh.get(i).get(key1).toString());
						String key2 = (String) it.next();
						v.setTituloVideo(vh.get(i).get(key2).toString());
					}
					
					videosHistorial.add(v);
				
				}
				
				//quitar repetidos y actualizar BBDD
				database.collection("Usuarios").document(usuario.getEmail()).update("historialVideos", quitarRepetidos(videosHistorial));
				
			}
			
			return videosHistorial;
	
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;

	}
	
	//Agrega al historial
	@Override
	public void agregarHistorial(Usuario usuario) {
		
		Firestore database = FirestoreClient.getFirestore();
		database.collection("Usuarios").document(usuario.getEmail()).update("historialVideos", usuario.getHistorialVideos());

	}
	
	@Override
	public List<Video> quitarRepetidos(List<Video> videosRep) {
		HashMap<String, Video> videosSinRep = new HashMap<>();
		List<Video> listSinRep = new ArrayList<>();
		for(int i=0; i<videosRep.size(); i++) {
			videosSinRep.put(videosRep.get(i).getTituloVideo(), videosRep.get(i));
		}
		//videosSinRep.putAll(videosSinRep);
		for(int i=0; i<videosSinRep.size(); i++) {
			listSinRep.add(videosRep.get(i));
		}
		
		//videosSinRep = videosRep.stream().distinct().collect(Collectors.toList());
		return listSinRep;
	}
	

}
