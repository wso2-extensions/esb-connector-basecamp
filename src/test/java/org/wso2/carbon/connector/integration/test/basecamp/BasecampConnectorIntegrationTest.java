/*
 *  Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.connector.integration.test.basecamp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.connector.integration.test.base.ConnectorIntegrationTestBase;
import org.wso2.connector.integration.test.base.RestResponse;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BasecampConnectorIntegrationTest extends ConnectorIntegrationTestBase {

    private Map<String, String> esbRequestHeadersMap = new HashMap<String, String>();

    private Map<String, String> apiRequestHeadersMap = new HashMap<String, String>();

    private String apiUrl;

    /**
     * Set up the environment.
     */
    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {
        String connectorName =
                System.getProperty("connector_name") + "-connector-" + System.getProperty("connector_version") + ".zip";
        init(connectorName);
        esbRequestHeadersMap.put("Content-Type", "application/json");
        apiRequestHeadersMap.putAll(esbRequestHeadersMap);
        apiRequestHeadersMap.put("Authorization", "Bearer " + connectorProperties.getProperty("accessToken"));
        apiUrl = connectorProperties.getProperty("apiUrl") + "/" + connectorProperties.getProperty("accountId");
    }

    /**
     * Positive test case for createProject method with mandatory parameters.
     *
     * @throws Exception
     */
    @Test(priority = 1,
          description = "Test createProject{BaseCamp} with Mandatory Parameters")
    public void testCreateProjectWithMandatoryParameters() throws Exception {

        esbRequestHeadersMap.put("Action", "urn:createProject");
        RestResponse<JSONObject> esbRestReponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_createProject_mandatory.json");
        connectorProperties.put("projectId", esbRestReponse.getBody().get("id").toString());

        String apiEndpoint = apiUrl + "/projects/" + esbRestReponse.getBody().getString("id") + ".json";

        RestResponse<JSONObject> apiRestReponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);

        Assert.assertEquals(esbRestReponse.getHttpStatusCode(), 201);
        Assert.assertEquals(esbRestReponse.getBody().getString("id"), apiRestReponse.getBody().getString("id"));
        Assert.assertEquals(esbRestReponse.getBody().getString("name"), apiRestReponse.getBody().getString("name"));
    }

    /**
     * Positive test case for createProject method with optional parameters.
     */
    @Test(priority = 1,
          dependsOnMethods = { "testCreateProjectWithMandatoryParameters" },
          description = "Test createProject{BaseCamp} with optional parameters")
    public void testCreateProjectWithOptionalParameters() throws Exception {

        esbRequestHeadersMap.put("Action", "urn:createProject");
        RestResponse<JSONObject> esbReponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_createProject_optional.json");

        String apiEndpoint = apiUrl + "/projects/" + esbReponse.getBody().getString("id") + ".json";

        RestResponse<JSONObject> apiReponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);

        Assert.assertEquals(esbReponse.getBody().getString("id"), apiReponse.getBody().getString("id"));
        Assert.assertEquals(esbReponse.getBody().getString("name"), apiReponse.getBody().getString("name"));
        Assert.assertEquals(esbReponse.getBody().getString("description"),
                apiReponse.getBody().getString("description"));
    }

    /**
     * Negative test case for createProject method with invalid parameters.
     */
    @Test(priority = 1,
          dependsOnMethods = { "testCreateProjectWithOptionalParameters" },
          description = "Test createProject{BaseCamp} with negative case")
    public void testCreateProjectWithNegativeCase() throws Exception {

        esbRequestHeadersMap.put("Action", "urn:createProject");
        RestResponse<JSONObject> esbReponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_createProject_negative.json");

        String apiEndpoint = apiUrl + "/projects" + ".json";
        RestResponse<JSONObject> apiReponse = sendJsonRestRequest(apiEndpoint, "POST", apiRequestHeadersMap,
                "api_createProject_negative.json");

        Assert.assertEquals(esbReponse.getHttpStatusCode(), apiReponse.getHttpStatusCode());
        Assert.assertEquals(esbReponse.getHttpStatusCode(), 422);
        Assert.assertEquals(apiReponse.getHttpStatusCode(), 422);
    }

    /**
     * Positive test case for getProject method with mandatory parameters.
     */
    @Test(priority = 1,
          dependsOnMethods = "testCreateProjectWithNegativeCase",
          description = "Test getProject{BaseCamp} with Mandatory Parameters")
    public void testGetProjectWithMandatoryParameters() throws Exception {

        esbRequestHeadersMap.put("Action", "urn:getProject");
        RestResponse<JSONObject> esbReponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_getProject_mandatory.json");
        RestResponse<JSONObject> apiReponse = sendJsonRestRequest(
                apiUrl + "/projects/" + connectorProperties.getProperty("projectId") + ".json", "GET",
                apiRequestHeadersMap);
        Assert.assertEquals(esbReponse.getBody().getString("id"), apiReponse.getBody().getString("id"));
        Assert.assertEquals(esbReponse.getBody().getString("name"), apiReponse.getBody().getString("name"));
        JSONArray dock = esbReponse.getBody().getJSONArray("dock");
        for (int i = 0; i < dock.length(); i++) {
            String DockName = ((JSONObject) dock.get(i)).getString("name");
            if (DockName.equals("todoset")) {
                connectorProperties.put("todoSetId", ((JSONObject) dock.get(i)).getString("id"));
            } else if (DockName.equals("message_board")) {
                connectorProperties.put("messageBoardId", ((JSONObject) dock.get(i)).getString("id"));
            }
        }
    }

    /**
     * Negative test case for getProject method with invalid parameters.
     */
    @Test(priority = 1,
          dependsOnMethods = "testGetProjectWithMandatoryParameters",
          description = "Test getProject{BaseCamp} with negative case")
    public void testGetProjectWithNegativeCase() throws Exception {

        esbRequestHeadersMap.put("Action", "urn:getProject");
        RestResponse<JSONObject> esbReponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_getProject_negative.json");
        RestResponse<JSONObject> apiReponse = sendJsonRestRequest(apiUrl + "/projects/INVALID.json", "GET",
                apiRequestHeadersMap);
        Assert.assertEquals(esbReponse.getHttpStatusCode(), 404);
        Assert.assertEquals(esbReponse.getHttpStatusCode(), apiReponse.getHttpStatusCode());
    }

    /**
     * Positive test case for listProjects method with mandatory parameters.
     */
    @Test(priority = 1,
          dependsOnMethods = "testGetProjectWithNegativeCase",
          description = "Test listProjects{BaseCamp} with Mandatory Parameters")
    public void testListProjectsWithMandatoryParameters() throws Exception {

        esbRequestHeadersMap.put("Action", "urn:listProjects");
        String apiEndpoint = apiUrl + "/projects.json";
        RestResponse<JSONObject> esbReponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_listProjects_mandatory.json");
        RestResponse<JSONObject> apiReponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);
        JSONArray esbJsonArray = new JSONArray(esbReponse.getBody().getString("output"));
        JSONArray apiJsonArray = new JSONArray(apiReponse.getBody().getString("output"));
        Assert.assertEquals(esbJsonArray.length(), apiJsonArray.length());
        Assert.assertEquals(esbJsonArray.getJSONObject(0).get("id").toString(),
                apiJsonArray.getJSONObject(0).get("id").toString());
        Assert.assertEquals(esbJsonArray.getJSONObject(0).get("name").toString(),
                apiJsonArray.getJSONObject(0).get("name").toString());
    }

    /**
     * Positive test case for listProjects method with optional parameters.
     */
    @Test(priority = 1,
          dependsOnMethods = "testListProjectsWithMandatoryParameters",
          description = "Test listProjects{BaseCamp} with Mandatory Parameters")
    public void testListProjectsWithOptionalParameters() throws Exception {

        esbRequestHeadersMap.put("Action", "urn:listProjects");
        String apiEndpoint = apiUrl + "/projects.json?status=trashed";
        RestResponse<JSONObject> esbReponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_listProjects_optional.json");
        RestResponse<JSONObject> apiReponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);
        JSONArray esbJsonArray = new JSONArray(esbReponse.getBody().getString("output"));
        JSONArray apiJsonArray = new JSONArray(apiReponse.getBody().getString("output"));
        Assert.assertEquals(esbJsonArray.length(), apiJsonArray.length());
        Assert.assertEquals(esbJsonArray.getJSONObject(0).get("id").toString(),
                apiJsonArray.getJSONObject(0).get("id").toString());
        Assert.assertEquals(esbJsonArray.getJSONObject(0).get("name").toString(),
                apiJsonArray.getJSONObject(0).get("name").toString());
    }

    /**
     * Positive test case for updateProject
     */
    @Test(priority = 1,
          dependsOnMethods = "testListProjectsWithOptionalParameters",
          description = "Test updateProject{BaseCamp} with Mandatory Parameters")
    public void testUpdateProjectWithMandatoryParameters() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:updateProject");
        String apiEndpoint = apiUrl + "/projects/" + connectorProperties.getProperty("projectId") + ".json";
        sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_updateProject_mandatory.json");
        RestResponse<JSONObject> apiReponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(apiReponse.getBody().getString("name"), "updateProjectName");
        Assert.assertEquals(apiReponse.getBody().getString("description"), "Update sample description");
    }

    /**
     * Negative test case for updateProject
     */
    @Test(priority = 1,
          dependsOnMethods = "testUpdateProjectWithMandatoryParameters",
          description = "Test updateProject{BaseCamp} with negative case")
    public void testUpdateProjectWithNegativeCase() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:updateProject");
        String apiEndpoint = apiUrl + "/projects/" + "Invalid.json";
        RestResponse<JSONObject> esbReponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_updateProject_negative.json");
        RestResponse<JSONObject> apiReponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap,
                "api_updateProject_negative.json");
        Assert.assertEquals(esbReponse.getHttpStatusCode(), 404);
        Assert.assertEquals(esbReponse.getHttpStatusCode(), apiReponse.getHttpStatusCode());
    }

    /**
     * Positive test case for listPeople method with mandatory parameters.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testCreateProjectWithMandatoryParameters" },
          description = "Basecamp {listPeople} integration test with mandatory parameters.")
    public void testListPeopleWithMandatoryParameters() throws JSONException, IOException {

        esbRequestHeadersMap.put("Action", "urn:listPeople");
        String apiEndpoint = apiUrl + "/people.json";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_listPeople_mandatory.json");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);
        String esbResponse = esbRestResponse.getBody().get("output").toString();
        String apiResponse = apiRestResponse.getBody().get("output").toString();

        JSONArray esbJsonAry = new org.json.JSONArray(esbResponse);
        JSONArray apiJsonAry = new org.json.JSONArray(apiResponse);
        connectorProperties.put("personId", esbJsonAry.getJSONObject(0).get("id").toString());
        Assert.assertEquals(esbJsonAry.getJSONObject(0).get("id").toString(),
                apiJsonAry.getJSONObject(0).get("id").toString());
        Assert.assertEquals(esbJsonAry.getJSONObject(0).get("name").toString(),
                apiJsonAry.getJSONObject(0).get("name").toString());
        Assert.assertEquals(esbJsonAry.length(), apiJsonAry.length());
    }

    /**
     * Negative test case for listPeople method.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testListPeopleWithMandatoryParameters" },
          description = "Basecamp {listPeople} integration test with negative case.")
    public void testListPeopleWithNegativeCase() throws JSONException, IOException {

        esbRequestHeadersMap.put("Action", "urn:listPeople");
        String apiEndpoint = connectorProperties.getProperty("apiUrl") + "/-/people.json";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_listPeople_negative.json");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 404);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 404);
    }

    /**
     * Positive test case for getProjectPeople method with mandatory parameters.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testCreateProjectWithMandatoryParameters" },
          description = "Basecamp {getProjectPeople} integration test with mandatory parameters.")
    public void testGetProjectPeopleWithMandatoryParameters() throws JSONException, IOException {

        esbRequestHeadersMap.put("Action", "urn:getProjectPeople");
        String apiEndpoint = apiUrl + "projects/" + connectorProperties.getProperty("projectId") + "/people.json";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_getProjectPeople_mandatory.json");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);
        String esbResponse = esbRestResponse.getBody().get("output").toString();
        String apiResponse = apiRestResponse.getBody().get("output").toString();

        JSONArray esbJsonAry = new org.json.JSONArray(esbResponse);
        JSONArray apiJsonAry = new org.json.JSONArray(apiResponse);
        Assert.assertEquals(esbJsonAry.getJSONObject(0).get("email_address").toString(),
                apiJsonAry.getJSONObject(0).get("email_address").toString());
        Assert.assertEquals(esbJsonAry.getJSONObject(0).get("name").toString(),
                apiJsonAry.getJSONObject(0).get("name").toString());
        Assert.assertEquals(esbJsonAry.length(), apiJsonAry.length());
    }

    /**
     * Negative test case for GetProjectPeople method.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testListPeopleWithMandatoryParameters" },
          description = "Basecamp {getProjectPeople} integration test with negative case.")
    public void testGetProjectPeopleWithNegativeCase() throws JSONException, IOException {

        esbRequestHeadersMap.put("Action", "urn:getProjectPeople");
        String apiEndpoint = apiUrl + "projects/Invalid/people.json";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_getProjectPeople_negative.json");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 404);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 404);
    }

    /**
     * Positive test case for updateProjectPeople method .
     */
    @Test(priority = 2,
          dependsOnMethods = { "testCreateProjectWithMandatoryParameters" },
          description = "Basecamp {updateProjectPeople} integration test with positive case")
    public void testUpdateProjectPeopleWithMandatoryParameters() throws JSONException, IOException {

        esbRequestHeadersMap.put("Action", "urn:updateProjectPeople");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_updateProjectPeople_mandatory.json");
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertNotNull(esbRestResponse.getBody().getJSONArray("granted"));
    }

    /**
     * Negative test case for updateProjectPeople method.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testUpdateProjectPeopleWithMandatoryParameters" },
          description = "Basecamp {updateProjectPeople} integration test with negative case.")
    public void testUpdateProjectPeopleWithNegativeCase() throws JSONException, IOException {

        esbRequestHeadersMap.put("Action", "urn:updateProjectPeople");
        String apiEndpoint = apiUrl + "projects/Invalid/people/users.json";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_updateProjectPeople_negative.json");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndpoint, "PUT", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 404);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 404);
    }

    /**
     * Positive test case for createTodoLists method with mandatory parameters.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testGetProjectWithMandatoryParameters" },
          description = "Basecamp {createTodoLists} integration test with mandatory parameters.")
    public void testCreateTodoListsWithMandatoryParameters() throws IOException, JSONException {
        esbRequestHeadersMap.put("Action", "urn:createTodoLists");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_createTodoLists_mandatory.json");
        connectorProperties.setProperty("todoListId", esbRestResponse.getBody().getString("id"));
        String apiEndPoint = apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/todolists/"
                + connectorProperties.getProperty("todoListId") + ".json";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 201);
        Assert.assertEquals(esbRestResponse.getBody().getString("name").toString(),
                connectorProperties.getProperty("name"));
        Assert.assertEquals(esbRestResponse.getBody().getString("created_at").toString(),
                apiRestResponse.getBody().getString("created_at").toString());
        Assert.assertEquals(esbRestResponse.getBody().getString("type").toString(), "Todolist");
    }

    /**
     * Positive test case for createTodoLists method with optional parameters.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testCreateTodoListsWithMandatoryParameters" },
          description = "Basecamp {createTodoLists} integration test with optional parameters.")
    public void testCreateTodoListsWithOptionalParameters() throws IOException, JSONException {
        esbRequestHeadersMap.put("Action", "urn:createTodoLists");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_createTodoLists_optional.json");
        connectorProperties.setProperty("todoListOptionalId", esbRestResponse.getBody().getString("id"));
        String apiEndPoint = apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/todolists/"
                + connectorProperties.getProperty("todoListOptionalId") + ".json";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 201);
        Assert.assertEquals(esbRestResponse.getBody().getString("created_at").toString(),
                apiRestResponse.getBody().getString("created_at").toString());
        Assert.assertNotNull(esbRestResponse.getBody().getString("description").toString());
        Assert.assertEquals(esbRestResponse.getBody().getString("description").toString(),
                apiRestResponse.getBody().getString("description").toString());
    }

    /**
     * Negative test case for createTodoLists method.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testCreateTodoListsWithOptionalParameters" },
          description = "Basecamp {createTodoLists} integration test case for negative case.")
    public void testCreateTodoListsWithNegativeCase() throws IOException, JSONException {
        esbRequestHeadersMap.put("Action", "urn:createTodo");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_createTodoLists_negative.json");
        String apiEndPoint = apiUrl + "/buckets/" + connectorProperties.getProperty("projectId")
                + "/todosets/Invalid/todolists.json";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
                "api_createTodoLists_negative.json");
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 404);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 404);
    }

    /**
     * Positive test case for getTodoLists method with mandatory parameters.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testCreateTodoListsWithNegativeCase" },
          description = "Basecamp {getTodoLists} integration test with mandatory parameters.")
    public void testGetTodoListsWithMandatoryParameters() throws IOException, JSONException {
        esbRequestHeadersMap.put("Action", "urn:getTodoLists");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_getTodoLists_mandatory.json");
        String apiEndPoint = apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/todolists/"
                + connectorProperties.getProperty("todoListId") + ".json";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getBody().get("name").toString(),
                apiRestResponse.getBody().get("name").toString());
        Assert.assertEquals(esbRestResponse.getBody().get("created_at").toString(),
                apiRestResponse.getBody().get("created_at").toString());
    }

    /**
     * Negative test case for getTodoLists method.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testGetTodoListsWithMandatoryParameters" },
          description = "Basecamp {getTodoLists} integration test for Negative case.")
    public void testGetTodoListsWithNegativeCase() throws IOException, JSONException {
        esbRequestHeadersMap.put("Action", "urn:getTodoLists");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_getTodoLists_negative.json");
        String apiEndPoint =
                apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/todolists/Invalid.json";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 404);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 404);
    }

    /**
     * Positive test case for updateTodoLists method with mandatory parameters.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testCreateTodoListsWithNegativeCase" },
          description = "Basecamp {updateTodoLists} integration test with mandatory parameters.")
    public void testUpdateTodoListsWithMandatoryParameters() throws IOException, JSONException {
        esbRequestHeadersMap.put("Action", "urn:updateTodoLists");
        sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_updateTodoLists_mandatory.json");
        String apiEndPoint = apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/todolists/"
                + connectorProperties.getProperty("todoListId") + ".json";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(apiRestResponse.getBody().get("name").toString(), "Update Name");
        Assert.assertEquals(apiRestResponse.getBody().get("description").toString(), "Update description");
    }

    /**
     * Negative test case for updateTodoLists method.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testGetTodoListsWithMandatoryParameters" },
          description = "Basecamp {updateTodoLists} integration test for Negative case.")
    public void testUpdateTodoListsWithNegativeCase() throws IOException, JSONException {
        esbRequestHeadersMap.put("Action", "urn:updateTodoLists");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_updateTodoLists_negative.json");
        String apiEndPoint =
                apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/todolists/Invalid.json";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "PUT", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 404);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 404);
    }

    /**
     * Positive test case for listTodoLists method with mandatory parameters.
     */
    @Test(priority = 1,
          dependsOnMethods = "testCreateTodoListsWithOptionalParameters",
          description = "Test listTodoLists{BaseCamp} with Mandatory Parameters")
    public void testListTodoListsWithMandatoryParameters() throws Exception {

        esbRequestHeadersMap.put("Action", "urn:listTodoLists");
        String apiEndpoint =
                apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/todosets/" + connectorProperties
                        .getProperty("todoSetId") + "/todolists.json";
        RestResponse<JSONObject> esbReponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_listTodoLists_mandatory.json");
        RestResponse<JSONObject> apiReponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);
        JSONArray esbJsonArray = new JSONArray(esbReponse.getBody().getString("output"));
        JSONArray apiJsonArray = new JSONArray(apiReponse.getBody().getString("output"));
        Assert.assertEquals(esbReponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbJsonArray.length(), apiJsonArray.length());
        Assert.assertEquals(esbJsonArray.getJSONObject(0).get("id").toString(),
                apiJsonArray.getJSONObject(0).get("id").toString());
        Assert.assertEquals(esbJsonArray.getJSONObject(0).get("name").toString(),
                apiJsonArray.getJSONObject(0).get("name").toString());

    }

    /**
     * Positive test case for listTodoLists method with optional parameters.
     */
    @Test(priority = 1,
          dependsOnMethods = { "testListTodoListsWithMandatoryParameters" },
          description = "Test listTodoLists{BaseCamp} with optional parameters")
    public void testListTodoListsWithOptionalParameters() throws Exception {

        esbRequestHeadersMap.put("Action", "urn:listTodoLists");
        RestResponse<JSONObject> esbReponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_listTodoLists_optional.json");
        String apiEndpoint =
                apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/todosets/" + connectorProperties
                        .getProperty("todoSetId") + "/todolists.json?status=" + connectorProperties
                        .getProperty("status");
        RestResponse<JSONObject> apiReponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);
        JSONArray esbJsonArray = new JSONArray(esbReponse.getBody().getString("output"));
        JSONArray apiJsonArray = new JSONArray(apiReponse.getBody().getString("output"));
        Assert.assertEquals(esbReponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbJsonArray.length(), apiJsonArray.length());
        Assert.assertEquals(esbJsonArray.getJSONObject(0).get("id").toString(),
                apiJsonArray.getJSONObject(0).get("id").toString());
        Assert.assertEquals(esbJsonArray.getJSONObject(0).get("name").toString(),
                apiJsonArray.getJSONObject(0).get("name").toString());
    }

    /**
     * Negative test case for listTodoLists method with invalid parameters.
     */
    @Test(priority = 1,
          dependsOnMethods = { "testListTodoListsWithOptionalParameters" },
          description = "Test listTodoLists{BaseCamp} with negative case")
    public void testListTodoListsWithNegativeCase() throws Exception {

        esbRequestHeadersMap.put("Action", "urn:listTodoLists");
        RestResponse<JSONObject> esbReponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_listTodoLists_negative.json");
        String apiEndpoint = apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/todosets/INVALID"
                + "/todolists.json";
        RestResponse<JSONObject> apiReponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbReponse.getHttpStatusCode(), 404);
        Assert.assertEquals(esbReponse.getHttpStatusCode(), apiReponse.getHttpStatusCode());
    }

    /**
     * Positive test case for createMessage method with mandatory parameters.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testListTodoListsWithNegativeCase" },
          description = "Basecamp {createMessage} integration test with mandatory parameters.")
    public void testCreateMessageMandatoryParameters() throws JSONException, IOException {

        esbRequestHeadersMap.put("Action", "urn:createMessage");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_createMessage_mandatory.json");
        connectorProperties.put("messageId", esbRestResponse.getBody().get("id").toString());
        String apiEndpoint =
                apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/messages/" + connectorProperties
                        .getProperty("messageId") + ".json";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 201);
        Assert.assertEquals(apiRestResponse.getBody().get("id").toString(),
                connectorProperties.getProperty("messageId").toString());
        Assert.assertEquals(apiRestResponse.getBody().get("subject").toString(),
                connectorProperties.getProperty("subject").toString());
    }

    /**
     * Positive test case for createMessage method with optional parameters.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testCreateMessageMandatoryParameters" },
          description = "Basecamp {createMessage} integration test with optional parameters.")
    public void testCreateMessageOptionalParameters() throws JSONException, IOException {

        esbRequestHeadersMap.put("Action", "urn:createMessage");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_createMessage_optional.json");
        connectorProperties.put("messageId", esbRestResponse.getBody().get("id").toString());
        String apiEndpoint =
                apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/messages/" + connectorProperties
                        .getProperty("messageId") + ".json";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 201);
        Assert.assertEquals(apiRestResponse.getBody().get("id").toString(),
                connectorProperties.getProperty("messageId").toString());
        Assert.assertEquals(apiRestResponse.getBody().get("content").toString(),
                connectorProperties.getProperty("content").toString());
    }

    /**
     * Negative test case for createMessage method.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testCreateMessageOptionalParameters" },
          description = "Basecamp {createMessage} integration test negative case.")
    public void testCreateMessageNegativeCase() throws JSONException, IOException {

        esbRequestHeadersMap.put("Action", "urn:createMessage");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_createMessage_negative.json");
        String apiEndpoint = apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/message_boards/"
                + "INVALID/messages.json";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndpoint, "PUT", apiRequestHeadersMap,
                "api_createMessage_negative.json");
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 404);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), apiRestResponse.getHttpStatusCode());
    }

    /**
     * Positive test case for getMessage method with mandatory parameters.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testCreateMessageNegativeCase" },
          description = "Basecamp {getMessage} integration test with mandatory parameters.")
    public void testGetMessageWithMandatoryParameters() throws JSONException, IOException {

        esbRequestHeadersMap.put("Action", "urn:getMessage");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_getMessage_mandatory.json");
        String apiEndpoint =
                apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/messages/" + connectorProperties
                        .getProperty("messageId") + ".json";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(apiRestResponse.getBody().get("id").toString(),
                esbRestResponse.getBody().get("id").toString());
        Assert.assertEquals(apiRestResponse.getBody().get("created_at").toString(),
                esbRestResponse.getBody().get("created_at").toString());
    }

    /**
     * Negative test case for getMessage method
     */
    @Test(priority = 2,
          dependsOnMethods = { "testGetMessageWithMandatoryParameters" },
          description = "Basecamp {getMessage} integration test case for negative case.")
    public void testGetMessageWithNegativeCase() throws JSONException, IOException {

        esbRequestHeadersMap.put("Action", "urn:getMessage");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_getMessage_negative.json");
        String apiEndpoint =
                apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/messages/INVALID.json";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 404);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 404);
    }

    /**
     * Positive test case for updateMessage method with mandatory parameters.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testCreateMessageNegativeCase" },
          description = "Basecamp {updateMessage} integration test with mandatory parameters.")
    public void testUpdateMessageWithMandatoryParameters() throws JSONException, IOException {

        esbRequestHeadersMap.put("Action", "urn:updateMessage");
        sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_updateMessage_mandatory.json");
        String apiEndpoint =
                apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/messages/" + connectorProperties
                        .getProperty("messageId") + ".json";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(apiRestResponse.getBody().get("subject").toString(), "Update Message");
        Assert.assertEquals(apiRestResponse.getBody().get("content").toString(), "Update Context");
    }

    /**
     * Negative test case for updateMessage method
     */
    @Test(priority = 2,
          dependsOnMethods = { "testUpdateMessageWithMandatoryParameters" },
          description = "Basecamp {updateMessage} integration test case for negative case.")
    public void testUpdateMessageWithNegativeCase() throws JSONException, IOException {

        esbRequestHeadersMap.put("Action", "urn:updateMessage");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_updateMessage_negative.json");
        String apiEndpoint =
                apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/messages/Invalid.json";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndpoint, "PUT", apiRequestHeadersMap,
                "api_updateMessage_negative.json");
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 404);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 404);
    }

    /**
     * Positive test case for createTodo method with mandatory parameters.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testGetMessageWithNegativeCase" },
          description = "Basecamp {createTodo} integration test with mandatory parameters.")
    public void testCreateTodoWithMandatoryParameters() throws IOException, JSONException {

        esbRequestHeadersMap.put("Action", "urn:createTodo");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_createTodo_mandatory.json");

        connectorProperties.setProperty("todoId", esbRestResponse.getBody().getString("id"));

        String apiEndPoint =
                apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/todos/" + connectorProperties
                        .getProperty("todoId") + ".json";

        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);

        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 201);
        Assert.assertEquals(esbRestResponse.getBody().getString("content").toString(),
                apiRestResponse.getBody().getString("content").toString());
        Assert.assertEquals(esbRestResponse.getBody().getString("created_at").toString(),
                apiRestResponse.getBody().getString("created_at").toString());
        Assert.assertEquals(esbRestResponse.getBody().getString("type").toString(), "Todo");
    }

    /**
     * Positive test case for createTodo method with optional parameters.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testCreateTodoWithMandatoryParameters" },
          description = "Basecamp {createTodo} integration test with optional parameters.")
    public void testCreateTodoWithOptionalParameters() throws IOException, JSONException {

        esbRequestHeadersMap.put("Action", "urn:createTodo");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_createTodo_optional.json");

        connectorProperties.setProperty("todoOptionalId", esbRestResponse.getBody().getString("id"));

        String apiEndPoint =
                apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/todos/" + connectorProperties
                        .getProperty("todoOptionalId") + ".json";

        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);

        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 201);
        Assert.assertEquals(esbRestResponse.getBody().getString("content").toString(),
                apiRestResponse.getBody().getString("content").toString());
        Assert.assertNotNull(esbRestResponse.getBody().getString("due_on").toString());
        Assert.assertEquals(esbRestResponse.getBody().getString("due_on").toString(),
                apiRestResponse.getBody().getString("due_on").toString());
    }

    /**
     * Negative test case for createTodo method.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testCreateTodoWithOptionalParameters" },
          description = "Basecamp {createTodo} integration test case for negative case.")
    public void testCreateTodoWithNegativeCase() throws IOException, JSONException {

        esbRequestHeadersMap.put("Action", "urn:createTodo");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_createTodo_negative.json");

        String apiEndPoint = apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/todolists/"
                + connectorProperties.getProperty("todoListId") + "/todos.json";

        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
                "api_createTodo_negative.json");

        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 422);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 422);
    }

    /**
     * Positive test case for getTodo method with mandatory parameters.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testCreateTodoWithNegativeCase" },
          description = "Basecamp {getTodo} integration test with mandatory parameters.")
    public void testGetTodoWithMandatoryParameters() throws IOException, JSONException {

        esbRequestHeadersMap.put("Action", "urn:getTodo");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_getTodo_mandatory.json");

        String apiEndPoint =
                apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/todos/" + connectorProperties
                        .getProperty("todoId") + ".json";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);

        Assert.assertEquals(esbRestResponse.getBody().get("content").toString(),
                apiRestResponse.getBody().get("content").toString());
        Assert.assertEquals(esbRestResponse.getBody().get("created_at").toString(),
                apiRestResponse.getBody().get("created_at").toString());
    }

    /**
     * Negative test case for getTodo method.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testGetTodoWithMandatoryParameters" },
          description = "Basecamp {getTodo} integration test for Negative case.")
    public void testGetTodoWithNegativeCase() throws IOException, JSONException {

        esbRequestHeadersMap.put("Action", "urn:getTodo");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_getTodo_negative.json");

        String apiEndPoint = apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/todos/-.json";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);

        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 404);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 404);
    }

    /**
     * Positive test case for updateTodo method with mandatory parameters.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testCreateTodoWithNegativeCase" },
          description = "Basecamp {updateTodo} integration test with mandatory parameters.")
    public void testUpdateTodoWithMandatoryParameters() throws IOException, JSONException {
        esbRequestHeadersMap.put("Action", "urn:updateTodo");
        sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_updateTodo_mandatory.json");
        String apiEndPoint =
                apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/todos/" + connectorProperties
                        .getProperty("todoId") + ".json";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(apiRestResponse.getBody().get("content").toString(), "updateCreateTodoOptionalContent");
    }

    /**
     * Negative test case for updateTodo method.
     */
    @Test(priority = 2,
          dependsOnMethods = { "testGetTodoWithMandatoryParameters" },
          description = "Basecamp {updateTodo} integration test for Negative case.")
    public void testUpdateTodoWithNegativeCase() throws IOException, JSONException {
        esbRequestHeadersMap.put("Action", "urn:updateTodo");
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_updateTodo_negative.json");
        String apiEndPoint =
                apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/todos/Invalid.json";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "PUT", apiRequestHeadersMap,
                "api_updateTodo_negative.json");
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 404);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 404);
    }

    /**
     * Positive test case for getEvents method with mandatory parameters.
     */
    @Test(priority = 1,
          dependsOnMethods = { "testCreateProjectWithMandatoryParameters", "testCreateTodoWithMandatoryParameters" },
          description = "Test getEvents{BaseCamp} with Mandatory Parameters")
    public void testGetEventsWithMandatoryParameters() throws Exception {

        esbRequestHeadersMap.put("Action", "urn:getEvents");
        RestResponse<JSONObject> esbReponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_getEvents_mandatory.json");
        String apiEndpoint = apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/recordings/"
                + connectorProperties.getProperty("todoId") + "/events.json";
        RestResponse<JSONObject> apiReponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);
        JSONArray esbJsonArray = new JSONArray(esbReponse.getBody().getString("output"));
        JSONArray apiJsonArray = new JSONArray(apiReponse.getBody().getString("output"));
        Assert.assertEquals(esbReponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbJsonArray.length(), apiJsonArray.length());
        Assert.assertEquals(esbJsonArray.getJSONObject(0).get("id").toString(),
                apiJsonArray.getJSONObject(0).get("id").toString());
        Assert.assertEquals(esbJsonArray.getJSONObject(0).get("created_at").toString(),
                apiJsonArray.getJSONObject(0).get("created_at").toString());
    }

    /**
     * Negative test case for getEvents method.
     */
    @Test(priority = 1,
          dependsOnMethods = { "testGetEventsWithMandatoryParameters" },
          description = "Test getEvents{BaseCamp} with Negative case")
    public void testGetEventsWithNegativeCase() throws Exception {

        esbRequestHeadersMap.put("Action", "urn:getEvents");
        RestResponse<JSONObject> esbReponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_getEvents_negative.json");
        String apiEndpoint =
                apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/recordings/INVALID/events.json";

        RestResponse<JSONObject> apiReponse = sendJsonRestRequest(apiEndpoint, "GET", apiRequestHeadersMap);

        Assert.assertEquals(esbReponse.getHttpStatusCode(), 404);
        Assert.assertEquals(apiReponse.getHttpStatusCode(), 404);
    }

    /**
     * Positive test case for createComment method with mandatory parameters.
     *
     * @throws Exception
     */
    @Test(priority = 1,
          dependsOnMethods = { "testGetEventsWithNegativeCase" },
          description = "Test createComment{BaseCamp} with Mandatory Parameters")
    public void testCreateCommentWithMandatoryParameters() throws Exception {

        esbRequestHeadersMap.put("Action", "urn:createComment");
        RestResponse<JSONObject> esbReponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_createComment_mandatory.json");
        Assert.assertEquals(esbReponse.getHttpStatusCode(), 201);
        RestResponse<JSONObject> apiReponse = sendJsonRestRequest(
                apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/comments/" + esbReponse
                        .getBody().getString("id") + ".json", "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbReponse.getHttpStatusCode(), 201);
        Assert.assertEquals(connectorProperties.getProperty("content"), esbReponse.getBody().getString("content"));
        Assert.assertEquals(apiReponse.getBody().getString("content"), esbReponse.getBody().getString("content"));
        Assert.assertEquals(apiReponse.getBody().getString("created_at"), esbReponse.getBody().getString("created_at"));
    }

    /**
     * Negative test case for createComment method with invalid parameters.
     *
     * @throws Exception
     */
    @Test(priority = 1,
          dependsOnMethods = { "testCreateCommentWithMandatoryParameters" },
          description = "Test createComment{BaseCamp} with negative case")
    public void testCreateCommentWithNegativeCase() throws Exception {

        esbRequestHeadersMap.put("Action", "urn:createComment");
        RestResponse<JSONObject> esbReponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
                "esb_createComment_negative.json");

        RestResponse<JSONObject> apiReponse = sendJsonRestRequest(
                apiUrl + "/buckets/" + connectorProperties.getProperty("projectId") + "/recordings/"
                        + connectorProperties.getProperty("todoId") + "/comments.json", "POST", apiRequestHeadersMap,
                "api_createComment_negative.json");

        Assert.assertEquals(esbReponse.getHttpStatusCode(), apiReponse.getHttpStatusCode());
        Assert.assertEquals(esbReponse.getHttpStatusCode(), 422);
        Assert.assertEquals(apiReponse.getHttpStatusCode(), 422);
    }
}
