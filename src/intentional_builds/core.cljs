(ns intentional-builds.core
    (:require
      [reagent.core :as r]
      [intentional-builds.loggers :as loggers]))

(defonce selected-logger (r/atom :log4j2))

;; -------------------------
;; Views

(defn logger-item [n]
  (let [id (str "logger-" (name n))]
    [:li {:key id}
      [:input {:type :radio 
               :name :logger 
               :value n
               :defaultChecked (= @selected-logger n)
               :id id 
               :on-click #(swap! selected-logger (fn [] identity n))}]
      [:label {:for id} n]]))

(defn logger-choices []
  [:div
    [:ul
      (doall (map logger-item (keys loggers/frameworks)))]])

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
      <!-- Fail when unwanted packages used. 
           See http://checkstyle.sourceforge.net/config_imports.html#IllegalImport -->
      <property name=\"illegalPkgs\" value=\"
        
        <!-- we only want to use " (loggers/describe @selected-logger) " for logging -->
          "
        (loggers/format-excludes @selected-logger)
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
