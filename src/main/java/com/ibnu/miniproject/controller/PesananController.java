package com.ibnu.miniproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibnu.miniproject.model.PesananRequest;
import com.ibnu.miniproject.model.Response;
import com.ibnu.miniproject.service.PesananService;

@Controller
@ResponseBody
public class PesananController {
	@Autowired
	private PesananService pesananService;
	
	@PostMapping("/pesanan/save")
	public ResponseEntity<Response> savePesanan(@RequestBody PesananRequest request){
		Response response = pesananService.savePesanan(request);
		if(response.getCode().equals(404))
			return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@PutMapping("/pesanan/edit")
	public ResponseEntity<Response> editPesanan(@RequestParam()String registerNo,
												@RequestParam()Integer jumlah){
		Response response = pesananService.editPesanan(registerNo, jumlah);
		if(response.getCode().equals(404)) {
			return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@GetMapping("/pesanan/findByName/{nama}")
	public ResponseEntity<Response> getByName(@PathVariable()String nama){
		Response response = pesananService.getListBarangByName(nama);
		
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
