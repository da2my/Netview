package com.dmj.Netview.repositorios;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.dmj.Netview.modelo.Caratula;



@Repository
public interface CaratulaRepositorio {
	
	List<Caratula> findAll();

}
