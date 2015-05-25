;;;; package.lisp

(defpackage #:pm
  (:use #:cl)
  (:export #:pm-main))

(defpackage #:pm.test
  (:use #:cl))

(defpackage #:pm.master
  (:use #:cl)
  (:export #:master
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
           #:start
           #:stop))

(defpackage #:pm.worker
  (:use #:cl
        #:clsql
        #:clsql-sys
        #:ningle)
  (:export #:worker
           #:add-item
           #:delete-item
           #:list-items
           #:find-item
           #:database-interface
           #:db-handle
           ;; API functions
           #:post-user
           #:get-api-describe
           #:get-version
           #:get-echo
           #:get-test-query
           #:get-list-users
           #:get-user-info
           #:get-test-query))

(defpackage #:pm.api-master
  (:use #:cl)
  (:export #:version
           #:echo))

