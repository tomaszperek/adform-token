(defproject loginscript "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [http.async.client "1.1.0"]
                 [org.clojure/data.json "0.2.6"]]
  :main ^:skip-aot loginscript.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
