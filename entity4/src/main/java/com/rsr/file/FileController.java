package com.rsr.file;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/file")
class FileController {

	@Autowired
	FileService fileService;

	@PostMapping("/upload")
	ResponseEntity<Long> upload(@RequestParam("file") MultipartFile file, @RequestParam("fk") Long fk) throws IOException {
		System.out.println("Upload File - " + file.getOriginalFilename());		
		return new ResponseEntity<>(fileService.upload(file, fk), HttpStatus.OK);
	}	

	@GetMapping(path = { "/download/{fileName}" })
	ResponseEntity<File> download(@PathVariable("fileName") String fileName) throws IOException {
		System.out.println("Download File - " + fileName);
		return new ResponseEntity<>(fileService.download(fileName), HttpStatus.OK);
	}

}