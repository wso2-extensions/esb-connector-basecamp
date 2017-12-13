# Working with People in Basecamp

[[  Overview ]](#overview)  [[ Operation details ]](#operation-details)  [[  Sample configuration  ]](#sample-configuration)

### Overview 

The following operations allow you to work with people. Click an operation name to see details on how to use it.
For a sample proxy service that illustrates how to work with messages, see [Sample configuration](#sample-configuration).

| Operation        | Description |
| ------------- |-------------|
| [listPeople](#listing-people)    | Retrieves all people in the account|
| [getProjectPeople](#retrieving-people-on-a-project)    | Retrieves all active people on the project|
| [updateProjectPeople](#updating-people-on-a-project)    | Allows granting new and existing people access to a project, revoking access from people|

### Operation details

This section provides more details on each of the operations.

####  Listing People
The listPeople operation allows you to return all people visible to the current user. It does not take any properties.

**listPeople**
```xml
<basecamp.listPeople/> 
```

**Sample request**

Following is a sample REST request that can be handled by the listPeople operation.

```json
{
    "apiUrl":"https://3.basecampapi.com",
    "accessToken":"BAhbByIBsHsidmVyc2lvbiI6MSwidXNlcl9pZHMiOlsyMTDg13LTA0VDA3OjM2OjMxWiJ9dToJVGltZQ2HmBzAqS77kQ==--1fb2c32e4d904b7960b77d5e81db7c6666dee01c2",
    "accountId":"2669154"
}  
```

**Related Basecamp documentation**

https://github.com/basecamp/bc3-api/blob/master/sections/people.md#get-all-people

#### Retrieving people on a project
The getProjectPeople operation returns all active people on the project.

**getProjectPeople**

```xml
<basecamp.getProjectPeople>
    <projectId>{$ctx:projectId}</projectId>
</basecamp.getProjectPeople>  
```

**Properties**
* projectId: The identifier of the project from which the people will be retrieved.

**Sample request**

Following is a sample REST request that can be handled by the getMessage operation.

```json
{
    "apiUrl":"https://3.basecampapi.com",
    "accessToken":"BAhbByIBsHsidmVyc2lvbiI6MSwidXNlcl9pZHMiOlsyMTDg13LTA0VDA3OjM2OjMxWiJ9dToJVGltZQ2HmBzAqS77kQ==--1fb2c32e4d904b7960b77d5e81db7c6666dee01c2",
    "accountId":"2669154",
    "projectId":"6224144"
}
```

**Related Basecamp documentation**

https://github.com/basecamp/bc3-api/blob/master/sections/people.md#get-people-on-a-project

#### Updating people on a project
The updateProjectPeople operation allows granting new and existing people access to a project, and revoking access from existing people.

**updateProjectPeople**
```xml
<basecamp.updateProjectPeople>
    <projectId>{$ctx:projectId}</projectId>
    <grant>{$ctx:grant}</grant>
    <revoke>{$ctx:revoke}</revoke>
    <create>{$ctx:create}</create>
</basecamp.updateProjectPeople>   
```

**Properties**
* projectId: The identifier of the project.
* grant: An array of execting people IDs to grant permission to access the project.
* revoke: An array of execting people IDs to revoking the access .
* create: An array of new people IDs to grant permission to access the project.

**Sample request**

Following is a sample REST request that can be handled by the updateProjectPeople operation.
```json
{
    "apiUrl":"https://3.basecampapi.com",
    "accessToken":"BAhbByIBsHsidmVyc2lvbiI6MSwidXNlcl9pZHMiOlsyMTDg13LTA0VDA3OjM2OjMxWiJ9dToJVGltZQ2HmBzAqS77kQ==--1fb2c32e4d904b7960b77d5e81db7c6666dee01c2",
    "accountId":"2669154",
    "projectId":"6224144",
    "grant":[1007299151,1007299157,1007299159],
    "revoke":[1007299150,1007299152],
    "create":[{"name": "Victor Copper","email_address": "victor@hanchodesign.com"}]
}
```

**Related Basecamp documentation**

https://github.com/basecamp/bc3-api/blob/master/sections/people.md#update-who-can-access-a-project

#### Sample configuration

Following is a sample proxy service that illustrates how to connect to Basecamp with the init operation and use the listPeople operation. The sample request for this proxy can be found in listPeople sample request.

**Sample Proxy**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<proxy xmlns="http://ws.apache.org/ns/synapse" name="basecamp_listPeople" transports="https,http" statistics="disable" trace="disable" startOnLoad="true">
  <target>
    <inSequence>
      <property name="apiUrl" expression="json-eval($.apiUrl)"/>
      <property name="accessToken" expression="json-eval($.accessToken)"/>
      <property name="accountId" expression="json-eval($.accountId)"/>         
      <basecamp.init>
        <apiUrl>{$ctx:apiUrl}</apiUrl>
        <accessToken>{$ctx:accessToken}</accessToken>
        <accountId>{$ctx:accountId}</accountId>    
      </basecamp.init> 
      <basecamp.listPeople/>   
      <respond/>
    </inSequence>
    <outSequence>
      <send/>
    </outSequence>
  </target>
</proxy> 
```
