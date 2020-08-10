<<<<<<< HEAD
package ma.munisys.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService implements IStorageService {
	
	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	private final Path rootLocation = Paths.get("upload-dir");

	@Override
	public void store(MultipartFile file, String file_name) {
		try {
			
			Files.copy(file.getInputStream(), this.rootLocation.resolve(file_name+".jpg"));
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}
	}

	@Override
	public Resource loadFile(String filename) {
		try {
			Path file = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}
	

	@Override
	public void init() {
		try {
			if(!Files.exists(rootLocation))
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not initialize storage!");
		}
	}

}
=======
package ma.munisys.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService implements IStorageService {
	
	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	private final Path rootLocation = Paths.get("upload-dir");

	@Override
	public void store(MultipartFile file, String file_name) {
		try {
			
			Files.copy(file.getInputStream(), this.rootLocation.resolve(file_name+".jpg"));
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}
	}

	@Override
	public Resource loadFile(String filename) {
		try {
			Path file = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}
	

	@Override
	public void init() {
		try {
			if(!Files.exists(rootLocation))
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not initialize storage!");
		}
	}

}
>>>>>>> munisysRepo/main
