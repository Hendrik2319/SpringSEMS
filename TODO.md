# TODO

### this project in general
* write README.md
-> **started**

* push to github
-> **solved**

* externalize some config (e.q. MySQL url/password/user)
-> **solved**

### REST interface
* move REST api in subpath (e.q. "api" or "rest")
-> **solved**

### database
* define relationships (entity relationship model)  
    * unidirectional relations -> **created**
    * extend to bidirectional relations -> **solved**

### user interface
* "create" tasks -> **solved**
* "delete" tasks (in "all" tables) -> **solved**
* "update" tasks
    * e.q. combined with "add" tasks (a select above \["Add new", "existing item 1", "existing item 2", ... \]) -> **discarded**
    * or in separate views, called from "all" tables -> **solved**
* switch task endpoints from "GET" to "POST" -> **solved**
* parameterize "redirect"s at end of tasks -> **solved**
* make tab panel code more compact -> **solved**
* search in thymeleaf docs for a more sophisticated way to build (dynamic) views

### IDE
* get CoPilot
