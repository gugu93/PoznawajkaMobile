(in-package #:pm.worker)

(defclass authenticator ()
    ((database)))

(defgeneric validate-request (authenticator pm.common:api-request))
