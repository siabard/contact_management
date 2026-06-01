(ns contacts.core
  (:require [org.httpkit.server :refer [run-server]]
            [reitit.ring :as ring]
            [reitit.ring.middleware.exception :refer [exception-middleware]]
            [reitit.ring.middleware.muuntaja :refer [format-request-middleware
                                                     format-response-middleware
                                                     format-negotiate-middleware]]
            [muuntaja.core :as m]
            [contracts.db :as db]))


(defonce server (atom nil))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))
    

(def app 
  (ring/ring-handler
   (ring/router 
    [["/api"
      ["/ping" {:get (fn [req]
                      {:status 200
                       :body {:hello "ok"}})}]
      ["/contracts" {:get (fn [req]
                            {:status 200
                             :body (db/get-contracts db/config)})}]]]
    {:data {:muuntaja m/instance
            :middleware [
                         format-negotiate-middleware
                         format-response-middleware
                         exception-middleware
                         format-request-middleware]}})
   (ring/routes 
    (ring/redirect-trailing-slash-handler)
    (ring/create-default-handler 
     {:not-found (constantly {:status 404
                              :body "Route not found"})}
     ))))

(defn -main []
  (println "Server started ")
  (reset! server (run-server app {:port 4000})))

(defn restart-server []
  (stop-server)
  (-main))

(comment 
  (restart-server)
  (app {:request-method :get 
        :uri "/api"})
  (app {:request-method :get 
        :uri "/api/ping"})
  (app {:request-method :get 
        :uri "/api/contracts"})
)
