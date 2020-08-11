
package ma.munisys.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {

	public void store(MultipartFile file, String file_name);
	public Resource loadFile(String filename);
	public void deleteAll();
	public void init();
	
}
