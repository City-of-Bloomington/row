<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="report" method="post">    
  <h3> Detailed Reports </h3>
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
  <p>Select only one of the reports.</p>
  <table border="1" width="100%">
		<tr>
			<td>
				<table with="100%">
					<tr>
						<th valig="top" width="20%">&nbsp;&nbsp;</th>
						<td align="left">			
							<s:iterator value="report_types">
								<li><s:radio name="report.report_type" list="#{id:name}" /></li>
							</s:iterator>
						</td>				
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






































