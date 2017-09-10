java -classpath "../dist/player-game-1.0-SNAPSHOT-jar-with-dependencies.jar" com.barthezzko.playergame.runner.Runner socket-server 9090 &
echo "Waiting for 5 seconds to init server..."
timeout 5
java -classpath "../dist/player-game-1.0-SNAPSHOT-jar-with-dependencies.jar" com.barthezzko.playergame.runner.Runner socket-client 9090
