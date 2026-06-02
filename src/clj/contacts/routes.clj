(ns contacts.routes
  (:require [contacts.db :as db])
  )

(def ping-routes 
  ["/ping" {:get (fn [req]
                   {:status 200
                    :body {:hello "ok"}})}])
(def contacts-routes 
  ["/contacts" {:get (fn [req]
                        {:status 200
                         :body (db/get-contacts db/config)})}])
