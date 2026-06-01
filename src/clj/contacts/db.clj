(ns contracts.db
  (:require [hugsql.core :as hugsql])
)

(def config
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname "//localhost:5432/contracts"
   :user "myuser"
   :password "myuser"})

(hugsql/def-db-fns "contracts.sql")

(comment
  (create-contracts-table config)
  (get-contracts config)
  (insert-contract config {:first-name "연호" :last-name "장" :email "siabard@gmail.com"})
  (get-contract-by-id config {:id 1})
  (update-contract-by-id config {:first-name "연호" :last-name "장" :email "siabard@cardong.co.kr" :id 1})
)

