exports.handler = (event, context, callback) => {

        if (event.triggerSource === "CustomMessage_SignUp") {
            event.response.smsMessage = "Welcome to the service. Your confirmation code is " + event.request.codeParameter;
            event.response.emailSubject = "Welcome to the service";
            event.response.emailMessage = "Thank you for signing up. " + event.request.codeParameter + " is your verification code";
        }else if (event.triggerSource === "CustomMessage_ForgotPassword") {
                //TODO
        }else if (event.triggerSource === "CustomMessage_ResendCode") {
                //TODO
                 }
    // Return to Amazon Cognito
    callback(null, event);
};
