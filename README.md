## How to run

* Run the RemoteWatcheeApp first (Remote Provider)

* Run the ClusterWatcherApp next (Cluster Provider with use-unsafe-remote-features-without-cluster = on)

**Outcome:** 

* ClusterWatcherApp is **able** to receive Terminated signal for RemoteWatcheeApp, which solves the bug.

**Problem:**

* Providing `use-unsafe-remote-features-without-cluster = on` for ClusterWatcherApp does not seem right as it is already has a cluster provider, instead,
the flag should be provided for RemoteWatcheeApp which has a remote provider.

 


