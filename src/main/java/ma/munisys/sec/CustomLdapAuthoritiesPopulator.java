<<<<<<< HEAD
package ma.munisys.sec;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Component;

import ma.munisys.entities.AppProfile;
import ma.munisys.entities.AppUser;
import ma.munisys.entities.Authorisation;
import ma.munisys.service.AccountService;

@Component
public class CustomLdapAuthoritiesPopulator implements LdapAuthoritiesPopulator {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UserAuthorityService userAuthorityService;

	@Override
	public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData,
			String username) {
		AppUser appUser = accountService.findUserByUsername(username);
		if (appUser == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
		
		 Collection<Authorisation> authorities1 = new ArrayList<>();
		 
		for(AppProfile profile : accountService.findProfilesByUsers(username)) {
        	for(Authorisation auth : accountService.findAuthorityByPrflName(profile.getPrflName())) {
        		authorities1.add(auth);
        	}
        }
        
        for(Authorisation auth : accountService.findUserAuthority(username)) {
        	if(!authorities1.contains(auth)) {
        		authorities1.add(auth);
        	}
        }
		
		 Collection<? extends GrantedAuthority> authorities = userAuthorityService.getGrantedAuthorities(authorities1);
		return authorities;
	}

}
=======
package ma.munisys.sec;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Component;

import ma.munisys.entities.AppProfile;
import ma.munisys.entities.AppUser;
import ma.munisys.entities.Authorisation;
import ma.munisys.service.AccountService;

@Component
public class CustomLdapAuthoritiesPopulator implements LdapAuthoritiesPopulator {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UserAuthorityService userAuthorityService;

	@Override
	public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData,
			String username) {
		AppUser appUser = accountService.findUserByUsername(username);
		if (appUser == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
		
		 Collection<Authorisation> authorities1 = new ArrayList<>();
		 
		for(AppProfile profile : accountService.findProfilesByUsers(username)) {
        	for(Authorisation auth : accountService.findAuthorityByPrflName(profile.getPrflName())) {
        		authorities1.add(auth);
        	}
        }
        
        for(Authorisation auth : accountService.findUserAuthority(username)) {
        	if(!authorities1.contains(auth)) {
        		authorities1.add(auth);
        	}
        }
		
		 Collection<? extends GrantedAuthority> authorities = userAuthorityService.getGrantedAuthorities(authorities1);
		return authorities;
	}

}
>>>>>>> munisysRepo/main
