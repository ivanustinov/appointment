package ru.ustinov.appointment.web.soap;

import appointment.schedule_web_service.CreateScheduleRequest;
import appointment.schedule_web_service.CreateScheduleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.ustinov.appointment.service.AppointmentService;


/**
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 29.01.2024
 */
@Endpoint
public class ScheduleService {

    @Autowired
    private AppointmentService appointmentService;

    @PayloadRoot(namespace = "http://appointment/schedule-web-service", localPart = "createScheduleRequest")
    @ResponsePayload
    public CreateScheduleResponse createSchedule(@RequestPayload CreateScheduleRequest request) {
        return appointmentService.createScheduleResponse(request);
    }

}

