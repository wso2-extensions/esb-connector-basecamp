# Working with To-do Items in Basecamp

[[Overview]](#overview)  [[Operation details]](#operation-details)  [[Sample configuration]](#sample-configuration)

### Overview 

The following operations allow you to work with to-do items. Click an operation name to see details on how to use it.
For a sample proxy service that illustrates how to work with to-do items, see [Sample configuration](#sample-configuration).

| Operation        | Description |
| ------------- |-------------|
| [createTodo](#creating-a-to-do)    | Creates a to-do item|
| [getTodo](#retrieving-a-to-do)    | Retrieves a to-do item|
| [updateTodo](#updating-a-to-do)    | Updates a to-do item|

### Operation details

This section provides more details on each of the operations.

#### Creating a to-do
The createTodo operation adds a new to-do item to a particular to-do list based on the properties that you specify.

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
* projectId: The identifier of the project in which the to-do item should be created.
* todoListId: The identifier of the to-do list where the to-do item should be created.
* content: The content of the to-do item.
* description: Optional - The description of the to-do item.
* dueDate: Optional - The due date of the to-do item.
* assignee: Optional - An array of IDs of people you want to assign to the to-do item.
* notify: Optional - Set this to true if you want to notify assignees that they are assigned to the created to-do item.
* startDate: Optional - The start date of the to-do item.

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

#### Retrieving a to-do

The getTodo operation retrieves a to-do item based on the properties that you specify.

**getTodo**
```xml
<basecamp.getTodo>
    <todoId>{$ctx:todoId}</todoId>
    <projectId>{$ctx:projectId}</projectId>
</basecamp.getTodo> 
```

**Properties**
* todoId: The identifier of the to-do item that you want to retrieve.
* projectId: The identifier of the project where the to-do item to be retrieved exists.

**Sample request**

Following is a sample REST request that can be handled by the getTodo operation.

```json
{
    "apiUrl": "https://3.basecampapi.com",
    "accessToken": "BAhbByIBsHsidmVyc2lvbiI6MSwidXNlcl9pZHMiOlsyMTDg13LTA0VDA3OjM2OjMxWiJ9dToJVGltZQ2HmBzAqS77kQ==--1fb2c32e4d904b7960b77d5e81db7c6666dee01c2",
    "accountId": "2669154",
    "todoId":"108590347",
    "projectId":"6224144"
} 
```

**Related Basecamp documentation**

https://github.com/basecamp/bc3-api/blob/master/sections/todos.md#get-a-to-do

#### Updating a to-do

The updateTodo operation updates an existing to-do item based on the properties that you specify.

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
* todoId: The identifier of the to-do item that you want to update.
* projectId: The identifier of the project in which the to-do item to be updated exists.
* content: The new content of the to-do item.
* description: The new description of the to-do item.
* dueDate: The new due date of the to-do item.
* assignee: An array of IDs of people you want to assign to the to-do item.
* notify: Set this to true if you want to notify assignees that they are assigned to the created to-do item.
* startDate: The new start date of the to-do item.

**Sample request**

Following is a sample REST request that can be handled by the updateTodo operation.

```json
{
    "apiUrl": "https://3.basecampapi.com",
    "accessToken": "BAhbByIBsHsidmVyc2lvbiI6MSwidXNlcl9pZHMiOlsyMTDg13LTA0VDA3OjM2OjMxWiJ9dToJVGltZQ2HmBzAqS77kQ==--1fb2c32e4d904b7960b77d5e81db7c6666dee01c2",
    "accountId": "2669154",
    "todoId":"108590347",
    "projectId":"6224144",
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

### Sample configuration

Following is a sample proxy service that illustrates how to connect to Basecamp with the init operation, and then use the createTodo operation. The sample request for this proxy can be found in the createTodo sample request. You can use this sample as a template for using other operations in this category.

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
