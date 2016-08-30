package com.prodyna.pac;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class contains all constants which are used in the application.
 */
public final class Constants {

	/**
	 * The system environment variable which uses the application to determine the stage.
	 */
	public static final String SYSTEM_VARIABLE = "voting.stage";

	/**
	 * Application stage {@code development} for local development including JWT authorization.
	 */
	public static final String STAGE_DEVELOPMENT = "development";

	/**
	 * Application stage {@code development} for local development without JWT authorization.
	 */
	public static final String STAGE_DEVELOPMENT_NO_SECURITY = "development.no.security";

	/**
	 * Application stage {@code quality.assurance} for QA including JWT authorization and MongoDB cluster.
	 */
	public static final String STAGE_QUALITY_ASSURANCE = "quality.assurance";

	/**
	 * Application stage {@code production} for production including JWT authorization and MongoDB cluster.
	 */
	public static final String STAGE_PRODUCTION = "production";

	/**
	 * Read only list of all known stages the application can serve.
	 */
	public static final List<String> KNOWN_STAGES = Collections.unmodifiableList(Arrays.asList(STAGE_DEVELOPMENT, STAGE_DEVELOPMENT_NO_SECURITY, STAGE_QUALITY_ASSURANCE, STAGE_PRODUCTION));

	/**
	 * Name of the header field used for JWT authorization.
	 */
	public static final String JWT_AUTHENTICATION_HEADER = "X-AUTH-TOKEN";

	/**
	 * Time constant matching a ten days period of time.
	 */
	public static final long TEN_DAYS = 1000 * 60 * 60 * 24 * 10;
}
