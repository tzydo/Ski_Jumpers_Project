package com.pl.skijumping.batch.findracedatajob.processor;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.dto.JumpCategoryDTO;
import com.pl.skijumping.service.JumpCategoryService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class FindRaceDataTest {

 private String text = "<div class=\"table-row reset-padding\"><div class=\"container g-row px-sm-1 px-xs-0\"><div class=\"" +
         "g-lg-22 g-md-22 g-sm-21 g-xs-24\"><div class=\"g-row\"><a class=\"g-lg-1 g-md-1 g-sm-2 hidden-sm-down justify-left\"" +
         " href=\"https://www.fis-ski.com/DB/general/results.html?sectorcode=JP&raceid=5190\" target=\"_self\"><div class=\"" +
         "status\"><div class=\"status__inner\"><div class=\"status__item-wrapper\"><span class=\"status__item status__item_selected\"" +
         " title=\"Results available\">R</span><span class=\"status__item status__item_selected\" title=\"PDF available\"" +
         ">P</span></div><div class=\"status__item-wrapper\"><span class=\"status__item\" title=\"No changes\">C</span><span class=\"" +
         "status__item\" title=\"Not cancelled\">C</span></div></div></div></a><a class=\"px-md-1 px-lg-1 pl-xs-1 g-lg-2 g-md-3 g-sm-2 g-xs-4 justify-left" +
         "\" href=\"https://www.fis-ski.com/DB/general/results.html?sectorcode=JP&raceid=5190\" target=\"_self\">14 Dec</a><div class=\"" +
         "g-lg-2 g-md-2 g-sm-3 g-xs-4 justify-center\"><a class=\"link link_theme_dark stop-bubbling\" " +
         "href=\"http://live.fis-ski.com/jp-3051/results-pda.htm\" target=\"_blank\"><div class=\"split-row\"><div class=\"" +
         "split-row__item split-row__item_text_medium reset-padding\"><div class=\"link__text\" style=\"text-align:center\"" +
         ">3051</div></div><div class=\"split-row__item reset-padding\"><div class=\"live review\" title=\"review live results\"" +
         "><div class=\"live__content\">review</div></div></div></div></a></div><a class=\"g-lg g-md justify-left hidden-sm-down\"" +
         " href=\"https://www.fis-ski.com/DB/general/results.html?sectorcode=JP&raceid=5190\" target=\"_self\"><div class=\"split-row\"" +
         "><div class=\"split-row__item split-row__item_text_medium\"><div class=\"g-xs-24 justify-left\"><div class=\"clip\"" +
         ">HS90</div></div></div></div><a class=\"g-sm-8 g-xs-9 hidden-md-up\" href=\"https://www.fis-ski.com/DB/general/results.html?sectorcode=JP&raceid=5190\"" +
         " target=\"_self\"><div class=\"split-row split-row_bordered\"><div class=\"split-row__item\"><div class=\"g-xs-24 justify-left\"><div class=\"" +
         "clip\">HS90</div></div></div><div class=\"split-row__item\"><div class=\"g-row\"><div class=\"g-xs-12 justify-left\">QUA</div><div class=\"" +
         "g-xs-12 justify-right bold\"><div class=\"gender\"><div class=\"gender__inner\"><div class=\"gender__item gender__item_l\"" +
         ">L</div></div></div></div></div></div></div></a><a class=\"g-lg-2 g-xs-2 justify-center hidden-sm-down\" href=\"" +
         "https://www.fis-ski.com/DB/general/results.html?sectorcode=JP&raceid=5190\" target=\"_self\">QUA</a><a class=\"" +
         "g-lg-1 g-md-1 g-sm-1 g-xs-2 justify-center hidden-sm-down bold\" href=\"https://www.fis-ski.com/DB/general/results.html?sectorcode=JP&raceid=5190\"" +
         " target=\"_self\"><div class=\"gender\"><div class=\"gender__inner\"><div class=\"gender__item gender__item_l\">L</div></div></div></a><a class=\"" +
         "g-lg-8 g-md-6 g-sm-6 hidden-xs\" href=\"https://www.fis-ski.com/DB/general/results.html?sectorcode=JP&raceid=5190\" target=\"_self\"><div class=\"" +
         "split-row split-row_bordered\"><div class=\"split-row__item\"><div class=\"g-row\"><div class=\"g-lg-4 g-md-5 g-sm-8 g-xs-8 justify-left justify-sm-center\"" +
         ">1st</div><div class=\"g-lg-4 g-md-5 g-sm-8 g-xs-8 justify-left justify-sm-center\">14:00</div><div class=\"g-lg-4 g-md-5 g-sm-8 g-xs-8 justify-left justify-sm-center\"" +
         ">14:00</div><div class=\"g-lg-5 g-md-9 justify-left hidden-sm-down\">Official results </div><div class=\"g-lg-7 justify-left hidden-md-down\"" +
         "></div></div></div></div></a><a class=\"px-1 g-lg-3 g-md-3 g-sm-4 g-xs-4 justify-left\" href=\"https://www.fis-ski.com/DB/general/results.html?sectorcode=JP&raceid=5190\"" +
         " target=\"_self\"></a></div></div><div class=\"g-lg-2 g-md-2 g-sm-3 hidden-xs justify-md-center justify-sm-left\"><div class=\"g-row\"><div class=\"" +
         "g-xs-12 split-row stop-bubbling open-modal\" onclick=\"" +
         "fct_set_calendar_download('https://data.fis-ski.com/services/public/icalendar-feed-fis-events.html?seasonCode=2019&sectorcode=JP&raceid=5190','webcal://data.fis-ski.com/services/public/icalendar-feed-fis-events.html?seasonCode=2019&sectorcode=JP&raceid=5190')\"" +
         "><span class=\"icon icon_color_pale-gray icon_size_large icon_pointer icon_calendar\"><svg><use xlink:href=\"" +
         "https://www.fis-ski.com/DB/general/event-details.html?sectorcode=JP&eventid=43146#calendar\"></use></svg></span></div><div class=\"g-xs-12 split-row stop-bubbling\"><div data-module=\"" +
         "drop-btn\" class=\"drop-btn\"><div class=\"drop-btn__inner\"><button class=\"btn btn_icon-only drop-btn__main-btn\" role=\"button\" tabindex=\"1\" name=\"drop\"" +
         "><span class=\"icon btn__icon icon_calendar-download-icon\"><svg><use xlink:href=\"https://www.fis-ski.com/DB/general/event-details.html?sectorcode=JP&eventid=43146#calendar-download\"" +
         "></use></svg></span><span class=\"btn__label\"></span>";

    @Test
    public void generateRaceDataTest() {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        JumpCategoryService mockJumpCategoryService = Mockito.mock(JumpCategoryService.class);
        Mockito.when(mockJumpCategoryService.findByShortName(Mockito.anyString())).thenReturn(Optional.of(new JumpCategoryDTO().id(1)));

        FindRaceData findRaceData = new FindRaceData(diagnosticMonitorMock, "2019", "123", mockJumpCategoryService);
        DataRaceDTO dataRaceDTO = findRaceData.generateRaceData(text);

        DataRaceDTO expectedDataRaceDTO = new DataRaceDTO()
                .raceId(5190L)
                .gender("L")
                .isCancelled(false)
                .codex("3051")
                .eventId(123L)
                .competitionType("HS90")
                .jumpCategoryId(1)
                .seasonCode(2019)
                .date(LocalDate.of(2018, 12,14));

        Assertions.assertThat(dataRaceDTO).isEqualToComparingFieldByField(expectedDataRaceDTO);
    }
}
