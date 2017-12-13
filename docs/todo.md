# Working with To-do's in Basecamp

[[  Overview ]](#overview)  [[ Operation details ]](#operation-details)  [[  Sample configuration  ]](#sample-configuration)

### Overview 

The following operations allow you to work with to-do's. Click an operation name to see details on how to use it.
For a sample proxy service that illustrates how to work with to-do's, see [Sample configuration](#sample-configuration).

| Operation        | Description |
| ------------- |-------------|
| [createTodo](#creating-a-todo)    | Creates a to-do|
| [getTodo](#retrieving-a-todo)    | Retrieves a to-do|
| [updateTodo](#updating-a-todo)    | Updates a to-do|

### Operation details

This section provides more details on each of the operations.

#### Creating a todo
The createTodo operation adds a new to-do to the specified to-do list from the parameters passed.

**createTodo**
```xml
<basecamp.createTodo>
    <projectId>{$ctx:projectId}</projectId>
    <todoListId>{$ctx:todoListId}</todoListId>
    <content>{$ctx:content}</content>
    <description>{$ctx:description}</description>
    <dueDate>{$ctx:dueDate}</dueDate>
    <assignee>{$ctx:assignee}</assignee>
    <notify>{$ctx:notify}</notify>
    <startDate>{$ctx:startDate}</startDate>
</basecamp.createTodo>  
```

**Properties**
* projectId: The identifier of the project in which the to-do will be created.
* todoListId: The identifier of the to-do list where the to-do will be created.
* content: The content of the to-do.
* description[optional]: The description of the to-do.
* dueDate[optional]: The due date of the to-do.
* assignee[optional]: An array of people Id that will be assigned to this to-do.
* notify[optional]: when set to true, will notify the assignees about being assigned.
* startDate[optional]: The start date of the to-do.

**Sample request**

Following is a sample REST request that can be handled by the createTodo operation.

```json
{
    "apiUrl": "https://3.basecampapi.com",
    "accessToken": "BAhbByIBsHsidmVyc2lvbiI6MSwidXNlcl9pZHMiOlsyMTDg13LTA0VDA3OjM2OjMxWiJ9dToJVGltZQ2HmBzAqS77kQ==--1fb2c32e4d904b7960b77d5e81db7c6666dee01c2",
    "accountId": "2669154",
    "projectId": "6224144",
    "todoListId": "17697645",
    "content": "Create todo",
    "description": "Create New todo",
    "dueDate": "2018-03-27",
    "assignee": {"id": 8278029,"type": "Person"},
    "notify": "true",
    "startDate": "2018-02-27"
} 
```

**Related Basecamp documentation**

https://github.com/basecamp/bc3-api/blob/master/sections/todos.md#create-a-to-do

#### Retrieving a todo

The getTodo operation retrieves a specified to-do.

**getTodo**
```xml
<basecamp.getTodo>
    <todoId>{$ctx:todoId}</todoId>
    <projectId>{$ctx:projectId}</projectId>
</basecamp.getTodo> 
```

**Properties**
* todoId: The ID of the to-do to retrieve.
* projectId: The ID of the project containing the to-do.

**Sample request**

Following is a sample REST request that can be handled by the getTodo operation.

```json
{
    "apiUrl": "https://3.basecampapi.com",
    "accessToken": "BAhbByIBsHsidmVyc2lvbiI6MSwidXNlcl9pZHMiOlsyMTDg13LTA0VDA3OjM2OjMxWiJ9dToJVGltZQ2HmBzAqS77kQ==--1fb2c32e4d904b7960b77d5e81db7c6666dee01c2",
    "accountId": "2669154",
    "projectId":"6224144",
    "todoId":"108590347"
} 
```

**Related Basecamp documentation**

https://github.com/basecamp/bc3-api/blob/master/sections/todos.md#get-a-to-do

#### Updating a todo

The updateTodo operation update a specified to-do.

**updateTodo**
```xml
<basecamp.updateTodo>
    <todoId>{$ctx:todoId}</todoId>
    <projectId>{$ctx:projectId}</projectId>
    <content>{$ctx:content}</content>
    <description>{$ctx:description}</description>
    <dueDate>{$ctx:dueDate}</dueDate>
    <assignee>{$ctx:assignee}</assignee>
    <notify>{$ctx:notify}</notify>
    <startDate>{$ctx:startDate}</startDate>
</basecamp.updateTodo>   
```

**Properties**
* todoId: The ID of the to-do to update.
* projectId: The ID of the project containing the to-do.
* content: The content of the to-do.
* description: The description of the to-do.
* dueDate: The due date of the to-do.
* assignee: An array of people Id that will be assigned to this to-do.
* notify: when set to true, will notify the assignees about being assigned.
* startDate: The start date of the to-do.

**Sample request**

Following is a sample REST request that can be handled by the updateTodo operation.

```json
{
    "apiUrl": "https://3.basecampapi.com",
    "accessToken": "BAhbByIBsHsidmVyc2lvbiI6MSwidXNlcl9pZHMiOlsyMTDg13LTA0VDA3OjM2OjMxWiJ9dToJVGltZQ2HmBzAqS77kQ==--1fb2c32e4d904b7960b77d5e81db7c6666dee01c2",
    "accountId": "2669154",
    "projectId":"6224144",
    "todoId":"108590347",
    "content": "Update todo",
    "description": "Updating a todo",
    "dueDate": "2018-03-27",
    "assignee": {"id": 8278029,"type": "Person"},
    "notify": "true",
    "startDate": "2018-02-27"
}
```

**Related Basecamp documentation**

https://github.com/basecamp/bc3-api/blob/master/sections/todos.md#update-a-to-do

#### Sample configuration

Following is a sample proxy service that illustrates how to connect to Basecamp with the init operation and use the createTodo operation. The sample request for this proxy can be found in createTodo sample request. You can use this sample as a template for using other operations in this category.

**Sample Proxy**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<proxy xmlns="http://ws.apache.org/ns/synapse" name="basecamp_createTodo" transports="https,http" statistics="disable" trace="disable" startOnLoad="true">
  <target>
    <inSequence>
    <property name="accessToken" expression="json-eval($.accessToken)"/>
    <property name="apiUrl" expression="json-eval($.apiUrl)"/>
    <property name="accountId" expression="json-eval($.accountId)"/>
    <property name="projectId" expression="json-eval($.projectId)"/>
    <property name="todoListId" expression="json-eval($.todoListId)"/>
    <property name="content" expression="json-eval($.content)"/>
    <property name="description" expression="json-eval($.description)"/>
    <property name="dueDate" expression="json-eval($.dueDate)"/>
    <property name="assignee" expression="json-eval($.assignee)"/>
    <property name="notify" expression="json-eval($.notify)"/>
    <property name="startDate" expression="json-eval($.startDate)"/>
      <basecamp.init>
        <accessToken>{$ctx:accessToken}</accessToken>
        <apiUrl>{$ctx:apiUrl}</apiUrl>
        <accountId>{$ctx:accountId}</accountId>
      </basecamp.init>     
      <basecamp.createTodo>
        <projectId>{$ctx:projectId}</projectId>
        <todoListId>{$ctx:todoListId}</todoListId>
        <content>{$ctx:content}</content>
        <description>{$ctx:description}</description>
        <dueDate>{$ctx:dueDate}</dueDate>
        <assignee>{$ctx:assignee}</assignee>
        <notify>{$ctx:notify}</notify>
        <startDate>{$ctx:startDate}</startDate>
      </basecamp.createTodo>   
        <respond/>         
    </inSequence>
    <outSequence>
     <send/>
    </outSequence>
  </target>
</proxy> 
```
