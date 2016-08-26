package com.prodyna.pac;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Constants {

    public static final String SYSTEM_VARIABLE = "voting.stage";

    public static final String STAGE_DEVELOPMENT = "development";
    public static final String STAGE_DEVELOPMENT_NO_SECURITY = "development.no.security";
    public static final String STAGE_QUALITY_ASSURANCE = "quality.assurance";
    public static final String STAGE_PRODUCTION = "production";

    public static final List<String> KNOWN_STAGES = Collections.unmodifiableList(Arrays.asList(STAGE_DEVELOPMENT, STAGE_DEVELOPMENT_NO_SECURITY, STAGE_QUALITY_ASSURANCE, STAGE_PRODUCTION));
    
    public static final String JWT_AUTHENTICATION_HEADER = "X-AUTH-TOKEN";    

    public static final long TEN_DAYS = 1000 * 60 * 60 * 24 * 10;
}
