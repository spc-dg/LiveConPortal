<%@tag description="PageMenuTag" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@attribute name="selected" description="Numeric index of menu that should be highlighted as selected"
             rtexprvalue="true" required="true" type="java.lang.Integer" %>


<%--
  ~ Copyright (c) 2013.
  ~ LICENSE: GNU General Public License v3 (GPL-3).
  --%>


<%--suppress HtmlUnknownTarget --%>
<ul>
    <li <c:if test="${selected==1}">class="current_page_item"</c:if>><a href="${pageContext.request.contextPath}"
                                                                        accesskey="1" title=""><s:message
            code="pages.search_games.menu"/></a></li>
</ul>
<a href="change_language/en">
    <img src="static/images/flags/flag_usa.png" class="menu-flag" alt="<s:message code="languages.english"/>"/>
</a>
<a href="change_language/ru">
    <img src="static/images/flags/flag_russia.png" class="menu-flag" alt="<s:message code="languages.russian"/>"/>
</a>
<a href="change_language/ro">
    <img src="static/images/flags/flag_romania.png" class="menu-flag" alt="<s:message code="languages.romanian"/>"/>
</a>