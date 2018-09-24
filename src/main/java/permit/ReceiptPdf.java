/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;

import java.util.*;
import java.sql.*;
import java.io.*;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.*;
import javax.naming.directory.*;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Chunk;
import com.lowagie.text.Phrase;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Font;
import com.lowagie.text.html.HtmlWriter;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; 

public class ReceiptPdf extends HttpServlet {

    String url="",url2="";
		static Logger logger = LogManager.getLogger(ReceiptPdf.class);
    boolean debug = false;
		static final long serialVersionUID = 1147L;

		public void doPost(HttpServletRequest req, 
											 HttpServletResponse res) 
				throws ServletException, IOException{
				doGet(req, res);
		}
    public void doGet(HttpServletRequest req, 
											HttpServletResponse res) 
				throws ServletException, IOException{
				res.setContentType("text/html");

				String id="", date_from="",date_to="", process_date="";
	    
				String name, value, message="", action="";
				Enumeration<String> values = req.getParameterNames();
				if(url.equals("")){
						url    = getServletContext().getInitParameter("url");
				}
				User user = null;
				HttpSession session = req.getSession(false);
				if(session != null){
						user = (User)session.getAttribute("user");
						if(user == null){
								String str = url+"Login";
								res.sendRedirect(str);
								return; 
						}
				}
				else{
						String str = url+"Login";
						res.sendRedirect(str);
						return; 
				}
				String [] vals;
				Receipt receipt = new Receipt();
				while (values.hasMoreElements()){
						name = values.nextElement().trim();
						vals = req.getParameterValues(name);
						value = vals[vals.length-1].trim();
			
						if (name.equals("id")) {
								receipt.setId(value);
						}			
						else if (name.equals("action")) {
								action=value;
						}
				}
				if(!action.equals("")){ 
						String back = receipt.doSelect();
						if(!back.equals("")){
								message += back;
						}
						else {
								writePages(res, receipt);
								return;
						}
				}
				PrintWriter out = res.getWriter();			  
				if(!message.equals("")){
						out.println("<html><head><title>ROW Permits</title>");
						out.println("</head><body>");
						out.println("<center>");
						out.println("<p>"+message+"</p>");
						out.println("<br />");
						out.print("</center></body></html>");
						out.close();
				}
				else{
						out.println("<html><head><title>ROW Permits</title>");
						out.println("</head><body>");
						out.println("<center>");
						if(receipt == null)
								out.println("<p>No receipt found</p>");				
						out.println("<br />");
						out.print("</center></body></html>");
						out.close();			
				}
    }
		/**
		 * handles the letter header
		 */
		PdfPTable getHeader(){
				//
				String str = "";
				String spacer = "   ";
				PdfPTable headTable = null;
				try{
						//
						// for http url use
						//
						Image image = Image.getInstance(url+"js/images/city_logo3.jpg");
						Font fnt = new Font(Font.TIMES_ROMAN, 10, 
																Font.NORMAL);
						Font fntb = new Font(Font.TIMES_ROMAN, 10, 
																 Font.BOLD);
						float[] widths = {25f, 40f, 35f}; // percentages
						headTable = new PdfPTable(widths);
						headTable.setWidthPercentage(100);
						headTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
						headTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
						image.scalePercent(15f);
						PdfPCell cell = new PdfPCell(image);
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						headTable.addCell(cell);
						//
						float[] width ={33f}; 
						PdfPTable midTable = new PdfPTable(width);
						midTable.setWidthPercentage(33);
						midTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
						midTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
						Phrase phrase = new Phrase();
						Chunk ch = new Chunk("City of Bloomington ",fntb);
						phrase.add(ch);
						cell = new PdfPCell(phrase);
						cell.setBorder(Rectangle.NO_BORDER);
						midTable.addCell(cell);
						//
						phrase = new Phrase();
						ch = new Chunk("Planning and Transportation Department ",fntb);
						phrase.add(ch);
						cell = new PdfPCell(phrase);
						cell.setBorder(Rectangle.NO_BORDER);
						midTable.addCell(cell);
						//
						phrase = new Phrase();
						ch = new Chunk("bloomington.in.gov", fntb);
						phrase.add(ch);
						cell = new PdfPCell(phrase);
						cell.setBorder(Rectangle.NO_BORDER);
						midTable.addCell(cell);
						//
						headTable.addCell(midTable);
						//
						PdfPTable rightTable = new PdfPTable(width);
						rightTable.setWidthPercentage(33);
						rightTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
						rightTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);			
						phrase = new Phrase();
						ch = new Chunk("401 N Morton St Suite 130 ",fntb);
						phrase.add(ch);
						cell = new PdfPCell(phrase);
						cell.setBorder(Rectangle.NO_BORDER);
						rightTable.addCell(cell);
						//
						phrase = new Phrase();
						ch = new Chunk("PO Box 100 \nBloomington, IN 47404",fntb);
						phrase.add(ch);
						cell = new PdfPCell(phrase);
						cell.setBorder(Rectangle.NO_BORDER);
						rightTable.addCell(cell);			
						//
						phrase = new Phrase();
						ch = new Chunk("\n Phone: (812) 349-3423\nFax (812) 349-3520\nEmail: planning@bloomington.in.gov",fntb);
						phrase.add(ch);
						cell = new PdfPCell(phrase);
						cell.setBorder(Rectangle.NO_BORDER);
						rightTable.addCell(cell);
						headTable.addCell(rightTable);
				}
				catch(Exception ex){
						logger.error(ex);
				}
				return headTable;
		}
		//
    void writePages(HttpServletResponse res,
										Receipt receipt){
				//
				// paper size legal (A4) 8.5 x 11
				// page 1-inch = 72 points
				//
				Invoice invoice = receipt.getInvoice();
				String fileName = "row_receipt_"+receipt.getId()+".pdf";
				Rectangle pageSize = new Rectangle(612, 792); // 8.5" X 11"
        Document document = new Document(pageSize, 36, 36, 18, 18);
				ServletOutputStream out = null;
				Font fnt = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL);
				Font fntb = new Font(Font.TIMES_ROMAN, 10, Font.BOLD);
				Font fnts = new Font(Font.TIMES_ROMAN, 8, Font.NORMAL);
				Font fntbs = new Font(Font.TIMES_ROMAN, 8, Font.BOLD);		
				String spacer = "   ";
				PdfPTable header = getHeader();
				Company company = invoice.getCompany();
				Contact contact = null;
				if(invoice.hasContact()){
						contact = invoice.getContact(); 
				}
				List<Page> pages = invoice.getPages();
				try{
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						PdfWriter writer = PdfWriter.getInstance(document, baos);
						String str = "";
						document.open();
						document.add(header);
						//
						// title
						float[] width = {100f}; // one cell
						PdfPTable table = new PdfPTable(width);
						table.setWidthPercentage(100.0f);
						PdfPCell cell = new PdfPCell(new Phrase("RECEIPT", fntb));
						//			
						cell.setBorder(Rectangle.BOTTOM);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
						document.add(table);
						//
						// we need these later
						Paragraph pp = new Paragraph();
						Chunk ch = new Chunk(" ", fntb);
						Phrase phrase = new Phrase();			
						//
						float[] widths = {35f, 30f, 35f}; // percentages
						table = new PdfPTable(widths);
						table.setWidthPercentage(100.0f);
						table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
						//
						// first row
						float[] widthOne = {100f};
						PdfPTable leftTable = new PdfPTable(widthOne);
						leftTable.setWidthPercentage(35.0f);
						leftTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
						//
						if(company != null){
								ch = new Chunk("Company\n", fntb);
								phrase = new Phrase();
								phrase.add(ch);
								cell = new PdfPCell(phrase);
								cell.setBorder(Rectangle.NO_BORDER);
								leftTable.addCell(cell);			
								phrase = new Phrase();				
								ch = new Chunk(company.getName()+"\n", fnt);
								phrase.add(ch);
								cell = new PdfPCell(phrase);
								cell.setBorder(Rectangle.NO_BORDER);
								leftTable.addCell(cell);				
						}
						if(contact != null){
								phrase = new Phrase();
								ch = new Chunk(contact.getFullName(), fnt);
								phrase.add(ch);
								cell = new PdfPCell(phrase);
								cell.setBorder(Rectangle.NO_BORDER);
								leftTable.addCell(cell);
								phrase = new Phrase();
								ch = new Chunk(contact.getAddress(), fnt);
								phrase.add(ch);
								cell = new PdfPCell(phrase);
								cell.setBorder(Rectangle.NO_BORDER);
								leftTable.addCell(cell);				
								ch = new Chunk(contact.getCityStateZip(), fnt);
								phrase = new Phrase();
								phrase.add(ch);
								cell = new PdfPCell(phrase);
								cell.setBorder(Rectangle.NO_BORDER);
								leftTable.addCell(cell);
						}
						table.addCell(leftTable);
						//
						// middle cell
						//
						cell = new PdfPCell(new Phrase(spacer, fnt));
						cell.setBorder(Rectangle.NO_BORDER);
						table.addCell(cell);
						//
						float[] widths2 = {50f, 50f}; // percentages
						PdfPTable rightTable = new PdfPTable(widths2);
						rightTable.setWidthPercentage(35.0f);
						rightTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
						//
						ch = new Chunk("Receipt No.", fntb);
						phrase = new Phrase();
						phrase.add(ch);			
						cell = new PdfPCell(phrase);
						cell.setBorder(Rectangle.NO_BORDER);
						rightTable.addCell(cell);
						//
						ch = new Chunk(receipt.getId(), fnt);
						phrase = new Phrase();
						phrase.add(ch);			
						cell = new PdfPCell(phrase);
						cell.setBorder(Rectangle.NO_BORDER);
						rightTable.addCell(cell);
						//
						ch = new Chunk("Invoice No.", fntb);
						phrase = new Phrase();
						phrase.add(ch);			
						cell = new PdfPCell(phrase);
						cell.setBorder(Rectangle.NO_BORDER);
						rightTable.addCell(cell);
						//
						ch = new Chunk(invoice.getInvoice_num(), fnt);
						phrase = new Phrase();
						phrase.add(ch);			
						cell = new PdfPCell(phrase);
						cell.setBorder(Rectangle.NO_BORDER);
						rightTable.addCell(cell);			
						//
						ch = new Chunk("Receipt Date", fntb);
						phrase = new Phrase();
						phrase.add(ch);			
						cell = new PdfPCell(phrase);
						cell.setBorder(Rectangle.NO_BORDER);
						rightTable.addCell(cell);
						//
						ch = new Chunk(receipt.getDate(), fnt);
						phrase = new Phrase();
						phrase.add(ch);			
						cell = new PdfPCell(phrase);
						cell.setBorder(Rectangle.NO_BORDER);
						rightTable.addCell(cell);
						//
						ch = new Chunk("Amount Paid $", fntb);
						phrase = new Phrase();
						phrase.add(ch);
						cell = new PdfPCell(phrase);
						cell.setBorder(Rectangle.NO_BORDER);
						rightTable.addCell(cell);
						//
						phrase = new Phrase();			
						ch = new Chunk("$"+receipt.getAmount_paid(), fnt);
						phrase.add(ch);			
						cell = new PdfPCell(phrase);
						cell.setBorder(Rectangle.NO_BORDER);
						rightTable.addCell(cell);
						//
						ch = new Chunk("Payment Method ", fntb);
						phrase = new Phrase();
						phrase.add(ch);
						cell = new PdfPCell(phrase);
						cell.setBorder(Rectangle.NO_BORDER);
						rightTable.addCell(cell);
						//
						phrase = new Phrase();
						ch = new Chunk(receipt.getPayment_type(), fnt);
						phrase.add(ch);			
						cell = new PdfPCell(phrase);
						cell.setBorder(Rectangle.NO_BORDER);
						rightTable.addCell(cell);
						//
						table.addCell(rightTable);
						//
						document.add(table);
						//
						phrase = new Phrase(new Chunk(spacer, fnt));
						document.add(phrase);
						//
						int jj = 0;
						if(pages != null){
								for(Page page:pages){
										jj++;
										PdfPTable borderTable = new PdfPTable(widthOne);
										borderTable.setWidthPercentage(100.0f);
										borderTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
										float[] widthTwo = {50f,50f};
										PdfPTable titleTable = new PdfPTable(widthTwo);
										titleTable.setWidthPercentage(75.0f);
										titleTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
										phrase = new Phrase("Receipt Number ", fntb);
										ch = new Chunk(receipt.getId(), fnt);
										phrase.add(ch);
										cell = new PdfPCell(phrase);
										cell.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
										cell.setBorder(Rectangle.NO_BORDER);
										titleTable.addCell(cell);			
										//
										phrase = new Phrase(page.getPage_num(), fnt);
										cell = new PdfPCell(phrase);
										cell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
										cell.setBorder(Rectangle.NO_BORDER);
										titleTable.addCell(cell);			
										//
										borderTable.addCell(titleTable);
										float[] width4 = {25f,40f,25f,10f};
										PdfPTable contTable = new PdfPTable(width4);
										cell = new PdfPCell(new Phrase("Excavation Permit Number", fntb));
										contTable.addCell(cell);
										cell = new PdfPCell(new Phrase("Project", fntb));
										contTable.addCell(cell);
										cell = new PdfPCell(new Phrase("Date Issued", fntb));
										contTable.addCell(cell);				
										cell = new PdfPCell(new Phrase("Permit Fee", fntb));
										contTable.addCell(cell);
										List<Permit> permits = page.getPermits();
										if(permits != null){
												for(Permit permit:permits){
														cell = new PdfPCell(new Phrase(permit.getPermit_num(), fnt));
														contTable.addCell(cell);
														phrase = new Phrase(permit.getProject()+"\n", fnt);
														List<Excavation> cuts = permit.getExcavations();
														if(cuts != null){
																for(Excavation one:cuts){
																		ch = new Chunk(one.getAddress()+" ("+one.getCut_type()+")",fnt);
																		phrase.add(ch);
																}
														}
														cell = new PdfPCell(phrase);
														contTable.addCell(cell);
														cell = new PdfPCell(new Phrase(permit.getDate(), fnt));
														contTable.addCell(cell);
														cell = new PdfPCell(new Phrase("$"+permit.getFee(), fnt));
														cell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
														contTable.addCell(cell);
														cell = new PdfPCell(new Phrase(spacer, fnt));
														//
														// space line
														//
														cell.setColspan(4);
														contTable.addCell(cell);
												}
										}
										if(page.getNeededLines() > 0){ // first page 
												for(int j=0;j<page.getNeededLines();j++){
														cell = new PdfPCell(new Phrase(spacer, fnt));
														contTable.addCell(cell);
														contTable.addCell(cell);
														contTable.addCell(cell);
														contTable.addCell(cell);
												}
										}
										if(jj == pages.size()){
												cell = new PdfPCell(new Phrase("Total Invoice Amount\n"+invoice.getTotal(), fntb));
												cell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
												cell.setColspan(4);
												contTable.addCell(cell);					
										}
										borderTable.addCell(contTable);
										cell = new PdfPCell(new Phrase("Thank You.",fnt));
					
										cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
										cell.setBorder(Rectangle.NO_BORDER);
										borderTable.addCell(cell);
										borderTable.addCell(titleTable); // receipt and date
										document.add(borderTable);
										if(jj < pages.size()){
												document.newPage();
										}
								}
						}
						//
						document.close();
						writer.close();
						res.setHeader("Expires", "0");
						res.setHeader("Cache-Control", 
													"must-revalidate, post-check=0, pre-check=0");
						// res.setHeader("Content-Disposition","attachment; filename="+fileName);			
						res.setHeader("Pragma", "public");
						//
						// setting the content type
						res.setContentType("application/pdf");
						//
						// the contentlength is needed for MSIE!!!
						res.setContentLength(baos.size());
						//
						out = res.getOutputStream();
						if(out != null){
								baos.writeTo(out);
						}
				}catch(Exception ex){
						logger.error(ex);
				}

		}
}






















































