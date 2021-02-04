package com.ibnu.miniproject.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name="pesanan")
@Entity
public class Pesanan {
	
	@Id
	@Column(name="register_no",nullable=false)
	private String registerNo;
	@Column(name="nama_pemesan",nullable=false)
	private String namaPemesan;
	@Column(name="nama_barang",nullable=false)
	private String namaBarang;
	private String keterangan;
	private Integer jumlah;
	@Column(name="alamat_pemesan")
	private String alamatPemesan;
	@Column(name="tanggal_pemesanan",nullable=false)
	private LocalDateTime tanggalPemesanan;
}
