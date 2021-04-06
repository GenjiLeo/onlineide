# Servlet Application

This project demonstrates the use of the [HttpServlet API](https://tomcat.apache.org/tomcat-5.5-doc/servletapi/javax/servlet/http/HttpServlet.html) and shows how to configure an [embedded Apache Tomcat](http://tomcat.apache.org/), which can simply be used through a Maven module.

Apache Tomcat provides a "pure Java" HTTP web server environment in which Java code can run.
Catalina is Tomcat's servlet container. Catalina implements the specifications for Java servlet and JavaServer Pages (JSP).

## Servlet Container (for JSPs and servlets)

The life of a JSP in a servlet container such as Tomcat.
The difference between servlets and JSP is that servlets typically embed HTML inside Java code, while JSPs embed Java code in HTML.

![Servlet container](https://upload.wikimedia.org/wikipedia/commons/4/40/JSPLife.png) 

See [this](https://en.wikipedia.org/wiki/Java_servlet#Life_cycle_of_a_servlet) for a more detailed description.
