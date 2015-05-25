(in-package #:pm.worker)

(defmacro if-request-valid (arguments-list &body if-valid)
  "Request validation macro. Takes THIS, USERNAME and CHECKSUM from current lexical context and verifies validity according to ARGUMENTS-LIST valid. If true, evaluates IF-VALID. Else returns p-list (:type verify :result error)"
  `(if (pm.util:request-valid-p this username checksum ,arguments-list)
       (progn
         ,@if-valid)
       (list :type 'verify :result 'error)))

(defclass worker (database-interface pm.api-listener:api-listener)
  ((pm.api-listener:listen-port :type interger
                                :initarg :listen-port
                                :initform 1121
                                :accessor listen-port)
  (pm.api-listener:routed-package :accessor routed-package
                                  :initarg :routed-package
                                  :initform :pm.worker
                                  :type keyword)))

;; everything has to return plist

(defmethod get-api-describe ((this worker) symbol-name)
  "Describe supplied symbol"
  `(:type describe :description
          ,(cl-ppcre:regex-replace-all "\\n"
                                       (with-output-to-string (stream)
                                         (describe (intern (string-upcase symbol-name) 'pm.worker)
                                                   stream))
                                       " ")))

(defmethod get-version ((this worker))
  '(:type version :version 0.0.4))

(defmethod get-echo ((this worker) (message string))
  `(:type echo :message ,message))

(defmethod get-list-users ((this worker))
  `(:type get-users-list :user-list ,(mapcar (lambda (user)
                                               (list (id user) (username user)))
                                             (list-items this 'user))))

(defmethod get-test-query ((this worker)
                           (argument string)
                           &key username checksum)
  "Test method for veryfying querires"
  (if-request-valid (list "test-query" argument)
    '(:type verify :result ok)))

(defmethod post-user ((this worker)
                      &key
                        username
                        password
                        first-name
                        last-name
                        sex
                        status
                        preferences
                        description
                        age)
  "Create new user according to supplied parameters."
  (assert (and username password first-name last-name
               sex status preferences description age))
  (let* ((user (make-instance 'user :username username :password password))
         (user-id (car (add-item this user)))
         (user-info (make-instance 'user-info
                                   :user-id user-id
                                   :first-name first-name
                                   :last-name last-name
                                   :sex (parse-integer sex)
                                   :status (parse-integer status)
                                   :preferences preferences
                                   :description description
                                   :age (parse-integer age)))
         (user-info-id (car (add-item this user-info))))
    `(:type post-user :user-id ,user-id :user-info-id ,user-info-id)))

(defmethod get-user-info ((this worker) user-id
                          &key username checksum)
  "Get informations about specific user ID"
  (if-request-valid (list "get-user-info" user-id)
    (let ((user-info (car (find-items this 'user-info 'user-id (parse-integer user-id) :test-fun #'=))))
      `(:type get-user-info
              :first-name ,(first-name user-info)
              :last-name ,(last-name user-info)
              :age ,(age user-info)))))

(defmethod post-user-delete ((this worker) user-id
                             &key username checksum)
  "Delete user and user-info with USER-ID"
  (assert (and username checksum user-id))
  (if-request-valid (list "post-user-delete" user-id)
    (let ((user (car (find-items this 'user 'id user-id)))
          (user-info (car (find-items this 'user-info 'user-id user-id))))
      (when (and (delete-item this user-info)
                 (delete-item this user))
        `(:type post-user-delete :result ok)))))

(defmethod post-friendship ((this worker) user-id friend-id
                            &key username checksum)
  "Create new friendship, from USER-ID to FRIEND-ID"
  (assert (and username checksum user-id friend-id))
  (if-request-valid (list "post-friendship" user-id friend-id)
    (let* ((friendship (make-instance 'friendship :id user-id :friend-id friend-id))
           (friendship-id (car (add-item this friendship))))
      `(:type post-friendship :id ,friendship-id))))

(defmethod post-friendship-delete ((this worker) friendship-id
                                   &key username checksum)
  "Delete friendship with id FRIENDSHIP-ID"
  (assert (and username checksum friendship-id))(in-package #:pm.worker)
  (if-request-valid (list "post-user-delete" friendship-id)
    (let ((friendship (car (find-items this 'friendship 'id friendship-id))))
      (delete-item this friendship)
      `(:type post-friendship-delete :result ok))))

(defmethod get-friendship-list ((this worker) user-id
                                &key username checksum)
  "Get list of USER-ID friends."
  (assert (and username checksum))
  (if-request-valid (list "get-friendship-list" user-id)
    `(:type get-friendship-list :friendship-list ,(mapcar #'id
                                                          (find-items this
                                                                      'friendship
                                                                      'user-id
                                                                      (parse-integer user-id)
                                                                      :test-fun #'=)))))
  
      
      
  

                          
