package com.pl.skijumping.batch.matchingword;

public class RegexpPattern {
    private RegexpPattern() {
//
    }

    static final String EVENT_ID = "eventid=(.{5}?)";

    static final String FIND_RACE_DATA_TEMPLATE = "<div class=\"table-row reset-padding\">(.*?)g-xs-12 split-row stop-bubbling";
    static final String FIND_RACE_DATA_GET_RACE_ID = "raceid=(.*?)\"";
    static final String FIND_RACE_DATA_CHECK_CANCELLED = "<span class=\"status__item\" title=\"(.*?)cancelled\">";
    static final String FIND_RACE_DATA_GET_DATE = "medium reset-padding\"><div class=\"g-xs-24 justify-left\">(.*?)</div>";
    static final String FIND_RACE_DATA_GET_DATE_SECOND_OPT = "target=\"_self\">([0-9]{2}.*?)</a>";
    static final String FIND_RACE_DATA_GET_CODEX = "target=\"_self\">([0-9]{4})<"; //"target=\"_self\">([0-9]*)<"
    static final String FIND_RACE_DATA_GET_CODEX_SECOND = "\\sstyle=\"text-align:center\">(.*?)</div>";
    static final String FIND_RACE_DATA_GET_CATEGORY = "<div\\sclass=\"g-xs-12\\sjustify-left\">(.*?)</div>"; //return category
    static final String FIND_RACE_DATA_GET_GENDER = "gender__item_.\">(.+?)</div>"; //return gender
    static final String FIND_RACE_DATA_GET_HILL_SIZE = "<div class=\"clip\">(.*?)</div>"; //return eq. HS140

    static final String FIND_RACE_DATA_GET_CITY = "event-header__name\">(.*?)</h1>";

    static final String FIND_JUMP_RESULT_TEMPLATE = "(<a class=\"table-row\"(.*?)</a>)";
    static final String FIND_JUMP_RESULT_FIS_CODE = "g-lg-2 g-md-2 g-sm-2 hidden-xs justify-right gray pr-1\">(.*?)<";
    static final String FIND_JUMP_RESULT_JUMPER_NAME = "justify-left bold\">(.*?)<";
    static final String FIND_JUMP_RESULT_POINTS = "g-lg-2 g-md-2 g-sm-2 justify-right bold hidden-xs\">(.*?)<";
    static final String FIND_JUMP_RESULT_RANK = "gray\\sbold\">(.*?)<";
    static final String FIND_JUMP_RESULT_TOTAL = "blue bold\\s\">(.*?)<";
    static final String FIND_JUMP_RESULT_COMPETITOR_ID = "competitorid=(.*?)&";
    static final String FIND_JUMP_RESULT_TEAM_TOTAL= "justify-right\">([0-9]*.?[0-9]*)</div>JUMP_RESULT";

    static final String FIND_TEAM_JUMP_RESULT_GROUPS = "#events-info-results(.*?)</main></div>";
    static final String FIND_TEAM_JUMP_RESULT_RANK= "justify-right gray pr-1\">(.*?)</div>";
    static final String FIND_TEAM_FIS_CODE= "justify-right gray pr-1\">(.*?)</div>";
    static final String FIND_TEAM_JUMP_RESULT = "table-row_theme_additional(.*?)JUMP_RESULT_[0-9]{2}";

    public static final String JUMP_RESULT_DATA = "</div></div></a>";
    public static final String JUMP_RESULT_DATA_BEGINING_PART = "table-row table-row_theme_main";

    static final String FIND_SKI_JUMPER_BIRTHDAY = "(Birthdate(.*?)profile-info__value\">(.*?)</span>)";
    static final String FIND_SKI_JUMPER_GENDER = "(Gender(.*?)profile-info__value\">(.*?)</span>)";
    static final String FIND_SKI_JUMPER_MARTIAL_STATUS = "(Marital Status(.*?)profile-info__value\">(.*?)</span>)";
    static final String FIND_SKI_JUMPER_TEAM = "team spacer__section\">(.*?)</div>";
    static final String FIND_SKI_JUMPER_COUNTRY = "country__name\">(.*?)</span>";
}