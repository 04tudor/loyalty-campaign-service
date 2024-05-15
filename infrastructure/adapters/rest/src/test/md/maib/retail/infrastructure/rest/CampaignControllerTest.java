package md.maib.retail.infrastructure.rest;

import md.maib.retail.application.CampaignAllInfo;
import md.maib.retail.application.CampaignSomeInfo;
import md.maib.retail.application.campaigns_list_by_date.CampaignsListByDateUseCase;
import md.maib.retail.application.delete_campaign.DeleteCampaignUseCase;
import md.maib.retail.application.find_campaign_by_id.FindCampaignByIdService;
import md.maib.retail.application.find_campaign_by_id.FindCampaignByIdUseCase;
import md.maib.retail.application.find_campaign_by_metainfo.FindCampaignByMetaInfoUseCase;
import md.maib.retail.application.register_newcampaign.RegisterCampaign;
import md.maib.retail.application.register_newcampaign.RegistrationCampaignUseCase;
import md.maib.retail.application.register_newcampaign.UseCaseProblemConflict;

import md.maib.retail.infrastructure.rest.CampaignController;
import md.maib.retail.infrastructure.rest.RegisterCampaignRequest;
import md.maib.retail.model.campaign.*;
import md.maib.retail.model.ports.Campaigns;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

 class CampaignControllerTest {

     @Test
     void testRegisterCampaign_Success() {
         RegistrationCampaignUseCase registrationCampaignUseCase = mock(RegistrationCampaignUseCase.class);
         CampaignController campaignController = new CampaignController(
                 null, null, registrationCampaignUseCase, null, null);
         List<LoyaltyEventField> loyaltyEventField = List.of(new LoyaltyEventField(UUID.randomUUID(), "Field", FieldType.STRING));
         LoyaltyEventType loyaltyEventType = new LoyaltyEventType(UUID.randomUUID(), "Event", loyaltyEventField);

         RegisterCampaignRequest request = RegisterCampaignRequest.valueOf(new RegisterCampaign(
                 new CampaignMetaInfo(Collections.emptyMap()),
                 LocalDate.now(),
                 LocalDate.now().plusDays(7),
                 CampaignState.ACTIVE,
                 loyaltyEventType,
                 Collections.emptyList()));
         CampaignId campaignId = new CampaignId(CampaignId.newIdentity().campaignId());
         when(registrationCampaignUseCase.registerCampaign(any(RegisterCampaign.class))).thenReturn(right(campaignId));

         ResponseEntity<Object> response = campaignController.registerCampaign(request);

         assertEquals(HttpStatus.CREATED, response.getStatusCode());
     }


    @Test
    void testRegisterCampaign_Failure() {
        RegistrationCampaignUseCase registrationCampaignUseCase = mock(RegistrationCampaignUseCase.class);
        CampaignController campaignController = new CampaignController(
                null, null, registrationCampaignUseCase, null, null);

        RegisterCampaignRequest request = new RegisterCampaignRequest(null,null,null,CampaignState.ACTIVE,null,null);
        when(registrationCampaignUseCase.registerCampaign(any())).thenReturn(left(new UseCaseProblemConflict("Conflict")));

        assertThrows(ResponseStatusException.class, () -> campaignController.registerCampaign(request));
    }

    @Test
    void testFindCampaignByMetaInfo_Success() {
        FindCampaignByMetaInfoUseCase findCampaignByMetaInfoUseCase = mock(FindCampaignByMetaInfoUseCase.class);
        CampaignController campaignController = new CampaignController(
                null, findCampaignByMetaInfoUseCase, null, null, null);
        Map<String, Object> properties = new HashMap<>();
        properties.put("key", "value");
        CampaignMetaInfo campaignMetaInfo = new CampaignMetaInfo(properties);

        List<CampaignAllInfo> expected = List.of(
                new CampaignAllInfo(
                        new CampaignId(CampaignId.newIdentity().campaignId()),
                        campaignMetaInfo,
                        null,
                        CampaignState.ACTIVE,
                        null,
                        null));
        when(findCampaignByMetaInfoUseCase.findByMetaInfo(anyString(), anyString())).thenReturn(expected);

        List<CampaignAllInfo> result = campaignController.findCampaignByMetaInfo("key", "value");

        assertEquals(expected, result);
    }

    @Test
    void testFindCampaignByMetaInfo_Failure() {
        FindCampaignByMetaInfoUseCase findCampaignByMetaInfoUseCase = mock(FindCampaignByMetaInfoUseCase.class);
        CampaignController campaignController = new CampaignController(
                null, findCampaignByMetaInfoUseCase, null, null, null);

        List<CampaignAllInfo> expected = Collections.emptyList();
        when(findCampaignByMetaInfoUseCase.findByMetaInfo(anyString(), anyString())).thenReturn(expected);

        List<CampaignAllInfo> result = campaignController.findCampaignByMetaInfo("key", "value");

        assertEquals(expected, result);
    }

    @Test
    void testFindById_Success() {
        Campaigns campaigns = mock(Campaigns.class);
        CampaignId campaignId = new CampaignId(CampaignId.newIdentity().campaignId());
        Campaign campaign = new Campaign(campaignId, null, null, null, null, null);

        when(campaigns.findById(campaignId)).thenReturn(Optional.of(campaign));

        FindCampaignByIdUseCase findCampaignByIdUseCase = new FindCampaignByIdService(campaigns);

        Optional<CampaignAllInfo> result = findCampaignByIdUseCase.findById(campaignId);

        assertEquals(campaignId, result.get().id());
    }

    @Test
    void testFindById_NotFound() {
        Campaigns campaigns = mock(Campaigns.class);

        when(campaigns.findById(any())).thenReturn(Optional.empty());

        FindCampaignByIdUseCase findCampaignByIdUseCase = new FindCampaignByIdService(campaigns);

        Optional<CampaignAllInfo> result = findCampaignByIdUseCase.findById(new CampaignId(CampaignId.newIdentity().campaignId()));

        assertEquals(Optional.empty(), result);
    }

    @Test
    void testDelete_Success() {
        DeleteCampaignUseCase deleteCampaignUseCase = mock(DeleteCampaignUseCase.class);
        CampaignController campaignController = new CampaignController(
                null, null, null, deleteCampaignUseCase, null);

        CampaignId campaignId=CampaignId.newIdentity();

        ResponseEntity<Object> response = campaignController.delete(campaignId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    @Test
    void testDelete_Failure() {
        DeleteCampaignUseCase deleteCampaignUseCase = mock(DeleteCampaignUseCase.class);
        CampaignController campaignController = new CampaignController(
                null, null, null, deleteCampaignUseCase, null);

        CampaignId campaignId = CampaignId.newIdentity();

      when(deleteCampaignUseCase.deleteCampaign(any())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND,"No campaign found"));

        assertThrows(ResponseStatusException.class, () -> campaignController.delete(campaignId));
    }

    @Test
    void testListByDate_Success() {
        CampaignsListByDateUseCase campaignsListByDateUseCase = mock(CampaignsListByDateUseCase.class);
        CampaignController campaignController = new CampaignController(
                null, null, null, null, campaignsListByDateUseCase);

        LocalDate date = LocalDate.now();
        List<CampaignSomeInfo> expected = List.of(
                new CampaignSomeInfo(
                        new CampaignId(CampaignId.newIdentity().campaignId()),
                        null,
                        null,
                        CampaignState.ACTIVE,
                        null
                        ));
        when(campaignsListByDateUseCase.activeCampaignsByDate(date)).thenReturn(expected);

        List<CampaignSomeInfo> result = campaignController.listByDate(date);

        assertEquals(expected, result);
    }

    @Test
    void testListByDate_Failure() {
        CampaignsListByDateUseCase campaignsListByDateUseCase = mock(CampaignsListByDateUseCase.class);
        CampaignController campaignController = new CampaignController(
                null, null, null, null, campaignsListByDateUseCase);

        LocalDate date = LocalDate.now();
        when(campaignsListByDateUseCase.activeCampaignsByDate(date))
                .thenThrow(new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,"Failed to fetch campaigns by date"));

        assertThrows(ResponseStatusException.class, () -> campaignController.listByDate(date));
    }
}
