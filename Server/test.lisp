(in-package #:pm.test)

(defvar *worker*)

(defun test-worker ()
  (setf *worker* (make-instance 'pm.worker:worker
                                :username "pm-worker"
                                :password "5xK1xWKudjT3DXbLVzuMCH5wEHhcLnPdJRtGsoyoccEhc2aSnb8WPfKVEnBijs7y"
                                :database "pm"
                                :hostname "gen2.org")))

(defun stop-worker ()
  (pm.worker::disconnect-db *worker*)
  (pm.api-listener:stop *worker*))
