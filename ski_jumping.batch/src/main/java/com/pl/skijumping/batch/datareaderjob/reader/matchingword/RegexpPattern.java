package com.pl.skijumping.batch.datareaderjob.reader.matchingword;

class RegexpPattern {
    private RegexpPattern() {
    }

    static final String TOURNAMENT_YEAR = "<option\\svalue\\=\\\"(.+?)\"\\s";
    static final String SEASON_DATA = ".*<div\\sclass=\"date\">.*\"date-day\">(.+?)</span>.*\"date-month\">(.+?)</span.*\"date-year\">(.+?)</span>.*data-race=\"(.+?)\".*";


    static final String RACE_DATA = "<div\\s*class=\"date-text\">\\s*<h6>(.+?)</h6>\\s*<div>\\" +
            "s*<p>(.+?)</p>\\s*<p>(.+?)</p>\\s*<p>(.+?)</p>\\s*</div>";

}
