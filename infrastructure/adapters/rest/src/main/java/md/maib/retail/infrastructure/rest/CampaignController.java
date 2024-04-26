package md.maib.retail.infrastructure.rest;

import am.ik.yavi.core.ConstraintViolation;
import am.ik.yavi.core.ConstraintViolations;
import lombok.RequiredArgsConstructor;
import md.maib.retail.application.CampaignAllInfo;
import md.maib.retail.application.CampaignSomeInfo;
import md.maib.retail.application.campaigns_list_by_date.CampaignsListByDateUseCase;
import md.maib.retail.application.delete_campaign.DeleteCampaign;
import md.maib.retail.application.delete_campaign.DeleteCampaignUseCase;
import md.maib.retail.application.find_campaign_by_id.FindCampaignByIdUseCase;
import md.maib.retail.application.find_campaign_by_metainfo.FindCampaignByMetaInfoUseCase;
import md.maib.retail.application.register_newcampaign.RegisterCampaign;
import md.maib.retail.application.register_newcampaign.RegistrationCampaignUseCase;
import md.maib.retail.model.campaign.CampaignId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/campaigns")
@RequiredArgsConstructor
final class CampaignController {

    private final FindCampaignByIdUseCase findCampaignByIdUseCase;
    private final FindCampaignByMetaInfoUseCase findCampaignByMetaInfoUseCase;
    private final RegistrationCampaignUseCase registrationCampaignUseCase;
    private final DeleteCampaignUseCase deleteCampaignUseCase;
    private final CampaignsListByDateUseCase campaignsListByDateUseCase;

    private ResponseStatusException responseExceptionFromViolations(ConstraintViolations violations) {
        String errorMessage = violations
                .violations()
                .stream()
                .map(ConstraintViolation::message)
                .collect(Collectors.joining(", "));
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Validation failed: " + errorMessage);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Object> registerCampaign(@RequestBody RegisterCampaignRequest request) {
        var validated = RegisterCampaign.create(
                request.getMetaInfo(),
                request.getStartInclusive(),
                request.getEndExclusive(),
                request.getState(),
                request.getLoyaltyEventType(),
                request.getRules()
        );

        return validated.fold(
                violations -> {
                    throw responseExceptionFromViolations(violations);
                },
                command -> {
                    var result = registrationCampaignUseCase.registerCampaign(command);
                    return result.map(campaignId -> {
                        var location = ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(campaignId.campaignId().toString()).toUri();
                        return ResponseEntity.created(location).build();
                    }).getOrElse(() -> ResponseEntity.notFound().build());
                }
        );
    }

    @GetMapping(path = "/metainfo", produces = APPLICATION_JSON_VALUE)
    List<CampaignAllInfo> findCampaignByMetaInfo(@RequestParam String key, @RequestParam String value) {
        return findCampaignByMetaInfoUseCase.findByMetaInfo(key, value);
    }

    @GetMapping(path = "/date", produces = APPLICATION_JSON_VALUE)
    List<CampaignSomeInfo> listByDate(@RequestParam LocalDate date) {
        return campaignsListByDateUseCase.activeCampaignsByDate(date);
    }

    @GetMapping(path = "/{campaignId}", produces = APPLICATION_JSON_VALUE)
    Optional<CampaignAllInfo> findById(@PathVariable("campaignId") CampaignId campaignId) {
        return findCampaignByIdUseCase.findById(campaignId);
    }

    @DeleteMapping(path = "/{campaignId}")
    @ResponseStatus(NO_CONTENT)
    void delete(@PathVariable("campaignId") DeleteCampaign campaignId) {
        deleteCampaignUseCase.deleteCampaign(campaignId);
    }
}
