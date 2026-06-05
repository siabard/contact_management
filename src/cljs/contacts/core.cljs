(ns contacts.core
  (:require [ajax.core :refer [GET]]
            [helix.core :refer [defnc $ <>]]
            [helix.dom :as d]
            ["react-dom/client" :as rdom]))

(defnc nav []
  (d/nav {:class "py-1 shadow"}
         (d/div {:class "container"}
                (d/h2 {:class "text-xl"} "Contact Book"))))

(defnc app []
  (<>
    ($ nav)))


(defn ^:export init []
  (.render (rdom/createRoot (js/document.getElementById "app")) ($ app)))

(comment
  (GET "http://localhost:4000/api/contacts/" 
       {
        :handler (fn [response] (.log js/console response))
        })
)
