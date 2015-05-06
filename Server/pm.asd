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
               (:file "api")
               (:file "master")
               (:file "master-clack")
               (:file "worker")
               (:file "common")
               (:file "authenticator")
               (:file "database")))

