Refutation of silly argument that JVM overhead on RPi3 is too big for simple processing of data from sensors (getting image from URL [camera], scaling it down, adding text, saving it to a file and uploading it to the internet).

- - -

```
pi@raspberrypi:~/scala_sensor_test $ ./launch.sh 
18:20:04.082 Sub-shell is starting JVM
18:20:05.158 Starting sensor processing program.
18:20:05.323 1965ms: Downloading image...
18:20:07.299 50ms: Getting sensor data...
18:20:07.351 1541ms: Scaling from (1920,1080) to (640,480)
18:20:08.894 129ms: Adding text from sensor.
18:20:09.024 3443ms: Saving file...
Image URL: http://sk.uploads.im/mRoSn.png
18:20:12.469 4431ms: Uploading file...
18:20:16.901 Halting.
18:20:16.923 Sub-shell exiting

real    0m12.847s
user    0m11.890s
sys     0m0.320s
```

Raspberry PI - JVM overhead 1.076s which is ~8%.

- - -

For comparison i5 with SSD:

```
$ ./launch.sh 
20:39:49.882 Sub-shell is starting JVM
20:39:50.067 Starting sensor processing program.
20:39:50.097 546ms: Downloading image...
20:39:50.646 50ms: Getting sensor data...
20:39:50.697 151ms: Scaling from (1920,1080) to (640,480)
20:39:50.848 27ms: Adding text from sensor.
20:39:50.876 908ms: Saving file...
Image URL: http://sm.uploads.im/GIatE.png
20:39:51.784 1553ms: Uploading file...
20:39:53.337 Halting.
20:39:53.346 Sub-shell exiting

real    0m3.465s
user    0m2.980s
sys     0m0.124s

```

Which gives ~5% JVM launch overhead (0.185s).

- - -

Conclusion: JVM overhead is in my opinion negligible compared to other required task.
