# Working with To-do Lists in Basecamp

[[  Overview ]](#overview)  [[ Operation details ]](#operation-details)  [[  Sample configuration  ]](#sample-configuration)

### Overview 

The following operations allow you to work with to-do lists. Click an operation name to see details on how to use it.
For a sample proxy service that illustrates how to work with to-do lists, see [Sample configuration](#sample-configuration).

| Operation        | Description |
| ------------- |-------------|
| [createTodoLists](#creating-a-todo-list)    | Creates a to-do lists|
| [getTodoLists](#retrieving-a-todo-list)    | Retrieves a to-do lists|
| [listTodoLists](#listing-todo-lists)    | Lists to-do lists|
| [updateTodoLists](#updating-a-todo-list)    | Updates a to-do lists|

### Operation details

This section provides more details on each of the operations.

####  Creating a todo list
The createTodoLists operation adds a new to-do list to the to-do set from the parameters passed.

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
* projectId: The identifier of the project in which the to-do list will be created.
* todoSetId: The identifier of the to-set.
* name: The name of the to-do list.
* description[optional]: The description of the to-do list.

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
    "description": "Create New todo list"
} 
```

**Related Basecamp documentation**

https://github.com/basecamp/bc3-api/blob/master/sections/todolists.md#create-a-to-do-list

#### Retrieving a todo list

The getTodoLists operation retrieves a specified to-do list.

**getTodoLists**
```xml
<basecamp.getTodoLists>
    <todoListId>{$ctx:todoListId}</todoListId>
    <projectId>{$ctx:projectId}</projectId>
</basecamp.getTodoLists>  
```

**Properties**
* todoListId: The ID of the to-do list to retrieve.
* projectId: The ID of the project containing the to-do list.

**Sample request**

Following is a sample REST request that can be handled by the getTodoLists operation.

```json
{
    "apiUrl": "https://3.basecampapi.com",
    "accessToken": "BAhbByIBsHsidmVyc2lvbiI6MSwidXNlcl9pZHMiOlsyMTDg13LTA0VDA3OjM2OjMxWiJ9dToJVGltZQ2HmBzAqS77kQ==--1fb2c32e4d904b7960b77d5e81db7c6666dee01c2",
    "accountId": "2669154",
    "projectId":"6224144",
    "todoListId":"108590347"
} 
```

**Related Basecamp documentation**

https://github.com/basecamp/bc3-api/blob/master/sections/todolists.md#get-a-to-do-list

#### Listing todo Lists

The listTodoLists operation lists to-do lists for a project.

**listTodoLists**
```xml
<basecamp.listTodoLists>
    <todoSetId>{$ctx:todoSetId}</todoSetId>
    <projectId>{$ctx:projectId}</projectId>
    <status>{$ctx:status}</status>
</basecamp.listTodoLists>  
```

**Properties**
* todoSetId: The identifier of the to-set.
* projectId: The identifier of the project whose to-do lists you are listing.
* status[optional]: when set to archived or trashed, will return archived or trashed to-do lists that are in this to-do set.

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

#### Updating a todo list

The updateTodo operation update a specified to-do list. 

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
* todoListId: The ID of the to-do list to update.
* projectId: The ID of the project containing the to-do list.
* name: The name of the to-do list.
* description: The description of the to-do list.

**Sample request**

Following is a sample REST request that can be handled by the updateTodoLists operation.

```json
{
    "apiUrl": "https://3.basecampapi.com",
    "accessToken": "BAhbByIBsHsidmVyc2lvbiI6MSwidXNlcl9pZHMiOlsyMTDg13LTA0VDA3OjM2OjMxWiJ9dToJVGltZQ2HmBzAqS77kQ==--1fb2c32e4d904b7960b77d5e81db7c6666dee01c2",
    "accountId": "2669154",
    "projectId":"6224144",
    "todoListId": "17697645",
    "name": "Update todo name",
    "description": "Updating a todo"
} 
```

**Related Basecamp documentation**

https://github.com/basecamp/bc3-api/blob/master/sections/todolists.md#update-a-to-do-list

#### Sample configuration

Following is a sample proxy service that illustrates how to connect to Basecamp with the init operation and use the listTodoLists operation. The sample request for this proxy can be found in listTodoLists sample request. You can use this sample as a template for using other operations in this category.
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
