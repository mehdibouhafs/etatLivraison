package ma.munisys.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.search.LdapUserSearch;
import org.springframework.stereotype.Component;

import ma.munisys.entities.AppUser;
import ma.munisys.service.AccountService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	
	
	@Autowired
	private AccountService accountService;
	
	@Value( "${vkauthentication.ldap.server.url}" )
	private String ldapServerUrl;
	
	@Value( "${vkauthentication.ldap.server.user}" )
	private String ldapManagerDn;
	
	@Value( "${vkauthentication.ldap.server.password}" )
	private String ldapManagerPassword;
	
	@Value( "${vkauthentication.ldap.user.search.base}" )
	private String ldapUserSearchBase;
	
	@Value( "${vkauthentication.ldap.user.search.filter}" )
	private String ldapUserSearchFilter;
	@Value("${vkauthentication.ldap.root}")
	private String ldapGroupSearchBase;
	
	
	@Autowired
	CustomLdapAuthoritiesPopulator customLdapAuthoritiesPopulator;
	
	
	 @Bean
	 public AuthenticationProvider ldapAuthenticationProvider() throws Exception {
	        DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource(ldapServerUrl);
	        contextSource.setUserDn(ldapManagerDn);
	        contextSource.setPassword(ldapManagerPassword);
	        contextSource.afterPropertiesSet();
	        LdapUserSearch ldapUserSearch = new FilterBasedLdapUserSearch("",ldapUserSearchFilter , contextSource);
	        BindAuthenticator bindAuthenticator = new BindAuthenticator(contextSource);
	        bindAuthenticator.setUserSearch(ldapUserSearch);
	        LdapAuthenticationProvider ldapAuthenticationProvider = new LdapAuthenticationProvider(bindAuthenticator, customLdapAuthoritiesPopulator);
	        return ldapAuthenticationProvider;
	   }

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		 String username = authentication.getName();
	        AppUser userEntity = accountService.findUserByUsername(username);
	        if (userEntity == null) {
	            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
	        }
	        try {
				return ldapAuthenticationProvider().authenticate(authentication);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}

