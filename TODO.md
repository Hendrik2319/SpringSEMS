# TODO

### this project in general
* **[started]** write README.md
* **[solved]** push to github
* **[solved]** externalize some config (e.q. MySQL url/password/user)
* rebuild application in pure Spring (without Spring Boot)

### REST interface
* **[solved]** move REST api in subpath (e.q. "api" or "rest")

### database
* define relationships (entity relationship model)  
    * **[created]** unidirectional relations
    * **[solved]** extend to bidirectional relations

### user interface
* **[solved]** "create" tasks
* **[solved]** "delete" tasks (in "all" tables)
* "update" tasks
    * **[discarded]** e.q. combined with "add" tasks (a select above \["Add new", "existing item 1", "existing item 2", ... \])
    * **[solved]** or in separate views, called from "all" tables
* **[solved]** switch task endpoints from "GET" to "POST"
* **[solved]** parameterize "redirect"s at end of tasks
* **[solved]** make tab panel code more compact
* search in thymeleaf docs for a more sophisticated way to build (dynamic) views
* add authentication and authorization features (Spring Security)

### IDE
* get CoPilot
