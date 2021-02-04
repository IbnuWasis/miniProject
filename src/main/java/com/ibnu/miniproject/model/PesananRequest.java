package com.ibnu.miniproject.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
@Data
@JsonSerialize
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PesananRequest {
	private String namaPemesan;
	private String namaBarang;
	private Integer jumlahBarang;
	private String alamat;
	private String keterangan;
}
