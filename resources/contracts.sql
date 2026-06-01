-- :name create-contracts-table
-- :command :execute 
-- ::result :raw
-- :doc create contracts table

CREATE TABLE contracts (
       id SERIAL PRIMARY KEY,
       first_name TEXT,
       last_name TEXT,
       email TEXT,
       created_at TIMESTAMP NOT NULL DEFAULT current_timestamp
);


-- :name get-contracts :? :*
SELECT * FROM contracts;

-- :name get-contract-by-id :? :*
SELECT * FROM contracts
WHERE id = :id;

-- :name insert-contract :insert :*
INSERT INTO contracts(first_name, last_name, email)
VALUEs (:first-name, :last-name, :email)
RETURNING ID;

-- :name update-contract-by-id :! :1
UPDATE contracts
SET first_name=:first-name, last_name=:last-name, email=:email
WHERE id=:id;

-- :name delete-contract-by-id :! :1
DELETE FROM contracts WHERE id=:id;
