(in-package #:pm.test)

(defvar *worker*)
(defvar *master*)

(defun test-worker ()
  (setf *worker* (make-instance 'pm.worker:worker
                                :username "pm-worker"
                                :password "5xK1xWKudjT3DXbLVzuMCH5wEHhcLnPdJRtGsoyoccEhc2aSnb8WPfKVEnBijs7y"
                                :database "pm"
                                :hostname "gen2.org")))

(defun stop-worker ()
  (pm.worker::disconnect-db *worker*)
  (pm.api-listener:stop *worker*))

(defun test-master ()
  (setf *master* (make-instance 'pm.master:master))
  (pm.master:add-worker *master* "test2-nonexistant" "8.8.8.8" 1121)
  (pm.master:add-worker *master* "test1" "127.0.0.1" 1121))
