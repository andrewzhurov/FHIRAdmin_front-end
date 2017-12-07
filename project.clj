(defproject client "0.1.0"
  :dependencies [[org.clojure/clojure "1.9.0-RC1"]
                 [org.clojure/clojurescript "1.9.946"]
                 [reagent "0.7.0" :exclusions [cljsjs/react]]
                 [cljsjs/react-with-addons "15.6.1-0"]

                 [re-frame "0.10.2"]
                 [com.pupeno/free-form "0.6.0"]
                 [bidi "2.1.2"]
                 [kibu/pushy "0.3.8"]
                 [compojure "1.6.0"]
                 [ring/ring-defaults "0.3.1"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-jetty-adapter "1.6.3"]
                 [day8.re-frame/http-fx "0.1.4"]
                 [cljs-ajax "0.7.3"]
                 [environ "1.1.0"]
                 [akiroz.re-frame/storage "0.1.2"]]

  :plugins [[lein-cljsbuild "1.1.4"]]

  :main client.core

  :min-lein-version "2.5.3"

  :source-paths ["src/clj" "src/cljc"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :figwheel {:css-dirs ["resources/public/css"]
             :ring-handler client.core/app}
  :repl-options {:timeout 120000}

  :profiles
  {:dev
   {:source-paths ["dev"]
    :dependencies [[binaryage/devtools "0.9.7"]
                   [com.cemerick/piggieback "0.2.2"]
                   [figwheel-sidecar "0.5.14"]]
    
    :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]
                   :init-ns user}

    :plugins      [[lein-figwheel "0.5.10"]]
    }}

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs" "src/cljc"]
     :figwheel     {:on-jsload "client.core/mount-root"
                    :websocket-host :js-client-host}
     :compiler     {:main                 client.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "/js/compiled/out"
                    :source-map-timestamp true
                    :preloads             [devtools.preload]
                    :external-config      {:devtools/config {:features-to-install :all}}
                    }}

    {:id           "min"
     :source-paths ["src/cljs" "src/cljc"]
     :compiler     {:main            client.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :simple
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false
                    }}
    ]})
