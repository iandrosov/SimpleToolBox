<HTML>
<HEAD>
<LINK REL="stylesheet" TYPE="text/css" HREF="webMethods.css">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
<META HTTP-EQUIV="Expires" CONTENT="-1">
<script src="menu.js.txt"></script>
<style>
body {     border-top: 1px solid #97A6CB; }
</style>
</HEAD>
%scope param(filename='./packages/SimpleToolBox/config/ui.cnf')%
%invoke SimpleToolBox.admin:mainMenu%
<BODY CLASS="menu" onload="initMenu('%value buttonUrls/sections[0]/submenu[0]/url%');">



<TABLE WIDTH="100%" cellspacing=0 cellpadding=1 border=0>

%scope buttonUrls%
%loop sections%

    <TR><TD CLASS="menusection-%value name%">
       <img src="images/blank.gif" width="4" height="1" border="0">%value text%
    </TD></TR>

%loop submenu%
    <TR>
    <TD id="i%value url%"
        %ifvar url equals('none')%
          class="menuitem-unclickable"
        %else%
          class="menuitem"
          onmouseover="menuMouseOver(this, '%value url%');"
          onmouseout="menuMouseOut(this);"
          onclick="document.all['a%value url%'].click();"
        %endif%>
    <nobr>
    <img valign="middle" src="images/blank.gif" width="4" height="1" border="0"><img valign="middle"
            %ifvar url%id="%value url%" name="%value url%"%endif%
            src="images/blank.gif"
            height="8" width="8" border="0">
        %ifvar url equals('none')%%value text%
        %else%<A id="a%value url%" TARGET="%ifvar target%%value $host%%value target%%else%body%endif%"
           HREF="%value url%"
           %ifvar noarrow%%else%onclick="menuMove('%value url%', %ifvar target%'%value target%'%else%'body'%endif%); return true;"%endif%><span class="menuitemspan">%value text%%ifvar target%...
%endif%
</span></A>%endif%
    </nobr></TD></TR>%endloop%
<tr>
<td class="menuseparator"><img src="images/blank.gif" width="3" height="3" border="0"></td>
</tr>


    %endloop%
%endscope%
%endscope%
</TABLE>

<div style="height=0; width=0">
<form name="urlsaver">
<input type="hidden" name="helpURL" value="doc/OnlineHelp/SimpleToolBox_Service.htm">
</form>
</div>

</BODY>
</HTML>
