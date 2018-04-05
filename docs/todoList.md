# Working with To-do Lists in Basecamp

[[Overview]](#overview)  [[Operation details]](#operation-details)  [[Sample configuration]](#sample-configuration)

### Overview 

The following operations allow you to work with to-do lists. Click an operation name to see details on how to use it.
For a sample proxy service that illustrates how to work with to-do lists, see [Sample configuration](#sample-configuration).

| Operation        | Description |
| ------------- |-------------|
| [createTodoLists](#creating-a-to-do-list)    | Creates a to-do list|
| [getTodoLists](#retrieving-a-to-do-list)    | Retrieves a specified to-do list|
| [listTodoLists](#retrieving-all-active-to-do-lists)    | Retrieves all active to-do lists in a project|
| [updateTodoLists](#updating-a-to-do-list)    | Updates a to-do list|

### Operation details

This section provides more details on each of the operations.

####  Creating a to-do list
The createTodoLists operation adds a new to-do list to a particular to-do set based on the properties that you specify.

**createTodoLists**
```xml
<basecamp.createTodoLists>
    <projectId>{$ctx:projectId}</projectId>
    <todoSetId>{$ctx:todoSetId}</todoSetId>
    <name>{$ctx:name}</name>
    <description>{$ctx:description}</description>
</basecamp.createTodoLists>  
```

**Properties**
* projectId: The identifier of the project in which the to-do list should be created.
* todoSetId: The identifier of the to-do set in which the to-do list should be created.
* name: The name of the to-do list.
* description: Optional - The description of the to-do list.

**Sample request**

Following is a sample REST request that can be handled by the createTodoLists operation.

```json
{
    "apiUrl": "https://3.basecampapi.com",
    "accessToken": "BAhbByIBsHsidmVyc2lvbiI6MSwidXNlcl9pZHMiOlsyMTDg13LTA0VDA3OjM2OjMxWiJ9dToJVGltZQ2HmBzAqS77kQ==--1fb2c32e4d904b7960b77d5e81db7c6666dee01c2",
    "accountId": "2669154",
    "projectId": "6224144",
    "todoSetId": "17697645",
    "name": "To-do List A",
    "description": "New todo list"
} 
```

**Related Basecamp documentation**

https://github.com/basecamp/bc3-api/blob/master/sections/todolists.md#create-a-to-do-list

#### Retrieving a to-do list

The getTodoLists operation retrieves a to-do list based on the properties that you specify.

**getTodoLists**
```xml
<basecamp.getTodoLists>
    <todoListId>{$ctx:todoListId}</todoListId>
    <projectId>{$ctx:projectId}</projectId>
</basecamp.getTodoLists>  
```

**Properties**
* todoListId: The identifier of the to-do list that you want to retrieve.
* projectId: The identifier of the project where the to-do list to be retrieved exists.

**Sample request**

Following is a sample REST request that can be handled by the getTodoLists operation.

```json
{
    "apiUrl": "https://3.basecampapi.com",
    "accessToken": "BAhbByIBsHsidmVyc2lvbiI6MSwidXNlcl9pZHMiOlsyMTDg13LTA0VDA3OjM2OjMxWiJ9dToJVGltZQ2HmBzAqS77kQ==--1fb2c32e4d904b7960b77d5e81db7c6666dee01c2",
    "accountId": "2669154",
    "todoListId":"108590347",
    "projectId":"6224144"
} 
```

**Related Basecamp documentation**

https://github.com/basecamp/bc3-api/blob/master/sections/todolists.md#get-a-to-do-list

#### Retrieving all active to-do lists

The listTodoLists operation retrieves all active to-do lists in a project.

**listTodoLists**
```xml
<basecamp.listTodoLists>
    <todoSetId>{$ctx:todoSetId}</todoSetId>
    <projectId>{$ctx:projectId}</projectId>
    <status>{$ctx:status}</status>
</basecamp.listTodoLists>  
```

**Properties**
* todoSetId: The identifier of the to-do set in which the to-do lists to be retrieved exist.
* projectId: The identifier of the project where the to-do lists to be retrieved exist.
* status: Optional - If you want to retrieve archived or trashed to-do lists that exist in a to-do set, you need to set the status to either archived or trashed.

**Sample request**

Following is a sample REST request that can be handled by the listTodoLists operation.

```json
{
    "apiUrl": "https://3.basecampapi.com",
    "accessToken": "BAhbByIBsHsidmVyc2lvbiI6MSwidXNlcl9pZHMiOlsyMTDg13LTA0VDA3OjM2OjMxWiJ9dToJVGltZQ2HmBzAqS77kQ==--1fb2c32e4d904b7960b77d5e81db7c6666dee01c2",
    "accountId": "2669154",
    "todoSetId":"2669112",
    "projectId":"6224144",
    "status":"archived"
}
```

**Related Basecamp documentation**

https://github.com/basecamp/bc3-api/blob/master/sections/todolists.md#get-to-do-lists

#### Updating a to-do list

The updateTodo operation updates a to-do list based on the properties that you specify.
. 

**updateTodoLists**
```xml
<basecamp.updateTodoLists>
    <todoListId>{$ctx:todoListId}</todoListId>
    <projectId>{$ctx:projectId}</projectId>
    <name>{$ctx:name}</name>
    <description>{$ctx:description}</description>
</basecamp.updateTodoLists> 
```

**Properties**
* todoListId: The identifier of the to-do list that you want to update.
* projectId: The identifier of the project in which the to-do list that you want to update exists.
* name: The new name of the to-do list.
* description: The new description of the to-do list.

**Sample request**

Following is a sample REST request that can be handled by the updateTodoLists operation.

```json
{
    "apiUrl": "https://3.basecampapi.com",
    "accessToken": "BAhbByIBsHsidmVyc2lvbiI6MSwidXNlcl9pZHMiOlsyMTDg13LTA0VDA3OjM2OjMxWiJ9dToJVGltZQ2HmBzAqS77kQ==--1fb2c32e4d904b7960b77d5e81db7c6666dee01c2",
    "accountId": "2669154",
    "todoListId": "17697645",
    "projectId":"6224144",
    "name": "Updated to-do name",
    "description": "Updated to-do"
} 
```

**Related Basecamp documentation**

https://github.com/basecamp/bc3-api/blob/master/sections/todolists.md#update-a-to-do-list

### Sample configuration

Following is a sample proxy service that illustrates how to connect to Basecamp with the init operation, and then use the listTodoLists operation. The sample request for this proxy can be found in the listTodoLists sample request. You can use this sample as a template for using other operations in this category.

**Sample Proxy**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<proxy xmlns="http://ws.apache.org/ns/synapse" name="basecamp_listTodoLists" transports="https,http" statistics="disable" trace="disable" startOnLoad="true">
  <target>
    <inSequence>
      <property name="apiUrl" expression="json-eval($.apiUrl)"/> 
      <property name="accessToken" expression="json-eval($.accessToken)"/>
      <property name="accountId" expression="json-eval($.accountId)"/>   
      <property name="todoSetId" expression="json-eval($.todoSetId)"/>
      <property name="projectId" expression="json-eval($.projectId)"/>   
      <property name="status" expression="json-eval($.status)"/>     
      <basecamp.init>
        <apiUrl>{$ctx:apiUrl}</apiUrl>
        <accessToken>{$ctx:accessToken}</accessToken>       
        <accountId>{$ctx:accountId}</accountId>
      </basecamp.init>     
      <basecamp.listTodoLists>
        <todoSetId>{$ctx:todoSetId}</todoSetId>
        <projectId>{$ctx:projectId}</projectId>
        <status>{$ctx:status}</status>
      </basecamp.listTodoLists>
      <respond/>
    </inSequence>
    <outSequence>
      <send/>
    </outSequence>
  </target>
</proxy> 
```
