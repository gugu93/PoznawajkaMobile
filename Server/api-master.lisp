(in-package :pm.api-master)

;; everything has to return plist

(defun version ()
  '(:type version :version 0.0.2))

(defun echo (message)
  `(:type echo :message ,message))

(defun test ()
  '(:type test :test "not exported"))