(ns intentional-builds.core
    (:require
      [reagent.core :as r]))

(def loggers
  {:log4j  "org.apache.log4j"
   :log4j2 "org.apache.logging.log4j"
   :slf4j  "org.slf4j"})

(defonce selected-logger (r/atom nil))

;; -------------------------
;; Views

(defn logger-item [n]
  (let [id (str "logger-" (name n))]
    [:li {:key id}
      [:input {:type :radio :name :logger :value n :id id :on-click #(swap! selected-logger (fn [] identity n))}]
      [:label {:for id} n]]))

(defn logger-choices []
  [:div
    [:ul
      (map logger-item (keys loggers))]])

(defn checkstyle-config []
  [:div
    [:pre
      (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>
<!DOCTYPE module PUBLIC
        \"-//Puppy Crawl//DTD Check Configuration 1.3//EN\"
        \"http://www.puppycrawl.com/dtds/configuration_1_3.dtd\">

<module name=\"Checker\">
    <module name=\"TreeWalker\">
        <module name=\"IllegalImport\">
            <property name=\"illegalPkgs\" value=\""
            (clojure.string/join "," (map #(val %) (remove #(= (key %) @selected-logger) loggers)))
            "\"/>
        </module>
    </module>
</module>")
]])

(defn home-page []
  [:div 
    [:h2 "Intentional Builds"]
    [:h3 "Choose a logging framework"]
    (logger-choices)
    [:h3 "Checkstyle config"]
    (checkstyle-config)])



;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
