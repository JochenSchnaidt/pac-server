package com.prodyna.pac;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Constants {

    public static final String SYSTEM_VARIABLE = "voting.stage";

    public static final String STAGE_DEVELOPMENT = "development";
    public static final String STAGE_DEVELOPMENT_NO_SECURITY = "development.no.security";
    public static final String STAGE_QUALITY_ASSURANCE = "qual";
    public static final String STAGE_PRODUCTION = "prod";

    public static final List<String> KNOWN_STAGES = Collections.unmodifiableList(Arrays.asList(STAGE_DEVELOPMENT, STAGE_DEVELOPMENT_NO_SECURITY, STAGE_QUALITY_ASSURANCE, STAGE_PRODUCTION));

}
