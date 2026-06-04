(ns contacts.routes
  (:require [schema.core :as s]
            [contacts.contacts :refer [get-contacts
                                       get-contact-by-id
                                       create-contact
                                       update-contact
                                       delete-contact]])
  )

(defn dummy [req]
   {:status 200
    :body {:ping "pong"}})

(def ping-routes 
  ["/ping" {:get dummy}])


(def contacts-routes 
  ["/contacts" 
   ["/" {:get get-contacts
         :post {:parameters {:body {:first-name s/Str
                                    :last-name s/Str
                                    :email s/Str}}
                :handler create-contact}}]
   ["/:id" {:parameters {:path {:id s/Int}}
           :get get-contact-by-id
           :put {:parameters {:body {:first-name s/Str
                                     :last-name s/Str
                                     :email s/Str}}
                 :handler update-contact}
           :delete delete-contact}]])

(comment

)


