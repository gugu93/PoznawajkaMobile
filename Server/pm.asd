;;;; pm.asd

(asdf:defsystem #:pm
  :description "Describe pm here"
  :author "Your Name <your.name@example.com>"
  :license "Specify license here"
  :serial t
  :depends-on (:ningle
               :cl-mysql
               :split-sequence
               :cl-json)
  :components ((:file "package")
               (:file "pm")
               (:file "master")
               (:file "worker")
               (:file "api-listener")
               (:file "api-listener-router")
               (:file "api-master")
               (:file "common")
               (:file "authenticator")
               (:file "database")))

