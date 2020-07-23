package ma.munisys.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component

public class Constants {
	
	
	private static String CODE_PROJET;
	private static String RAL;
	private static String LNF;
	private static String LFP;
	private static String LIV;
	
	private static List<String> requiredHeaders = new ArrayList<String>();
	
	public static String getCODE_PROJET() {
		return CODE_PROJET;
	}
	
	@Value("${etat.codeProjet}")
	public void setCODE_PROJET(String codeProjet) {
		CODE_PROJET = codeProjet;
		requiredHeaders.add(CODE_PROJET);
	}
	public static String getRAL() {
		return RAL;
	}
	@Value("${etat.ral}")
	public  void setRAL(String rAL) {
		RAL = rAL;
		requiredHeaders.add(RAL);
	}
	public static String getLNF() {
		return LNF;
	}
	@Value("${etat.lnf}")
	public  void setLNF(String lNF) {
		LNF = lNF;
		requiredHeaders.add(LNF);
	}

	public static String getLFP() {
		return LFP;
	}

	@Value("${etat.lfp}")
	public void setLFP(String lFP) {
		LFP = lFP;
		requiredHeaders.add(LNF);
	}

	public static String getLIV() {
		return LIV;
	}

	@Value("${etat.liv}")
	public void setLIV(String lIV) {
		LIV = lIV;
		requiredHeaders.add(LIV);
	}

	public static List<String> getRequiredHeaders() {
		return requiredHeaders;
	}

	public static void setRequiredHeaders(List<String> requiredHeaders) {
		Constants.requiredHeaders = requiredHeaders;
	}

	

	
	
	
	
	
	
	
	
	
	
	

}
