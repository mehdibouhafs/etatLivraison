<<<<<<< HEAD
package ma.munisys.sec;

import java.util.Collection;
import java.util.HashSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ma.munisys.entities.Authorisation;

/**
 *
 * Description courte de la classe
 *
 * @author mbouhafs
 * @since 30 mars 2018.  API version : 1.0
 * @version 1.0
 * 
 *          {@inheritDoc}
 */
@Component
public class UserAuthorityService {

    public Collection<? extends GrantedAuthority> getGrantedAuthorities(Collection<Authorisation> authorities) {

        Collection<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        
        
        for(Authorisation auth : authorities) {
        	grantedAuthorities.add(new SimpleGrantedAuthority(auth.getAuthName()));
        }
        
        return grantedAuthorities;
    }
}
=======
package ma.munisys.sec;

import java.util.Collection;
import java.util.HashSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ma.munisys.entities.Authorisation;

/**
 *
 * Description courte de la classe
 *
 * @author mbouhafs
 * @since 30 mars 2018.  API version : 1.0
 * @version 1.0
 * 
 *          {@inheritDoc}
 */
@Component
public class UserAuthorityService {

    public Collection<? extends GrantedAuthority> getGrantedAuthorities(Collection<Authorisation> authorities) {

        Collection<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        
        
        for(Authorisation auth : authorities) {
        	grantedAuthorities.add(new SimpleGrantedAuthority(auth.getAuthName()));
        }
        
        return grantedAuthorities;
    }
}
>>>>>>> munisysRepo/main
