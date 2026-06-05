(ns contacts.core
  (:require [org.httpkit.server :refer [run-server]]
            [reitit.ring :as ring]
            [ring.middleware.cors :refer [wrap-cors]]
            [reitit.ring.middleware.exception :refer [exception-middleware]]
            [reitit.ring.middleware.parameters :refer [parameters-middleware]]
            [reitit.ring.middleware.muuntaja :refer [format-request-middleware
                                                     format-response-middleware
                                                     format-negotiate-middleware]]
            [reitit.ring.coercion :refer [coerce-exceptions-middleware
                                          coerce-request-middleware
                                          coerce-response-middleware]]
            [reitit.coercion.schema]
            [schema.core :as s]
            [muuntaja.core :as m]
            [contacts.routes :refer [ping-routes contacts-routes]]))


(defonce server (atom nil))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))
    

(def app 
  (ring/ring-handler
   (ring/router 
    [["/api"
      ping-routes
      contacts-routes
      ]]
    {:data {:coercion reitit.coercion.schema/coercion                   
            :muuntaja m/instance
            :middleware [[wrap-cors 
                          :access-control-allow-origin [#"http://localhost:4000"]
                          :access-control-allow-methods [:get :post :put :delete]]
                         parameters-middleware
                         format-negotiate-middleware
                         format-response-middleware
                         exception-middleware
                         format-request-middleware
                         coerce-exceptions-middleware
                         coerce-response-middleware
                         coerce-request-middleware]}})
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
        :uri "/api/contacts"})
)
