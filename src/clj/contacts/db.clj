(ns contacts.db
  (:require [hugsql.core :as hugsql])
)

(def config
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname "//localhost:5432/contacts"
   :user "myuser"
   :password "myuser"})

(hugsql/def-db-fns "contacts.sql")

(comment
  (create-contacts-table config)
  (get-contacts config)
  (insert-contact config {:first-name "연호" :last-name "장" :email "siabard@gmail.com"})
  (get-contact-by-id config {:id 1})
  (update-contact-by-id config {:first-name "연호" :last-name "장" :email "siabard@cardong.co.kr" :id 1})
)

