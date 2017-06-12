package facade;

import javax.ejb.Remote;

/**
 * Remote rozhranie pre fasadnu beanu, ktora generuje pdf subor.
 * @author Matus Barabas
 *
 */
@Remote
public interface PdfFacadeBeanRemote {
	public boolean createNewPdf();
}
