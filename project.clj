(defproject fitbit-backup-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [clj-http "3.12.0"]
                 [ring/ring-jetty-adapter "1.9.1"]
                 [ring/ring-codec "1.1.3"]
                 [org.clojure/tools.logging "1.1.0"]
                 [org.clojure/data.json "1.1.0"]
                 [org.clojure/tools.reader "1.3.4"]]
  :repl-options {:init-ns fitbit-backup-clj.core}
  :main fitbit-backup-clj.core/-main)
