(in-package #:pm.worker)

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
  `(:type users-list :user-list ,(mapcar (lambda (user)
                                           (list (id user) (username user)))
                                         (list-items this 'user))))

(defmethod get-test-query ((this worker)
                           (argument string)
                           &key username checksum)
  "Test method for veryfying querires"
  (declare (optimize (debug 3)))
  (if (pm.util:request-valid-p this
                               username
                               checksum
                               (list "test-query" argument))
      '(:type verify :result ok)
      '(:type verify :result error)))

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

(defmethod get-user-info ((this worker) user-id)
  "Get informations about specific user ID"
  (let ((user-info (find-item this 'user-info 'user-id (parse-integer user-id) :test-fun #'=)))
    `(:type user-info
            :first-name ,(first-name user-info)
            :last-name ,(last-name user-info)
            :age ,(age user-info))))

    

