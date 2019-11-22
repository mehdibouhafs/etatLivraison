package ma.munisys.service;

import org.springframework.data.domain.Page;

import ma.munisys.entities.AppUser;

public interface UserService {
	
	public Page<AppUser> searchUser(String tosearch, int page, int size);
	public Page<AppUser> searchUserByServiceAndName(Long idService, String tosearch, int page, int size);
	public AppUser getUser(String username);
	
}
