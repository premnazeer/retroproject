package com.retrospective.controller;

import com.retrospective.domain.RetroStore;
import com.retrospective.domain.Retrospective;
import com.retrospective.service.RetrospectiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/retro")
public class RetrospectiveHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetrospectiveHandler.class);

    private RetrospectiveService retroService;

    public RetrospectiveHandler(RetrospectiveService retroService) {
        this.retroService = retroService;
    }

    @GetMapping("/view/{localDate}")
    private List<Retrospective> getRetrospectiveByDate(@PathVariable LocalDate localDate) {
        return RetroStore.getRetrospectives().values().stream()
                .filter(retro -> retro.getDate().equals(localDate))
                .toList();
    }

    @GetMapping("/view/all")
    private Map<String,Retrospective> getAllRetrospectives() {
        return RetroStore.getRetrospectives();
    }

    @GetMapping("/view")
    private Page<Retrospective> getRetrospectiveByDate(@RequestParam Optional<Integer> pageNumber, @RequestParam Optional<Integer> pageSize) {

        LOGGER.info("Params are:" + pageNumber + ":" + pageSize);
        Map<String,Retrospective> allRestros = RetroStore.getRetrospectives();
        int actualSize = allRestros.size();

        Integer pageValue = pageNumber.orElse(0);
        pageValue = (pageValue > 0 ) ? pageValue - 1 : 0;
        int sizeValue = pageSize.orElse((actualSize > 0 ? actualSize: 1));

        List<Retrospective> list = new ArrayList<>(allRestros.values());
        var pageable = PageRequest.of(pageValue, sizeValue);
        int start = (int) pageable.getOffset();
        int end = (int) ((start + pageable.getPageSize()) > list.size() ? list.size()
                : (start + pageable.getPageSize()));
        return new PageImpl<>(list.subList(start, end), pageable, actualSize);

    }

    @PostMapping("/add")
    private String postRetrospective(@RequestBody Retrospective retro) {
        String successMsg = "Retrospective Successfully Added";
        LOGGER.info(String.valueOf(retro));
        retroService.addRetrospective(retro);
        LOGGER.info(successMsg);
        return successMsg;
    }

    @PostMapping("/create")
    private String createRetrospective(@RequestBody CreateRetroRequest createRetro) {
        String successMsg = "Retrospective Successfully Created";
        LOGGER.info(String.valueOf(createRetro));
        retroService.createRetrospective(createRetro);
        LOGGER.info(successMsg);
        return successMsg;
    }

    @PostMapping("/addfeedback")
    private String createRetrospective(@RequestBody AddFeedbackRequest addFeedback) {
        String successMsg = "Feedback has been added successfully";
        LOGGER.info(String.valueOf(addFeedback));
        retroService.addFeedback(addFeedback);
        LOGGER.info(successMsg);
        return successMsg;
    }
}
