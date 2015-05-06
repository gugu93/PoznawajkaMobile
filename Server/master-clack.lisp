(in-package #:pm.master)

(defun get-param (key params)
  "extracts value of certain [key] from [params]"
  (cdr (assoc key params :test #'string=)))

(defun string-fun (name package)
  "returns function named [name] from specified [package]"
  (let ((symbol (find-symbol (string-upcase name)
                             package)))
    (if symbol
        (symbol-function symbol)
        (error "Function ~A not present in API" name))))

(defgeneric set-routing (master-listener package))

(defmethod set-routing ((this master-listener) (package symbol))
  "create ningle routing table - all requests routed to package [package]"
  (let ((app (ningle-app this)))
    ;; meta-route
    (setf (route app "/")
          (format nil "pm.master: routed package: ~A~%" package))
    (setf (route app "/:fn-name" :method :GET)
          #'(lambda (params)
              (handler-case 
                  (cl-json:encode-json-plist-to-string
                   (funcall (string-fun (get-param :fn-name params)
                                        package)))
                (error (condition)
                  (cl-json:encode-json-plist-to-string
                  `(:type error :condition ,(format nil "~A" condition)))))))
    (setf (route app "/:fn-name/*" :method :GET)
          #'(lambda (params)
              (handler-case 
                  (cl-json:encode-json-plist-to-string
                   (apply (string-fun (get-param :fn-name params)
                                      package)
                                    (split-sequence:split-sequence #\/ (car (get-param :splat params)))))
                (error (condition)
                  (cl-json:encode-json-plist-to-string
                  `(:type error :condition ,(format nil "~A" condition)))))))))
