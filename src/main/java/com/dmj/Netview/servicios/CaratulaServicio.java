package com.dmj.Netview.servicios;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import com.dmj.Netview.modelo.Caratula;
import com.dmj.Netview.repositorios.CaratulaRepositorio;


@Service
public class CaratulaServicio implements CaratulaRepositorio {

	@Override
	public List<Caratula> findAll() {

		JSONObject lecturaAPI;

		String ruta = "https://image.tmdb.org/t/p/w300";
		String key = "?api_key=3aaff5d3ea485a0edd6a5c13d4ceacdb";
		
		List<Caratula> caratulas = new ArrayList<>();

		try {

			lecturaAPI = new JSONObject(IOUtils.toString(
					new URL("https://api.themoviedb.org/3/discover/movie?api_key=3aaff5d3ea485a0edd6a5c13d4ceacdb"),
					Charset.forName("UTF-8")));
			
			JSONArray result = (JSONArray) lecturaAPI.get("results");

			for (int i = 0; i < result.length(); i++) {
				
				Caratula nuevaCaratula = new Caratula();

				JSONObject pelicula = result.getJSONObject(i);
				
				nuevaCaratula.setTituloCaratula((String) pelicula.get("title"));
				
				String poster_path = (String) pelicula.get("poster_path");

				String urlImagen = ruta + poster_path + key;
				
				nuevaCaratula.setUrlCaratula(urlImagen);
				
				caratulas.add(nuevaCaratula);
			}
			
			return caratulas;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
