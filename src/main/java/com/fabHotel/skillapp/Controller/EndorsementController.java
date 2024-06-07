package com.fabHotel.skillapp.Controller;
import com.fabHotel.skillapp.Entity.EndorseSkillRequest;
import com.fabHotel.skillapp.Service.EndorsementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EndorsementController {

    @Autowired
    private EndorsementService endorsementService;

    @PostMapping("/endorse")
    public Map<String, Object> endorseSkill(@RequestBody EndorseSkillRequest request) {
        return endorsementService.endorseSkill(
                request.getRevieweeUserId(),
                request.getReviewerUserId(),
                request.getSkillId(),
                request.getScore()
        );
    }

    @GetMapping("/endorsements/{userId}")
    public Map<String, List<String>> getEndorsements(@PathVariable Long userId) {
        return endorsementService.getEndorsements(userId);
    }
}


