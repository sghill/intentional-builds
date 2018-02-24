(ns intentional-builds.loggers
    (:require [cuerdas.core :as cuerdas]))

(def frameworks
  {:commons-logging "org.apache.commons.logging"
   :jul             "java.util.logging"
   :log4j           "org.apache.log4j"
   :log4j2          "org.apache.logging.log4j"
   :slf4j           "org.slf4j"})

(defn describe [logger]
    (str (name logger) " (" (get frameworks logger) ")"))

(defn except [selected]
    (remove #(= (key %) selected) frameworks))

(defn longest-name []
    (apply max
        (map 
            #(count (name %))
            (keys frameworks))))

(defn format-excludes [selected]
    (clojure.string/join ",\n          " (map #(val %)
        (except selected))))

