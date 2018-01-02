# Configuring Basecamp Operations

[[Obtaining user credentials]](#obtaining-user-credentials)  [[Initializing the connector]](#initializing-the-connector)   [[Additional information]](#additional-information)

> NOTE: To work with the Basecamp connector, you need to have a Basecamp account. If you do not have a Basecamp account, go to [https://basecamp.com/](https://basecamp.com/) and create a Basecamp trial account.

To use the Basecamp connector, add the <basecamp.init> element in your configuration before any other Basecamp operation. This configuration authenticates with Basecamp via user credentials.

Basecamp uses the OAuth 2.0 protocol for authentication and authorization. All requests to the Basecamp API must be authorized by an authenticated user.

## Obtaining user credentials

* **Follow the steps below to obtain an access token**

    1. Go to https://launchpad.37signals.com/integrations and click **Register Application**. 
    2. Enter appropriate values for the **Name of your application**, **Your company’s name**, **Your website URL**, and **Redirect URI**.
    3. Click **Register this App**. This registers your application and assigns a **Client ID** and **Client Secret** to your application.
    4. Click on the name of your registered application and make a note of the **Client ID** and **Client Secret**. 
    5. Then click **Preview authorisation dialog** to retrieve the authorization code from Azure Access Control Service. This displays a message requesting access to your Basecamp account. Click **Yes,I'll allow access**. This redirects you to the URI specified when you registered the app. The redirect URL is structured as follows and includes the authorization code as a query string: https://redirect_url?code=<authorization_code>. 
    6. Next, extract the access token and refresh token from the code, and construct the post request URL as follows: 
       > NOTE:**https:/<i></i>/launchpad.37signals.com/authorization/token?type=web_server&client_id=your-client-id&redirect_uri=your-redirect-uri&client_secret=your-client-secret&code=verification-code**.

For more information on the authentication process, see [Basecamp authentication](https://github.com/basecamp/api/blob/master/sections/authentication.md#oauth-2)

## Initializing the connector
To use the Basecamp connector, add the <basecamp.init> operation in your configuration before carrying out any other Basecamp operation.

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
* accountId: The unique identifier of the account.
  > NOTE: You can find the accountId in the Basecamp URL, which is generally structured as follows: https://<i></i>3.basecamp.com/**accountId**/projects.
* userAgent: The user agent used by the API to store developer information.
* eTag: Optional - The eTag of the previous response.
* lastModifiedDate: Optional - The last modified date of the resource.

## Additional information

Ensure that the following Axis2 configurations are added and enabled in the <EI_HOME>/conf/axis2/axis2.xml file.

**Required message formatters**
```xml
<messageFormatter contentType="text/html" class="org.wso2.carbon.relay.ExpandingMessageFormatter"/>
```
**Required message builders**
```xml
<messageBuilder contentType="text/html" class="org.wso2.carbon.relay.BinaryRelayBuilder"/>
```

Now that you have connected to Basecamp, see following topics for information on how to perform various operations with the connector:
* [Working with Comments in Basecamp](comment.md)
* [Working with Events in Basecamp](event.md)
* [Working with Messages in Basecamp](message.md)
* [Working with People in Basecamp](people.md)
* [Working with Projects in Basecamp](project.md)
* [Working with To-do's in Basecamp](todo.md)
* [Working with To-do Lists in Basecamp](todoList.md)

