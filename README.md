# Welcome to your CDK Java project!

This is a project for Java development with CDK.

The `cdk.json` file tells the CDK Toolkit how to execute your app.

## Useful commands

 * `mvn package`     compile and run tests
 * `cdk ls`          list all stacks in the app
 * `cdk synth`       emits the synthesized CloudFormation template
 * `cdk deploy`      deploy this stack to your default AWS account/region
 * `cdk diff`        compare deployed stack with current state
 * `cdk docs`        open CDK documentation

Enjoy!

## Compile project
 `mvn clean package -q`
## create a new Lambda stack
 `cdk bootstrap`
## deploy all  stack
 `cdk deploy --all`

#### Note
https://docs.aws.amazon.com/de_de/cdk/latest/guide/troubleshooting.html#troubleshooting_resource_not_deleted
My S3 bucket, DynamoDB table, or other resource is not deleted when I issue cdk destroy
By default, resources that can contain user data have a removalPolicy (Python: removal_policy) property of RETAIN,
and the resource is not deleted when the stack is destroyed. Instead, the resource is orphaned from the stack. You must
then delete the resource manually after the stack is destroyed. Until you do, redeploying the stack fails, because
the name of the new resource being created during deployment conflicts with the name of the orphaned resource.
If you set a resource's removal policy to DESTROY, that resource will be deleted when the stack is destroyed