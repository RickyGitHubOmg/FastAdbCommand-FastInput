# FastAdbCommand-FastInput
If you think your adb command is slow, you may want to try this!
This project only improves Android's "input" command, you can modify the source the speed up other commands.

HOW IT WORKS
Android "input" command is slow, because "input" runs in java and it takes time to start a java process everytime when you run "input".
To speed up "input", FastInput will be running in background and preload "input" code, and execute input commands at once when it receives signal. As we now just need to send a signal to run a commmand, it does not need to start a slow java process anymore.

HOW TO INSTALL
1). Download fastadbcommand.dex, fastinput
2). adb push fastadbcommand.dex /data/local/tmp/
3). adb push fastinput /data/local/tmp/
4). adb shell chmod +x /data/local/tmp/fastinput

HOW TO USE
1). Run "/data/local/tmp/fastinput start", then it will create a process running in background to receive new input commands.
2). Run your input command by "/data/local/tmp/fastinput YOUR_ARGS". For example, /data/local/tmp/fastinput text "a b c"
