(in-package #:pm.worker)

(defclass worker ()
  ((database)))

(defgeneric accept-connection (worker pm.common:api-request))
(export 'accept-connection)
