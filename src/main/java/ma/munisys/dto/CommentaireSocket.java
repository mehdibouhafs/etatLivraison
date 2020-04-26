package ma.munisys.dto;

import ma.munisys.entities.Commentaire;

public class CommentaireSocket extends Commentaire{
	
	private String joinId;
	
	
	public CommentaireSocket() {
		super();
	}


	public String getJoinId() {
		return joinId;
	}


	public void setJoinId(String joinId) {
		this.joinId = joinId;
	}
	
	
	
	

}
