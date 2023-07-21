package com.rsr.file;

import java.util.Date;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

	@Autowired
	FileRepository fileRepository;
	
	@Value("${file.uploadFolder}")
	private String UPLOAD_FOLDER;
	// Upload
	public Long upload(MultipartFile file, Long fk) throws IOException {
		System.out.println("Original Image Byte Size - " + file.getBytes().length);
		// Save File 
        Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
        Files.write(path, file.getBytes());		
		// Save Entity
        File entity = new File(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()), fk, new Date());
		return (fileRepository.save(entity)).getId();
	}
	// DownLoad
	public File download(String fileName) throws IOException {
		System.out.println("Get File - " + fileName);
		File file = new File();
		final Optional<File> entity=fileRepository.findByName(fileName);
		if (entity.isPresent()) {
			file=entity.get();
			file.setPicByte(decompressBytes(entity.get().getPicByte()));
			//return new File(entity.get().getName(), entity.get().getType(), decompressBytes(entity.get().getPicByte()), entity.get().getFk(), entity.get().getCreation());
		}		 		
		return file;
	}	
	// Compress file before storing it in the database
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
		return outputStream.toByteArray();
	}
	// Uncompress file before returning it to the client
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException | DataFormatException ioe) {}
		return outputStream.toByteArray();
	}

}
