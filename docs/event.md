# Working with Events in Basecamp

[[Overview]](#overview)  [[Operation details]](#operation-details)  [[Sample configuration]](#sample-configuration)

### Overview 

The following operation allows you to work with events. Click the operation name to see details on how to use it.
For a sample proxy service that illustrates how to work with events, see [Sample configuration](#sample-configuration).

| Operation        | Description |
| ------------- |-------------|
| [getEvents](#retrieving-events)    | Retrieves events |

### Operation details

This section provides more details on the operation.

#### Retrieving events
In Basecamp, all actions such as starting a new to-do list, adding a new to-do, or adding a comment can generate an event in the progress log. The getEvents operation retrieves all events related to a specified recording. 

**getEvents**
```xml
<basecamp.getEvents>
    <recordingId>{$ctx:recordingId}</recordingId>
    <projectId>{$ctx:projectId}</projectId>
</basecamp.getEvents> 
```

**Properties**
* recordingId: The identifier of the recording to retrieve related events. A recording can either be a message, a to-do list, a to-do or a document.
* projectId: The identifier of the project from which you want to retrieve events.
> NOTE: You can find the projectId in the Basecamp project URL. A Basecamp project URL is generally structured as follows:"https://<i></i>3.basecamp.com/**accountId**/projects/**projectId**”.

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

### Sample configuration

Following is a sample proxy service that illustrates how to connect to Basecamp with the init operation, and then use the getEvents operation. The sample request for this proxy can be found in the getEvents sample request.

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

