(ns quote-of-the-day.core
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn])
  (:import (java.net ServerSocket)))

(def quotes (edn/read-string
              (slurp
                (io/resource "quotes.edn"))))

(defn listener [port]
  (ServerSocket. port))

(defn accept [fn l]
  (loop []
    (with-open [sock (.accept l)]
      (fn sock))
    (recur)))

(defn handler [sock]
  (let [out (.getOutputStream sock)]
    (spit out (rand-nth quotes))))

(defn -main []
  (println "Running a quote of the day server on port 2020")
  (accept handler (listener 2020)))