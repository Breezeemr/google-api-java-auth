(ns com.breezeehr.google-api.java-auth
  (:import [com.google.auth.oauth2 GoogleCredentials AccessToken]))

(defn ^AccessToken get-access-token [^GoogleCredentials cred]
  (.refreshIfExpired cred)
  (.getAccessToken cred))

(defn ^String get-oauth-token [^GoogleCredentials cred]
  (.getTokenValue (get-access-token cred)))

(defn application-default-credentials []
  (GoogleCredentials/getApplicationDefault))

(comment
  (def client {:credential (application-default-credentials)})
  (get-oauth-token (:credential client)))

