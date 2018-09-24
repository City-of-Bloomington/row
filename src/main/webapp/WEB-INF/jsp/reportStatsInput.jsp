<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="statsReport" method="post">    
  <h3> Stats Reports </h3>
  <s:if test="hasActionErrors()">
		<div class="errors">
      <s:actionerror/>
		</div>
  </s:if>
  <s:if test="hasActionMessages()">
		<div class="welcome">
      <s:actionmessage/>
		</div>
  </s:if>
  <p>Select one or more of report types.</p>
  <table border="1" width="100%">
		<tr>
			<td>
				<table with="100%">
					<tr>
						<td align="right" valign="top" rowspan="8"><label>Report type:</label></td>
						<td align="left"><s:checkbox name="report.permits" value="report.permits"  />Permit Stats</td>
					</tr>
					<tr>
						<td align="left"><s:checkbox name="report.excavations" value="report.excavations"  />Excavation Stats</td>
					</tr>
					<tr>
						<td align="left"><s:checkbox name="report.invoices" value="report.invoices"  />Invoices Stats</td>
					</tr>
					<tr>
						<td align="left"><s:checkbox name="report.bonds" value="report.bonds"  />Bonds Expire Stats</td>
					</tr>		  
				</table>
			</td>
		</tr>
		<tr>
			<td align="left">
				<table width="100%">
					<tr>  
						<td align="right"><label>Year</label></td>
						<td align="left"><s:select name="report.year" list="years" value="%{report.year}" /> or</td>
					</tr>
					<tr>
						<td align="right"><label>Date, from:</label></td>
						<td align="left"><s:textfield name="report.date_from" maxlength="10" size="10" value="%{report.date_from}" cssClass="date" /><label> To </label><s:textfield name="report.date_to" maxlength="10" size="10" value="%{report.date_to}" cssClass="date" /></td>
					</tr>
				</table>
			</td>
		</tr>	
		<tr>
			<td colspan="2" valign="top" align="right">
				<s:submit name="action" type="button" value="Submit" />
			</td>
		</tr>
  </table>
</s:form>  
<%@  include file="footer.jsp" %>	






































