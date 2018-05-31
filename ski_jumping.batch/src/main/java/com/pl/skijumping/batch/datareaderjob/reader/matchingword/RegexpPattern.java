package com.pl.skijumping.batch.datareaderjob.reader.matchingword;

class RegexpPattern {
    private RegexpPattern() {
    }

    static final String TOURNAMENT_YEAR = "<option\\svalue\\=\\\"(.+?)\"\\s";
    static final String FIRST_STEP_DATA_RACE = "<div\\sclass=\"date\">(.*?)<!--\\sdate-content\\s-->";
    static final String SECOND_STEP_DATA_RACE = "data-race=\"(.+?)<div id=\"mobile_race";
    static final String THIRD_STEP_DATA_RACE = "data-race=\"(.+?)\".*big-flag-(.+?)\">.*<h6>(.+?)</h6>.*";
    static final String FOURTH_STEP_DATA_RACE = "<p>(.+?)</p>";
    static final String SEASON_DATE = ".*<div\\sclass=\"date\">.*\"date-day\">(.+?)</span>.*\"date-month\">(.+?)</span.*\"date-year\">(.+?)</span>";
}
