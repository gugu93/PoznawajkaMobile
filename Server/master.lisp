(in-package #:pm.master)

(defclass worker ()
  ((worker-address)
   (worker-port)))

(defclass master (pm.api-listener:api-listener)
  ((pm.api-listener:listen-port :type interger
                                :initarg :listen-port
                                :initform 1120
                                :accessor listen-port)
  (pm.api-listener:routed-package :accessor routed-package
                                  :initarg :routed-package
                                  :initform :pm.api-master
                                  :type keyword)))

(defclass worker-manager ()
  ((workers)))

(defgeneric add-worker (worker-manager worker))
(defgeneric delete-worker (worker-manager worker))
(defgeneric get-worker (worker-manager))
