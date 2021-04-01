package fr.bergit;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Runtime;

public class InfraCognitoLambdaStack extends Stack {

    public static Function customMessageFunction;

    public InfraCognitoLambdaStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public InfraCognitoLambdaStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);
        // Defines a new lambda resource
        customMessageFunction = Function.Builder.create(this, "lambdaTriggerPresignup")
                .runtime(Runtime.NODEJS_10_X)    // execution environment
                .code(Code.fromAsset("src/lambda"))  // code loaded from the "lambda" directory
                .handler("hello.handler")        // file is "hello", function is "handler"
                .build();
    }

}
