This is the stub README.txt for the "pm" project.

## Dependencies and prerequirements
* Modern Linux system
* Steel Bank Common Lisp (tested on version 1.2.11)
* Working QuickLisp distribution

## Prepare hosts
### Master
1. Clone "pm" into QuickLisp `local-projects` directory.
2. Open SBCL REPL
3. Using quicklisp, quickload project `:pm`
4. Enter into package `pm.master`
5. Create `master` class instance, set initargs :listen-port to desired
port (or leave default). Initarg `:routed-package` set to `pm.master`
6. Using `add-worker` method add workers.

### Worker
1. Clone "pm" into QuickLisp `local-projects` directory.
2. Open SBCL REPL
3. Using quicklisp, quickload project `:pm`
4. Enter into package `pm.worker`
5. Create `worker` class instance, set initargs :listen-port to desired
port (or leave default). Initarg `:routed-package` set to `pm.worker`


