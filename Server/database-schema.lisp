(in-package #:pm.worker)

(defclass database-item () ())

(clsql:def-view-class group (database-item)
  ((gr_IdGroup :db-kind :key
               :db-constraints :auto-increment
               :type integer
               :accessor id
               :initard :id)
   (gr_Name :type (string 50)
            :db-constraints :not-null
            :accessor name 
            :initarg :name)
   (gr_Description :type (string 500)
                   :db-constraints :not-null
                   :initarg :description 
                   :accessor description))
  (:base-table "groups"))

(clsql:def-view-class user (database-item)
  ((us_UserId :db-kind :key
              :db-constraints :auto-increment
              :type integer
              :accessor id
              :initard :id)
   (us_UserName :type (string 50)
                :db-constraints :not-null
                :accessor username
                :initarg :username)
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
          :db-constraints '(:not-null :auto-increment)
          :initarg :id
          :accessor id)
   (uf_UserId :type integer
              :db-constraints :not-null
              :initarg :id
              :accessor user-id)
   (uf_UserFriendId :type integer
                    :db-constraints :not-null
                    :initarg :friend-id
                    :accessor friend-id))
  (:base-table "user_friendship"))

(clsql:def-view-class user-info (database-item)
  ((ui_UserId :type integer
              :accessor user-id
              :db-kind :key
              :db-constraints :not-null
              :initarg :user-id)
   (ui_Name :type (string 100)
            :initarg :first-name
            :accessor first-name)
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
           :initarg :age
           :accessor age))
  (:base-table "users_info"))

(clsql:def-view-class user-prov (database-item)
  ((pr_Id :type integer
          :db-constraints :auto-increment
          :initarg :id
          :accessor id)
   (pr_UserId :type integer
              :initarg :user-id
              :accessor user-id)
   (pr_TargetUserId :type integer
                    :initarg :target-id
                    :accessor target-id)
   (pr_TypeProv :type integer
                :initarg :prov-type
                :accessor prov-type)
   (pr_Message :type (string 500)
               :initarg :message
               :accessor message))
  (:base-table "user_prov"))

(clsql:def-view-class user-photo (database-item)
  (up_PhotoId :type integer
              :db-constraints :auto-increment
              :initarg :id
              :accessor id)
  (up_UserId :type integer
             :initarg :user-id
             :accessor user-id)
  (up_UserGalleryId :type integer
                    :initarg :gallery-id
                    :accessor gallery-id)
  (:base-table "user_photo"))

(clsql:def-view-class user-gallery (database-item)
  (ug_Id :type integer
         :db-constraints :auto-increment
         :initarg :id
         :accessor id)
  (ug_UserId :type integer
             :initarg :user-id
             :accessor user-id)
  (ug_GalleryName :type (string 50)
                  :initarg :gallery-name
                  :accessor gallery-name)
  (ug_Size :type integer
           :initarg :size
           :accessor size)
  (:base-table "user_gallery"))

(clsql:def-view-class user-rating (database-item)
  ((ur_Id :type integer
          :db-constraints :auto-increment
          :initarg :id
          :accessor id)
   (ur_UserId :type integer
              :initarg :user-id
              :accessor user-id)
   (ur_Rate :type integer
            :initarg :rate
            :accessor rate)
   (ur_Comment :type string
               :initarg :comment
               :accessor comment)
   (ur_FromUserId :type integer
                  :initarg from-user-id
                  :accessor from-user-id))
  (:base-table "user_rating"))
