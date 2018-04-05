# Working with Comments in Basecamp

[[Overview]](#overview)  [[Operation details]](#operation-details)  [[Sample configuration]](#sample-configuration)

### Overview 

The following operation allows you to work with comments. Click the operation name to see details on how to use it.
For a sample proxy service that illustrates how to work with comments, see [Sample configuration](#sample-configuration).

| Operation        | Description |
| ------------- |-------------|
| [createComment](#creating-a-comment)    | Creates a comment |

### Operation details

This section provides more details on the operation.

#### Creating a comment
The createComment operation creates a comment in the project and publishes the comment under a particular recording. The recording can either be a message, a to-do list, a to-do or a document.

**createComment**
```xml
<basecamp.createComment>
    <content>{$ctx:content}</content>
    <recordingId>{$ctx:recordingId}</recordingId>
    <projectId>{$ctx:projectId}</projectId>
</basecamp.createComment> 
```

**Properties**
* content: The content of the comment.
* recordingId: The identifier of the recording. The recording can either be a message, a to-do list, a to-do or a document.
* projectId: The identifier of the project in which you want to create a comment.
> NOTE: You can find the projectId in the Basecamp project URL. A Basecamp project URL is generally structured as follows:"https://<i></i>3.basecamp.com/**accountId**/projects/**projectId**".

**Sample request**

Following is a sample REST request that can be handled by the createComment operation.

```json
{
    "apiUrl":"https://3.basecampapi.com",
    "accessToken":"BAhbByIBsHsidmVyc2lvbiI6MSwidXNlcl9pZHMiOlsyMTDg13LTA0VDA3OjM2OjMxWiJ9dToJVGltZQ2HmBzAqS77kQ==--1fb2c32e4d904b7960b77d5e81db7c6666dee01c2",
    "accountId":"2669154",
    "content": "This is a test comment",
    "recordingId": "1234557",
    "projectId":"6224144"
} 
```

**Related Basecamp documentation**

[https://github.com/basecamp/bc3-api/blob/master/sections/comments.md#create-a-comment](https://github.com/basecamp/bc3-api/blob/master/sections/comments.md#create-a-comment)

#### Sample configuration

Following is a sample proxy service that illustrates how to connect to Basecamp with the init operation, and then use the createComment operation. The sample request for this proxy can be found in the createComment sample request.

**Sample Proxy**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<proxy xmlns="http://ws.apache.org/ns/synapse" name="basecamp_createComment" transports="https,http" statistics="disable" trace="disable" startOnLoad="true">
  <target>
    <inSequence>
      <property name="apiUrl" expression="json-eval($.apiUrl)"/>
      <property name="accessToken" expression="json-eval($.accessToken)"/>
      <property name="accountId" expression="json-eval($.accountId)"/>
      <property name="projectId" expression="json-eval($.projectId)"/>
      <property name="recordingId" expression="json-eval($.recordingId)"/>
      <property name="content" expression="json-eval($.content)"/>       
      <basecamp.init>
        <apiUrl>{$ctx:apiUrl}</apiUrl>
        <accessToken>{$ctx:accessToken}</accessToken>
        <accountId>{$ctx:accountId}</accountId>
      </basecamp.init>     
      <basecamp.createComment>
        <projectId>{$ctx:projectId}</projectId>
        <recordingId>{$ctx:recordingId}</recordingId>
        <content>{$ctx:content}</content>
      </basecamp.createComment>    
      <respond/>
    </inSequence>
    <outSequence>
      <log/>
      <send/>
    </outSequence>
 </target>
</proxy>
```
