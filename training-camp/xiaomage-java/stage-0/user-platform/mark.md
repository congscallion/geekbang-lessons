#Step 2. Install the Jolokia Agent on Your Tomcat Server

1. Download the latest version of the Jolokia war file from: https://jolokia.org/download.html.
2. Rename the file from jolokia-war-X.X.X.war to jolokia.war.
3. Copy the jolokia.war file to ${TOMCAT_HOME}/webapps.
4. Add jolokia as role in tomcat-users.xml (mandatory for Jolokia 1.6 or later)
5. Start or restart your Tomcat server.
6. Verify the Jolokia agent installation by accessing this URL: http://<address>:<port>/jolokia/version.

The result looks similar to this:

```json
{"request":{"type":"version"},"value":{"agent":"1.3.7","protocol":"7.2","config":{"maxCollectionSize":"0","agentId":"10.152.24.99-29844-172f5788-servlet","debug":"false","agentType":"servlet","serializeException":"false","detectorOptions":"{}","dispatcherClasses":"org.jolokia.jsr160.Jsr160RequestDispatcher","maxDepth":"15","discoveryEnabled":"false","canonicalNaming":"true","historyMaxEntries":"10","includeStackTrace":"true","maxObjects":"0","debugMaxEntries":"100"},"info":{"product":"tomcat","vendor":"Apache","version":"8.5.23"}},"timestamp":1509955465,"status":200}
```



### 参考项目

https://github.com/wwfdoink/jolokia-web.git



```xml
 <servlet>
    <servlet-name>jolokia-agent</servlet-name>
    <servlet-class>org.jolokia.http.AgentServlet</servlet-class>
    <init-param>
        <description>
            Class names (comma separated) of RequestDispatcher used in addition
            to the LocalRequestDispatcher
        </description>
        <param-name>dispatcherClasses</param-name>
        <param-value>org.jolokia.jsr160.Jsr160RequestDispatcher</param-value>
    </init-param>
  </servlet>
```

