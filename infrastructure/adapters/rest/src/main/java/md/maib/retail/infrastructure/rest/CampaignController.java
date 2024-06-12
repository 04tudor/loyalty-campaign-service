package md.maib.retail.infrastructure.rest;

import am.ik.yavi.core.ConstraintViolation;
import am.ik.yavi.core.ConstraintViolations;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import md.maib.retail.application.CampaignAllInfo;
import md.maib.retail.application.CampaignSomeInfo;
import md.maib.retail.application.activate_campaign.ActivateCampaignUseCase;
import md.maib.retail.application.campaigns_list_by_date.CampaignsListByDateUseCase;
import md.maib.retail.application.delete_campaign.DeleteCampaign;
import md.maib.retail.application.delete_campaign.DeleteCampaignUseCase;
import md.maib.retail.application.find_campaign_by_id.FindCampaignByIdUseCase;
import md.maib.retail.application.find_campaign_by_metainfo.FindCampaignByMetaInfoUseCase;
import md.maib.retail.application.find_effect_type_by_id.FindByIdLoyaltyEffectTypeUseCase;
import md.maib.retail.application.find_event_type_by_id.FindByIdLoyaltyEventTypeUseCase;
import md.maib.retail.application.list_all_campaigns.ListAllCampaignsUseCase;
import md.maib.retail.application.register_newcampaign.RegisterCampaign;
import md.maib.retail.application.register_newcampaign.RegistrationCampaignUseCase;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.campaign.LoyaltyEventType;
import md.maib.retail.model.effects.LoyaltyEffectType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.UUID.fromString;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/campaigns")
@RequiredArgsConstructor
public final class CampaignController {

    private final FindCampaignByIdUseCase findCampaignByIdUseCase;
    private final FindCampaignByMetaInfoUseCase findCampaignByMetaInfoUseCase;
    private final RegistrationCampaignUseCase registrationCampaignUseCase;
    private final DeleteCampaignUseCase deleteCampaignUseCase;
    private final FindByIdLoyaltyEventTypeUseCase findByIdLoyaltyEventTypeUseCase;
    private final FindByIdLoyaltyEffectTypeUseCase findByIdLoyaltyEffectTypeUseCase;
    private final CampaignsListByDateUseCase campaignsListByDateUseCase;
    private final ActivateCampaignUseCase activateCampaignUseCase;
    private final ListAllCampaignsUseCase listAllCampaignsUseCase;

    private ResponseStatusException responseExceptionFromViolations(ConstraintViolations violations) {
        String errorMessage = violations
                .violations()
                .stream()
                .map(ConstraintViolation::message)
                .collect(Collectors.joining(", "));
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Validation failed: " + errorMessage);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerCampaign(@RequestBody RegisterCampaignRequest request) {

        Either<ConstraintViolations, RegisterCampaign> validationResult = RegisterCampaign.create(
                request.metaInfo(),
                request.startInclusive(),
                request.endExclusive(),
                request.state(),
                request.eventTypeRecord(),
                request.rules(),
                request.effectTypeRecord()
        );

        if (validationResult.isLeft()) {
            List<String> errorMessages = validationResult.getLeft().violations()
                    .stream()
                    .map(ConstraintViolation::message)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        } else {
            RegisterCampaign command = validationResult.get();
            Optional<LoyaltyEventType> loyaltyEventType = findByIdLoyaltyEventTypeUseCase.findById(request.eventTypeRecord().id());
            Optional<LoyaltyEffectType> loyaltyEffectType = findByIdLoyaltyEffectTypeUseCase.findById(request.effectTypeRecord().id());

            if (loyaltyEventType.isEmpty() || loyaltyEffectType.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var result = registrationCampaignUseCase.registerCampaign(command);

            if (result.isRight()) {
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Map.of("campaignId", result.get().campaignId()));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getLeft().getMessage());
            }
        }

    }

    @GetMapping(path = "/{key}/{value}", produces = APPLICATION_JSON_VALUE)
    public List<CampaignAllInfo> findCampaignByMetaInfo(@PathVariable String key, @PathVariable String value) {
        return findCampaignByMetaInfoUseCase.findByMetaInfo(key, value);
    }

    @GetMapping(path = "/listAll", produces = APPLICATION_JSON_VALUE)
    public List<CampaignAllInfo> listAll() {
        return listAllCampaignsUseCase.listAll();
    }



    @GetMapping(path = "/date/{date}", produces = APPLICATION_JSON_VALUE)
    public List<CampaignSomeInfo> listByDate(@PathVariable Instant date) {
        return campaignsListByDateUseCase.activeCampaignsByDate(date);
    }


    @GetMapping(path = "/{campaignId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CampaignAllInfo> findById(@PathVariable("campaignId") String campaignId) {
        CampaignId campaignId1 = CampaignId.valueOf(fromString(campaignId));
        return findCampaignByIdUseCase.findById(campaignId1)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{campaignId}")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Object> delete(@PathVariable("campaignId") String campaignId) {
        var validate = DeleteCampaign.create(CampaignId.valueOf(UUID.fromString(campaignId)));
        return validate.fold(
                violations -> {
                    throw responseExceptionFromViolations(violations);
                },
                validCommand -> {
                    deleteCampaignUseCase.deleteCampaign(validCommand);
                    return ResponseEntity.noContent().build();
                }
        );
    }

    @PostMapping(path = "/activate/{campaignId}")
    public ResponseEntity<?> activateCampaign(@PathVariable("campaignId") String campaignId) {
        CampaignId id = CampaignId.valueOf(fromString(campaignId));
        boolean result = activateCampaignUseCase.activate(id);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Activation failed.");
        }
    }
}
