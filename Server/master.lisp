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

;;(defclass master-listener ())


  
