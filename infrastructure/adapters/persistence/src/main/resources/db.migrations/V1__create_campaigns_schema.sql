CREATE TABLE campaigns.campaign (
    campaign_id UUID NOT NULL,
    metainfo JSONB NOT NULL,
    interval_start DATE NOT NULL,
    interval_end DATE NOT NULL,
    is_active BOOLEAN NOT NULL,
    loyalty_event_type_id UUID NOT NULL,
    CONSTRAINT pk_campaign_id PRIMARY KEY (campaign_id),
    CONSTRAINT fk_loyalty_event_type FOREIGN KEY (loyalty_event_type_id) REFERENCES loyalty_event_type(id)
);

CREATE TABLE campaigns.rule (
    id           UUID,
    campaign_id  UUID NOT NULL,
    conditions   JSONB NOT NULL,
    effects      JSONB NOT NULL,
    CONSTRAINT pk_rule_id PRIMARY KEY (id),
    CONSTRAINT fk_rule_campaign_id FOREIGN KEY (campaign_id) REFERENCES campaigns.campaign(campaign_id)
);
ALTER TABLE campaigns.rule ADD CONSTRAINT FK_RULE_ON_CAMPAIGN FOREIGN KEY (campaign_id) REFERENCES campaigns.campaign (campaign_id);