package facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import executive.PdfExecutiveBean;

/**
 * Fasadna beana pre vytvorenie pdf suboru.
 * @author Matus Barabas
 *
 */
@Stateless
public class PdfFacadeBean implements PdfFacadeBeanRemote{

	@EJB
	private PdfExecutiveBean pdf;

	@Override
	public boolean createNewPdf() {
		return pdf.createPdf();
	}
}
