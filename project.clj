(defproject liberator-tutorial "0.1.0-SNAPSHOT"
  :description "The Liberator tutorial project"
  :url "https://github.com/cowlike/liberator-tutorial"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-ring "0.8.11"]]
  :ring {:handler liberator-tutorial.core/handler}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [liberator "0.12.2"]
                 [compojure "1.2.0"]
                 [ring/ring-core "1.3.1"]])
