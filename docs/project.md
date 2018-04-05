# Working with Projects in Basecamp

[[Overview]](#overview)  [[Operation details]](#operation-details)  [[Sample configuration]](#sample-configuration)

### Overview 

The following operations allow you to work with projects. Click an operation name to see details on how to use it.
For a sample proxy service that illustrates how to work with projects, see [Sample configuration](#sample-configuration).

| Operation        | Description |
| ------------- |-------------|
| [createProject](#creating-a-project)    | Creates a project|
| [getProject](#retrieving-a-project)    | Retrieves a project|
| [listProjects](#retrieving-all-projects)    | Retrieves all active/archived projects |
| [updateProject](#updating-a-project)    | Updates a project|

### Operation details

This section provides more details on each of the operations.

####  Creating a project
The createProject operation creates a new project based on the specified details. 

**createProject**
```xml
<basecamp.createProject>
    <name>{$ctx:name}</name>
    <description>{$ctx:description}</description>
</basecamp.createProject>  
```
**Properties**
* name: The name of the project that you want to create.
* description: Optional - The description of the project that you want to create.

**Sample request**

Following is a sample REST request that can be handled by the createProject operation.

```json
{
    "apiUrl":"https://3.basecampapi.com",          
    "accessToken":"BAhbByIBsHsidmVyc2lvbiI6MSwidXNlcl9pZHMiOlsyMTDg13LTA0VDA3OjM2OjMxWiJ9dToJVGltZQ2HmBzAqS77kQ==--1fb2c32e4d904b7960b77d5e81db7c6666dee01c2",
    "accountId":"2669154",
    "name":"ProjectName",
    "description":"This is a sample Project"
} 
```

**Related Basecamp documentation**

https://github.com/basecamp/bc3-api/blob/master/sections/projects.md#create-a-project

#### Retrieving a project
The getProject operation retrieves a project based on the specified project ID.

**getProject**

```xml
<basecamp.getProject>       
    <projectId>{$ctx:projectId}</projectId>
</basecamp.getProject>  
```

**Properties**
* projectId: The identifier of the project that you want to retrieve.
> NOTE: You can find the projectId in the Basecamp project URL. A Basecamp project URL is generally structured as follows:"https://<i></i>3.basecamp.com/**accountId**/projects/**projectId**”.

**Sample request**

Following is a sample REST request that can be handled by the getProject operation.

```json
{
    "apiUrl":"https://3.basecampapi.com",          
    "accessToken":"BAhbByIBsHsidmVyc2lvbiI6MSwidXNlcl9pZHMiOlsyMTDg13LTA0VDA3OjM2OjMxWiJ9dToJVGltZQ2HmBzAqS77kQ==--1fb2c32e4d904b7960b77d5e81db7c6666dee01c2",
    "accountId":"2669154",
    "projectId":"6225303"
} 
```

**Related Basecamp documentation**

https://github.com/basecamp/bc3-api/blob/master/sections/projects.md#get-a-project

#### Retrieving all projects
The listProjects operation retrieves all active/archived projects.

**listProjects**
```xml
<basecamp.listProjects>
    <status>{$ctx:status}</status>
</basecamp.listProjects>  
```

**Properties**
* status: Optional - If you want to retrieve archived or trashed projects that are visible to the current user, you need to set the status to either archived or trashed.

**Sample request**

Following is a sample REST request that can be handled by the listProjects operation.

```json
{
    "apiUrl":"https://3.basecampapi.com",          
    "accessToken":"BAhbByIBsHsidmVyc2lvbiI6MSwidXNlcl9pZHMiOlsyMTDg13LTA0VDA3OjM2OjMxWiJ9dToJVGltZQ2HmBzAqS77kQ==--1fb2c32e4d904b7960b77d5e81db7c6666dee01c2",
    "accountId":"2669154",
    "status":"archived"
} 
```

**Related Basecamp documentation**

https://github.com/basecamp/bc3-api/blob/master/sections/projects.md#get-projects

#### Updating a project
The updateProject operation updates the name and the description of an existing project based on the new values that are specified.

**updateProject**
```xml
<basecamp.updateProject>
    <projectId>{$ctx:projectId}</projectId>
    <name>{$ctx:name}</name>
    <description>{$ctx:description}</description>
</basecamp.updateProject>  
```

**Properties**
* projectId: The identifier of the project that you want to update.
> NOTE: You can find the projectId in the Basecamp project URL. A Basecamp project URL is generally structured as follows:"https://<i></i>3.basecamp.com/**accountId**/projects/**projectId**”.
* name: The new name that you want the project to have.
* description: The new description that you want the project to have.
  
**Sample request**

Following is a sample REST request that can be handled by the updateProject operation.

```json
{
    "apiUrl":"https://3.basecampapi.com",          
    "accessToken":"BAhbByIBsHsidmVyc2lvbiI6MSwidXNlcl9pZHMiOlsyMTDg13LTA0VDA3OjM2OjMxWiJ9dToJVGltZQ2HmBzAqS77kQ==--1fb2c32e4d904b7960b77d5e81db7c6666dee01c2",
    "accountId":"2669154",
    "status":"archived"
} 
```

**Related Basecamp documentation**

https://github.com/basecamp/bc3-api/blob/master/sections/projects.md#update-a-project

### Sample configuration

Following is a sample proxy service that illustrates how to connect to Basecamp with the init operation, and then use the createProject operation. The sample request for this proxy can be found in createProject sample request. You can use this sample as a template for using other operations in this category.

**Sample Proxy**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<proxy xmlns="http://ws.apache.org/ns/synapse" name="basecamp_createProject" transports="https,http" statistics="disable" trace="disable" startOnLoad="true">
  <target>
    <inSequence>
      <property name="apiUrl" expression="json-eval($.apiUrl)"/>
      <property name="accessToken" expression="json-eval($.accessToken)"/>
      <property name="accountId" expression="json-eval($.accountId)"/>
      <property name="name" expression="json-eval($.name)"/>
      <property name="description" expression="json-eval($.description)"/>
      <basecamp.init>
        <accessToken>{$ctx:accessToken}</accessToken>
        <apiUrl>{$ctx:apiUrl}</apiUrl>
        <accountId>{$ctx:accountId}</accountId>
      </basecamp.init>     
      <basecamp.createProject>
        <name>{$ctx:name}</name>
        <description>{$ctx:description}</description>
      </basecamp.createProject>
      <respond/>
    </inSequence>
    <outSequence>
      <log/>
      <send/>
    </outSequence>
  </target>
</proxy>
```
