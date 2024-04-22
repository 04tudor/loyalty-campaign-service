package md.maib.retail.Model;

import org.threeten.extra.Interval;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class Campaign {
    private UUID id;

    private Map<String ,String>metaInfo;
    private Interval activityInterval;
    private Collection<Rule> rules;

}
