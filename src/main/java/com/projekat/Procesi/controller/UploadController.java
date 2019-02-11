package com.projekat.Procesi.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.projekat.Procesi.storage.FileSystemStorageService;

@RestController
@RequestMapping("/projekat/file")
public class UploadController {
	
	@Autowired
	private FileSystemStorageService storageService;
	
	
	@PostMapping("/upload")
	public ResponseEntity<String> upload(@RequestParam("doc") MultipartFile file){
				
		// Cuvanje fajla u upload-dir
		File storedFile = storageService.store(file);
		if(storedFile == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		String filename = storedFile.getName();
				
		return new ResponseEntity<>(filename, HttpStatus.OK);
	}

	@GetMapping("/download/{fileID}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable("fileID") String fileID){
		Resource file = storageService.loadAsResource(fileID);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	
}
