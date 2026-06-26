(ns contacts.core
  (:require [ajax.core :refer [GET]]
            [helix.core :refer [defnc $ <> provider]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            ["react-dom/client" :as rdom]
            [contacts.components.nav :refer [nav]]
            [contacts.components.contact-list :refer [contact-list]]
            [contacts.components.contact-form :refer [contact-form]]
            [contacts.state :refer [app-state initial-state app-reducer use-app-state]]
            ))

(defonce root (rdom/createRoot (.getElementById js/document "app")))

(defnc app []
  (let [[state actions] (use-app-state)
        init (:init actions)
        contacts (:contacts state)]
    (hooks/use-effect
     :once
     (GET "http://localhost:4000/api/contacts/" 
          {
           :handler init
           :response-format :json
           :keywords? true
           }))
    (.log js/console (clj->js state))
    (if state
      (<>
       ($ nav)
       (d/div {:class "container pt-4"}
              ($ contact-list {:contacts contacts})
              ($ contact-form {:contact (first contacts)}))))))

(defnc provided-app []
  (provider {:context app-state
             :value (hooks/use-reducer app-reducer initial-state)}
   
   ($ app)))

(defn render []
  (.render root ($ provided-app)))

(defn ^:export init 
  []
  (render))

(defn ^:dev/after-load reload 
  []
  (render))

(comment
  (GET "http://localhost:4000/api/contacts/" 
       {
        :handler (fn [response] (.log js/console response))
        })
)
