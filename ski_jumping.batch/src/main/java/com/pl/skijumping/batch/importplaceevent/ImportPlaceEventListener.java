package com.pl.skijumping.batch.importplaceevent;

import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.DataRaceToPlaceDTO;
import com.pl.skijumping.dto.MessageDTO;
import com.pl.skijumping.dto.MessageProperties;
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
            exchange = @Exchange(value = "${skijumping.rabbitmq.exchange}", type = ExchangeTypes.TOPIC, durable = "true")
    ))
    public DataRaceToPlaceDTO importPlace(MessageDTO messageDTO) throws InternalServiceException {
        if(messageDTO == null) {
            return null;
        }

        Long dataRaceId = (Long) messageDTO.getProperties().get(MessageProperties.DARA_RACE_ID.getValue());
        ParsePlaceData parsePlaceData = new ParsePlaceData(diagnosticMonitor, countryService);
        PlaceDTO placeDTO = parsePlaceData.parse(messageDTO);
        Optional<PlaceDTO> foundPlace = placeService.findByCityAndHillType(placeDTO);

        if (!foundPlace.isPresent()) {
            return save(placeService.save(placeDTO), dataRaceId);
        } else {
            return save(foundPlace.get(), dataRaceId);
        }

        //todo delete file
    }

    private DataRaceToPlaceDTO save(PlaceDTO placeDTO, Long dataRaceId) {
        return dataRaceToPlaceService.save(new DataRaceToPlaceDTO().placeId(placeDTO.getId()).dataRaceId(dataRaceId));
    }
}
