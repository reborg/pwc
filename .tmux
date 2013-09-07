export WORKING_DIR=`pwd`
export PROJECT_NAME=`basename $WORKING_DIR`
cd $WORKING_DIR;
 
# create the session
tmux start-server
tmux new-session -d -s $PROJECT_NAME -n work

# start vim in working dir
tmux select-window -t$PROJECT_NAME:1
tmux send-keys -t$PROJECT_NAME:1 "cd $WORKING_DIR && vim" C-m

# create an horizontal split with a repl
tmux split-window "cd $WORKING_DIR && lein repl"
tmux send-keys "(use 'midje.repl)" "C-m"
tmux send-keys "(autotest)" "C-m"
# tmux resize-pane -D 10
 
tmux attach-session -d -t$PROJECT_NAME
