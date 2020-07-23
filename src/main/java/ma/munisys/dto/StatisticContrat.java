package ma.munisys.dto;

public class StatisticContrat {

	
	private double totalMontantAnnuel;
	private double totalMontantFactureAn;
	private double totalMontantAFactureAn;
	private double totalMontantProvisionFactureAn;
	private double totalMontantProvisionAFactureAn;
	
	public StatisticContrat() {
	}
	
	

	public StatisticContrat(double totalMontantAnnuel, double totalMontantFactureAn, double totalMontantAFactureAn,
			double totalMontantProvisionFactureAn, double totalMontantProvisionAFactureAn) {
		super();
		this.totalMontantAnnuel = totalMontantAnnuel;
		this.totalMontantFactureAn = totalMontantFactureAn;
		this.totalMontantAFactureAn = totalMontantAFactureAn;
		this.totalMontantProvisionFactureAn = totalMontantProvisionFactureAn;
		this.totalMontantProvisionAFactureAn = totalMontantProvisionAFactureAn;
	}



	public double getTotalMontantAnnuel() {
		return totalMontantAnnuel;
	}

	public void setTotalMontantAnnuel(double totalMontantAnnuel) {
		this.totalMontantAnnuel = totalMontantAnnuel;
	}

	public double getTotalMontantFactureAn() {
		return totalMontantFactureAn;
	}

	public void setTotalMontantFactureAn(double totalMontantFactureAn) {
		this.totalMontantFactureAn = totalMontantFactureAn;
	}

	public double getTotalMontantAFactureAn() {
		return totalMontantAFactureAn;
	}

	public void setTotalMontantAFactureAn(double totalMontantAFactureAn) {
		this.totalMontantAFactureAn = totalMontantAFactureAn;
	}

	public double getTotalMontantProvisionFactureAn() {
		return totalMontantProvisionFactureAn;
	}

	public void setTotalMontantProvisionFactureAn(double totalMontantProvisionFactureAn) {
		this.totalMontantProvisionFactureAn = totalMontantProvisionFactureAn;
	}

	public double getTotalMontantProvisionAFactureAn() {
		return totalMontantProvisionAFactureAn;
	}

	public void setTotalMontantProvisionAFactureAn(double totalMontantProvisionAFactureAn) {
		this.totalMontantProvisionAFactureAn = totalMontantProvisionAFactureAn;
	}
	
	
	
	
	
}
