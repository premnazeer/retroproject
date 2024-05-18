package com.retrospective.service;

import com.retrospective.controller.AddFeedbackRequest;
import com.retrospective.controller.CreateRetroRequest;
import com.retrospective.domain.RetroStore;
import com.retrospective.domain.Retrospective;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class RetrospectiveServiceTest {

    @InjectMocks
    private RetrospectiveService retrospectiveService;

    @Test
    void invalidRetroRequestWithoutName() {

        CreateRetroRequest crr = getSuccessfulCreateRetroRequest();
        crr.setName(null);

        assertThrows(RetroApplicationException.class, () -> {
            retrospectiveService.createRetrospective(crr);
        });
    }

    @Test
    void invalidRetroRequestWithoutSummary() {
        CreateRetroRequest crr = getSuccessfulCreateRetroRequest();
        crr.setSummary(null);

        assertThrows(RetroApplicationException.class, () -> {
            retrospectiveService.createRetrospective(crr);
        });
    }

    @Test
    void invalidRetroRequestWithoutDate() {
        CreateRetroRequest crr = getSuccessfulCreateRetroRequest();
        crr.setDate(null);

        assertThrows(RetroApplicationException.class, () -> {
            retrospectiveService.createRetrospective(crr);
        });
    }

    @Test
    void invalidRetroRequestWithoutParticipants() {
        CreateRetroRequest crr = getSuccessfulCreateRetroRequest();
        crr.setParticipants(null);

        assertThrows(RetroApplicationException.class, () -> {
            retrospectiveService.createRetrospective(crr);
        });
    }

    @Test
    void validRetroRequest() {
        CreateRetroRequest crr = getSuccessfulCreateRetroRequest();
        retrospectiveService.createRetrospective(crr);
        String retroId = crr.getName() + "-" + crr.getDate();
        Retrospective retro = RetroStore.getRetrospective(retroId);

        assertTrue(retro != null);

    }

    @Test
    void validFeedbackRequest() {
        CreateRetroRequest crr = getSuccessfulCreateRetroRequest();
        retrospectiveService.createRetrospective(crr);
        AddFeedbackRequest fr = getSuccessfulAddFeedbackRequest();
        retrospectiveService.addFeedback(fr);
        String retroId = crr.getName() + "-" + crr.getDate();
        Retrospective retro = RetroStore.getRetrospective(retroId);

        assertTrue(retro.getFeedback() != null && !retro.getFeedback().isEmpty());
    }

    private CreateRetroRequest getSuccessfulCreateRetroRequest() {

        CreateRetroRequest crr = new CreateRetroRequest();
        crr.setName("Retro1");
        crr.setDate(LocalDate.of(2024, 05, 20));
        crr.setSummary("Test Summary");
        List<String> participants = new ArrayList<String>();
        participants.add("Viktor");
        crr.setParticipants(participants);
        return crr;
    }

    private AddFeedbackRequest getSuccessfulAddFeedbackRequest() {

        AddFeedbackRequest fbr = new AddFeedbackRequest();
        fbr.setRetroName("Retro1");
        fbr.setRetroDate(LocalDate.of(2024, 05, 20));
        fbr.setName("Viktor");
        fbr.setFeedbackBody("Excellent Sprint");
        fbr.setFeedbackType(AddFeedbackRequest.FeedbackType.valueOf("POSITIVE"));

        return fbr;
    }

}
