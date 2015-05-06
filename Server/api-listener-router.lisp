(in-package #:pm.api-listener)

(defun get-param (key params)
  "extracts value of certain [key] from [params]"
  (cdr (assoc key params :test #'string=)))

(defun string-fun (name package)
  "returns function named [name] from specified [package]"
  (multiple-value-bind (symbol type)
      (find-symbol (string-upcase name)
                   package)
    (if symbol
        (if (eq type :external)
            (symbol-function symbol)
            (error "symbol ~A is not :EXTERNAL" symbol))
        (error "symbol ~A not present in package ~A" (string-upcase name) package))))

(defgeneric set-routing (api-listener))

(defmethod set-routing ((this api-listener))
  "create ningle routing table - all requests routed to package [package]"
  (let ((app (ningle-app this)))
    ;; meta-route
    (setf (route app "/")
          (format nil "pm.api-listener: routed package: ~A~%" (routed-package this)))
    (setf (route app "/:fn-name" :method :GET)
          #'(lambda (params)
              (handler-case 
                  (cl-json:encode-json-plist-to-string
                   (funcall (string-fun (get-param :fn-name params)
                                        (routed-package this))))
                (error (condition)
                  (cl-json:encode-json-plist-to-string
                  `(:type error :condition ,(format nil "~A" condition)))))))
    (setf (route app "/:fn-name/*" :method :GET)
          #'(lambda (params)
              (handler-case 
                  (cl-json:encode-json-plist-to-string
                   (apply (string-fun (get-param :fn-name params)
                                      (routed-package this))
                                    (split-sequence:split-sequence #\/ (car (get-param :splat params)))))
                (error (condition)
                  (cl-json:encode-json-plist-to-string
                  `(:type error :condition ,(format nil "~A" condition)))))))))
