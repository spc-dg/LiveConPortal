<%--
  ~ Copyright (c) 2013.
  ~ LICENSE: GNU General Public License v3 (GPL-3).
  --%>

<%--suppress HtmlUnknownTarget --%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="scs" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>

<head>
    <scs:html_header_tag/>
    <title>LiveCon - <s:message code="pages.search_games.title_fragment"/></title>
</head>

<body>

<div id="header-wrapper">
    <div id="header" class="container">
        <div id="logo">
            <h1><a href="${pageContext.request.contextPath}">LiveCon</a></h1>
        </div>
        <div id="menu">
            <scs:page_menu_tag selected="1"/>
        </div>
    </div>
</div>

<div id="wrapper">
    <div id="page" class="container">

        <div id="content">
            <div class="title">
                <h2><s:message code="pages.search_games.title"/></h2>
                <span class="byline"><s:message code="pages.search_games.subtitle"/></span>
            </div>

            <div id="sidebar">
                <div id="stwo-col">
                    <div class="sbox1">
                        <h2><s:message code="pages.search_games.active_servers"/></h2>
                        <%--suppress HtmlFormInputWithoutLabel --%>
                        <select multiple id="servers" class="server_selects">
                            <c:forEach items="${servers}" var="server">
                                <c:if test="${server.archived eq 0}">
                                    <option value="${server.id}" selected="selected">${server.name}</option>
                                </c:if>
                            </c:forEach>
                        </select>

                        <h2 style="padding-top:10px;"><s:message
                                code="pages.search_games.archive_servers"/></h2>
                        <%--suppress HtmlFormInputWithoutLabel --%>
                        <select multiple id="archive_servers" class="server_selects">
                            <c:forEach items="${servers}" var="server">
                                <c:if test="${server.archived eq 1}">
                                    <option value="${server.id}" selected="selected">${server.name}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="sbox1">
                        <h2><s:message code="pages.search_games.had_player"/></h2>
                        <%--suppress HtmlFormInputWithoutLabel --%>
                        <input type="text" id="player_finder"/>

                        <h2 style="padding-top:11px;"><s:message code="pages.search_games.with_territory"/></h2>
                        <%--suppress HtmlFormInputWithoutLabel --%>
                        <select multiple id="selected_territory" class="territory_selects"
                                style="padding-bottom:0px;">
                            <option value="---" selected="selected">---</option>
                            <option value="NA">NA</option>
                            <option value="SA">SA</option>
                            <option value="EU">EU</option>
                            <option value="RU">RU</option>
                            <option value="AS">AS</option>
                            <option value="AF">AF</option>
                        </select>
                        <button id="add_player" style="height:22px; margin-bottom:2px;"></button>
                    </div>
                    <div class="sbox1">
                        <table border="0" class="display" id="players_added" style="padding-top:10px;"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <div id="featured-wrapper">
        <div id="game_list" class="container loadmasked"></div>
    </div>
    <div id="paginator_wrapper" class="loadmasked">
        <div id="paginator"></div>
    </div>
</div>
<scs:html_footer_tag/>
<script type="text/javascript">
var numAddedPlayers = 0;
var addedPlayers = [];
var addedTerritories = [];
var rowSequence = 0;
var page = 1;
var maxPage = 1;

/**
 * Does the actual search for the games.
 */
function searchData() {

    // Get raw server info
    var inServers = [];
    var servers = $("#servers").val();
    var archiveServers = $("#archive_servers").val();

    // Get all selected servers
    if (servers) {
        $.each(servers, function (index, value) {
            inServers.push(value);
        });
    }

    if (archiveServers) {
        $.each(archiveServers, function (index, value) {
            inServers.push(value);
        });
    }

    // There must be servers
    if (inServers.length > 0) {
        // Now it gets all player info
        var page = 1;
        var numPerPage = 8;
        var playerData = {};
        var players = [];

        // If they exist
        if (addedPlayers.length > 0) {
            $.each(addedPlayers, function (index, value) {
                var playerTerritory = addedTerritories[index];

                // create a player object and push it to the player array
                if (isThere(value)) {
                    playerData = {name: value, territory: playerTerritory};
                    players.push(playerData);
                }
            });
        }

        // Now create a request object, add the data to it and serialise it
        var req = {
            page: page,
            numPerPage: 6,
            servers: inServers,
            players: players
        };
        var jreq = $.JSON.encode(req);
        var jresp = {};
        var jerr = {};

        // mask elements
        $(".loadmasked").mask("<s:message code="ui.mask.loading"/>", 500);

        // Does the search
        $.ajax({
            context: this,
            type: 'post',
            url: 'search',
            data: jreq,
            async: true,
            contentType: "application/json",

            complete: function (data) {
                $(".loadmasked").unmask();
            },
            success: function (data) {
                jresp = data;

                // If errors
                if (jresp.hadErrors) {
                    var errors = "";
                    $.each(jresp.errors, function (index, value) {
                        errors += value + "\n";
                    });
                    alert(errors);
                    return;
                }

                var strGames = '';
                $.each(data.games, function (index, value) {
                    var strGame = '';
                });


                // clean the old data
                $("#game_list").html('');

                // Then handle pagination info and load the paginator
                var maxPage = data.maxPages;
                page = data.page;

                // Page pagination
                $("#paginator").paginate({
                    count: maxPage,
                    start: 1,
                    display: page,
                    border: true,
                    border_color: '#fff',
                    text_color: '#fff',
                    background_color: '#0073ea',
                    border_hover_color: '#ccc',
                    text_hover_color: '#000',
                    background_hover_color: '#fff',
                    images: false,
                    mouse: 'press',
                    firstName: '<s:message code="ui.pagination.firstname"/>',
                    lastName: '<s:message code="ui.pagination.lastname"/>',
                    onChange: function (newPage) {
                        // change page and search
                        page = newPage;
                        searchData();
                    }
                });

                // Then load the game data
                var terrMap = {"NA": 1, "SA": 2, "EU": 3, "RU": 4, "AS": 5, "AF": 6};
                var teamMap = {"RED": 1, "GREEN": 2, "BLUE": 3, "YELLOW": 4, "ORANGE": 5, "TURQ": 6, "WHITE": 7};
                var allStr = "";
                var index = 1;
                $.each(data.games, function (igames, game) {
                    var gameStr = "";
                    var wrap3 = (igames + 1) % 3;

                    var start = $.format.date(new Date(game.startDate), 'yyyy-MM-dd H:mm');
                    var end = $.format.date(new Date(game.endDate), 'yyyy-MM-dd H:mm');
                    var dcrec = game.dcrec;

                    // Create the image request string
                    var imgReqStr = "/territory/";
                    if (game.serverData.diplo)
                        imgReqStr += "2";
                    else
                        imgReqStr += "1";

                    $.each(game.playerData, function (iplayers, playerData) {
                        imgReqStr += "_";
                        imgReqStr += terrMap[playerData.territory] + "-";
                        imgReqStr += teamMap[playerData.teams[playerData.teams.length - 1]];
                    });
                    imgReqStr += ".png";

                    // scores signed or unsigned
                    var signed = '<s:message code="page.search_games.game_box.signed"/>';
                    var signedColor = 'green'

                    if (!game.scoresSigned) {
                        signed = '<s:message code="page.search_games.game_box.unsigned"/>';
                        signedColor = 'red';
                    }

                    // Create a small individual game string and compose it
                    gameStr += '<div class="column' + wrap3 + '">';
                    gameStr += '<span class="icon icon-gif"></span>';
                    gameStr += '<div style="float:left; ">';
                    gameStr += '<img src="' + imgReqStr + '"/>';
                    gameStr += '</div>';
                    gameStr += '<div class="title" style="float:left; padding-left:2px; width: 215px;">';
                    gameStr += '<h3 style="padding-left:2px;">' + game.serverData.name + '</h3>';
                    gameStr += '<table>';
                    gameStr += '<tr>';
                    gameStr += '<td><span style="float:left;"><s:message code="page.search_games.game_box.startdate"/>:</span></td><td><span style="float:right;"> ' + start + '</span> ';
                    gameStr += '</td></tr><tr>';
                    gameStr += '<td><span style="float:left;"><s:message code="page.search_games.game_box.enddate"/>:</span></td><td> <span style="float:right;">' + end + '</span>';
                    gameStr += '</td></tr>';
                    gameStr += '<tr><td><span style="float:left; color:' + signedColor + ';">' + signed + '</td></tr>';
                    gameStr += '<tr><td colspan="2">';

                    if (typeof dcrec !== 'undefined' && dcrec != null && dcrec.trim() != '')
                        gameStr += '<button onclick="goPost(\'dcrec' + '\',{dcrecPath:\'' + dcrec + '\'},\'_new\');" style="float:left;" class="dcrec_btn">dcrec</button>';

                    gameStr += '</td></tr>';
                    gameStr += '</table>';
                    gameStr += '</div>';
                    gameStr += '<p>';
                    gameStr += '<table id="gamebox_' + index + '" class="gamebox_table">';
                    gameStr += '<thead>';
                    gameStr += '<tr>';
                    gameStr += '<th><s:message code="page.search_games.game_box.player"/></th>';
                    gameStr += '<th><s:message code="page.search_games.game_box.territory"/></th>';
                    gameStr += '<th><s:message code="page.search_games.game_box.teams"/></th>';
                    gameStr += '<th><s:message code="page.search_games.game_box.score"/></th>';
                    gameStr += '</tr>';
                    gameStr += '</thead>';
                    gameStr += '<tbody>';

                    $.each(game.playerData, function (iplayers, playerData) {
                        var itemPlayer = "";
                        $.each(playerData.names, function (ip, ipData) {
                            itemPlayer += ipData + '</br>';
                        });

                        var itemTerritory = playerData.territory;

                        var itemTeams = "";
                        var lastTeam = "";
                        $.each(playerData.teams, function (it, itData) {
                            itemTeams += '<img src="static/images/teams/team_' + itData + '.png" widht="12" height="12" style="padding-right:3px;"/>';
                            lastTeam = itData
                        });

                        // adding this for sortability
                        itemTeams += '<span style="display:none;">' + lastTeam + '</span>';

                        var itemScore = playerData.score;


                        gameStr += '<tr>';
                        gameStr += '<td>' + itemPlayer + '</td>';
                        gameStr += '<td>' + itemTerritory + '</td>';
                        gameStr += '<td>' + itemTeams + '</td>';
                        gameStr += '<td>' + itemScore + '</td>';
                        gameStr += '</tr>';

                    });


                    gameStr += '</tbody>';
                    gameStr += '</table>';
                    gameStr += '</p>';
                    gameStr += '</div>';

                    // Add it to the big string
                    allStr += gameStr;

                    // increment the master index
                    index++;
                });

                // Then just append it to the dom
                $("#game_list").append(allStr);

                // Data table the game tables
                $('.gamebox_table').dataTable({
                    "bPaginate": false,
                    "bLengthChange": true,
                    "bFilter": false,
                    "bAutoWidth": true,
                    "bInfo": false
                });

                // Button the dcrec button
                $(".dcrec_btn").button();
            },
            error: function (data) {
                jerr = data;
                alert($.JSON.encode(jerr));
            }
        });
    }
}

/**
 * Deletes a player from the table.
 * @param rowId row id
 */
function del(rowId) {
    var id = "player_" + rowId;
    $('#players_added').dataTable().fnDeleteRow($("#" + id)[0]);

    numAddedPlayers--;
    delete addedPlayers[rowId];
    delete addedTerritories[rowId];

    searchData();
}

$(function () {
    // Multiselect server selects
    $(".server_selects").multiselect({
        noneSelectedText: "<s:message code="ui.multiselect.non_selected_text"/>",
        selectedText: "# <s:message code="ui.multiselect.of"/> # <s:message code="ui.multiselect.selected"/>",
        checkAllText: "<s:message code ="ui.multiselect.checkall"/>",
        uncheckAllText: "<s:message code="ui.multiselect.uncheckall"/>",
        close: function (event, ui) {
            searchData();
        }
    });

    // Autocomplete player finder
    $("#player_finder").autocomplete({
        minLength: 0,
        delay: 500,
        source: 'autocomplete_player'
    });

    // Autocomplete player finder on click
    $("#player_finder").click(function () {
        $("#player_finder").autocomplete("search");
    });

    // Multiselect territory select
    $(".territory_selects").multiselect({
        noneSelectedText: "<s:message code="ui.multiselect.non_selected_text"/>",
        selectedText: "# <s:message code="ui.multiselect.of"/> # <s:message code="ui.multiselect.selected"/>",
        multiple: false,
        selectedList: 1,
        minWidth: 120,
        height: 220
    });

    // Add player button
    $("#add_player").button({
        icons: {
            primary: "ui-icon-circle-plus"
        },
        text: false
    });

    $("#add_player").click(function () {
        var name = $("#player_finder").val();
        var territory = $("#selected_territory").val()[0];
        var id = "player_" + rowSequence;

        // Error checking logic
        if (name.trim() == '')
            alert('<s:message code="pages.search_games.add_player.name_box_empty"/>');
        else if (numAddedPlayers + 1 > 6)
            alert('<s:message code="pages.search_games.add_player.max_six_players"/>');
        else if ($.inArray(name, addedPlayers) > -1)
            alert('<s:message code="pages.search_games.add_player.player_already_added"/>');
        else if (territory != '---' && $.inArray(territory, addedTerritories) > -1)
            alert('<s:message code="pages.search_games.add_player.territory_already_added"/>')
        else {
            // Create a unique delete button for removing items from the table
            var button = '<button id="btn_del_' + rowSequence + '" style="height:22px;" onclick="del(' + rowSequence + ');"></button>';

            // Add data to the player table
            $('#players_added').dataTable().fnAddData([
                {
                    "0": name,
                    "1": territory,
                    "2": button,
                    "DT_RowId": id
                }
            ]);

            // Transform the button into a jquery ui button
            $("#btn_del_" + rowSequence).button({
                icons: {
                    primary: "ui-icon-circle-minus"
                },
                text: false
            });

            rowSequence++;
            numAddedPlayers++;
            addedPlayers.push(name);
            addedTerritories.push(territory);

            searchData();
        }
    });

    // Players played table
    $('#players_added').dataTable({
        "bPaginate": false,
        "bLengthChange": true,
        "bFilter": false,
        "bSort": false,
        "bAutoWidth": false,
        "bInfo": false,
        "aoColumns": [
            { "sTitle": '<s:message code="pages.search_games.table.player"/>' },
            { "sTitle": '<s:message code="pages.search_games.table.territory"/>' },
            { "sTitle": '' }
        ],
        "oLanguage": {
            "sZeroRecords": '<s:message code="pages.search_games.table.no_data"/>'
        }
    });

    // Do a search for page 1 without any other data but the full servers selected
    page = 1;
    searchData();
});
</script>
<scs:page_footer_tag/>
</body>
</html>



