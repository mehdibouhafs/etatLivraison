package ma.munisys.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import ma.munisys.entities.Employer;
import ma.munisys.entities.Projet;
import ma.munisys.entities.Reunion;



/* to expose IDs for specific classes.  */

@Configuration
public class GlobalConfigurationRestConfigurer extends RepositoryRestConfigurerAdapter {
	
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    	// TODO Auto-generated method stub
    	config.exposeIdsFor(Projet.class);
    	config.exposeIdsFor(Reunion.class);
    	config.exposeIdsFor(Employer.class);
    	config.setReturnBodyOnCreate(true);
    	config.setReturnBodyOnUpdate(true);
    	config.getCorsRegistry().addMapping("/**").allowedOrigins("*").allowedHeaders("*").allowedMethods("OPTIONS","HEAD","PATCH","POST","PUT","GET","DELETE","*");
    	
    }

}
