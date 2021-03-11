(ns fitbit-backup-clj.config)

(def config (read-string (slurp "config/config.edn")))

