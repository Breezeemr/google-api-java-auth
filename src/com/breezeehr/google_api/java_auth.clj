(ns com.breezeehr.google-api.java-auth
  (:require [clojure.java.io :as io])
  (:import [com.google.auth.oauth2 GoogleCredentials AccessToken]))

(defn ^AccessToken get-access-token [^GoogleCredentials cred]
  (.refreshIfExpired cred)
  (.getAccessToken cred))

(defn ^String get-oauth-token [^GoogleCredentials cred]
  (.getTokenValue (get-access-token cred)))

(defn application-default-credentials []
  (GoogleCredentials/getApplicationDefault))

(defn service-account-credentials [f]
  (GoogleCredentials/fromStream (io/input-stream f)))

(defn init-client [config]
  (cond-> config
          (nil? (:credential config))
          (assoc :credential (application-default-credentials))))


(defn add-auth [req client]
  (assoc-in
    req
    [:headers "Authorization"]
    (str "Bearer "
         (get-oauth-token (:credential client)))))


(comment
  (def client {:credential (application-default-credentials)})
  (get-oauth-token (:credential client)))
