package md.maib.retail.infrastructure.config;

import md.maib.retail.application.campaigns_list_by_date.CampaignsListByDateUseCase;
import md.maib.retail.application.delete_campaign.DeleteCampaignUseCase;
import md.maib.retail.application.find_campaign_by_id.FindCampaignByIdUseCase;
import md.maib.retail.application.find_campaign_by_metainfo.FindCampaignByMetaInfoUseCase;
import md.maib.retail.application.find_effect_type_by_id.FindByIdLoyaltyEffectTypeUseCase;
import md.maib.retail.application.find_event_type_by_id.FindByIdLoyaltyEventTypeUseCase;
import md.maib.retail.application.register_newcampaign.RegistrationCampaignUseCase;
import md.maib.retail.model.ports.Campaigns;
import md.maib.retail.model.ports.LoyaltyEffectTypes;
import md.maib.retail.model.ports.LoyaltyEventTypes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UseCasesConfiguration {


    @Bean
    FindCampaignByIdUseCase findCampaignByIdUseCase(Campaigns campaigns) {
        return FindCampaignByIdUseCase.defaultService(campaigns);
    }

    @Bean
    FindCampaignByMetaInfoUseCase findCampaignByMetaInfoUseCase(Campaigns campaigns) {
        return FindCampaignByMetaInfoUseCase.defaultService(campaigns);
    }

    @Bean
    CampaignsListByDateUseCase campaignsListByDateUseCase(Campaigns campaigns) {
        return CampaignsListByDateUseCase.defaultService(campaigns);
    }

    @Bean
    DeleteCampaignUseCase deleteCampaignUseCase(Campaigns campaigns) {
        return DeleteCampaignUseCase.defaultService(campaigns);
    }

    @Bean
    RegistrationCampaignUseCase registrationCampaignUseCase(Campaigns campaigns, LoyaltyEventTypes loyaltyEventTypes, LoyaltyEffectTypes loyaltyEffectTypes) {
        return RegistrationCampaignUseCase.defaultService(campaigns, loyaltyEventTypes, loyaltyEffectTypes);
    }

    @Bean
    FindByIdLoyaltyEventTypeUseCase findByIdLoyaltyEventTypeUseCase(LoyaltyEventTypes loyaltyEventTypes) {
        return FindByIdLoyaltyEventTypeUseCase.defaultService(loyaltyEventTypes);
    }

    @Bean
    FindByIdLoyaltyEffectTypeUseCase findByIdLoyaltyEffectTypeUseCase(LoyaltyEffectTypes loyaltyEffectTypes) {
        return FindByIdLoyaltyEffectTypeUseCase.defaultService(loyaltyEffectTypes);
    }

}
