package fr.bergit;

import org.jetbrains.annotations.NotNull;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Duration;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.cognito.*;
import java.util.Arrays;
import java.util.List;

public class InfraCognitoStack extends Stack {

    public static final String LCDD_POOL_NAME = "lcddPool";
    public static final List<String> URL_WHEN_LOGIN_IS_SUCCESSFUL = Arrays.asList("https://www.google.com");
    public static final String DOMAIN_PREFIX = "lcdd-connection";//Domain that can use in order to connect to cognito to sign-in/up
    public static final String DOMAIN_NAME = "domain_name,";
    public static final String CLIENT_NAME = "client_name";

    public InfraCognitoStack(final Construct parent, final String id) {
            this(parent, id, null);
    }

    public InfraCognitoStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);
        final UserPoolProps userPoolProps = getUserPoolProps();
        final UserPoolClientOptions userPoolClientOptions = getUserPoolClientOptions();
        final UserPoolDomainOptions domainOptions = getDomain();

        final UserPool userPool = new UserPool(this, LCDD_POOL_NAME, userPoolProps);
        userPool.addClient(CLIENT_NAME,userPoolClientOptions);
        userPool.addDomain(DOMAIN_NAME, domainOptions);
        final CfnUserPool child = (CfnUserPool) userPool.getNode().getDefaultChild();
        child.overrideLogicalId(LCDD_POOL_NAME);
    }

    @NotNull
    private UserPoolClientOptions getUserPoolClientOptions() {
        final OAuthSettings oAuthSettings = OAuthSettings.builder()
                .flows(OAuthFlows.builder().implicitCodeGrant(true).authorizationCodeGrant(false).build())
                .callbackUrls(URL_WHEN_LOGIN_IS_SUCCESSFUL)
                .scopes(Arrays.asList(OAuthScope.EMAIL,OAuthScope.OPENID,OAuthScope.COGNITO_ADMIN, OAuthScope.PROFILE))
                .build();

        return UserPoolClientOptions.builder()
                                    .refreshTokenValidity(Duration.days(1))
                                    .accessTokenValidity(Duration.days(1))
                                    .idTokenValidity(Duration.days(1))
                                    .authFlows(AuthFlow.builder().userSrp(true).userPassword(true).custom(true).adminUserPassword(true).build())
                                    .oAuth(oAuthSettings)
                                    .build();
    }

    @NotNull
    private UserPoolDomainOptions getDomain() {
        final CognitoDomainOptions cognitoDomainOptions = CognitoDomainOptions.builder().domainPrefix(DOMAIN_PREFIX).build();
        return UserPoolDomainOptions.builder().cognitoDomain(cognitoDomainOptions).build();
    }

    /**
     * During a user pool update, you can add new schema attributes but you cannot modify or delete an existing schema attribute
     * https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/aws-resource-cognito-userpool.html
     * @return
     */
    @NotNull
    private UserPoolProps getUserPoolProps() {
/*        java.util.Map<String, ICustomAttribute> customAttributes = new HashMap<>();
        final ICustomAttribute build = StringAttribute.Builder.create().build();
        customAttributes.put("interested_subject",build);*/

        // The code that defines your stack goes here
        return UserPoolProps.builder()
                .passwordPolicy(PasswordPolicy.builder().minLength(8).requireUppercase(true).requireDigits(true).requireLowercase(true).build())
                .standardAttributes(StandardAttributes.builder()
                        .email(StandardAttribute.builder().mutable(true).required(true).build())
                        .familyName(StandardAttribute.builder().mutable(true).required(true).build())
                        .givenName(StandardAttribute.builder().mutable(true).required(true).build())
                        //.nickname(StandardAttribute.builder().mutable(true).required(true).build())
                        //.gender(StandardAttribute.builder().mutable(true).required(true).build())
                        .phoneNumber(StandardAttribute.builder().mutable(true).required(false).build())
                        .build())
                //.customAttributes(customAttributes)
                .accountRecovery(AccountRecovery.EMAIL_ONLY)
                .selfSignUpEnabled(true)
                .autoVerify(AutoVerifiedAttrs.builder().email(true).build())
                .signInAliases(SignInAliases.builder().email(true).username(false).phone(false).preferredUsername(false).build())
                .build();
    }
}
