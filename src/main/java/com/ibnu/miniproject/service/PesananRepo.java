package com.ibnu.miniproject.service;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ibnu.miniproject.model.Pesanan;

@Repository
public interface PesananRepo extends JpaRepository<Pesanan, String>{
	@Query("SELECT COUNT(P) FROM Pesanan P WHERE DATE(P.tanggalPemesanan) = ?1 ")
	Integer countByTanggalPemesanan(Date date);
	
	List<Pesanan> findAllByNamaPemesan(String namaPemesan);
	
	Pesanan findAllByNamaPemesanAndNamaBarang(String namaPemesan, String namaBarang);
}
