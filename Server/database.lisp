(in-package #:pm.db)

(defclass database-interface ()
  ((db-handle :accessor db-handle)))

(defgeneric db-connect (database-interface hostname database username password))
(defmethod db-connect ((interface database-interface)
                       (hostname string)
                       (database string)
                       (username string)
                       (password string))
  "Connects to selected MySQL database"
  (setf (db-handle interface)
        (connect (list hostname database username password)
                 :database-type :mysql)))
        
(defgeneric add-item (database-interface item-to-add))
(defmethod add-item ((interface database-interface)
                (item database-item))
  "Add database-item element to database"
  (update-records-from-instance item
                                :database (db-handle interface)))
  
(defgeneric delete-item (database-interface item-to-delete))
(defmethod delete-item ((interface database-interface)
                        (item database-item))
  "Delete database-item element from database"
  (delete-instance-records item
                           :database (db-handle interface)))

(defgeneric list-items (database-interface item-class-to-list))
(defmethod list-items ((interface database-interface)
                       (item-class symbol))
  (select item-class))


;; (defparameter *db* (clsql-sys:connect '("gen2.org" "pm" "pm-worker" "5xK1xWKudjT3DXbLVzuMCH5wEHhcLnPdJRtGsoyoccEhc2aSnb8WPfKVEnBijs7y") :database-type :mysql))

;; (clsql:update-records-from-instance
;;    (make-instance 'post
;; II  :title "1st!"
;; II  :body "tresc postu"))
;; (defparameter *db*
;;   (clsql-sys:connect '("localhost" "pg_db" "pg_db" "ble")
;; II     :database-type :postgresql :if-exists :old))
