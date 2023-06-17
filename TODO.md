# TODO

### this project in general
* **[started]** write README.md
* **[solved]** push to github
* **[solved]** externalize some config (e.q. MySQL url/password/user)

### REST interface
* **[solved]** move REST api in subpath (e.q. "api" or "rest")

### database
* define relationships (entity relationship model)  
    * **[solved]** unidirectional relations
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
    * tighter binding between Spring and Thymeleaf (like [Thymeleaf & Spring tutorial](https://www.thymeleaf.org/doc/tutorials/3.1/thymeleafspring.html))

### improvements & refactorings
* **[discarded]** change `config` into a model attribute
* **[solved]** change endpoints structure (`form/delete_address` into `form/addresses/delete`)
* **[solved]** extract endpoint methods into separate controllers for each entity
* **[solved]** convert `index.html` into a template and create endpoint
* **[solved]** combine `all_...` tables with corresponding `add ...` forms (--> one template for each entity type and `edit-view` contains inserted fragments only)
* **[solved]** in `all_...` tables:
    * _transform_ separate forms for each delete/modify button in each row
    * _into_ a single from for the whole table with multiple submit buttons with a name (`='delete'` or `='update'`) and a value (`='${entity.getID()}'`)
    * **[solved]** adapt endpoint methods: one for both (delete and update) or **[chosen]** two separate distinguished by query parameter (`delete` or `update`)

### IDE
* get CoPilot

### bigger tasks
* add authentication and authorization features (Spring Security)
* rebuild application in pure Spring (without Spring Boot)
