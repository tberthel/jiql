Google App Engine for Java only supports JDO and JPA as standard protocols for data persistence. However many Java programmers are used to JDBC as the preferred protocol. Applications requiring JDBC support can now turn to jiql. jiql is a JDBC wrapper of the lower level Google DataStore APIs thus providing a familiar way to utilize Google's BigTable.

So a Java application, using standard JDBC calls to the jiql JDBC client, is able to store critical data in GBT. jiql
can also be used for querying Data persisted by other protocols,
such as JDO or JPA. And jiql can be configured for remote
access via the JiqlServlet. The jiql website is located at
http://www.jiql.org