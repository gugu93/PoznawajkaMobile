(in-package #:pm.master)

(defclass worker ()
  ((worker-address)
   (worker-port)))

(defgeneric parse-request (worker pm.common:api-request))


(defclass worker-manager ()
  ((workers)))

(defgeneric add-worker (worker-manager worker))
(defgeneric delete-worker (worker-manager worker))
(defgeneric get-worker (worker-manager))


(defclass master-listener ()
  ((ningle-app :accessor ningle-app
               :initform (make-instance '<app>))
   (clack-app :accessor clack-app
              :initform nil)
   ))

(defmethod api-start ((this master-listener))
  (set-routing this :pm.api)
  (setf (clack-app this) (clack:clackup (lack:builder (ningle-app this)))))

(defmethod api-stop ((this master-listener))
  (clack:stop (clack-app this)))
