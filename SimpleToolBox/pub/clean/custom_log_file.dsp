<HTML>

<HEAD>
%include ../../../WmRoot/pub/b2bStyle.css%

<SCRIPT LANGUAGE="JavaScript">


	function onClick (action) {
		if (action == "reset") {
			document.location="custom_log_file.dsp?action=reset";
		} else if (action == "submit") {
			document.changeform.submit();
		}
	}


</SCRIPT>
</HEAD>

<BODY>

<TABLE WIDTH=100%>

%ifvar action%
%switch action%
%case 'change'%
%scope param(file='./packages/SimpleToolBox/config/clean.properties') param(property='server.clean.file')%
	%invoke SimpleToolBox.config:setProperty%
	<TR><TH id="message" colspan=4>%value message%</TH></TR>
	%endinvoke%
%endscope%
%endswitch%
%endif action%

<TR><TD>
<FORM name="changeform" action="custom_log_file.dsp" method="POST">
<INPUT type="hidden" name="action" value="change">
<TABLE WIDTH=100%>

	<TR><TH class="title" colspan=2>Custom Log File Name</TH></TR>

	<TR><TD class="action" colspan=2>
		<INPUT type="button" value="Submit" onclick="onClick('submit');"></INPUT>
		</TD></TR>


	<TR class="heading"><TH colspan=2>Current Custom Log File</TH></TR>
%scope param(file='./packages/SimpleToolBox/config/clean.properties') param(property='server.clean.file')%
%invoke SimpleToolBox.config:getProperty%
			

	<TR><TH class="rowlabel">Custom Log File</TH>
	    <TD class="rowdata">
		%value value%
		</TD>
		</TR>

	<TR class="heading"><TH colspan=2>Custom Log File</TH></TR>

	
	<TR><TH class="rowlabel" width=28%>File Name</TH>
		<TD class="rowdata">

			<INPUT name="value" size=43 value="%value value%"></INPUT>
		</TD>
		</TR>

%endinvoke%
%endscope%
</TABLE>
</TD></TR></TABLE>
</FORM>
<P>
NOTE: Defines a configuration parameter to set custom log file name for removal.
</P>
</BODY>

</HTML>
