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
  "Return version of the worker"
  '(:type get-version :version 0.1.0))

(defmethod get-status ((this worker))
  "Return worker status"
  (let ((requests-taken (pm.api-listener:requests-taken this)))
    (setf (pm.api-listener:requests-taken this) 0)
    `(:type get-status :status ok :requests-taken ,requests-taken)))

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
          (user-info (car (find-items this 'user-info 'user-id user-id)))) (when (and (delete-item this user-info)
                 (delete-item this user))
        `(:type post-user-delete :result ok)))))

(defmethod post-friendship ((this worker) user-id friend-id
                            &key username checksum)
  "create new friendship, from user-id to friend-id"
  (assert (and username checksum user-id friend-id))
  (if-request-valid (list "post-friendship" user-id friend-id)
    (let* ((friendship (make-instance 'friendship :id user-id :friend-id friend-id))
           (friendship-id (car (add-item this friendship))))
      `(:type post-friendship :id ,friendship-id))))

(defmethod post-friendship-delete ((this worker) friendship-id
                                   &key username checksum)
  "delete friendship with id friendship-id"
  (assert (and username checksum friendship-id))
  (if-request-valid (list "post-user-delete" friendship-id)
    (let ((friendship (car (find-items this 'friendship 'id friendship-id))))
      (delete-item this friendship)
      `(:type post-friendship-delete :result ok))))

(defmethod get-friendship-list ((this worker) user-id
                                &key username checksum)
  "get list of user-id friends."
  (assert (and username checksum))
  (if-request-valid (list "get-friendship-list" user-id)
    `(:type get-friendship-list :friendship-list ,(mapcar #'id
                                                          (find-items this
                                                                      'friendship
                                                                      'user-id
                                                                      (parse-integer user-id)
                                                                      :test-fun #'=)))))
(defmethod post-group ((this worker) name description 
                            &key username checksum)
  "Create new group, from NAME and DESCRIPTION"
  (assert (and username checksum name description))
  (if-request-valid (list "post-group" name description)
    (let* ((group (make-instance 'group :name name :description description))
           (group-id (car (add-item this group))))
      `(:type post-group :id ,group-id))))

(defmethod post-group-delete ((this worker) group-id
                                   &key username checksum)
  "Delete group with id GROUP-ID"
  (assert (and username checksum group-id))
  (if-request-valid (list "post-group-delete" group-id)
    (let ((group (car (find-items this 'group 'id group-id))))
      (delete-item this group)
      `(:type post-group-delete :result ok))))

(defmethod get-group-list ((this worker)
                                &key username checksum)
  "Get list of groups"
  (assert (and username checksum))
  (if-request-valid (list "get-group-list")
                    `(:type get-groups-list :group-list ,(mapcar (lambda (group)
                                                                   (list (id group) (name group)))
                                                                 (list-items this 'group)))))
  
(defmethod post-prov ((this worker) user-id target-user-id prov-type message
                            &key username checksum)
  "create new prov, from user-id, target-user-id, prov-type, message"
  (assert (and username checksum user-id target-user-id prov-type message))
  (if-request-valid (list "post-prov" name description)
                    (let* ((prov (make-instance 'prov
                                                :user-id user-id
                                                :target-user-id target-user-id
                                                :prov-type prov-type
                                                :message message))
                           (prov-id (car (add-item this user-prov))))
                      `(:type post-prov :id ,prov-id))))

(defmethod post-prov-delete ((this worker) prov-id
                                   &key username checksum)
  "delete prov with id prov-id"
  (assert (and username checksum prov-id))
  (if-request-valid (list "post-prov-delete" prov-id)
    (let ((prov (car (find-items this 'user-prov 'id prov-id))))
      (delete-item this prov)
      `(:type post-prov-delete :result ok))))

(defmethod get-prov-list ((this worker)
                                &key username checksum)
  "get list of provs"
  (assert (and username checksum))
  (if-request-valid (list "get-prov-list")
                    `(:type get-prov-list :prov-list ,(mapcar (lambda (prov)
                                                                 (list (id prov)))
                                                               (list-items this 'user-prov)))))
  
(defmethod post-user-photo ((this worker) user-id target-user-id user-photo-type message
                            &key username checksum)
  "create new user-photo, from user-id, target-user-id, user-photo-type, message"
  (assert (and username checksum user-id target-user-id user-photo-type message))
  (if-request-valid (list "post-user-photo" name description)
                    (let* ((user-photo (make-instance 'user-photo
                                                :user-id user-id
                                                :target-user-id target-user-id
                                                :user-photo-type user-photo-type
                                                :message message))
                           (user-photo-id (car (add-item this user-user-photo))))
                      `(:type post-user-photo :id ,user-photo-id))))

(defmethod post-user-photo-delete ((this worker) user-photo-id
                                   &key username checksum)
  "delete user-photo with id user-photo-id"
  (assert (and username checksum user-photo-id))
  (if-request-valid (list "post-user-photo-delete" user-photo-id)
    (let ((user-photo (car (find-items this 'user-user-photo 'id user-photo-id))))
      (delete-item this user-photo)
      `(:type post-user-photo-delete :result ok))))

(defmethod get-user-photo-list ((this worker) user-id
                                &key username checksum)
  "Get list of USER-ID friends."
  (assert (and username checksum user-id))
  (if-request-valid (list "get-user-photo-list" user-id)
    `(:type get-user-photo-list :user-photo-list ,(mapcar #'id
                                                          (find-items this
                                                                      'user-photo
                                                                      'user-id
                                                                      (parse-integer user-id)
                                                                      :test-fun #'=)))))

(defmethod post-user-gallery ((this worker) user-id gallery-name size
                              &key username checksum)
  "Create new user-gallery, from USER-ID to GALLERY-NAME SIZE"
  (assert (and username checksum user-id gallery-name size))
  (if-request-valid (list "post-user-gallery" user-id gallery-name size)
                    (let* ((user-gallery (make-instance 'user-gallery
                                                        :id user-id
                                                        :gallery-name gallery-name
                                                        :size size))
           (user-gallery-id (car (add-item this user-gallery))))
      `(:type post-user-gallery :id ,user-gallery-id))))

(defmethod post-user-gallery-delete ((this worker) user-gallery-id
                                   &key username checksum)
  "Delete user-gallery with id USER-GALLERY-ID"
  (assert (and username checksum user-gallery-id))
  (if-request-valid (list "post-user-gallery-delete" user-gallery-id)
    (let ((user-gallery (car (find-items this 'user-gallery 'id user-gallery-id))))
      (delete-item this user-gallery)
      `(:type post-user-gallery-delete :result ok))))

(defmethod get-user-gallery-list ((this worker) gallery-id
                                &key username checksum)
  "Get list of GALLERY-ID friends."
  (assert (and username checksum))
  (if-request-valid (list "get-user-gallery-list" gallery-id)
    `(:type get-user-gallery-list :user-gallery-list ,(mapcar #'id
                                                              (find-items this
                                                                          'user-gallery
                                                                          'gallery-id
                                                                          (parse-integer gallery-id)
                                                                          :test-fun #'=)))))

(defmethod post-user-rating ((this worker) user-id rate comment from-user-id
                            &key username checksum)
  "create new user-rating, from user-id rate comment from-user-id"
  (assert (and username checksum user-id user-id rate comment from-user-id))
  (if-request-valid (list "post-user-rating" user-id rate comment from-user-id)
                    (let* ((user-rating (make-instance 'user-rating
                                                       :id user-id
                                                       :rate rate
                                                       :comment comment
                                                       :from-user-id from-user-id))
           (user-rating-id (car (add-item this user-rating))))
      `(:type post-user-rating :id ,user-rating-id))))

(defmethod post-user-rating-delete ((this worker) user-rating-id
                                   &key username checksum)
  "delete user-rating with id user-rating-id"
  (assert (and username checksum user-rating-id))
  (if-request-valid (list "post-user-delete" user-rating-id)
    (let ((user-rating (car (find-items this 'user-rating 'id user-rating-id))))
      (delete-item this user-rating)
      `(:type post-user-rating-delete :result ok))))

(defmethod get-user-rating-list ((this worker) user-id
                                &key username checksum)
  "get list of user-id friends."
  (assert (and username checksum))
  (if-request-valid (list "get-user-rating-list" user-id)
    `(:type get-user-rating-list :user-rating-list ,(mapcar #'id
                                                          (find-items this
                                                                      'user-rating
                                                                      'user-id
                                                                      (parse-integer user-id)
                                                                      :test-fun #'=)))))
