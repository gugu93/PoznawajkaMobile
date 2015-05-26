(in-package #:pm.master)

(defmacro if-request-valid (arguments-list &body if-valid)
  "Request validation macro. Takes THIS, USERNAME and CHECKSUM from current lexical context and verifies validity according to ARGUMENTS-LIST valid. If true, evaluates IF-VALID. Else returns p-list (:type verify :result error)"
  `(if (pm.util:request-valid-p this username checksum ,arguments-list)
       (progn
         ,@if-valid)
       (list :type 'verify :result 'error)))

(defclass master (pm.api-listener:api-listener)
  ((workers :type list
            :initform '()
            :accessor workers)
   (pm.api-listener:listen-port :type interger
                                :initarg :listen-port
                                :initform 1120
                                :accessor listen-port)
   (pm.api-listener:routed-package :accessor routed-package
                                   :initarg :routed-package
                                   :initform :pm.master
                                   :type keyword)))

;; everything has to return plist
(defmethod add-worker ((this master) name address port)
  "add new worker to master with name name, available at address:port"
  (setf (workers this)
        (append (workers this)
                (list (list :name name :address address :port port :status 'ok :requests-taken 0)))))

(defmethod update-workers ((this master))
  "Update list of workers with current status of every"
  (declare (optimize (debug 3)))
  (let ((updated-workers-list '()))
    (dolist (worker (workers this))
      (let* ((worker-status (handler-case
                                (alexandria:alist-plist
                                 (cl-json:decode-json-from-string
                                  (drakma:http-request (concatenate 'string
                                                                    "http://"
                                                                    (princ-to-string (getf worker :address))
                                                                    ":"
                                                                    (princ-to-string (getf worker :port))
                                                                    "/status")
                                                       :connection-timeout 1)))
                              (error () nil))))
        (if worker-status
            (setf (getf worker :requests-taken)
                  (getf worker-status :requests-taken)
                  (getf worker :status)
                  (getf worker-status :status))
            (setf (getf worker :requests-taken) 999999
                  (getf worker :status) 'error))
        (setf updated-workers-list (append updated-workers-list (list worker)))))
    (setf (workers this) updated-workers-list)))
                

(defmethod get-worker ((this master))
  "Get least utilized worker address and port"
  (let* ((workers (remove-if-not (lambda (x)
                                   (string= (getf x :status) "ok"))
                                 (workers this)))
         (elected-worker (reduce (lambda (element result)
                                   (if (< (getf element :requests-taken)
                                          (getf result :requests-taken))
                                       element result))
                                 workers)))
    (if elected-worker
        `(:type get-worker
                :status ok
                :worker-name ,(getf elected-worker :name)
                :worker-address ,(getf elected-worker :address)
                :worker-port ,(getf elected-worker :port))
        '(:type get-worker :status error :error no-workers-available))))
        

