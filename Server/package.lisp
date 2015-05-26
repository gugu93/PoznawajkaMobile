;;;; package.lisp

(defpackage #:pm
  (:use #:cl)
  (:export #:pm-main))

(defpackage #:pm.test
  (:use #:cl))

(defpackage #:pm.master
  (:use #:cl
        #:clsql
        #:clsql-sys
        #:ningle)
  (:export #:master
           #:add-worker
           #:update-workers
           #:get-worker
           #:start
           #:stop))

(defpackage #:pm.util
  (:use #:cl)
  (:export #:request-valid-p))

(defpackage #:pm.api-listener
  (:use #:cl
        #:ningle)
  (:export #:api-listener
           #:listen-port
           #:routed-package 
           #:requests-taken
           #:start
           #:stop))

(defpackage #:pm.worker
  (:use #:cl
        #:clsql
        #:clsql-sys
        #:ningle)
  (:export #:worker
           ;; API functions
           #:post-user
           #:get-api-describe
           #:get-version
           #:get-status
           #:get-test-query
           #:get-list-users
           #:get-user-info
           #:get-test-query
           #:post-user-delete
           #:post-friendship
           #:post-friendship-delete
           #:get-friendship-list))
