package com.pl.skijumping.batch.importplaceevent;

import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.DataRaceToPlaceDTO;
import com.pl.skijumping.dto.MessageDTO;
import com.pl.skijumping.dto.MessagePropertiesConst;
import com.pl.skijumping.dto.PlaceDTO;
import com.pl.skijumping.service.CountryService;
import com.pl.skijumping.service.DataRaceToPlaceService;
import com.pl.skijumping.service.PlaceService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.Optional;

@Component
public class ImportPlaceEventListener {

    private final DiagnosticMonitor diagnosticMonitor;
    private final CountryService countryService;
    private final PlaceService placeService;
    private final DataRaceToPlaceService dataRaceToPlaceService;

    public ImportPlaceEventListener(DiagnosticMonitor diagnosticMonitor,
                                    CountryService countryService,
                                    PlaceService placeService,
                                    DataRaceToPlaceService dataRaceToPlaceService) {
        this.diagnosticMonitor = diagnosticMonitor;
        this.countryService = countryService;
        this.placeService = placeService;
        this.dataRaceToPlaceService = dataRaceToPlaceService;
    }

    @RabbitListener(bindings =
    @QueueBinding(
            value = @Queue(value = "${skijumping.rabbitmq.queues.importPlaceEventListener}", durable = "true"),
            exchange = @Exchange(value = "${skijumping.rabbitmq.exchange}", type = ExchangeTypes.TOPIC, durable = "true"),
            key = "importPlaceEventListener"
    ))
    public void importPlace(MessageDTO messageDTO) throws InternalServiceException {
        if (messageDTO == null) {
            return;
        }

        Long dataRaceId = messageDTO.getProperties().getLongValue(MessagePropertiesConst.DARA_RACE_ID.getValue());
        ParsePlaceData parsePlaceData = new ParsePlaceData(diagnosticMonitor, countryService);
        PlaceDTO placeDTO = parsePlaceData.parse(messageDTO);
        Optional<PlaceDTO> foundPlace = placeService.findByCityAndHillType(placeDTO);
        Paths.get(messageDTO.getFilePath()).toFile().deleteOnExit();
        if (foundPlace.isPresent()) {
            save(foundPlace.get(), dataRaceId);
            return;
        }
        save(placeService.save(placeDTO), dataRaceId);
    }

    private DataRaceToPlaceDTO save(PlaceDTO placeDTO, Long dataRaceId) {
        DataRaceToPlaceDTO dataRaceToPlaceDTO = new DataRaceToPlaceDTO().placeId(placeDTO.getId()).dataRaceId(dataRaceId);
        Optional<DataRaceToPlaceDTO> foundDataRaceToPlace = dataRaceToPlaceService.findByDataRaceToPlace(dataRaceToPlaceDTO);
        if(foundDataRaceToPlace.isPresent()) {
            return foundDataRaceToPlace.get();
        }
        return dataRaceToPlaceService.save(dataRaceToPlaceDTO);
    }
}
