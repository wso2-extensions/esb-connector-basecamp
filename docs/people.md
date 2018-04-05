# Working with People in Basecamp

[[Overview]](#overview)  [[Operation details]](#operation-details)  [[Sample configuration]](#sample-configuration)

### Overview 

The following operations allow you to work with people. Click an operation name to see details on how to use it.
For a sample proxy service that illustrates how to work with people, see [Sample configuration](#sample-configuration).

| Operation        | Description |
| ------------- |-------------|
| [listPeople](#retrieving-details-of-people-in-an-account)    | Retrieves details of all people in an account|
| [getProjectPeople](#retrieving-details-of-people-in-a-project)    | Retrieves details of all active people in a project|
| [updateProjectPeople](#updating-permission-of-people-on-a-project)    | Updates user permission to a project |

### Operation details

This section provides more details on each of the operations.

####  Retrieving details of people in an account
The listPeople operation retrieves details of all people in an account. This information can be retrieved only by the account owner and the operation does not require any properties to be specified.

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

#### Retrieving details of people in a project
The getProjectPeople operation retrieves details of all active people in a specified project.

**getProjectPeople**

```xml
<basecamp.getProjectPeople>
    <projectId>{$ctx:projectId}</projectId>
</basecamp.getProjectPeople>  
```

**Properties**
* projectId: The identifier of the project for which you want to retrieve details of actively working people.


**Sample request**

Following is a sample REST request that can be handled by the getProjectPeople operation.

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

#### Updating permission of people on a project
The updateProjectPeople operation allows granting people permission to access a project and also allows revoking access permission of people to a project.

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
* projectId: The identifier of the project for which you want to update user permission.
* grant: An array of IDs of existing people to grant permission to access the project.
* revoke: An array of IDs of existing people to revoke permission to access the project.
* create: An array of the name and email address of new people to grant permission to access the project. 
> NOTE: If necessary you can specify the title and company name properties in the array as well . Specifying those properties are optional.

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

### Sample configuration

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
