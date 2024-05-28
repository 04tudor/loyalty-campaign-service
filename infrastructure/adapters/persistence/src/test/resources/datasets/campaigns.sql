
INSERT INTO campaigns.campaign (campaign_id, metainfo, interval_start, interval_end, is_active, loyalty_event_type_id)
VALUES ('1e7e7d50-9f9f-4b7c-bd9b-5f5f3d0f7f7f', '{"key": "value"}', '2024-06-01T00:00:00Z'::TIMESTAMPTZ, '2024-06-30T23:59:59Z'::TIMESTAMPTZ, true, '123e4567-e89b-12d3-a456-426614174000');

INSERT INTO campaigns.rule (id, campaign_id, conditions, effects)
VALUES ('44947ade-923d-4ca6-9006-30442779df3f', '1e7e7d50-9f9f-4b7c-bd9b-5f5f3d0f7f7f',
  '{"conditions":[{"field":"DECIMAL","operator":"GREATER","value":10}]}',
  '{"effects":[{"loyaltyEventTypeId":"4e1a8086-90de-4796-95e8-121f24412656","value":5}]}');
