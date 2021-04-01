package fr.bergit;

import software.amazon.awscdk.core.App;
public class InfraCognitoApp {

    public static final String EU_CENTRAL_1 = "eu-central-1";

    public static void main(final String[] args) {
        App app = new App();
        new InfraCognitoLambdaStack(app, "infra-cognito-lambda-stack");
        new InfraCognitoStack(app, "infra-cognito-stack");
        app.synth();
    }


    private static int function(int i){
        return i + 1;
    }
}