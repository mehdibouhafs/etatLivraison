package ma.munisys.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ma.munisys.entities.AppUser;
import ma.munisys.service.UserService;

@RestController
@CrossOrigin(origins="*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/searchUser", method=RequestMethod.GET)
	public Page<AppUser> searchUser(@RequestParam(name="tosearch") String tosearch, @RequestParam(name="page",defaultValue="0") int page, @RequestParam(name="size",defaultValue="10") int size) {
		return userService.searchUser(tosearch, page, size);
	}
	
	@RequestMapping(value="/searchUserByService", method=RequestMethod.GET)
	public Page<AppUser> searchUserByService(@RequestParam(name="idService") Long idService, @RequestParam(name="tosearch") String tosearch, @RequestParam(name="page",defaultValue="0") int page, @RequestParam(name="size",defaultValue="10") int size) {
		return userService.searchUserByServiceAndName(idService, tosearch, page, size);
	}
	
	@RequestMapping(value="/getUser", method=RequestMethod.GET)
	public AppUser getUser(@RequestParam(name="username") String username) {
		return userService.getUser(username);
	}
	
}
