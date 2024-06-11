package md.maib.retail.model;

import md.maib.retail.junit.UnitTest;
import md.maib.retail.model.campaign.FieldType;
import md.maib.retail.model.campaign.LoyaltyEventField;
import md.maib.retail.model.conditions.Condition;
import md.maib.retail.model.conditions.Operator;
import md.maib.retail.model.conditions.Rule;
import md.maib.retail.model.conditions.RuleId;
import md.maib.retail.model.effects.Effect;
import md.maib.retail.model.effects.LoyaltyEffectType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@UnitTest
 class RuleTest {


    @Test
    void testRule() {
        LoyaltyEventField loyaltyEventField = new LoyaltyEventField(
                UUID.randomUUID(),
                "fieldName",
                FieldType.STRING
        );

        Condition condition = new Condition(loyaltyEventField.getFieldType(), Operator.EQUALS, "value");
        LoyaltyEffectType loyaltyEffectType = new LoyaltyEffectType(
                UUID.randomUUID(),
                "effectName",
                UUID.fromString("cd9c30db-88d8-4fa0-9299-7b9bf63d1b15")
        );

        Effect effect = new Effect(loyaltyEffectType, "effectValue");

        Rule rule = new Rule(
                RuleId.newIdentity(),
                Collections.singletonList(condition),
                Collections.singletonList(effect)
        );

        // Rule Assertions
        assertThat(rule.getId()).isNotNull();
        assertThat(rule.getConditions()).containsExactly(condition);
        Assertions.assertThat(rule.getEffects()).containsExactly(effect);

        Rule sameRule = new Rule(rule.getId(), rule.getConditions(), rule.getEffects());
        assertThat(rule).isEqualTo(sameRule);

        //different id Rule
        Rule differentIdRule = new Rule(RuleId.newIdentity(), rule.getConditions(), rule.getEffects());
        assertThat(rule).isNotEqualTo(differentIdRule);

        //different Condition
        Condition differentCondition = new Condition(loyaltyEventField.getFieldType(), Operator.GREATER, "value");
        Rule differentConditionsRule = new Rule(rule.getId(), Collections.singletonList(differentCondition), rule.getEffects());
        assertThat(rule).isNotEqualTo(differentConditionsRule);

        //diferent Effect
        Effect differentEffect = new Effect(loyaltyEffectType, "differentValue");
        Rule differentEffectsRule = new Rule(rule.getId(), rule.getConditions(), Collections.singletonList(differentEffect));
        assertThat(rule).isNotEqualTo(differentEffectsRule);
    }
}
