
(in-package #:pm.api-listener)

(defclass api-listener ()
  ((listen-port :type interger
                :initarg :listen-port
                :accessor listen-port)
  (routed-package :accessor routed-package
                   :initarg :routed-package
                   :initform nil
                   :type keyword)
   (requests-taken :accessor requests-taken
                   :initform 0
                   :type integer)
   (ningle-app :accessor ningle-app
               :initform (make-instance '<app>))
   (clack-app :accessor clack-app
              :initform nil)))

(defmethod initialize-instance :after ((this api-listener) &key initargs)
  "Validate input parameters and support restarts"
  (declare (ignore initargs))
  (restart-case
      (unless (routed-package this)
        (error "routed-package must be set"))
    (restart (new-package)
      :report "Provide name of package to route"
      :interactive (lambda ()
                     (format t "Enter name of package to route: ")
                     (multiple-value-list (eval (read))))
      (setf (routed-package this) new-package)))
  (start this))

(defmethod start ((this api-listener))
  (set-routing this)
  (setf (clack-app this)
        (clack:clackup (lack:builder (ningle-app this))
                       :port (listen-port this))))

(defmethod stop ((this api-listener))
  (clack:stop (clack-app this)))
