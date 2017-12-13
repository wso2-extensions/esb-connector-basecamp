# Configuring Basecamp Operations

[[Initializing the Connector]](#initializing-the-connector)  [[Obtaining user credentials]](#obtaining-user-credentials)

> NOTE: To work with the Basecamp connector, you need to have a Basecamp account. If you do not have a Basecamp account, go to [https://basecamp.com/](https://basecamp.com/) and create a Basecamp trial account.

To use the Basecamp connector, add the <googlepubsub.init> element in your configuration before any other Basecamp operation. This configuration authenticates with Basecamp via user credentials.

Basecamp uses the OAuth 2.0 protocol for authentication and authorization. All requests to the Basecamp API must be authorized by an authenticated user.

## Obtaining user credentials

* **Follow the steps below to obtain an access token**

    1. Go to https://launchpad.37signals.com/integrations and click **Register Application**. 
    2. Give proper values for Name of Your Application, Your company’s name, Your website URL, and Redirect URL
    3. Then click **Register this App**, It assign a client id and client secret and  Make a note of the client id and client secret.
    4. Next, get the Authorization code from Azure Access Control Service Construct the authorization url as: **https://<i></i>launchpad.37signals.com/authorization/new?type=web_server&client_id=your-client-id&redirect_uri=your-redirect-uri**.
    6. It ask whether it's ok to give access to your app. Click on **Yes,I'll allow access**. 
    7. Then get the access token and refresh token from code, construct the post request URL: **https:/<i></i>/launchpad.37signals.com/authorization/token?type=web_server&client_id=your-client-id&redirect_uri=your-redirect-uri&client_secret=your-client-secret&code=verification-code**.

[Click here](https://github.com/basecamp/api/blob/master/sections/authentication.md#oauth-2) for further reference on the authentication process. 

## Initializing the Connector
Specify the init method as follows:

**init**
```xml
<basecamp.init>
    <accessToken>{$ctx:accessToken}</accessToken>
    <apiUrl>{$ctx:apiUrl}</apiUrl>
    <accountId>{$ctx:accountId}</accountId>
    <userAgent>{$ctx:userAgent}</userAgent>
    <eTag>{$ctx:eTag}</eTag>
    <lastModifiedDate>{$ctx:lastModifiedDate}</lastModifiedDate>
</basecamp.init>
```
**Properties** 
* accessToken: The access token to authenticate Basecamp using OAuth 2.0.
* apiUrl: The URL of the Basecamp API. 
* accountId: The unique identifier of the account(You can find the accountId in basecamp URL. The URL is structured like : https://<i></i>3.basecamp.com/**accountId**/projects).
* userAgent: This is used by the API to store developer information.
* eTag: Optional - The e-tag of a previously given response.
* lastModifiedDate: Optional - The last modified date of the resource.

## Additional Information

Ensure that the following Axis2 configurations are added and enabled in the <EI_HOME>\conf\axis2\axis2.xml file.

**Required message formatters**
```xml
<messageFormatter contentType="text/html" class="org.wso2.carbon.relay.ExpandingMessageFormatter"/>
```
**Required message builders**
```xml
<messageBuilder contentType="text/html" class="org.wso2.carbon.relay.BinaryRelayBuilder"/>
```

Now that you have connected to Basecamp, use the information in the following topics to perform various operations with the connector.
* [Working with Comments in Basecamp](comment.md)
* [Working with Events in Basecamp](event.md)
* [Working with Messages in Basecamp](message.md)
* [Working with People in Basecamp](people.md)
* [Working with Projects in Basecamp](project.md)
* [Working with To-do's in Basecamp](todo.md)
* [Working with To-do Lists in Basecamp](todoList.md)

