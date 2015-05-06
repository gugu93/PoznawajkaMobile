;;;; package.lisp

(defpackage #:pm
  (:use #:cl))

(defpackage #:pm.master
  (:use #:cl
        #:ningle))

(defpackage #:pm.worker
  (:use #:cl))

(defpackage #:pm.common
  (:use #:cl)
  (:export #:api-request))

(defpackage #:pm.api
  (:use #:cl)
  (:export #:version)
  )
