(in-package #:pm.api-listener)

(defun get-param (key params)
  "extracts value of certain [key] from [params]"
  (cdr (assoc key params :test #'string=)))

(defmethod string-http-fun ((this api-listener) params method)
  "Returns function named [name] from specified [package] according to HTTP request. Method might be :get or :post."
  (let ((name (string-upcase
               (concatenate 'string
                            (cond ((eq method :get)
                                   "get-")
                                  ((eq method :post)
                                   "post-")
                                  (t (error "Invalid method: ~A. method must be one of :get :post" method)))
                            (get-param :fn-name params))))
        (package (routed-package this)))
    (multiple-value-bind (symbol type)
        (find-symbol name package)
      (if symbol
        (if (eq type :external)
            (symbol-function symbol)
            (error "symbol ~A is not :EXTERNAL" symbol))
        (error "symbol ~A not present in package ~A" name package)))))

(defmethod params-to-apply-plist ((this api-listener) params)
  "Transform Ningle's params list to property list ready for apply and delete fn-name keyword with optional function arguments"
  (declare (optimize (debug 3)))
  (let ((apply-params
         (alexandria:alist-plist
          (mapcar (lambda (c)
                    (if (keywordp (car c))
                        c
                        (cons (intern (string-upcase (car c)) :keyword)
                              (cdr c))))
                  params)))
        (splat-params
         (split-sequence:split-sequence #\/ (car (get-param :splat params)))))
    (remf apply-params :fn-name)
    (remf apply-params :splat)
    (append (list this)
            (if (car splat-params)
                splat-params)
            apply-params)))
         
(defgeneric set-routing (api-listener))

(defmethod set-routing ((this api-listener))
  "create ningle routing table - all requests routed to package [package]"
  (let ((app (ningle-app this)))
    (setf (route app "/")
          (progn
            (incf (pm.api-listener:requests-taken this))
            (format nil "pm.api-listener: ~A routed package: ~A~%" this (routed-package this))))
    ;;
    ;; handle functions without arguments
    ;;
    (setf (route app "/:fn-name" :method :get)
          #'(lambda (params)
              (incf (pm.api-listener:requests-taken this))
              (handler-case 
                  (cl-json:encode-json-plist-to-string
                   (apply (string-http-fun this params :get)
                          (params-to-apply-plist this params)))
                (error (condition)
                  (cl-json:encode-json-plist-to-string
                  `(:type error :condition ,(format nil "~a" condition))))))
          (route app "/:fn-name" :method :post)
          #'(lambda (params)
              (incf (pm.api-listener:requests-taken this))
              (handler-case 
                  (cl-json:encode-json-plist-to-string
                   (apply (string-http-fun this params :post)
                          (params-to-apply-plist this params)))
                (error (condition)
                  (cl-json:encode-json-plist-to-string
                  `(:type error :condition ,(format nil "~a" condition))))))
          ;;
          ;; handle functions with arguments, splitting with /
          ;;
          (route app "/:fn-name/*" :method :get)
          #'(lambda (params)
              (incf (pm.api-listener:requests-taken this))
              (handler-case 
                  (cl-json:encode-json-plist-to-string
                   (apply (string-http-fun this params :get)
                          (params-to-apply-plist this params)))
                (error (condition)
                  (cl-json:encode-json-plist-to-string
                  `(:type error :condition ,(format nil "~A" condition))))))
          (route app "/:fn-name/*" :method :post)
          #'(lambda (params)
              (incf (pm.api-listener:requests-taken this))
              (handler-case 
                  (cl-json:encode-json-plist-to-string
                   (apply (string-http-fun this params :post)
                          (params-to-apply-plist this params)))
                (error (condition)
                  (cl-json:encode-json-plist-to-string
                  `(:type error :condition ,(format nil "~A" condition)))))))))
