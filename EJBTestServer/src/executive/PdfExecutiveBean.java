package executive;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dao.AccountDAO;
import entity.Account;

/**
 * Exekutivna beana, ktora vytvara pdf subor.
 * @author Matus Barabas
 *
 */
@Stateless
public class PdfExecutiveBean {
	private static String FILE = "c:/Users/Mat��/Desktop/Statistics_of_bet.pdf";
	private static Logger LOG;
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 50, Font.BOLD);
	private static Font chapterFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private static Font tableBold = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	private static Font tableBold1 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
	
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * Vytvorenie dokumentu, volanie funkcii na pridanie obsahu a titulnej strany.
	 * @return
	 */
	public boolean createPdf(){
		LOG = Logger.getLogger(PdfExecutiveBean.class.getName());
		AccountDAO accountDAO = new AccountDAO();
		List<Account> a = accountDAO.getAllStats(em);
		
		Document document = new Document();
	    try {
			PdfWriter.getInstance(document, new FileOutputStream(FILE));
		} catch (FileNotFoundException e) {
			LOG.equals(e);
		} catch (DocumentException e) {
			LOG.equals(e);
		}
	    document.open();
	    addTitlePage(document);
	    addContent(document, a);
	    document.close();
	    
		return true;
	}
	
	/**
	 * Pridanie titulnej strany dokumentu.
	 * @param document
	 */
	private static void addTitlePage(Document document){
		LOG = Logger.getLogger(PdfExecutiveBean.class.getName());
		Paragraph preface = new Paragraph();
	    addEmptyLine(preface, 15);
	    
	    Paragraph par = new Paragraph("Statistics of account", catFont);
	    par.setAlignment(Element.ALIGN_CENTER);
	    preface.add(par);
	    addEmptyLine(preface, 1);
	    
	    par = new Paragraph("Document generated by: " + System.getProperty("user.name") + ", " + new Date(), smallBold);
	    par.setAlignment(Element.ALIGN_CENTER);
	    preface.add(par);

	    try {
			document.add(preface);
		} catch (DocumentException e) {
			LOG.equals(e);
		}
	    document.newPage();
	}
	
	/**
	 * Pridanie hlavneho obsahu, volanie metody na vytvorenie tabulky.
	 * @param document
	 * @param a
	 */
	private static void addContent(Document document, List<Account> a){
		LOG = Logger.getLogger(PdfExecutiveBean.class.getName());
		Anchor anchor = new Anchor("Comlete statistics of all bets", chapterFont);
	    anchor.setName("Comlete statistics of all bets");
	    
	    Chapter catPart = new Chapter(new Paragraph(anchor), 1);
		
	    Paragraph subPara = new Paragraph("Table of bets", subFont);
	    addEmptyLine(subPara, 1);
	    Section subCatPart = catPart.addSection(subPara);
	
	    createTable(subCatPart, a);
	    
	    try {
			document.add(catPart);
		} catch (DocumentException e) {
			LOG.equals(e);
		}
	}
	
	/**
	 * Vytvorenie tabulky, ktora obsahuje data o jednotlivych stavkach.
	 * @param subCatPart
	 * @param a
	 */
	private static void createTable(Section subCatPart, List<Account> a){
		PdfPTable table = new PdfPTable(9);

		PdfPCell c1 = new PdfPCell(new Paragraph("H. Team", tableBold));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Paragraph("HS", tableBold));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Paragraph("GS", tableBold));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Paragraph("N", tableBold));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Paragraph("G. Team", tableBold));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Paragraph("Bet", tableBold));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Paragraph("Money Bet", tableBold));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Paragraph("May Win", tableBold));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Paragraph("Result", tableBold));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);		
		table.addCell(c1);	  
		 
		for(int i = 0; i < a.size() ; i++){
			table.addCell(new PdfPCell(new Paragraph(a.get(i).getBetting_event().getGames().getHome().getShortcut(), tableBold1)));
			table.addCell(new PdfPCell(new Paragraph(a.get(i).getBetting_event().getGames().getGoals_home().toString(), tableBold1)));
			table.addCell(new PdfPCell(new Paragraph(a.get(i).getBetting_event().getGames().getGoals_visitor().toString(), tableBold1)));
			table.addCell(new PdfPCell(new Paragraph(a.get(i).getBetting_event().getGames().getNote(), tableBold1)));
			table.addCell(new PdfPCell(new Paragraph(a.get(i).getBetting_event().getGames().getVisitor().getShortcut(), tableBold1)));
			table.addCell(new PdfPCell(new Paragraph(a.get(i).getBet().toString(), tableBold1)));
			table.addCell(new PdfPCell(new Paragraph(a.get(i).getMoney_bet().toString(), tableBold1)));
			table.addCell(new PdfPCell(new Paragraph(a.get(i).getMay_win().toString(), tableBold1)));
			table.addCell(new PdfPCell(new Paragraph(a.get(i).getResult(), tableBold1)));
		}
		  
		subCatPart.add(table);
	}

	/**
	 * Pridanie prazdneho riadku do pdf suboru.
	 * @param paragraph
	 * @param number
	 */
	private static void addEmptyLine(Paragraph paragraph, int number) {
	    for (int i = 0; i < number; i++) {
	      paragraph.add(new Paragraph(" "));
	    }
	}
}
