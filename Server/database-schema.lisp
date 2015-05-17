(in-package #:pm.db)

(defclass database-item () ())

(clsql:def-view-class user (database-item)
  ((us_UserId :db-kind :key
              :db-constraints :auto-increment
              :type integer
              :accessor id
              :initard :id)
   (us_UserName :type (string 50)
                :db-constraints :not-null
                :accessor name
                :initarg :name)
   (us_UserPassword :type (string 100)
                    :db-constraints :not-null
                    :initarg :password
                    :accessor password)
   (us_UserPasswordReset :type integer
                         :initarg :password-reset
                         :accessor password-reset
                         :nulls-ok t))
  (:base-table "users"))

(clsql:def-view-class friendship (database-item)
  ((uf_Id :type integer
          :db-kind :key
          :db-constraints :not-null
          :initarg :id
          :accessor id)
   (uf_UserId :type integer
              :db-constraints :not-null
              :initarg :id
              :accessor user-id)
   (uf_UserFriendId :type integer
                    :db-constraints :not-null
                    :initard friend-id
                    :accessor friend-id))
  (:base-table "user_friendship"))

(clsql:def-view-class user-info (database-item)
  ((ui_UserId :type integer
              :accessor user-id
              :initarg :user-id)
   (ui_Name :type (string 100)
            :initarg :name
            :accessor name)
   (ui_LastName :type (string 100) 
                :initarg last-name
                :accessor last-name)
   (ui_Sex :type integer
           :initarg sex
           :accessor sex)
   (ui_Status :type integer
              :initarg :user-status
              :accessor user-status)
   (ui_Preferences :type (string 1000)
                   :initarg :preferences
                   :accessor preferences)
   (ui_Description :type (string 1000)
                   :initarg :description
                   :accessor description)
   (ui_GpsX :type float
            :initarg gps-x
            :accessor gps-x)
   (ui_GpsY :type float
            :initarg gps-y
            :accessor gps-y)
   (ui_Age :type integer
           :initart :age
           :accessor age))
  (:base-table "users_info"))

(def-view-class user-prov ()
  ((id :type integer :accessor id)
   (user-id :type integer :accessor user-id)
   (target-id :type integer :accessor target-id)
   (prov-type :type integer :accessor prov-type)
   (message :type string :accessor message)))

(def-view-class user-rating ()
  ((id :type integer :accessor id)
   (user-id :type integer :accessor user-id)
   (rate :type integer :accessor rate)
   (prov-type :type integer :accessor prov-type)
   (comment :type string :accessor comment)
   (from-user-id :type integer :accessor from-user-id)))
