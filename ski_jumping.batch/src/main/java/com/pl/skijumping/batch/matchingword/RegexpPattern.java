package com.pl.skijumping.batch.matchingword;

class RegexpPattern {
    private RegexpPattern() {
//
    }

    static final String EVENT_ID = "eventid=(.{5}?)";

    static final String FIND_RACE_DATA_TEMPLATE = "<div class=\"table-row reset-padding.*?\">(.*?)\"btn__label\">download";
    static final String FIND_RACE_DATA_GET_RACE_ID = "px-md-1\\s.*raceid=(.*?)'";
    static final String FIND_RACE_DATA_CHECK_CANCELLED = "<span class=\"status__item\" title=\"(.*?)cancelled\">";
    static final String FIND_RACE_DATA_GET_DATE = "target=\"_self\">([0-9]{2}.*?)</a>";
    static final String FIND_RACE_DATA_GET_CODEX = "style=\"text-align:center\">(.*?)</div>";
    static final String FIND_RACE_DATA_GET_CATEGORY = "<div\\sclass=\"g-xs-12\\sjustify-left\">(.*?)</div>"; //return category
    static final String FIND_RACE_DATA_GET_GENDER = "gender__item_.\">(.+?)</div>"; //return gender
    static final String FIND_RACE_DATA_GET_HILL_SIZE = "<div class=\"clip\">(.*?)</div>"; //return eq. HS140



    static final String SECOND_FILTER_TOURNAMENT_YEAR = "<option\\svalue=\\\"(.*?)\"";
    static final String FIRST_STEP_DATA_RACE = "<div\\sclass=\"date\">(.*?)<!--\\sdate-content\\s-->";
    static final String SECOND_STEP_DATA_RACE = "data-race=\"(.+?)<div id=\"mobile_race";
    static final String THIRD_STEP_DATA_RACE = "data-race=\"(.+?)\".*big-flag-(.+?)\">.*<h6>(.+?)</h6>.*";
    static final String FOURTH_STEP_DATA_RACE = "<p>(.+?)</p>";
    static final String SEASON_DATE = ".*<div\\sclass=\"date\">.*\"date-day\">(.+?)</span>.*\"date-month\">(.+?)</span.*\"date-year\">(.+?)</span>";
    static final String RESULT_SYNCHRONIZE_FILTER = "</thead>(.+?)</tbody>";
    static final String RESULT_SYNCHRONIZE_FIRST_FILTER = "<tr>(.*?)</tr>";
    static final String RESULT_SYNCHRONIZE_SECOND_FILTER = "<td .*?>([^<]+?)</td>*";
    static final String RESULT_SYNCHRONIZE_SKIJUMPER_NAME = "<a .*?>([^<]+?)</a>";
}