(in-package #:pm.worker)

(defclass database-interface ()
  ((db-handle :accessor db-handle)
   (hostname :type string
             :accessor hostname
             :initarg :hostname)
   (database :type string
             :accessor database
             :initarg :database)
   (username :type string
             :accessor username
             :initarg :username)
   (password :type string
             :accessor password
             :initarg :password)))

(defmethod connect-db ((this database-interface))
  "Connect to database"
  (setf (db-handle this)
        (connect (list (hostname this)
                       (database this)
                       (username this)
                       (password this))
                 :database-type :mysql)))

(defmethod disconnect-db ((this database-interface))
  "Disconnect from database"
  (disconnect :database (db-handle this)))

(defmethod initialize-instance :after ((this database-interface) &key)
  (connect-db this))


(defgeneric add-item (database-interface item-to-add))
(defmethod add-item ((interface database-interface)
                (item database-item))
  "Add database-item element to database"
  ;; (reconnect :database (db-handle interface))
  (update-records-from-instance item
                                :database (db-handle interface)))
  
(defgeneric delete-item (database-interface item-to-delete))
(defmethod delete-item ((interface database-interface)
                        (item database-item))
  "Delete database-item element from database"
  ;; (reconnect :database (db-handle interface))
  (delete-instance-records item
                           :database (db-handle interface)))

(defgeneric list-items (database-interface item-class-to-list))
(defmethod list-items ((interface database-interface)
                       (item-class symbol))
  "List items of specific class in database"
  (mapcar #'car
          (select item-class)))

(defgeneric find-items (database-interface item-class-to-search search-key lookup-value &key test-fun))
(defmethod find-items ((interface database-interface)
                      (item-class-to-search symbol)
                      (search-key symbol)
                      lookup-value
                      &key (test-fun #'string=))
  "Find items of type item-class-to-search, with search-key string= to lookup-value."
  ;; (reconnect :database (db-handle interface))
  (remove-if-not (lambda (item)
                   (funcall test-fun lookup-value
                            (funcall search-key item)))
                 (list-items interface item-class-to-search)))
