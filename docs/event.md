# Working with Events in Basecamp

[[  Overview ]](#overview)  [[ Operation details ]](#operation-details)  [[  Sample configuration  ]](#sample-configuration)

### Overview 

The following operation allows you to work with events. Click an operation name to see details on how to use it.
For a sample proxy service that illustrates how to work with events, see [Sample configuration](#sample-configuration).

| Operation        | Description |
| ------------- |-------------|
| [getEvents](#retrieving-events)    | Retrieving events|

### Operation details

This section provides more details on each of the operations.

#### Retrieving events
All actions in Basecamp, such as starting a new to-do list, add a new todo, or adding a comment, generate an event for the progress log. The getEvents operation returns all the events of the recording (e.g- recording : message, todo List, todo ). 

**getEvents**
```xml
<basecamp.getEvents>
    <recordingId>{$ctx:recordingId}</recordingId>
    <projectId>{$ctx:projectId}</projectId>
</basecamp.getEvents> 
```

**Properties**
* recordingId: The identifier of recording. The recording can be a message, todo List, todo and document.
* projectId: The project identifier to which the comment will be added (When click a project, You can find the projectId in basecamp URL. The URL is structured like :"https://<i></i>3.basecamp.com/**accountId**/projects/**projectId**").

**Sample request**

Following is a sample REST request that can be handled by the getEvents operation.

```json
{
    "apiUrl":"https://3.basecampapi.com",
    "accessToken":"BAhbByIBsHsidmVyc2lvbiI6MSwidXNlcl9pZHMiOlsyMTDg13LTA0VDA3OjM2OjMxWiJ9dToJVGltZQ2HmBzAqS77kQ==--1fb2c32e4d904b7960b77d5e81db7c6666dee01c2",
    "accountId":"2669154",
    "recordingId": "1234557",
    "projectId":"6224144"
}
```

**Related Basecamp documentation**

[https://github.com/basecamp/bc3-api/blob/master/sections/events.md#get-events](https://github.com/basecamp/bc3-api/blob/master/sections/events.md#get-events)

#### Sample configuration

Following is a sample proxy service that illustrates how to connect to Basecamp with the init operation and use the getEvents operation. The sample request for this proxy can be found in getEvents sample request.

**Sample Proxy**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<proxy xmlns="http://ws.apache.org/ns/synapse" name="basecamp_getEvents" transports="https,http" statistics="disable" trace="disable" startOnLoad="true">
  <target>
    <inSequence>   
      <property name="apiUrl" expression="json-eval($.apiUrl)"/>
      <property name="accessToken" expression="json-eval($.accessToken)"/>
      <property name="accountId" expression="json-eval($.accountId)"/>
      <property name="recordingId" expression="json-eval($.recordingId)"/>   
      <property name="projectId" expression="json-eval($.projectId)"/>
      <basecamp.init>
        <accessToken>{$ctx:accessToken}</accessToken>
        <apiUrl>{$ctx:apiUrl}</apiUrl>
        <accountId>{$ctx:accountId}</accountId>
      </basecamp.init>     
      <basecamp.getEvents>
        <recordingId>{$ctx:recordingId}</recordingId>
        <projectId>{$ctx:projectId}</projectId>
      </basecamp.getEvents>    
      <respond/>   
    </inSequence>
    <outSequence>  
      <log/>
      <send/>
    </outSequence>
  </target>
</proxy> 
```
