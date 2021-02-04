package com.ibnu.miniproject.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibnu.miniproject.model.Pesanan;
import com.ibnu.miniproject.model.PesananDetail;
import com.ibnu.miniproject.model.PesananRequest;
import com.ibnu.miniproject.model.Response;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PesananService {
	@Autowired
	private PesananRepo pesananRepo;
	
	@Transactional
	public Response savePesanan(PesananRequest request) {
		if(validate(request)) {
			return Response.builder()
					.code(404)
					.status("Error")
					.message("Nama pemesan,nama barang,jumlah barang,keterangan,alamat pemesan tidak boleh kosong ").build();
		}
		Pesanan pesanan = pesananRepo.findAllByNamaPemesanAndNamaBarang(request.getNamaPemesan(), request.getNamaBarang());
		if(pesanan == null ) {
			pesanan = new Pesanan();
			pesanan.setRegisterNo(createNo());
		}else {
			if(!pesanan.getTanggalPemesanan().toLocalDate().equals(LocalDate.now())) {
				pesanan = new Pesanan();
				pesanan.setRegisterNo(createNo());
			}
		}
		
		pesanan.setNamaPemesan(request.getNamaPemesan());
		pesanan.setNamaBarang(request.getNamaBarang());
		pesanan.setJumlah(request.getJumlahBarang());
		pesanan.setKeterangan(request.getKeterangan());
		pesanan.setAlamatPemesan(request.getAlamat());
		pesanan.setTanggalPemesanan(LocalDateTime.now());
		pesananRepo.save(pesanan);
		
		return Response.builder()
				.code(200)
				.status("Success")
				.message("Pesanan telah diterima dengan nomor register "+pesanan.getRegisterNo()).build();
	}
	
	@Transactional
	public Response editPesanan(String noRegister,Integer jumlahBarang) {
		Pesanan pesanan = pesananRepo.findById(noRegister).orElse(null);
		if(pesanan == null) {
			return Response.builder()
					.code(404)
					.status("Error")
					.message("Pesanan Tidak Ditemukan").build();
		}
		pesanan.setJumlah(jumlahBarang);
		pesananRepo.save(pesanan);
		return Response.builder()
				.code(200)
				.status("Success")
				.message("Pesanan dengan nomor register "+pesanan.getRegisterNo()+" berhasil di-update").build();
		
	}
	
	public Response getListBarangByName(String nama) {
		List<Pesanan> listPesanan = pesananRepo.findAllByNamaPemesan(nama);
		List<PesananDetail> details = new ArrayList<PesananDetail>();
		for(Pesanan psn : listPesanan) {
			PesananDetail psnDetail = PesananDetail.builder()
										.namaBarang(psn.getNamaBarang())
										.jumlahBarang(psn.getJumlah())
										.keterangan(psn.getKeterangan())
										.build();
			details.add(psnDetail);
		}
		if(details.size()==0) {
			return Response.builder()
					.code(404)
					.status("Error")
					.message("Pesanan Tidak Ditemukan dengan nama : "+nama).build();
		}
		return Response.builder()
				.code(200)
				.status("Success")
				.data(details).build();
	}
	private String createNo() {
		Date date = Date.valueOf(LocalDate.now());
		Integer count = pesananRepo.countByTanggalPemesanan(date);
		String tiket = "0000";
		if(count != null && count > 0) {
			count=count + 1;
			log.info("jumlah keranjang "+count.toString());
			tiket = tiket + count.toString();
			tiket = tiket.substring(tiket.length() - 4);
		}else {
			tiket = "0001";
		}
		String result = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")) + tiket;
		return result;
		
	}
	
	private Boolean validate(PesananRequest request) {
		Boolean result = false;
		if(request.getNamaPemesan() == null)
			result = true;
		if(request.getNamaBarang() == null)
			result = true;
		if(request.getKeterangan() == null)
			result = true;
		if(request.getAlamat() == null)
			result = true;
		if(request.getJumlahBarang() == null)
			result = true;
		
		return result;
	}

}
