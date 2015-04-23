# compojure & bidi in combination

An example combining bidi routing with compojure destructuring via rfn, like this:

```clj
(def routes
  ["/" {"say/" {"hi" (rfn [name] {:status 200 :body (str "hi " name)})
                "bye" (rfn [name] {:status 200 :body (str "bye " name)})}}])
```
