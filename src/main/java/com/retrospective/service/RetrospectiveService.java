package com.retrospective.service;

import com.retrospective.controller.AddFeedbackRequest;
import com.retrospective.controller.CreateRetroRequest;
import com.retrospective.domain.RetroFeedback;
import com.retrospective.domain.RetroStore;
import com.retrospective.domain.Retrospective;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RetrospectiveService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetrospectiveService.class);

    public void addRetrospective(Retrospective retro) {

        String retroId = retro.getName() + "-" + retro.getDate();
        Map<String, Retrospective> retros =  RetroStore.getRetrospectives();
        Retrospective existingRetro = retros.get(retroId);
        if (existingRetro != null) {
            LOGGER.info("The retro exist with retro date:" + retroId);
        }
        retros.put(retroId, retro);
    }

    public void createRetrospective(CreateRetroRequest createRetro) {

        validateCreateRetroRequest(createRetro);
        String retroId = createRetro.getName() + "-" + createRetro.getDate();
        LocalDate localDate = createRetro.getDate();
        Map<String, Retrospective> retros =  RetroStore.getRetrospectives();
        Retrospective existingRetro = retros.get(retroId);
        if (existingRetro != null) {
            LOGGER.info("The retro exist with retro Id:" + retroId);
            existingRetro.setSummary(createRetro.getSummary());
            existingRetro.setName(createRetro.getName());
            existingRetro.setDate(createRetro.getDate());
            existingRetro.setParticipants(createRetro.getParticipants());

        } else {
            Retrospective retro = new Retrospective();
            retro.setDate(createRetro.getDate());
            retro.setName(createRetro.getName());
            retro.setSummary(createRetro.getSummary());
            retro.setParticipants(createRetro.getParticipants());
            retros.put(retroId, retro);
        }

    }

    public void addFeedback(AddFeedbackRequest feedback) {

        LocalDate localDate = feedback.getRetroDate();
        String retroId = feedback.getRetroName() + "-" + feedback.getRetroDate();
        Map<String, Retrospective> retros =  RetroStore.getRetrospectives();

        Retrospective existingRetro = retros.get(retroId);

        if (existingRetro != null) {
            LOGGER.info("The retro exist with retro date:" + localDate);
            RetroFeedback rfb = new RetroFeedback();
            rfb.setName(feedback.getName());
            rfb.setFeedbackBody(feedback.getFeedbackBody());
            rfb.setFeedbackType(feedback.getFeedbackType().toString());

            if(existingRetro.getFeedback() == null) {
                existingRetro.setFeedback(new ArrayList<RetroFeedback>());
            }
            existingRetro.getFeedback().add(rfb);
        } else {
            String errorMessage = String.format("Retro with name: %s and retro date: %s is not exists",
                    feedback.getRetroName(), feedback.getRetroDate());
             throw new RetroApplicationException("RETRO_NOT_FOUND", errorMessage);
        }

    }

    private void validateCreateRetroRequest(CreateRetroRequest createRetro) {

        boolean errorExist = false;
        StringBuilder errorMsg = new StringBuilder();
        if (createRetro.getDate() == null) {
            errorMsg.append("Retro Date can not be empty.");
            errorExist = true;
        }

        if (createRetro.getName() == null) {
            errorMsg.append("Retro Name can not be empty.");
            errorExist = true;
        }

        if (createRetro.getSummary() == null) {
            errorMsg.append("Retro Summary can not be empty.");
            errorExist = true;
        }
        if (CollectionUtils.isEmpty(createRetro.getParticipants())) {
            errorMsg.append("Retro Participant list can not be empty.");
            errorExist = true;
        }

        if (errorExist) {
            throw new RetroApplicationException("INVALID_CREATE_RETO_REQUEST",errorMsg.toString());
        }
    }
}
