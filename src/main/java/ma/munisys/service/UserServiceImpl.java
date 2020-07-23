package ma.munisys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ma.munisys.dao.UserRepository;
import ma.munisys.entities.AppUser;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Page<AppUser> searchUser(String tosearch, int page, int size) {
		return userRepository.searchUser("%"+tosearch+"%", new PageRequest(page, size));
	}

	@Override
	public AppUser getUser(String username) {
		
		return userRepository.getOne(username);
	}

	@Override
	public Page<AppUser> searchUserByServiceAndName(Long idService, String tosearch, int page, int size) {
		return userRepository.searchUserByServiceAnName(idService, "%"+tosearch+"%", new PageRequest(page, size));
	}

}
