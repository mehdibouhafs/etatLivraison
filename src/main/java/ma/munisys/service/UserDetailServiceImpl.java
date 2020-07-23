package ma.munisys.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ma.munisys.entities.AppProfile;
import ma.munisys.entities.AppUser;
import ma.munisys.entities.Authorisation;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private AccountService accountService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = accountService.findUserByUsername(username);
		//System.out.println("User " + user.toString());
		if(user == null) throw new UsernameNotFoundException(username);
		//System.out.println("user profiles " +accountService.findProfilesByUsers(username));
		
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		
		
		for(AppProfile profile : accountService.findProfilesByUsers(username)) {
        	for(Authorisation auth : accountService.findAuthorityByPrflName(profile.getPrflName())) {
        		authorities.add(new SimpleGrantedAuthority(auth.getAuthName()));
        	}
        }
        
        for(Authorisation auth : accountService.findUserAuthority(username)) {
        	if(!authorities.contains(new SimpleGrantedAuthority(auth.getAuthName()))) {
        		authorities.add(new SimpleGrantedAuthority(auth.getAuthName()));
        	}
        }
		
		
		return new User(user.getUsername(), user.getPassword(), true, true, true, true, authorities);
	}

}
