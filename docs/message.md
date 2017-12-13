# Working with Events in Basecamp

[[  Overview ]](#overview)  [[ Operation details ]](#operation-details)  [[  Sample configuration  ]](#sample-configuration)

### Overview 

The following operations allow you to work with messages. Click an operation name to see details on how to use it.
For a sample proxy service that illustrates how to work with messages,, see [Sample configuration](#sample-configuration).

| Operation        | Description |
| ------------- |-------------|
| [createMessage](#creating-a-message)    | Creates a new message|
| [getMessage](#retrieving-a-message)    | Retrieves a specified message|
| [updateMessage](#updating-a-message)    | Update a specified message|

### Operation details

This section provides more details on each of the operations.

#### Creating a message
The createMessage operation creates a new message from the parameters passed.

**createMessage**
```xml
<basecamp.createMessage>
    <subject>{$ctx:subject}</subject>
    <messageBoardId>{$ctx:messageBoardId}</messageBoardId>
    <status>{$ctx:status}</status>
    <projectId>{$ctx:projectId}</projectId>
    <content>{$ctx:content}</content>
    <categoryId>{$ctx:categoryId}</categoryId>
</basecamp.createMessage>  
```

**Properties**
* subject: The subject of the message.
* messageBoardId: The unique identifier of the message board. When click the message Board of the project, You can find the message Board Id in the URL (The URL is structured like :https://<i></i>3.basecamp.com/**accountId**/buckets/**projectId**/message_boards/**messageBoardId**).
* status: set to active to publish immediately
* projectId: The unique identifier of the project.
* content[optional]: The content of the message.
* categoryId[optional]: The unique identifier of the type of the message.

**Sample request**

Following is a sample REST request that can be handled by the createMessage operation.

```json
{
    "apiUrl":"https://3.basecampapi.com",
    "accessToken":"BAhbByIBsHsidmVyc2lvbiI6MSwidXNlcl9pZHMiOlsyMTDg13LTA0VDA3OjM2OjMxWiJ9dToJVGltZQ2HmBzAqS77kQ==--1fb2c32e4d904b7960b77d5e81db7c6666dee01c2",
    "accountId":"2669154",
    "subject":"sample",
    "messageBoardId":"1234567",
    "status": "active",
    "projectId":"6224144",
    "content":"sample message abc",
    "categoryId": "5671895"
} 
```

**Related Basecamp documentation**

https://github.com/basecamp/bc3-api/blob/master/sections/messages.md#create-a-message

#### Retrieving a message
The getMessage operation returns the specified message details.

**getMessage**
```xml

<basecamp.getMessage>
    <projectId>{$ctx:projectId}</projectId>
    <messageId>{$ctx:messageId}</messageId>
</basecamp.getMessage>  
```

**Properties**
* projectId: The identifier of the project from which the message will be retrieved.
* messageId: The message identifier to retrieve the specified message.

**Sample request**

Following is a sample REST request that can be handled by the getMessage operation.

```json
{
    "apiUrl":"https://3.basecampapi.com",
    "accessToken":"BAhbByIBsHsidmVyc2lvbiI6MSwidXNlcl9pZHMiOlsyMTDg13LTA0VDA3OjM2OjMxWiJ9dToJVGltZQ2HmBzAqS77kQ==--1fb2c32e4d904b7960b77d5e81db7c6666dee01c2",
    "accountId":"2669154",
    "projectId":"6224144",
    "messageId":"27383420"
}
```

**Related Basecamp documentation**

https://github.com/basecamp/bc3-api/blob/master/sections/messages.md#get-a-message

#### Updating a message
The updateMessage operation allows changing the subject, content and category_id of the message.

**updateMessage**
```xml
<basecamp.updateMessage>
    <projectId>{$ctx:projectId}</projectId>
    <messageId>{$ctx:messageId}</messageId>
    <subject>{$ctx:subject}</subject>
    <content>{$ctx:content}</content>
    <categoryId>{$ctx:categoryId}</categoryId>
</basecamp.updateMessage>   
```

**Properties**
* projectId: The identifier of the project from which the message will be updated.
* messageId: The message identifier to retrieve the specified message.
* subject: The subject of the message.
* content: The content of the message.
* categoryId: The unique identifier of the type of the message.

**Sample request**

Following is a sample REST request that can be handled by the updateMessage operation.

```json
{
    "apiUrl":"https://3.basecampapi.com",
    "accessToken":"BAhbByIBsHsidmVyc2lvbiI6MSwidXNlcl9pZHMiOlsyMTDg13LTA0VDA3OjM2OjMxWiJ9dToJVGltZQ2HmBzAqS77kQ==--1fb2c32e4d904b7960b77d5e81db7c6666dee01c2",
    "accountId":"2669154",
    "projectId":"6224144",
    "messageId":"27383420",
    "subject": "Update subject",
    "content":"Update sample message abc",
    "categoryId": "5671895"
}
```

**Related Basecamp documentation**

https://github.com/basecamp/bc3-api/blob/master/sections/messages.md#update-a-message

#### Sample configuration

Following is a sample proxy service that illustrates how to connect to Basecamp with the init operation and use the createMessage operation. The sample request for this proxy can be found in createMessage sample request. You can use this sample as a template for using other operations in this category.

**Sample Proxy**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<proxy xmlns="http://ws.apache.org/ns/synapse" name="basecamp_createMessage" transports="https,http" statistics="disable" trace="disable" startOnLoad="true">
  <target>
    <inSequence>
      <property name="apiUrl" expression="json-eval($.apiUrl)"/>
      <property name="accessToken" expression="json-eval($.accessToken)"/>
      <property name="accountId" expression="json-eval($.accountId)"/>   
      <property name="subject" expression="json-eval($.subject)"/>
      <property name="messageBoardId" expression="json-eval($.messageBoardId)"/>
      <property name="status" expression="json-eval($.status)"/>
      <property name="projectId" expression="json-eval($.projectId)"/>
      <property name="content" expression="json-eval($.content)"/>         
      <property name="categoryId" expression="json-eval($.categoryId)"/>     
      <basecamp.init>
        <apiUrl>{$ctx:apiUrl}</apiUrl>
        <accessToken>{$ctx:accessToken}</accessToken>
        <accountId>{$ctx:accountId}</accountId>    
      </basecamp.init>     
      <basecamp.createMessage>
        <subject>{$ctx:subject}</subject>
        <messageBoardId>{$ctx:messageBoardId}</messageBoardId>
        <status>{$ctx:status}</status>
        <projectId>{$ctx:projectId}</projectId>
        <content>{$ctx:content}</content>
        <categoryId>{$ctx:categoryId}</categoryId>
      </basecamp.createMessage>
      <respond/>
    </inSequence>
    <outSequence>
      <log/>
      <send/>
    </outSequence>
  </target>
</proxy> 
```
