<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<h3>Excavations Search Result</h3>
<s:if test="hasActionErrors()">
	<div class="errors">
		<s:actionerror/>
	</div>
</s:if>
<s:elseif test="hasActionMessages()">
	<div class="welcome">
		<s:actionmessage/>
  </div>
</s:elseif>
<s:if test="hasExcavations()">
	<s:set var="excavationsTitle" value="excavationsTitle" />	
	<s:set var="excavations" value="excavations" />
	<s:set var="apikey" value="key" />
	<%@  include file="excavations.jsp" %>	  
</s:if>
			
<%@  include file="footer.jsp" %>
























































