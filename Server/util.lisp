(in-package #:pm.util)

(defun request-valid-p (database username checksum arguments-list)
  "Verify request validity"
  (let ((verify-string
         (apply #'concatenate
                'string
                (append (list username
                              (pm.worker::password
                               (pm.worker::find-item database
                                                     'pm.worker::user
                                                     'pm.worker::username
                                                     username)))
                        arguments-list))))
    
    (string= checksum
             (sha256-sum verify-string))))


(defun sha256-sum (text)
  "generate sha256 sum from text string"
  (ironclad:byte-array-to-hex-string
   (ironclad:digest-sequence :sha256
                             (ironclad:ascii-string-to-byte-array text))))
                                      
