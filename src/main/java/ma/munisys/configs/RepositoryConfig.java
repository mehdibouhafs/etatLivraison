package ma.munisys.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import ma.munisys.entities.Projet;



/* to expose IDs for specific classes.  */

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
	
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    	// TODO Auto-generated method stub
    	config.exposeIdsFor(Projet.class);
    	
    }

}
