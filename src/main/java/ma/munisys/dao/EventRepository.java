package ma.munisys.dao;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ma.munisys.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
	
	@Query(value="select e from Event e where e.user.username=:y and e.statut = 0 and e.createdBy !=:y order by e.date ASC ")
	public Collection<Event> findAllEvents(@Param("y") String username);
	
	@Query(value="select e from Event e where e.user.username=:y and e.user.service.servName =:z and  e.statut = 0 and e.createdBy !=:y order by e.date ASC ")
	public Collection<Event> findAllEventsbyService(@Param("y") String username,@Param("z") String service);
	
	@Query(value="select e from Event e where e.user.username=:y and e.createdBy !=:y and e.projet.codeProjet = :c  ")
	public Collection<Event> getEventProjet(@Param("y") String username,@Param("c") String codeProjet);
	
	
	@Query(value="select e from Event e where e.user.username=:y  and e.createdBy !=:y and e.document.codePiece = :c  ")
	public Collection<Event> getEventDocument(@Param("y") String username,@Param("c") String idDocument);
	
	@Query(value="select e from Event e where e.user.username=:y  and e.createdBy !=:y and e.produit.id = :c  ")
	public Collection<Event> getEventProduit(@Param("y") String username,@Param("c") String idProduit);
	
	@Query(value="select e from Event e where e.produit.id = :c ")
	public Collection<Event> getEventProduit(@Param("c") String idProduit);
	

}
